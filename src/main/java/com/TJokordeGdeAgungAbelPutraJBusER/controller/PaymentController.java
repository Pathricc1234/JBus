package com.TJokordeGdeAgungAbelPutraJBusER.controller;

import com.TJokordeGdeAgungAbelPutraJBusER.Account;
import com.TJokordeGdeAgungAbelPutraJBusER.Bus;
import com.TJokordeGdeAgungAbelPutraJBusER.Payment;
import com.TJokordeGdeAgungAbelPutraJBusER.Station;
import com.TJokordeGdeAgungAbelPutraJBusER.dbjson.JsonAutowired;
import com.TJokordeGdeAgungAbelPutraJBusER.dbjson.JsonTable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController implements BasicGetController<Payment>{
    public JsonTable<Payment> paymentsTable;
    @JsonAutowired(value = Payment.class,filepath = "src\\main\\java\\com\\TJokordeGdeAgungAbelPutraJBusER\\json\\payment.json")
    private Payment payment;
    public PaymentController(){
        try {
            this.paymentsTable = new JsonTable<>(Payment.class,"src\\main\\java\\com\\TJokordeGdeAgungAbelPutraJBusER\\json\\payment.json");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public JsonTable<Payment> getJsonTable(){
        return paymentsTable;
    }
    @RequestMapping(value = "/makeBooking", method = RequestMethod.POST)
    public BaseResponse<Payment> makeBooking(@RequestParam int buyerId,
                                             @RequestParam int renterId,
                                             @RequestParam int busId,
                                             @RequestParam List<String> busSeats,
                                             @RequestParam String departureDate){
        AccountController accountController = new AccountController();
        BusController busController = new BusController();
        List<Account> allAcc = accountController.getJsonTable();
        List<Bus> allBus = busController.getJsonTable();
        boolean isAccExist = allAcc.stream().anyMatch(account -> account.id == buyerId);
        boolean isBusExist = allBus.stream().anyMatch(bus -> bus.id == busId);
        if(isAccExist && isBusExist){
            Account userAcc = allAcc.stream()
                    .filter(account -> account.id == buyerId)
                    .findFirst()
                    .orElse(null);

            Bus userBus = allBus.stream()
                    .filter(bus -> bus.id == busId)
                    .findFirst()
                    .orElse(null);
            if(userAcc.balance > userBus.price.price){
                Timestamp selectedDate = Timestamp.valueOf(departureDate);
                boolean isScheduleExist = allBus.stream().anyMatch(bus -> bus.departure.equals(selectedDate));
                if(isScheduleExist){
                    Payment newPayment = new Payment(buyerId,renterId,busId,busSeats,selectedDate);
                    paymentsTable.add(newPayment);
                    try {
                        paymentsTable.writeJson();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    return new BaseResponse<>(true,"Pembayaran berhasil mohon tunggu",newPayment);
                }
                else{
                    return new BaseResponse<>(false,"Tidak ditemukan Jadwal",null);
                }
            }
            else{
                return new BaseResponse<>(false,"Saldo tidak cukup",null);
            }
        }
        return new BaseResponse<>(false,"Tidak ditemukan akun buyer atau bus",null);
    }
    @RequestMapping(value = "/{id}/accept",method = RequestMethod.POST)
    public BaseResponse<Payment> accept(@PathVariable int id){
        Payment payment = paymentsTable.get(id);
        if(payment != null){
            paymentsTable.add(payment);
            try {
                paymentsTable.writeJson();
            }
            catch (IOException e){
                e.printStackTrace();
            }
            return new BaseResponse<>(true,"Pembayaran berhasil",payment);
        }
        return new BaseResponse<>(true,"Anda Belum melakukan pembayaran",null);
    }

    @RequestMapping(value = "/{id}/cancel", method = RequestMethod.POST)
    public BaseResponse<Payment> cancel(@PathVariable int id){
        Payment payment = paymentsTable.get(id);
        if (payment != null){
            BaseResponse<Payment> response = new BaseResponse<>(true,"Pembayaran dibatalkan",payment);
            paymentsTable.remove(id);
            return response;
        }
        return new BaseResponse<>(false,"Anda belum melakukan pembayaran",null);
    }
}