package tJokordeGdeAgungAbelPutraJBusER;
public class Price{
    double rebate;
    double price;
    int discount;
    
    public Price(double price){
        this.price = price;
        this.discount = 0;
        this.rebate = 0;
    }
    public Price(double price, int discount){
        this.price = price;
        this.discount = discount;
        this.rebate = 0;
    }
    public Price(double price, double rebate){
        this.price = price;
        this.rebate = rebate;
        this.discount = 0;
    }
    private double getDiscountedPrice(){
        if(this.discount > 100){
            this.discount = 100;
            return this.discount;
        }
        if(this.discount == 100){
            this.price = 0;
            return this.price;
        }
        else{
            this.price = (100 - this.discount) / 100 * this.price;
            return this.price;
        }
    }
    private double getRebatedPrice(){
        this.rebate = this.discount / 100 * this.price;
        return this.rebate;
    }
}
