import FileIO.OrderFile;
import FileIO.RecordNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public abstract class PurchasableUser extends User implements Purchasable {
    private Order order_cart;

    public PurchasableUser(String uid, String pw, String uname, String gender, String email, String phone_num, String role, String status) {
        super(uid, pw, uname, gender, email, phone_num, role, status);
        // if they have a cart, and if the cart is not cheched out, use old cart, or else, give them new cart

        ArrayList<Integer> indexOfAllOrderID;
        ArrayList<ArrayList<String>> allOrderFromFile;
        String latestOID;

        try {
            // if there is existing orders
            indexOfAllOrderID = OrderFile.indicesOf(this.user_id); // all the order id of this particular user
            allOrderFromFile = OrderFile.readAllOrders(); // all the orders
            latestOID = allOrderFromFile.get(0).get(indexOfAllOrderID.get(indexOfAllOrderID.size() - 1)); // get the last order id of this dude

            if (allOrderFromFile.get(2).get(indexOfAllOrderID.get(indexOfAllOrderID.size() - 1)).equals("PENDING")){
                this.order_cart = new Order(latestOID);
            } else {
                throw new RecordNotFoundException();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (RecordNotFoundException e) { // if there are no records for them
            OrderFile.addNewOrder(
                    new Date(),
                    "PENDING",
                    this.user_id
            ); // create a new entry for them first
            try {
                // basically same thing as above
                indexOfAllOrderID = OrderFile.indicesOf(this.user_id);
                allOrderFromFile = OrderFile.readAllOrders();
                latestOID = allOrderFromFile.get(0).get(indexOfAllOrderID.get(indexOfAllOrderID.size() - 1));

                this.order_cart = new Order(latestOID);
            } catch (IOException | RecordNotFoundException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public PurchasableUser (String uid) {
        super(uid);
        // if they have a cart, and if the cart is not cheched out, use old cart, or else, give them new cart

        ArrayList<Integer> indexOfAllOrderID;
        ArrayList<ArrayList<String>> allOrderFromFile;
        String latestOID;

        try {
            // if there is existing orders
            indexOfAllOrderID = OrderFile.indicesOf(this.user_id); // all the order id of this particular user
            allOrderFromFile = OrderFile.readAllOrders(); // all the orders
            latestOID = allOrderFromFile.get(0).get(indexOfAllOrderID.get(indexOfAllOrderID.size() - 1)); // get the last order id of this dude

            if (allOrderFromFile.get(2).get(indexOfAllOrderID.get(indexOfAllOrderID.size() - 1)).equals("PENDING")){
                this.order_cart = new Order(latestOID);
            } else {
                throw new RecordNotFoundException();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (RecordNotFoundException e) { // if there are no records for them
            OrderFile.addNewOrder(
                    new Date(),
                    "PENDING",
                    this.user_id
            ); // create a new entry for them first
            try {
                // basically same thing as above
                indexOfAllOrderID = OrderFile.indicesOf(this.user_id);
                allOrderFromFile = OrderFile.readAllOrders();
                latestOID = allOrderFromFile.get(0).get(indexOfAllOrderID.get(indexOfAllOrderID.size() - 1));

                this.order_cart = new Order(latestOID);
            } catch (IOException | RecordNotFoundException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public Order getOrder_cart() {
        return order_cart;
    }

    public void setOrder_cart(Order order_cart) {
        this.order_cart = order_cart;
    }

    public void generateInvoice() {
        // TODO : add PDF invoice code
    }

    public double balance(double amountPaid) {
        // likely redundant
        return amountPaid - this.order_cart.calculateFinal();
    }

    public void checkOut(boolean invoice) {
        this.order_cart.checkOutCart();

        if (invoice){
            generateInvoice();
        }
    }
}
