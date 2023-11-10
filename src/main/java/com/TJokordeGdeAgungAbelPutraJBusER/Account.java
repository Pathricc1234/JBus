package com.TJokordeGdeAgungAbelPutraJBusER;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code Account} merepresentasikan akun dari user
 * dan parent class {@link Serializable}
 *
 * class ini membuat akun dari user dan mengebalikan semua informasi
 */
public class Account extends Serializable{
    /** email dari user */
    public String email;
    /** nama user */
    public String name;
    /** password user */
    public String password;
    public Renter company;
    public double balance;
    /** REGEX untuk mengecek email dan password */
    public static final String REGEX_EMAIL = "^[a-zA-Z0-9]+@[a-zA-Z_]+?\\.[a-zA-Z.]+[a-zA-Z]+$";
    public static  final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";

    /**
     * @return null karena kosong
     */
    public Object write(){
        return null;
    }

    /**
     * @return false karena berupa boolean
     */
    public  boolean read(String content){
        return false;
    }

    /**
     * Konstruktor {@code Account} memberikan object sesuai parameter
     * @param name nama dari akun
     * @param email email dari akun
     * @param password password dari akun
     */
    public Account ( String name, String email, String password){
        super();
        this.email = email;
        this.name = name;
        this.password = password;
        this.balance = 0;
        this.company = null;
    }

    /**
     * Mengebalikan semua infromasi class
     * @return semua informasi dalam class
     */
    public String toString(){
        return email + name + password;
    }

    /**
     *
     * @param email untuk memasukan email yang ingin dicek
     * @param password untuk memasukan password yang ingin di cek
     * @return true jika keduanya sesuai format false jika salah satu atau keduanya tidak sesuai format
     */
    public static boolean validate(String email, String password){
        Pattern emailPattern = Pattern.compile(REGEX_EMAIL);
        Pattern passwordPattern = Pattern.compile(REGEX_PASSWORD);
        Matcher emailMatcher = emailPattern.matcher(email);
        Matcher passwordMatcher = passwordPattern.matcher(password);
        boolean emailFound = emailMatcher.find();
        boolean passwordFound = passwordMatcher.find();
        if(emailFound && passwordFound){
            return true;
        }
        return false;
    }

    public boolean topUp(double ammount){
        if(ammount <= 0){
            return false;
        }
        else{
            this.balance += ammount;
            return true;
        }
    }
}