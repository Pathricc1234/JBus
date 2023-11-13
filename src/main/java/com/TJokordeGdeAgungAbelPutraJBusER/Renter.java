package com.TJokordeGdeAgungAbelPutraJBusER;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Renter extends Serializable{
    public String address;
    public String companyName;
    public String phoneNumber;
    public static final String REGEX_PHONE = "[0-9{9,12}]";
    public static final String REGEX_NAME = " ^[A-Za-z0-9_]{4,20}$";
    
    public Renter(String companyName){
        super();
        this.address = "";
        this.companyName = companyName;
        this.phoneNumber = "";
    }
    public Renter(String companyName, String phoneNumber){
        super();
        this.address = "";
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
    }
    public Renter(String companyName, String phoneNumber, String address){
        super();
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public static boolean validate(String name, String phone){
        Pattern namePattern = Pattern.compile(REGEX_NAME);
        Pattern phonePattern = Pattern.compile(REGEX_PHONE);
        Matcher phoneMatch = phonePattern.matcher(phone);
        Matcher nameMatch = namePattern.matcher(name);
        boolean nameFound = nameMatch.find();
        boolean phoneFound = phoneMatch.find();
        if(nameFound && phoneFound){
            return true;
        }
        return false;
    }
}
