package TJokordeGdeAgungAbelPutraJBusER;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.*;
import java.util.List;

public class Payment extends Invoice{
    private int busId;
    public Timestamp departureDate;
    public String busSeat;
    public Payment(int buyerId, int renterId, int busId, String busSeat, Timestamp departureDate){
        super(buyerId,renterId);
        this.busId = busId;
        this.departureDate = departureDate;
        this.busSeat = busSeat;
    }
    public Payment(Account buyer, Renter renter, int busId, String busSeat, Timestamp departureDate){
        super(buyer,renter);
        this.busId = busId;
        this.departureDate = departureDate;
        this.busSeat = busSeat;
    }
    public int getBusId(){
        return busId;
    }
    public static boolean makeBooking(Timestamp departureSchedule, String seat, Bus bus){
        for (Schedule schedule : bus.schedules) {
            if (schedule.departureSchedule.equals(departureSchedule) && schedule.isSeatAvailable(seat)) {
                schedule.bookSeat(seat);
                return true;
            }
        }
        return false;
    }
    public String getDepartureInfo(){
        return busId + " " + departureDate + " " + busSeat;
    }
    public String getTime(){
        SimpleDateFormat SDFormat = new SimpleDateFormat("MM dd, yyyy hh:mm:ss");
        String f_date =  SDFormat.format(departureDate.getTime());
        return f_date;
    }
    public static Schedule availableSchedule(Timestamp departureSchedule, String seat, Bus bus) {
        Predicate<Schedule> condition = schedule -> schedule.departureSchedule.equals(departureSchedule) && schedule.isSeatAvailable(seat);
        return Algorithm.find(bus.schedules, condition);
    }
    public static Schedule availableSchedule(Timestamp departureSchedule, List<String> seats, Bus bus) {
        for (Schedule schedule : bus.schedules) {
            if (schedule.departureSchedule.equals(departureSchedule)) {
                return schedule;
            }
        }
        return null;
    }
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
