package TJokordeGdeAgungAbelPutraJBusER;

import java.util.*;

public class Algorithm {

    private Algorithm(){

    }

    public static <T> List<T> collect(Iterable<T> iterable, Predicate<T> predicate){
        final Iterator<T> it = iterable.iterator();
        return collect(it, predicate);
    }

    public static <T> List<T> collect(Iterable<T> iterable, T value){
        final Iterator<T> it = iterable.iterator();
        return collect(it, value);
    }

    public static <T> List<T> collect(T[] array, T value){
        final Iterator<T> it = Arrays.stream(array).iterator();
        return collect(it, value);
    }

    public static <T> List<T> collect(T[] array, Predicate<T> predicate){
        final Iterator<T> it = Arrays.stream(array).iterator();
        return collect(it, predicate);
    }


    public static <T> List<T> collect(Iterator<T> iterator, T value){
        final  Predicate<T> pred = value::equals;
        return collect(iterator, pred);
    }

    public static <T> List<T> collect(Iterator<T> iterator, Predicate<T> predicate){
        List<T> list = new ArrayList<>();
        while(iterator.hasNext()){
            T current = iterator.next();
            if(predicate.predicate(current)){
                list.add(current);
            }
        }
        return list;
    }

    public static <T> int count(Iterator<T> iterator, T value){
        final  Predicate<T> pred = value::equals;
        return count(iterator, pred);
    }

    public static <T> int count(T[] array, T value){
        final Iterator<T> it = Arrays.stream(array).iterator();
        return count(it, value);
    }

    public static <T> int count(Iterator<T> iterator, Predicate<T> predicate) {
        int count = 0;
        while (iterator.hasNext()) {
            T current = iterator.next();
            if (predicate.predicate(current)) {
                count++;
            }
        }
        return count;
    }

    public static <T> int count(T[] array, Predicate<T> predicate){
        final Iterator<T> it = Arrays.stream(array).iterator();
        return count(it, predicate);
    }

    public static <T> int count(Iterable<T> iterable, T value){
        final Iterator<T> it = iterable.iterator();
        return count(it, value);
    }

    public static <T> int count(Iterable<T> iterable, Predicate<T> predicate) {
        int count = 0;
        Iterator<T> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            T current = iterator.next();
            if (predicate.predicate(current)) {
                count++;
            }
        }
        return count;
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
        final Iterator<T> it = iterable.iterator();
        return find(it, predicate);
    }

    public static <T> T find(T[] array, T value){
        return find(Arrays.stream(array).iterator(),value);
    }

    public static <T> T find(T[] array, Predicate<T> predicate){
        return find(Arrays.stream(array).iterator(),predicate);
    }

    public static <T> T find(Iterator<T> iterator, T value){
        final Predicate<T> pred = value::equals;
        return find(iterator,pred);
    }

    public static <T> T find(Iterable<T> iterable, T value){
        return find(iterable.iterator(),value);
    }

    public static <T> T find(Iterator<T> iterator, Predicate<T> predicate){
        while(iterator.hasNext()){
            T current = iterator.next();
            if(predicate.predicate(current))
                return current;
        }
        return null;
    }

    public static <T> List<T> paginate(T[] array, int page, int pageSize, Predicate<T> predicate){
        List<T> result = new ArrayList<>();
        for(int i = (page * pageSize); i < pageSize;i++){
            if(predicate.predicate(array[i])){
                result.add(array[i]);
            }
        }
        return result;
    }

    public static <T> List<T> paginate(Iterable<T> iterable, int page, int pageSize, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        int startIndex = page * pageSize;
        int endIndex = startIndex + pageSize;
        int index = 0;

        for (T current : iterable) {
            if (index >= startIndex && predicate.predicate(current)) {
                result.add(current);
                if (result.size() >= pageSize) {
                    break;
                }
            }
            index++;
        }
        return result;
    }

    public static <T> List<T> paginate(Iterator<T> iterator, int page, int pageSize, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        int startIndex = page * pageSize;
        int endIndex = startIndex + pageSize;

        while (iterator.hasNext()) {
            T item = iterator.next();
            if (predicate.predicate(item)) {
                result.add(item);
                if (result.size() >= pageSize) {
                    break;
                }
            }
        }
        return result;
    }
}
