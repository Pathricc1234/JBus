package tJokordeGdeAgungAbelPutraJBusER;
public class JBus{
    public static void main(String[] args){
        Bus testBus = createBus();
        System.out.println(testBus.name);
        System.out.println(testBus.facility);
        System.out.println(testBus.price.price);
        System.out.println(testBus.capacity);
    }
    public static Bus createBus(){
        Price price = new Price(750000,5);
        Bus bus = new Bus("Netlab Bus", Facility.LUNCH ,price,25);
        return bus;
    }
}
