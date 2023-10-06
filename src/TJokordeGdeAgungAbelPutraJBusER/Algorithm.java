package TJokordeGdeAgungAbelPutraJBusER;

import java.util.*;

public class Algorithm {

    public static <T> List<T> collect(Iterable<T> iterable, T value){
        final Iterator<T> it = iterable.iterator();
        return collect(it, value);
    }

    public static <T> List<T> collect(T[] array, T value){
        final Iterator<T> it = Arrays.stream(array).iterator();
        return collect(it, value);
    }

    public static <T> List<T> collect(Iterator<T> iterator, T value){
        final  Predicate<T> pred = value::equals;
        return collect(iterator, pred);
    }


    public static <T> int count(Iterator<T> iterator, Predicate<T> predicate){
        final Iterator<T> it = iterable.iterator();
        return count(it, predicate);
    }


    public static <T> int count(Iterable<T> iterable, T value){
        final Iterator<T> it = iterable.iterator();
        return count(it, value);
    }


    public static <T> boolean exists(T[] array, T value){
        final Iterator<T> it = Arrays.stream(array).iterator();
        return exists(it, value);
    }

    public static <T> boolean exists(Iterable<T> iterable, T value){
        final Iterator<T> it = iterable.iterator();
        return exists(it, value);
    }

    public static <T> boolean exists(Iterator<T> iterator, T value){
        final  Predicate<T> pred = value::equals;
        return exists(iterator, pred);
    }

    public static <T> boolean exists(T[] array, Predicate<T> pred){
        final Iterator<T> it = Arrays.stream(array).iterator();
        return exists(it, pred);
    }

    public static <T> boolean exists(Iterable<T> iterable, Predicate<T> pred){
        final Iterator<T> it = iterable.iterator();
        return exists(it, pred);
    }

    public static <T> boolean exists(Iterator<T> iterator, Predicate<T> pred){
        while(iterator.hasNext()){
            T current = iterator.next();
            if(pred.predicate(current))
                return true;
        }
        return false;
    }

    public static <T> T find(Iterable<T> iterable, Predicate<T> predicate){
        return find(iterable.iterable(),predicate);
    }

    public static <T> T find(T[] array, T value){
        return find(Arrays.stream(array).iterator(),value);
    }

    public static <T> T find(T[] array, Predicate<T> predicate){
        return find(Arrays.stream(array).iterator(),value);
    }

    public static <T> T find(Iterator<T> iterator, T value){
        final Predicate<T> pred = value::equals;
        return find(iterator,pred);
    }

    public static <T> T find(Iterable<T> iterable, T value){
        return find(iterable.iterator(),value);
    }
}
