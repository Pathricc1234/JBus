package TJokordeGdeAgungAbelPutraJBusER;
import java.sql.Timestamp;
import java.text.*;

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
    public static boolean isAvailable(Timestamp departureSchedule, String seat, Bus bus){
        for(Schedule s: bus.schedules){
            if(s.isSeatAvailable(seat) == true && s.departureSchedule.equals(departureSchedule) == true){
                return true;
            }
        }
        return false;
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
}
