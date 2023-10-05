package TJokordeGdeAgungAbelPutraJBusER;
public class Price{
    public double rebate;
    public double price;
    
    public Price(double price){
        this.price = price;
        this.rebate = 0;
    }
    public Price(double price, double rebate){
        this.price = price;
        this.rebate = rebate;
    }
    public String toString(){
        String rebate_str = String.valueOf(this.rebate);
        String price_str = String.valueOf(this.price);
        return rebate_str + price_str;
    }
}
