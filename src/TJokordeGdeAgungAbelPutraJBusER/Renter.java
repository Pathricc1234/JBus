package TJokordeGdeAgungAbelPutraJBusER;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Renter extends Serializable{
    public String address;
    public String companyName;
    public int phoneNumber;
    public final String REGEX_PHONE = "123456789";
    public final String REGEX_NAME = "Abcdef_ghij";
    
    public Renter(String companyName){
        super();
        this.address = "";
        this.companyName = companyName;
        this.phoneNumber = 0;
    }
    public Renter( String companyName, String address){
        super();
        this.address = address;
        this.companyName = companyName;
        this.phoneNumber = 0;
    }
    public Renter(String companyName, int phoneNumber){
        super();
        this.address = "";
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
    }
    public Renter(String companyName, int phoneNumber, String address){
        super();
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public boolean validate(String REGEX_NAME, String REGEX_PHONE){
        Pattern name = Pattern.compile("^[A-Za-z0-9_]{4,20}$");
        Pattern phone = Pattern.compile("[0-9{9,12}]");
        Matcher phoneMatch = phone.matcher(REGEX_PHONE);
        Matcher nameMatch = name.matcher(REGEX_NAME);
        boolean nameFound = nameMatch.find();
        boolean phoneFound = phoneMatch.find();
        if(nameFound || phoneFound){
            return true;
        }
        return false;
    }
}
