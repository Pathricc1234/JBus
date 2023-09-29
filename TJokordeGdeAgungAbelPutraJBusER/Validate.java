package TJokordeGdeAgungAbelPutraJBusER;
import java.util.ArrayList;

public class Validate {
    public static ArrayList<Price> filter(Price[] list, int value, boolean less) {
        ArrayList<Price> filtered = new ArrayList<Price>();
        for (Price price : list) {
            if (less && price.price < value) {
                filtered.add(price);
            } else if (!less && price.price > value) {
                filtered.add(price);
            }
        }
        return filtered;
    }
}
