package TJokordeGdeAgungAbelPutraJBusER;
import java.util.Calendar;
import java.sql.Timestamp;
import java.util.*;

public class JBus{
    public static void main(String[] args) {
    }
    private static void testExist(Integer[] t) {
        int valueToCheck = 67;
        Predicate<Integer> p = (e) -> { return e==67; };
        boolean result3 = Algorithm.exists(t, p);
        if (result3) {
            System.out.println(valueToCheck + " exist in the array.");
        } else {
            System.out.println(valueToCheck + " doesn't exists in the array.");
        }
    }
    public static void testCount(Integer[] t) {
        int valueToCount = 18;
        int result = Algorithm.count(t, valueToCount);
        System.out.println("Number " + valueToCount + " appears " + result + " times");
    }

    
    public static Bus createBus() {
        Price price = new Price(750000, 5);
        Bus bus = new Bus("Netlab Bus", Facility.LUNCH, price, 12, BusType.REGULER, City.BANDUNG, new Station(1, "Depok Terminal", City.DEPOK, "Jl. Margonda Raya"), new Station(2, "Halte UI", City.JAKARTA, "Universitas Indonesia"));
        return bus;
    }
}
