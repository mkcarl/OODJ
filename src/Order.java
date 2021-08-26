import FileIO.OrderFile;
import FileIO.OrderProductFile;
import FileIO.ProductFile;
import FileIO.RecordNotFoundException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author mkcarl
 */
public class Order {
    private final String order_id;
    private String order_status;
    private final Date order_Date;
    private ArrayList<OrderItem> order_items;

    public Order(String oid, Date date, String ostatus) { // for totally new order
        this.order_id = oid;
        this.order_Date = date;
        this.order_status = ostatus;
    }

    public Order(String oid, Date date, String ostatus, ArrayList<String> PIDs){ // for already existing orders
        this.order_id = oid;
        this.order_Date = date;
        this.order_status = ostatus;

        for (String pid :
                PIDs) {
            this.order_items.add( new OrderItem( new Product(pid) ) );
        }
    }

    public void addItem(Product prod) {
        try {
            ArrayList<String> allPIDs = OrderProductFile.getPIDs(this.order_id);
            order_items.add(new OrderItem(prod));

            for (OrderItem orderItem :
                    this.order_items) {
                String currentPID = orderItem.getItemProduct().getProductID();
                if (!allPIDs.contains(currentPID)) {
                    OrderProductFile.addNewOrderProduct(this.order_id, currentPID, 1);
                }
            }

        } catch (IOException | RecordNotFoundException e) {
            e.printStackTrace();
        }
    }

    // ??
    public void addItem(String PID){
        order_items.add(new OrderItem(new Product(PID)));
    }


    public static ArrayList<Order> readAllOrderOf(String UID) {
        ArrayList<ArrayList<String>> allOrders = null;
        ArrayList<Order> target = new ArrayList<>();
        try {
            allOrders = OrderFile.readAllOrders();
            int numOfEntries = allOrders.get(0).size();
            for (int i = 0; i < numOfEntries; i++) {
                if (allOrders.get(0).get(i).equals(UID)) {
                    target.add(
                            new Order(
                                    allOrders.get(0).get(i),
                                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(allOrders.get(1).get(i)),
                                    allOrders.get(2).get(i)
                            )
                    );
                }
            }
        } catch (FileNotFoundException|ParseException e) {
            e.printStackTrace();
        }
        return target;
    }

    
    public static void addOrder(String UID){
        OrderFile.addNewOrder(new Date(), OrderStatus.PENDING.name(), UID);
    }

    public void removeItem(int index){
        order_items.remove(index);
        ArrayList<String> allPIDs = null;
        try {
            allPIDs = OrderProductFile.getPIDs(this.order_id);
            for (OrderItem orderItem :
                    this.order_items) {
                String currentPID = orderItem.getItemProduct().getProductID();
                if (!allPIDs.contains(currentPID)) {
                    OrderProductFile.deleteEntry(OrderProductFile.indicesOf(this.order_id).get(index));
                }
            }
        } catch (IOException | RecordNotFoundException e) {
            e.printStackTrace();
        }
    }

    public double calculateFinal(){
        double total = 0;
        for (OrderItem order_item :
                this.order_items) {
            total += order_item.getItemAmount();
        }
        return total;
    }

    public void checkOut(){
        this.order_status = OrderStatus.PAID.name();

        try {
            OrderFile.updateEntry(2, OrderFile.indexOf(this.order_id), this.order_status);
        } catch (IOException | RecordNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getOrderID(){
        return this.order_id;
    }

    public String getOrderStatus(){
        return this.order_status;
    }

    public Date getOrderDate(){
        return this.order_Date;
    }

    public ArrayList<OrderItem> getOrderItems(){
        return this.order_items;
    }
}