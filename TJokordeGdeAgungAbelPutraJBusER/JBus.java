package TJokordeGdeAgungAbelPutraJBusER;
public class JBus{
    public static void main(String[] args){
        Payment testPayment = new Payment(1,1,1,"A",1,"A","A");
        Invoice testInvoice = new Invoice(2,2,2,"B");
        Station testStation = new Station(3,"C", City.DEPOK);
        System.out.println(testPayment.print());
        System.out.println(testInvoice.print());
        System.out.println(testStation.print());
    }
}
