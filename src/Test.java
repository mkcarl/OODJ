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

            createDummyProducts();
//            System.out.println(FileIO.ProductFile.indexOf("P000003"));
//            FileIO.ProductFile.updateEntry(1,1, "MaybeACup");
//            FileIO.ProductFile.deleteEntry(FileIO.ProductFile.indexOf("P000003"));

            createDummyOrders();

            createDummyOrderItem();
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
        UserFile.addNewUser("123", "Carl", "MALE", "123@gmail.com", "111222333", "CUSTOMER", "ACTIVE");
        UserFile.addNewUser("1234", "Bob", "MALE", "1234@gmail.com", "111222333", "ADMIN", "ACTIVE");
        UserFile.addNewUser("1235", "Jane", "FEMALE", "1235@gmail.com", "111222333", "CUSTOMER", "ACTIVE");
        UserFile.addNewUser("234", "Mary", "FEMALE", "1236@gmail.com", "111222333", "ADMIN", "ACTIVE");
        UserFile.addNewUser("456", "Adam", "MALE", "Adam@gmail.com", "01233434", "CUSTOMER", "ACTIVE");
        UserFile.addNewUser("567", "May", "FEMALE", "May1236@gmail.com", "4487970234", "CUSTOMER", "ACTIVE");
        UserFile.addNewUser("123433", "June", "FEMALE", "June66@gmail.com", "344234234", "CUSTOMER", "ACTIVE");

        // TODO : deleting user, change status or delete record

    }

    public static void createDummyProducts(){
        try{
            ProductFile.createProductFile();
        } catch (IOException e){
            e.printStackTrace();
        }
        ProductFile.addNewProduct("Toilet paper", "NOT_FRAGILE", 12.99, 0.5, 100, "ACTIVE");
        ProductFile.addNewProduct("Cup", "NOT_FRAGILE", 19.99, 1.5, 10, "ACTIVE");
        ProductFile.addNewProduct("Water bottle", "NOT_FRAGILE", 33.99, 0.5, 104, "ACTIVE");
        ProductFile.addNewProduct("Sugar", "NOT_FRAGILE", 3.99, 0.5, 1023, "INACTIVE");
        ProductFile.addNewProduct("Flour", "NOT_FRAGILE", 4.99, 0.5, 1203, "ACTIVE");
        ProductFile.addNewProduct("iPad mini", "FRAGILE", 1333.99, 1.5, 3, "INACTIVE");
        ProductFile.addNewProduct("Mechanical keyboard", "FRAGILE", 139.99, 1.5, 10, "ACTIVE");
        ProductFile.addNewProduct("Sanitary pads", "NOT_FRAGILE", 23.99, 0.5, 50, "ACTIVE");
        ProductFile.addNewProduct("Lobster cracker", "NOT_FRAGILE", 13.99, 0.5, 106, "ACTIVE");
        ProductFile.addNewProduct("Socks", "NOT_FRAGILE", 26.99, 0.5, 100, "ACTIVE");
        ProductFile.addNewProduct("Bass boosted earphone", "FRAGILE", 199.99, 1.5, 13, "ACTIVE");
    }

    public static void createDummyOrders(){
        try {
            OrderFile.createOrderFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        OrderFile.addNewOrder(new Date(), "PAID", "C000001");
        OrderFile.addNewOrder(new Date(), "PENDING", "A000004");
        OrderFile.addNewOrder(new Date(), "PENDING", "C000001");
        OrderFile.addNewOrder(new Date(), "PENDING", "C000006");
        OrderFile.addNewOrder(new Date(), "PENDING", "C000005");

    }

    public static void createDummyOrderItem(){
        try {
            OrderProductFile.createOrderProductFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        OrderProductFile.addNewOrderProduct("O000001", "P000001", 1);
        OrderProductFile.addNewOrderProduct("O000001", "P000002", 13);
        OrderProductFile.addNewOrderProduct("O000002", "P000001", 20);
        OrderProductFile.addNewOrderProduct("O000003", "P000006", 2);
        OrderProductFile.addNewOrderProduct("O000003", "P000007", 2);
        OrderProductFile.addNewOrderProduct("O000003", "P000005", 2);
        OrderProductFile.addNewOrderProduct("O000004", "P000002", 20);
        OrderProductFile.addNewOrderProduct("O000004", "P000003", 20);
        OrderProductFile.addNewOrderProduct("O000002", "P000001", 20);
    }

}
