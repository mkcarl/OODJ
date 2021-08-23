import FileIO.OrderProductFile;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @author mkcarl
 */
public class OrderItem {
    private final Product item_product;
    private int item_quantity;
    private double item_amount;

    public OrderItem(Product prod){
        this.item_product = prod;
        try {
            this.item_amount = calculateAmount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void modifyQuantity(int qty){
        this.item_quantity = qty;
    }

    public boolean isAvailable(){
        return this.item_product.getProductStatus().equals(ProductStatus.ACTIVE.name());
    }

    public boolean isEnough(int quantity){
        return
                this.item_product.getProductInventoryCount() > quantity
                &&
                this.item_product.getProductInventoryCount() != 0;
    }

    public double calculateAmount() throws Exception {
        if (isAvailable()){
            if (isEnough(this.item_quantity)) {
                return this.item_product.getProductUnitPrice() * this.item_quantity;
            } else {
                throw new Exception(
                        String.format("Cannot order %d %s, only have %d in stock.",
                                this.item_quantity,
                                this.item_product.getProductName(),
                                this.item_product.getProductInventoryCount()
                                )
                );
            }
        } else {
            throw new Exception(String.format("Item %s is not available", this.item_product.getProductName()));
        }
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
                    target.add(new OrderItem( new Product(allOrderItems.get(1).get(i)) ));
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return target;
    }

}
