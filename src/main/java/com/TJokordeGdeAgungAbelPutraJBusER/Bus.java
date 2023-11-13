package com.TJokordeGdeAgungAbelPutraJBusER;
import java.util.*;
import java.util.Calendar;
import java.sql.Timestamp;

/**
 * Class bus berisi informasi mengenai bus
 * dan menggunakan parrent class {@link Serializable}
 */
public class Bus extends Serializable{
    public int capacity;
    public List<Facility> facility;
    public String name;
    public Price price;
    public BusType busType;
    public Station departure;
    public Station arrival;
    List <Schedule> schedules = new ArrayList<>();

    /**
     * @param name berisi nama dari user
     * @param facility fasilitas yang dipilih user dari enum {@link Facility}
     * @param price harga dari tiket bus
     * @param capacity kapasitas bus
     * @param busType tipe dari bis dari enum {@link Facility}
     * @param departure tempat asal bus
     * @param arrival tempat tujuan bus
     */
    public Bus(String name, List<Facility> facility, Price price, int capacity, BusType busType,Station departure, Station arrival){
        super();
        this.name = name;
        this.facility = facility;
        this.price = price;
        this.capacity = capacity;
        this.busType = busType;
        this.departure = departure;
        this.arrival = arrival;
    }
    public Object write(){
        return null;
    }
    public boolean read(String content){
        return false;
    }

    /**
     *
     * @param newSchedule berisi waktu yang ingin ditambahkan
     * jika waktu yang ingin ditambahkan sudah ada maka tidak akan menambahkan
     * jika waktu yang ingin ditambahkan belum ada maka akan menambahkan  jadwal dengan waktu yang diinginkan
     */
    public void addSchedule(Timestamp newSchedule) {
        try {
            if (schedules.contains(newSchedule)) {
                throw new IllegalArgumentException("Jadwal sudah ada dalam daftar.");
            }
            schedules.add(new Schedule(newSchedule, this.capacity));
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    public int getId() {
        return id;
    }
}
