package TJokordeGdeAgungAbelPutraJBusER;

public class Voucher{
    public String name;
    private boolean used;
    public double minimum;
    public double cut;
    public int code;
    public Type type;
    
    public Voucher(String name, int code, Type type, double minimum, double cut){
        this.name = name;
        this.code = code;
        this.type = type;
        this.minimum = minimum;
        this.cut = cut;
    }
    public boolean isUsed(){
        return this.used;
    }
    public boolean canApply(Price price){
        if(price.price > this.minimum && this.used == false){
            return true;
        }
        else{
            return false;
        }
    }
    public double apply(Price price){
        this.used = true;
        if(this.type == Type.REBATE){
            price.price -= this.cut;
        }
        else if(this.type == Type.DISCOUNT){
            price.price -= (this.cut / 100 * price.price);
        }
        return price.price;
    }
}