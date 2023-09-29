package TJokordeGdeAgungAbelPutraJBusER;
import java.util.Calendar;
import java.text.*;

public class Payment extends Invoice{
    private int busId;
    public Calendar departureDate;
    public String busSeat;
    public Payment(int id, int buyerId, int renterId, int busId, String busSeat){
        super(id,buyerId,renterId);
        this.busId = busId;
        time.add(Calendar.DATE, 2);
        this.departureDate = time;
        this.busSeat = busSeat;
    }
    public Payment(int id, Account buyer, Renter renter, int busId, String busSeat){
        super(id,buyer,renter);
        this.busId = busId;
        time.add(Calendar.DATE, 2);
        this.departureDate = time;
        this.busSeat = busSeat;
    }
        public int getBusId(){
        return busId;
    }
    public String getDepartureInfo(){
        return busId + " " + departureDate.getInstance() + " " + busSeat;
    }
    public String getTime(){
        SimpleDateFormat SDFormat = new SimpleDateFormat("MM dd, yyyy hh:mm:ss");
        String f_date =  SDFormat.format(departureDate.getTime());
        return f_date;
    }
}
