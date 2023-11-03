package TJokordeGdeAgungAbelPutraJBusER;

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
}