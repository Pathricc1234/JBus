package TJokordeGdeAgungAbelPutraJBusER;
public class Account extends Serializable implements FileParser{
    public String email;
    public String name;
    public String password;
    
    public Object write(){
        return null;
    }
    public  boolean read(String content){
        return false;
    }
    
    public Account ( String name, String email, String password){
        super();
        this.email = email;
        this.name = name;
        this.password = password;
    }
    
    public String toString(){
        return email + name + password;
    }
}