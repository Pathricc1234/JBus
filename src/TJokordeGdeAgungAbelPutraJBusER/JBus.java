package TJokordeGdeAgungAbelPutraJBusER;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class JBus {
    public static void main(String[] args) {
        try {
            String filepath = "C:\\Users\\ASUS\\OneDrive\\Documents\\Kuliah\\Tugas\\Semester 3\\Praktikum OOP\\CS\\Modul 1\\JBus\\data\\busses_CS.json";
            JsonTable<Bus> busList = new JsonTable<>(Bus.class,filepath);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static List<Bus> filteredByDeparture(List<Bus> buses, City departure, int page, int pageSize) {
        return buses.stream()
                .filter(bus->bus.departure.city == departure)
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public static List<Bus> filteredByPrice(List<Bus> buses, int minPrice, int maxPrice) {
        return buses.stream()
            .filter(bus -> bus.price.price >= minPrice && bus.price.price <= maxPrice)
            .collect(Collectors.toList());
    }

    public static Bus filterBusId(List<Bus> buses, int id){
        for(Bus bus : buses) {
            if (bus.id == id) {
                return bus;
            }
        }
        return null;
    }

    public static List<Bus> filteredByDepartureAndArrival(List<Bus> buses, City departure, City arrival, int page, int pageSize) {
        return buses.stream()
                .filter(bus->bus.departure.city == departure && bus.arrival.city == arrival)
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}
