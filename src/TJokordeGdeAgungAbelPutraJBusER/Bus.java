package TJokordeGdeAgungAbelPutraJBusER;
import java.util.*;
import java.util.Calendar;
import java.sql.Timestamp;

public class Bus extends Serializable implements FileParser{
    public int capacity;
    public Facility facility;
    public String name;
    public Price price;
    public BusType busType;
    public City city;
    public Station departure;
    public Station arrival;
    List <Schedule> schedules = new ArrayList<>();
    
    public Bus(int id,String name, Facility facility, Price price, int capacity, BusType busType, City city, Station departure, Station arrival){
        super(id);
        this.name = name;
        this.facility = facility;
        this.price = price;
        this.capacity = capacity;
        this.busType = busType;
        this.city = city;
        this.departure = departure;
        this.arrival = arrival;
    }
    public Object write(){
        return null;
    }
    public boolean read(String content){
        return false;
    }
    public void addSchedule(Timestamp schedule) { 
        schedules.add(new Schedule(schedule, this.capacity));
    }
    public String toString(){
        return name + facility + price + capacity + busType + city + departure + arrival;
    }
    public int getId() {
        return id;
    }
}
