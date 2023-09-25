package TJokordeGdeAgungAbelPutraJBusER;

public class Invoice extends Serializable{
    public String time;
    public int buyerId;
    public int renterId;
    public BusRating rating;
    public PaymentStatus status;
    
    public enum PaymentStatus{
        FAILED,
        WAITING,
        SUCCESS;
    }
    
    public enum BusRating{
        NONE,
        NEUTRAL,
        GOOD,
        BAD;
    }
    
    protected Invoice(int id, int buyerId, int renterId, String time){
        super(id);
        this.buyerId = buyerId;
        this.renterId = renterId;
        this.time = time;
        this.rating = BusRating.NONE;
        this.status = PaymentStatus.WAITING;
    }
    public Invoice(int id, Account buyer, Renter renter, String time){
        super(id);
        this.time = time;
        this.buyerId = buyer.id;
        this.renterId = renter.id;
        this.rating = BusRating.NONE;
        this.status = PaymentStatus.WAITING;
    }
    public String toString(){
        String b = Integer.toString(buyerId);
        String r = Integer.toString(renterId);
        return "Time = " + time + " BuyerId = " + b + " RenterID = " + r;
    }
}
