package TJokordeGdeAgungAbelPutraJBusER;
import java.util.*;

public class Serializable implements Comparable<Serializable>{
    public final int id;
    public static HashMap<Class<?>,Integer> mapCounter = new HashMap<>();

    protected Serializable() {
        Class<?> thisClass = this.getClass();
        if (mapCounter.containsKey(thisClass)) {
            id = mapCounter.get(thisClass) + 1;
        } else {
            id = 1;
        }
        mapCounter.put(thisClass, id);
    }

    public static <T> Integer setLastAssignedId (Class <T> thisClass, int id){
        return mapCounter.put(thisClass, id);
    }
    public static <T> Integer getLastAssignedId(Class<T> thisClass){
        return mapCounter.get(thisClass);
    }
    @Override
    public int compareTo(Serializable serial) {
        return Integer.compare(this.id, serial.id);
    }
    public boolean equals(Serializable serial){
        if(serial.id == this.id){
            return true;
        }
        return false;
    }
    public boolean equals(Object object){
        if(object instanceof Serializable){
            if(this.id == ((Serializable) object).id){
                return true;
            }
        }
        return false;
    }
}
