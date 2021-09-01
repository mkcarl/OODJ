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
    private String order_id;
    private String order_status;
    private Date order_Date;
    private ArrayList<OrderItem> order_items;

    public Order(String oid, Date date, String ostatus) {
        this.order_id = oid;
        this.order_Date = date;
        this.order_status = ostatus;
        this.order_items = OrderItem.readAllOrderItemOf(oid);
    }


    public Order(String oid){
        try {
            ArrayList<ArrayList<String>> allOrderFromFile = OrderFile.readAllOrders();
            int entryIndex = allOrderFromFile.get(0).indexOf(oid);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            this.order_id = allOrderFromFile.get(0).get(entryIndex);
            this.order_Date = sdf.parse(allOrderFromFile.get(1).get(entryIndex));
            this.order_status = allOrderFromFile.get(2).get(entryIndex);
            this.order_items = OrderItem.readAllOrderItemOf(oid);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addItem(Product prod) {
        try {
            ArrayList<String> allPIDs = OrderProductFile.getPIDs(this.order_id);
            OrderProductFile.addNewOrderProduct(this.order_id, prod.getProductID(), 1);
            this.order_items.add(new OrderItem(this.order_id, prod));

        } catch (IOException | RecordNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Order> readAllOrder() {
        ArrayList<ArrayList<String>> allOrders = null;
        ArrayList<Order> target = new ArrayList<>();
        try {
            allOrders = OrderFile.readAllOrders();
            int numOfEntries = allOrders.get(0).size();
            for (int i = 0; i < numOfEntries; i++) {
                target.add(
                        new Order(
                                allOrders.get(0).get(i),
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(allOrders.get(1).get(i)),
                                allOrders.get(2).get(i)
                        )
                );
            }
        } catch (FileNotFoundException|ParseException e) {
            e.printStackTrace();
        }
        return target;
    }

    
    public static void addOrder(String UID){
        OrderFile.addNewOrder(new Date(), "PENDING", UID);
    }

    public void removeItem(int index){
        order_items.remove(index);
        try {
            ArrayList<Integer> allIndicesOfOID = OrderProductFile.indicesOf(this.order_id);
            int target = allIndicesOfOID.get(index);
            OrderProductFile.deleteEntry(target);
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

    public void checkOutCart(){
        this.order_status = "PAID";

        try {
            OrderFile.updateEntry(2, OrderFile.indexOf(this.order_id), this.order_status);
            for (OrderItem oi :
                    this.order_items) {
                Product prod = oi.getItemProduct();
                ProductFile.updateEntry(5, ProductFile.indexOf(prod.getProductID()), Integer.toString(prod.getProductInventoryCount()-oi.getItemQuantity()));
            }
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

    public void updateQuantityOf(int orderItemIndex, int newQuantity){
        try {
            this.order_items.get(orderItemIndex).modifyQuantity(newQuantity);
            OrderProductFile.updateEntry(
                    2,
                    OrderProductFile.indexOf(this.order_id, this.order_items.get(orderItemIndex).getItemProduct().getProductID()),
                    Integer.toString(newQuantity)
            );
        } catch (IOException | RecordNotFoundException e) {
            e.printStackTrace();
        }
    }
}
