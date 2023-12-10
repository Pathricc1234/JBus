package com.TJokordeGdeAgungAbelPutraJBusER;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Renter class merepresentasikan akun dari Renter
 *
 * @author Tjokorde Gde Agung Abel Putra
 * @version 1.0
 */
public class Renter extends Serializable{
    /**
     * alamat dari Renter
     */
    public String address;
    /**
     * nama perusahaan renter
     */
    public String companyName;
    /**
     * nomor telepon renter
     */
    public String phoneNumber;
    /**
     * Regex dari Renter
     * Regex phone hanya berisi angka dan jumlah karakter minimal 9 dan maksimal 12
     */
    public static final String REGEX_PHONE = "[0-9{9,12}]";
    /**
     * Regex nama huruf pertama kapital, hanya boleh huruf digit underscore, minimal 4 dan maksimal 20 karakter tidak boleh whitespace
     */
    public static final String REGEX_NAME = " ^[A-Za-z0-9_]{4,20}$";

    /**
     * konstruktor dari class Renter
     * @param companyName nama perusahaan
     * @param phoneNumber telepon perusahaan
     * @param address alamat perusahaan
     */
    public Renter(String companyName, String phoneNumber, String address){
        super();
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    /**
     * mengecek apakah nama perusahaan dan nomor telepon sesuai format
     * @param name nama perusahaan
     * @param phone nomor telepon perusahaan
     * @return true jika format benar untuk kedua, false jika tidak
     */
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
