package TJokordeGdeAgungAbelPutraJBusER;

public class JBus
{
    public static void main(String[] args){
        JBus ex = new JBus();
        float discountPercentage;
        int busId;
        int beforeDiscount = 1000;
        int afterDiscount = 900;
        int numberOfSeat = 2;
        int totalPrice;
        int oriPrice;
        String busName;
        boolean Discount;
        busId = ex.getBusId();
        System.out.println(busId);
        busName = ex.getBusName();
        System.out.println(busName);
        Discount = ex.isDiscount();
        System.out.println(Discount);
        discountPercentage = ex.getDiscountPercentage(beforeDiscount,afterDiscount);
        System.out.println(discountPercentage);
        oriPrice = ex.getDiscountedPrice(afterDiscount,discountPercentage);
        System.out.println(oriPrice);
        totalPrice = ex.getTotalPrice(afterDiscount,numberOfSeat);
        System.out.println(totalPrice);
    }
    public int getBusId(){
        return 0;
    }
    public String getBusName(){
        return "Bus";
    }
    public boolean isDiscount(){
        return true;
    }
    public int getDiscountPercentage(int beforeDiscount, int afterDiscount){
        if(beforeDiscount <= afterDiscount){
            return 0;
        }
        else{
            int discount = (beforeDiscount - afterDiscount)/10;
            return discount;
        }
    }
    public int getDiscountedPrice(int price, float discountPercentage){
        if(discountPercentage >= 100.0){
            return 0;
        }
        else{
            float fprice = (float)price;
            fprice = 100 / (100 - discountPercentage) * fprice;
            price = (int)fprice;
            return price;
        }
    }
    public float getAdminFeePercentage(){
        return 0.05f;
    }
    public int getAdminFee(int price){
        return price * 5 / 100;
    }
    public int getTotalPrice(int price, int numberOfSeat){
        int admin;
        admin = getAdminFee(price);
        price = price + admin;
        return price * numberOfSeat;
    }
}
