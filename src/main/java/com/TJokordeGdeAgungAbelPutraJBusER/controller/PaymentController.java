package com.TJokordeGdeAgungAbelPutraJBusER.controller;

import com.TJokordeGdeAgungAbelPutraJBusER.*;
import com.TJokordeGdeAgungAbelPutraJBusER.dbjson.JsonAutowired;
import com.TJokordeGdeAgungAbelPutraJBusER.dbjson.JsonTable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * PaymentController untuk melakukan backend yang berhubungan dengan class {@link Payment} payment
 * @author Tjokorde Gde Agung Abel Putra
 * @version 1.0
 */
@RestController
@RequestMapping("/payment")
public class PaymentController implements BasicGetController<Payment> {
    public JsonTable<Payment> paymentsTable;

    public PaymentController() {
        try {
            this.paymentsTable = new JsonTable<>(Payment.class, "src\\main\\java\\com\\TJokordeGdeAgungAbelPutraJBusER\\json\\payment.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @JsonAutowired(value = Account.class, filepath = "src\\main\\java\\com\\TJokordeGdeAgungAbelPutraJBusER\\json\\payment.json")
    private Payment payment;

    public JsonTable<Payment> getJsonTable() {
        return paymentsTable;
    }

    /**
     * Melakukan booking pada bus
     * @param buyerId ID dari pembeli
     * @param renterId ID dari akun pemiliki bus
     * @param busId ID dari bus
     * @param busSeats kursi yang dipesan
     * @param departureDate waktu keberangkatan
     * @return membuat class Payment dan menyimpan pada JSON
     * @throws ParseException ketika terjadi kegagalan
     */
    @RequestMapping(value = "/makeBooking", method = RequestMethod.POST)
    public BaseResponse<Payment> makeBooking(@RequestParam int buyerId,
                                             @RequestParam int renterId,
                                             @RequestParam int busId,
                                             @RequestParam List<String> busSeats,
                                             @RequestParam String departureDate) throws ParseException {
        AccountController accountController = new AccountController();
        BusController busController = new BusController();
        List<Account> allAcc = accountController.getJsonTable();
        List<Bus> allBus = busController.getJsonTable();
        boolean isAccExist = allAcc.stream().anyMatch(account -> account.id == buyerId);
        boolean isBusExist = allBus.stream().anyMatch(bus -> bus.id == busId);
        if (isAccExist && isBusExist) {
            Account userAcc = allAcc.stream()
                    .filter(account -> account.id == buyerId)
                    .findFirst()
                    .orElse(null);
            Bus userBus = allBus.stream()
                    .filter(bus -> bus.id == busId)
                    .findFirst()
                    .orElse(null);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            java.util.Date parsedDate = dateFormat.parse(departureDate);
            Timestamp newPaymentTime = new Timestamp(parsedDate.getTime());
            boolean isScheduleExist = allBus.stream()
                    .filter(bus -> bus.id == busId)
                    .flatMap(bus -> bus.schedules.stream())
                    .anyMatch(schedule -> schedule.departureSchedule.equals(newPaymentTime));
            if (isScheduleExist) {
                Payment newPayment = new Payment(buyerId, renterId, busId, busSeats, newPaymentTime);
                paymentsTable.add(newPayment);
                try {
                    paymentsTable.writeJson();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return new BaseResponse<>(true, "Pembayaran berhasil mohon tunggu", newPayment);
            }
        }
        return new BaseResponse<>(false, "Tidak ditemukan akun buyer atau bus", null);
    }

    /**
     * Menerima pembayaran
     * @param id ID dari pembayaran
     * @return mengeset status dari payment menjadi succesfull
     */
    @RequestMapping(value = "/{id}/accept", method = RequestMethod.POST)
    public BaseResponse<Payment> accept(@PathVariable int id) {
        Payment payment = paymentsTable.stream()
                .filter(pay -> pay.id == id)
                .findFirst()
                .orElse(null);
        if (payment != null) {
            payment.status = Invoice.PaymentStatus.SUCCESS;
            try {
                paymentsTable.writeJson();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new BaseResponse<>(true, "Pembayaran berhasil", payment);
        }
        return new BaseResponse<>(false, "Anda Belum melakukan pembayaran", null);
    }

    /**
     * Membatalkan transaski
     * @param id ID dari payment
     * @return mengeset status pembayaran menjadi failed
     */
    @RequestMapping(value = "/{id}/cancel", method = RequestMethod.POST)
    public BaseResponse<Payment> cancel(@PathVariable int id) {
        Payment payment = paymentsTable.stream()
                .filter(pay -> pay.id == id)
                .findFirst()
                .orElse(null);
        if (payment != null) {
            payment.status = Invoice.PaymentStatus.FAILED;
            try {
                paymentsTable.writeJson();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return new BaseResponse<>(true, "Pembayaran dibatalkan", payment);
        }
        return new BaseResponse<>(false, "Anda belum melakukan pembayaran", null);
    }

    /**
     * Memberikan rating pada bus
     * @param id ID pembayaran
     * @param rating Rating dari bus
     * @return memberikan Rating pada Payment
     */
    @PostMapping("/setRating")
    public BaseResponse<Payment> setRating(@RequestParam int id, @RequestParam Invoice.BusRating rating){
        Payment payment = paymentsTable.stream()
                .filter(pay -> pay.id == id)
                .findFirst()
                .orElse(null);
        assert payment != null;
        payment.rating = rating;
        try {
            paymentsTable.writeJson();
            return new BaseResponse<>(true,"Rating berhasil ditambahkan",payment);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Mendapatkan informasi payment pada akun tertentu
     * @param accountId ID dari akun
     * @return semua informasi payemnt yang sesuai dengan ID akun
     */
    @GetMapping("/getMyPayment")
    public List<Payment> getUserPayment(@RequestParam int accountId) {
        return Algorithm.<Payment>collect(getJsonTable(), b -> b.buyerId == accountId);
    }

    /**
     * Mendapatkan semua informasi kursi pada payment
     * @param paymentId ID dari payment
     * @return semua kursi pada payment sesuai ID
     */
    @GetMapping("/getSeats")
    public List<String> getSeats(@RequestParam int paymentId){
        Payment payment = paymentsTable.stream()
                .filter(pay -> pay.id == paymentId)
                .findFirst()
                .orElse(null);
        assert payment != null;
        return payment.busSeat;
    }
}
