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
}
