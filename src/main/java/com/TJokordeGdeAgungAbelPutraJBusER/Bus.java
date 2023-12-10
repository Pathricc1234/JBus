package com.TJokordeGdeAgungAbelPutraJBusER;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;
import java.sql.Timestamp;

/**
 * Bus class merepresentasikan bus dari company
 *
 * @author Tjokorde Gde Agung Abel Putra
 * @version 1.0
 */
public class Bus extends Serializable{
    /** id dari pemilik bus */
    public int accountId;
    /** kapasitas bus */
    public int capacity;
    /** fasilitas bus */
    public List<Facility> facility;
    /** Nama bus */
    public String name;
    /** Harga tiket dari satu kursi di bus */
    public Price price;
    /** Tipe Bus */
    public BusType busType;
    /** Stasiun keberangkatan bus */
    public Station departure;
    /** Stasiun kedatangan Bus */
    public Station arrival;
    /** Daftar jadwal bus */
    public List <Schedule> schedules;

    /**
     * @param name berisi nama dari bus
     * @param facility fasilitas yang dipilih user dari enum {@link Facility}
     * @param price harga dari tiket bus
     * @param capacity kapasitas bus
     * @param busType tipe dari bis dari enum {@link Facility}
     * @param departure tempat asal bus
     * @param arrival tempat tujuan bus
     */
    public Bus(int accountId,String name, List<Facility> facility, Price price, int capacity, BusType busType,Station departure, Station arrival){
        super();
        this.accountId = accountId;
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
    public void addSchedule(Timestamp newSchedule) throws IllegalArgumentException {
        if (schedules != null) {
            for (Schedule existingSchedule : schedules) {
                if (existingSchedule.departureSchedule.equals(newSchedule)) {
                    throw new IllegalArgumentException("Schedule already exists in the list.");
                }
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(newSchedule);

        Schedule newBusSchedule = new Schedule(new Timestamp(newSchedule.getTime()), capacity);
        if (schedules == null) {
            schedules = new ArrayList<>();
        }
        schedules.add(newBusSchedule);
    }

    /**
     * mendapatkan id dari bus
     * @return id bus
     */
    public int getId() {
        return id;
    }
}
