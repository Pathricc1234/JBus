package com.TJokordeGdeAgungAbelPutraJBusER;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Account class merepresentasikan akun dari user
 *
 * @author Tjokorde Gde Agung Abel Putra
 * @version 1.0
 */
public class Account extends Serializable{
    /** email dari user */
    public String email;
    /** nama user */
    public String name;
    /** password user */
    public String password;
    /** company dari user (user harus membuat di class lain agar dapat memiliki) */
    public Renter company;
    /** saldo uang yang dimiliki user */
    public double balance;
    /** REGEX untuk mengecek email dan password */
    /** EMAIl harus berupa huruf dan angka dan memiliki @ dan domain hanya memilki huruf */
    public static final String REGEX_EMAIL = "^[a-zA-Z0-9]+@[a-zA-Z_]+?\\.[a-zA-Z.]+[a-zA-Z]+$";
    /** Password minimal memiliki 1 kapital, 1 lowercase, 1 angka, dan minimal memilki panjang 8 karakter */
    public static  final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";

    public Object write(){
        return null;
    }
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

    /**
     * @param ammount adalah jumlah uang yang diinput user
     * @return false jika balance yang dimasukan negatif, true jika balance tidak negatif dan nemabah balance pada class
     */
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