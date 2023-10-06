package TJokordeGdeAgungAbelPutraJBusER;
import java.sql.Timestamp;

public class Invoice extends Serializable{
    public Timestamp time;
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
    
    protected Invoice(int buyerId, int renterId){
        super();
        this.buyerId = buyerId;
        this.renterId = renterId;
        this.time = time;
        this.rating = BusRating.NONE;
        this.status = PaymentStatus.WAITING;
    }
    public Invoice(Account buyer, Renter renter){
        super();
        this.time = time;
        this.buyerId = buyer.id;
        this.renterId = renter.id;
        this.rating = BusRating.NONE;
        this.status = PaymentStatus.WAITING;
    }
}
