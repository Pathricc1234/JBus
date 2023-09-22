package TJokordeGdeAgungAbelPutraJBusER;

public class Invoice extends Serializable{
    public String time;
    public int buyerId;
    public int renterId;
    
    protected Invoice(int id, int buyerId, int renterId, String time){
        super(id);
        this.buyerId = buyerId;
        this.renterId = renterId;
        this.time = time;
    }
    public Invoice(int id, Account buyer, Renter renter, String time){
        super(id);
        this.time = time;
    }
    public String print(){
        String b = Integer.toString(buyerId);
        String r = Integer.toString(renterId);
        return "Time = " + time + " BuyerId = " + b + " RenterID = " + r;
    }
}
