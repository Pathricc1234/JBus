package com.TJokordeGdeAgungAbelPutraJBusER;
/**
 * Price class merepresentasikan harga dari bus
 *
 * @author Tjokorde Gde Agung Abel Putra
 * @version 1.0
 */
public class Price{
    /**
     * Diskon dari harga bus
     */
    public double rebate;
    /**
     * harga bus
     */
    public double price;

    /**
     * konstruktor class untuk price tanpa rebate
     * @param price harga dari bus
     */
    public Price(double price){
        this.price = price;
        this.rebate = 0;
    }

    /**
     * konstruktor class dengan rebate
     * @param price harga bus
     * @param rebate rebate dari bus
     */
    public Price(double price, double rebate){
        this.price = price;
        this.rebate = rebate;
    }

    /**
     * mengembalikan informasi rebate dan harga bus
     * @return rebate dan harga bus
     */
    public String toString(){
        String rebate_str = String.valueOf(this.rebate);
        String price_str = String.valueOf(this.price);
        return rebate_str + price_str;
    }
}
