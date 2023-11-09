package com.TJokordeGdeAgungAbelPutraJBusER;
import java.util.*;
import java.sql.Timestamp;

public class BookingThread extends Thread{
    private Bus bus;
    private Timestamp timestamp;
    public BookingThread(String name, Bus bus, Timestamp timestamp){
        super(name);
        this.bus = bus;
        this.timestamp = timestamp;
        this.start();
    }

    public void run() {
        long Id = Thread.currentThread().getId();
        String threadNum = Thread.currentThread().getName();
        System.out.println(threadNum + " ID: " + Id + " is running");
        synchronized (bus) {
            boolean booked = Payment.makeBooking(timestamp, List.of("ER01"), bus);
            if (booked) {
                System.out.println(threadNum + " Berhasil Melakukan Booking");
            } else {
                System.out.println(threadNum + " Gagal Melakukan Booking");
            }
        }
    }
}
