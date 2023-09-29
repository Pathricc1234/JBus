package TJokordeGdeAgungAbelPutraJBusER;
import java.util.Calendar;
import java.util.*;

public class Schedule{
    public Calendar departureSchedule;
    Map <String, Boolean> seatAvailability;
    public Schedule(Calendar departureSchedule, int numberOfSeats){
        this.departureSchedule = departureSchedule;
        initializeSeatAvailability(numberOfSeats);
    }
    private void initializeSeatAvailability(int numberOfSeats){
        Map<String, Boolean> seatAvailability = new HashMap<String, Boolean>();
        for(int i = 1; i <= numberOfSeats; i++){
            seatAvailability.put("ER" + i, true);
        }
    }
}
