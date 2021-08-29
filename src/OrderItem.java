import FileIO.OrderProductFile;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * @author mkcarl
 */
public class OrderItem {
    private final Product item_product;
    private int item_quantity;
    private double item_amount;

    public OrderItem(String oid, Product prod){
        this.item_product = prod;
        try {
            ArrayList<ArrayList<String>> allOrderProductFromFile = OrderProductFile.readAllOrdersProducts();
            for (int i = 0; i < allOrderProductFromFile.get(0).size(); i++) {
                if (
                        allOrderProductFromFile.get(0).get(i).equals(oid) // if order id match
                                && allOrderProductFromFile.get(1).get(i).equals(prod.getProductID()) // and product id match
                ){
                this.item_quantity = Integer.parseInt(allOrderProductFromFile.get(2).get(i));
                this.item_amount = calculateAmount();
                break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void modifyQuantity(int qty){
        this.item_quantity = qty;
        calculateAmount();
    }

    public boolean isAvailable(){
        return this.item_product.getProductStatus().equals("ACTIVE");
    }

    public boolean isEnough(int quantity){
        return
                this.item_product.getProductInventoryCount() > quantity
                &&
                this.item_product.getProductInventoryCount() != 0;
    }

    public double calculateAmount(){
        double amount = (this.item_product.getProductUnitPrice() + this.item_product.getProductPackagingCharge()) * this.item_quantity;
        amount = new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP).doubleValue();
        this.item_amount = amount;
        return amount;

//        if (isAvailable()){
//            if (isEnough(this.item_quantity)) {
//                return this.item_product.getProductUnitPrice() * this.item_quantity;
//            }
//            else {
//                throw new Exception(
//                        String.format("Cannot order %d %s, only have %d in stock.",
//                                this.item_quantity,
//                                this.item_product.getProductName(),
//                                this.item_product.getProductInventoryCount()
//                                )
//                );
//            }
//        } else {
//            throw new Exception(String.format("Item %s is not available", this.item_product.getProductName()));
//        }
    }

    public double getItemAmount(){
        return this.item_amount;
    }

    public Product getItemProduct(){
        return this.item_product;
    }

    public int getItemQuantity(){
        return this.item_quantity;
    }

    public static ArrayList<OrderItem> readAllOrderItemOf(String oid){
        ArrayList<ArrayList<String>> allOrderItems = null;
        ArrayList<OrderItem> target = new ArrayList<>();
        try {
            allOrderItems = OrderProductFile.readAllOrdersProducts();
            int numberOfEnties = allOrderItems.get(0).size();
            for (int i = 0; i < numberOfEnties; i++) {
                if (allOrderItems.get(0).get(i).equals(oid)){
                    target.add(new OrderItem( oid, new Product(allOrderItems.get(1).get(i)) ));
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return target;
    }

}
