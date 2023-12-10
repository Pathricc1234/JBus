package com.TJokordeGdeAgungAbelPutraJBusER;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Station class adalah untuk melakukan serialisasi
 *
 * @author Tjokorde Gde Agung Abel Putra
 * @version 1.0
 */
public class Serializable implements Comparable<Serializable> {
    public final int id;
    private static HashMap<Class<?>, Integer> mapCounter = new HashMap<Class <?>, Integer>();
    protected Serializable(){
        Integer counter = mapCounter.get(getClass());
        counter = counter == null ? 0 : counter + 1;
        mapCounter.put(getClass(), counter);
        this.id = counter;
    }

    public static <T> Integer getLastAssignedId(Class<T> getter ){
        return mapCounter.get(getter);
    }

    public static <T> Integer setLastAssignedId(Class<T> setter, int number){
        return mapCounter.put(setter, number);
    }

    public int compareTo(Serializable temp){
        return ((Integer)this.id).compareTo(temp.id);
    }

    public boolean equals(Serializable temp){
        return temp.id == this.id;
    }

    public boolean equals(Object object){
        return object instanceof Serializable && ((Serializable) object).id == this.id;
    }

}

