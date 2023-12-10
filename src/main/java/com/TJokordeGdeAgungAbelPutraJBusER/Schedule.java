    package com.TJokordeGdeAgungAbelPutraJBusER;
    import java.sql.Timestamp;
    import java.util.*;
    import java.text.SimpleDateFormat;

    public class Schedule{
        public Timestamp departureSchedule;
        public Map<String, Boolean> seatAvailability;

        public Schedule(Timestamp departureSchedule, int numberOfSeats){
            this.departureSchedule = departureSchedule;
            seatAvailability = new LinkedHashMap<>();
            initializeSeatAvailability(numberOfSeats);
        }
        private void initializeSeatAvailability(int numberOfSeats) {
            for (int seatNumber = 1; seatNumber <= numberOfSeats; seatNumber++) {
                String sn = seatNumber < 10 ? "0" + seatNumber : "" + seatNumber;
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
        public String toString() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(departureSchedule);
            int occupied = Algorithm.count(seatAvailability.values().iterator(), false);
            int total = seatAvailability.size();
            return "Schedule: " + formattedDate + "\n" + "Occupied: " + occupied + "/" + total;
        }
    }
