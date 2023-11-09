package com.TJokordeGdeAgungAbelPutraJBusER;
import java.sql.Timestamp;
import java.util.*;
import java.text.SimpleDateFormat;

public class Schedule{
    public Timestamp departureSchedule;
    Map<String, Boolean> seatAvailability;

    public Schedule(Timestamp departureSchedule, int numberOfSeats){
        this.departureSchedule = departureSchedule;
        seatAvailability = new HashMap<>();
        initializeSeatAvailability(numberOfSeats);
    }
    private void initializeSeatAvailability(int numberOfSeats){
        for(int seatNumber = 1; seatNumber <= numberOfSeats; seatNumber++){
            String sn = seatNumber < 10 ? "0"+seatNumber : ""+seatNumber;
            seatAvailability.put("ER" + sn, true);
        }
    }
    public boolean isSeatAvailable(String seat){
        Boolean availability = seatAvailability.get(seat);
        if (availability != null && availability == true) {
            return true;
        }
        else{
            return false;
        }
    }
    public void bookSeat(String seat){
        seatAvailability.put(seat, false);
    }
    public void printSchedule(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy HH:mm:ss");
        String formattedDepartureSchedule = dateFormat.format(this.departureSchedule.getTime());
        System.out.println("Tanggal keberangkatan: " + formattedDepartureSchedule);
        System.out.println("Daftar kursi dan ketersediaan kursinya: ");
        int maxSeatsPerRow = 4;
        int currentSeat = 1;
        for (String seat : this.seatAvailability.keySet()) {
            String symbol = this.seatAvailability.get(seat)? "O" : "X";
            System.out.print(seat + " : " + symbol + "\t");
            if (currentSeat % maxSeatsPerRow == 0) {
                System.out.println();
            }
            currentSeat++;
        }
        System.out.println("\n");
    }
    public void bookSeat(List<String> seat) {
        for (String seats : seat) {
            if (seatAvailability.containsKey(seats) && seatAvailability.get(seats)) {
                seatAvailability.put(seats, false);
            }
        }
    }
    public boolean isSeatAvailable(List<String> seats) {
        for (String seat : seats) {
            Boolean available = seatAvailability.get(seat);
            if (available == null || !available) {
                return false;
            }
        }
        return true;
    }
    public String toString(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatted = dateFormat.format(this.departureSchedule.getTime());
        int occupied = Algorithm.count(seatAvailability.values().iterator(), false);
        int total = seatAvailability.size();
        return "Schedule: " + formatted + "\n" + "Occupied: " + occupied + "/" + total;
    }
}
