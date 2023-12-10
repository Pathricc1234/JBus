package com.TJokordeGdeAgungAbelPutraJBusER;

/**
 * Station class adalah station untuk keberangkatan dan kedatangan bus
 *
 * @author Tjokorde Gde Agung Abel Putra
 * @version 1.0
 */
public class Station extends Serializable{
    /** Kota stasiun berada **/
    public City city;
    /** Nama dari stasiun **/
    public String stationName;
    /** Alamat dari Stasiun **/
    public String address;

    /**
     * Constructor dari Station class
     * @param stationName nama stasiun
     * @param city adalah kota {@link City} stasiun tersebut berada
     * @param address adalah alamat dari stasiun
     */
    public Station (String stationName, City city, String address){
        super();
        this.stationName = stationName;
        this.city = city;
        this.address = address;
    }

    /**
     * Memberikan semua informasi dari stasiun
     * @return informasi berupa id dari stasiun, nama stasiun, kota stasiun, dan alamat stasiun
     */
    public String toString(){
        return "Station Id : " + id + " Station Name: " + stationName + " City: " + city + " Address: " + address;
    }
}
