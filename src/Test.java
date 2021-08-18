import FileIO.*;

import java.io.IOException;
import java.util.Date;

/**
 * @author mkcarl
 */
public class Test {

    public static void main(String[] args) {
        try {
//            FileIO.UserFile.updateEntry(1, 1, Integer.toString(3498579));
//            FileIO.UserFile.deleteEntry(1);
            createDummyUsers();
//            System.out.println(FileIO.UserFile.indexOf("A000005"));

//            createDummyProducts();
//            System.out.println(FileIO.ProductFile.indexOf("P000003"));
//            FileIO.ProductFile.updateEntry(1,1, "MaybeACup");
//            FileIO.ProductFile.deleteEntry(FileIO.ProductFile.indexOf("P000003"));

//            createDummyOrders();

//            createDummyOrderItem();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createDummyUsers(){
        try {
            UserFile.createUserFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserFile.addNewUser("123", "Carl", Gender.MALE.name(), "123@gmail.com", "111222333", Role.CUSTOMER.name(), UserStatus.ACTIVE.name());
        UserFile.addNewUser("1234", "Bob", Gender.MALE.name(), "1234@gmail.com", "111222333", Role.ADMIN.name(), UserStatus.ACTIVE.name());
        UserFile.addNewUser("1235", "Jane", Gender.FEMALE.name(), "1235@gmail.com", "111222333", Role.CUSTOMER.name(), UserStatus.ACTIVE.name());
        UserFile.addNewUser("1236", "Mary", Gender.FEMALE.name(), "1236@gmail.com", "111222333", Role.ADMIN.name(), UserStatus.ACTIVE.name());
        // TODO : deleting user, change status or delete record

    }

    public static void createDummyProducts(){
        try{
            ProductFile.createProductFile();
        } catch (IOException e){
            e.printStackTrace();
        }
        ProductFile.addNewProduct("Toilet paper", ProductType.NOT_FRAGILE.name(), 12.99, 0.5, 100, ProductStatus.ACTIVE.name());
        ProductFile.addNewProduct("Cup", ProductType.FRAGILE.name(), 19.99, 1.5, 10, ProductStatus.ACTIVE.name());
        ProductFile.addNewProduct("Water bottle", ProductType.NOT_FRAGILE.name(), 33.99, 0.5, 104, ProductStatus.ACTIVE.name());
        ProductFile.addNewProduct("Sugar", ProductType.NOT_FRAGILE.name(), 3.99, 0.5, 1023, ProductStatus.ACTIVE.name());
    }

    public static void createDummyOrders(){
        try {
            OrderFile.createOrderFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        OrderFile.addNewOrder(new Date(), OrderStatus.PENDING.name(), "C000001");
        OrderFile.addNewOrder(new Date(), OrderStatus.PENDING.name(), "A000004");

    }

    public static void createDummyOrderItem(){
        try {
            OrderProductFile.createOrderFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        OrderProductFile.addNewOrderProduct("O000001", "P000001", 1);
        OrderProductFile.addNewOrderProduct("O000001", "P000002", 13);
        OrderProductFile.addNewOrderProduct("O000002", "P000001", 20);
    }

}
