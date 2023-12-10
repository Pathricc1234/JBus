package com.TJokordeGdeAgungAbelPutraJBusER;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.*;
import java.util.List;

/**
 * Payment Class merepresentasikan pembelian dari bus
 *
 * @author Tjokorde Gde Agung Abel Putra
 * @version 1.0
 */
public class Payment extends Invoice{
    /**
     * id dari Bus yang dipilih
     */
    public int busId;
    /**
     * Waktu keberangkatan dari bus
     */
    public Timestamp departureDate;
    /**
     * daftar kursi yang dipesan
     */
    public List<String> busSeat;
    public Payment(int buyerId, int renterId, int busId, List<String> busSeat, Timestamp departureDate){
        super(buyerId,renterId,departureDate);
        this.busId = busId;
        this.departureDate = departureDate;
        this.busSeat = busSeat;
    }

    /**
     * method untuk mengetahui apakah schedule ada
     * @param departureSchedule tanggal keberangkatan yang dipilih
     * @param seat kursi yang dipilih
     * @param bus bus yang dipilih
     * @return schedule
     */
    public static Schedule availableSchedule(Timestamp departureSchedule, String seat, Bus bus) {
        Predicate<Schedule> condition = schedule -> schedule.departureSchedule.equals(departureSchedule) && schedule.isSeatAvailable(seat);
        return Algorithm.find(bus.schedules, condition);
    }

    /**
     * method untuk mengecek apakah kursi ada dan membooking
     * @param departureSchedule waktu keberangkatan
     * @param seats daftar kursi yang dipilih
     * @param bus bus yang dipilih
     * @return true jika booking berhasil
     */
    public static boolean makeBooking(Timestamp departureSchedule, List<String> seats, Bus bus) {
        boolean booked = false;
        for (String seat : seats) {
            Schedule schedule = availableSchedule(departureSchedule, seat, bus);
            if (schedule != null) {
                schedule.bookSeat(seat);
                booked = true;
            }
        }
        return booked;
    }
}
