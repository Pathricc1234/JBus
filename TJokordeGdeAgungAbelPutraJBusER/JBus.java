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
        int discPrice;
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
        discPrice = ex.getDiscountedPrice(beforeDiscount,discountPercentage);
        System.out.println(discPrice);
        oriPrice = ex.getOriginalPrice(discPrice,discountPercentage);
        System.out.println(oriPrice);
        totalPrice = ex.getTotalPrice(afterDiscount,numberOfSeat);
        System.out.println(totalPrice);
    }
    public static int getBusId(){
        return 0;
    }
    public static String getBusName(){
        return "Bus";
    }
    public static boolean isDiscount(){
        return true;
    }
    public static int getDiscountPercentage(int beforeDiscount, int afterDiscount){
        if(beforeDiscount < afterDiscount){
            return 0;
        }
        else{
            int discount = (beforeDiscount - afterDiscount)/10;
            return discount;
        }
    }
    public static int getDiscountedPrice(int price, float discountPercentage){
        if(discountPercentage > 100.0){
            discountPercentage = 100;
        }
        float fprice = (float)price;
        fprice = (100 - discountPercentage) / 100 * fprice;
        price = (int)fprice;
        return price;
    }
    public static int getOriginalPrice(int discountedPrice, float discountPercentage){
        if(discountPercentage > 100.0){
            discountPercentage = 100;
        }
        float fdiscountedPrice = (float)discountedPrice;
        fdiscountedPrice = 100 / (100 - discountPercentage) * fdiscountedPrice;
        discountedPrice = (int)fdiscountedPrice;
        return discountedPrice;
    }
    public static float getAdminFeePercentage(){
        return 0.05f;
    }
    public static int getAdminFee(int price){
        float percent = getAdminFeePercentage();
        float fAdminFee = price * percent;
        int adminFee = (int)fAdminFee;
        return adminFee;
    }
    public static int getTotalPrice(int price, int numberOfSeat){
        price = price + getAdminFee(price);
        return price * numberOfSeat;
    }
}
