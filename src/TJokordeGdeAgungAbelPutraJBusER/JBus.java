package TJokordeGdeAgungAbelPutraJBusER;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class JBus {
    public static void main(String[] args) {
        try {
            JsonTable<Account> tableAccount = new JsonTable<>(Account.class, "C:\\Users\\ASUS\\OneDrive\\Documents\\Kuliah\\Tugas\\Semester 3\\Praktikum OOP\\CS\\Modul 1\\JBus\\data\\accountDatabase.json");
            tableAccount.add(new Account("Dio", "dio@gmail.com", "NgikNgok"));
            tableAccount.add(new Account("Dio", "dio@gmail.com", "NgikNgok"));
            tableAccount.writeJson();

            for (Account account : tableAccount){
                System.out.println("Account ID: " + account.id + " Name: " + account.name + " Email: " + account.email + " Password: " + account.password);
            }
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }

        Bus bus = createBus();
        bus.schedules.forEach(Schedule::printSchedule);
        for(int i =0; i < 10; i++){
            BookingThread thread = new BookingThread("Thread " + i,bus, Timestamp.valueOf("2023-07-27 19:00:00"));
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        bus.schedules.forEach(Schedule::printSchedule);

    }

    public static Bus createBus() {
        Price price = new Price(750000, 5);
        Bus bus = new Bus("Netlab Bus", Facility.LUNCH, price, 25, BusType.REGULER, City.BANDUNG, new Station("Depok Terminal", City.DEPOK, "Jl. Margonda Raya"), new Station("Halte UI", City.JAKARTA, "Universitas Indonesia"));
        Timestamp timestamp = Timestamp.valueOf("2023-07-27 19:00:00");
        bus.addSchedule(timestamp);
        return bus;
    }

}
