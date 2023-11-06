package TJokordeGdeAgungAbelPutraJBusER;

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
    /** REGEX untuk mengecek email dan password */
    public final String REGEX_EMAIL = "netlab@ui.ac.id";
    public final String REGEX_PASSWORD = "Password1234";

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
    }

    /**
     * Mengebalikan semua infromasi class
     * @return semua informasi dalam class
     */
    public String toString(){
        return email + name + password;
    }

    public boolean validate(String REGEX_EMAIL, String REGEX_PASSWORD){
        Pattern email = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)[a-zA-Z\\\\d]{8,}$");
        Pattern password = Pattern.compile("^[a-zA-Z0-9]+@[a-zA-Z]+\\\\.[a-zA-Z]{2,4}$");
        Matcher emailMatcher = email.matcher(REGEX_EMAIL);
        Matcher passwordMatcher = password.matcher(REGEX_PASSWORD);
        boolean emailFound = emailMatcher.find();
        boolean passwordFound = passwordMatcher.find();
        if(emailFound && passwordFound){
            return true;
        }
        return false;
    }
}