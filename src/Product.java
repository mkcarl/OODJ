import FileIO.ProductFile;
import FileIO.RecordNotFoundException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * @author mkcarl
 */
public class Product {
    private String product_id, product_name, product_type, product_status;
    private double product_unit_price, product_packaging_charge;
    private int product_inventory_count;

    private int rowNumber;

    public Product(String id, String name, String type, double unitPrice, double pkgCharge, int invCount, String status){
        this.product_id = id;
        this.product_name = name;
        this.product_type = type;
        this.product_unit_price = unitPrice;
        this.product_packaging_charge = pkgCharge;
        this.product_inventory_count = invCount;
        this.product_status = status;

        try {
            this.rowNumber = ProductFile.indexOf(product_id);
        } catch (IOException | RecordNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Product(String pid){
        try {
            ArrayList<ArrayList<String>> allProductFromFile = ProductFile.readAllProducts();
            int entryNumber = allProductFromFile.get(0).indexOf(pid);

            this.product_id = allProductFromFile.get(1).get(entryNumber);
            this.product_name = allProductFromFile.get(2).get(entryNumber);
            this.product_type = allProductFromFile.get(3).get(entryNumber);
            this.product_unit_price = Double.parseDouble(allProductFromFile.get(4).get(entryNumber));
            this.product_packaging_charge = Double.parseDouble(allProductFromFile.get(5).get(entryNumber));
            this.product_inventory_count = Integer.parseInt(allProductFromFile.get(6).get(entryNumber));
            this.product_status = allProductFromFile.get(7).get(entryNumber);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void addProduct(String name, String type, double unitPrice, double pkgCharge, int invCount, String status){
        ProductFile.addNewProduct(name, type, unitPrice,pkgCharge, invCount, status);
    }

    public static void deleteProdcut(String productID){
        try {
            int productRowNum = ProductFile.indexOf(productID);
            if (ProductFile.readColumn("ustatus").get(productRowNum).equals("INACTIVE")) {
                ProductFile.updateEntry(7, productRowNum, ProductStatus.INACTIVE.name());
            }
        } catch (IOException | RecordNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void udpateProduct(){ // likely redudant
        try {
            ProductFile.updateEntry(1, rowNumber, this.product_name);
            ProductFile.updateEntry(2, rowNumber, this.product_type);
            ProductFile.updateEntry(3, rowNumber, Double.toString(this.product_unit_price));
            ProductFile.updateEntry(4, rowNumber, Double.toString(this.product_packaging_charge));
            ProductFile.updateEntry(5, rowNumber, Integer.toString(this.product_inventory_count));
            ProductFile.updateEntry(6, rowNumber, this.product_status);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Product> readAllProduct(){
        ArrayList<Product> allProducts = new ArrayList<>();
        try {
            ArrayList<ArrayList<String>> allProductFromFile = ProductFile.readAllProducts();
            int numOfEntries = allProductFromFile.get(0).size();
            for (int i = 0; i < numOfEntries; i++) {
                allProducts.add(
                        new Product(
                                allProductFromFile.get(1).get(i),
                                allProductFromFile.get(2).get(i),
                                allProductFromFile.get(3).get(i),
                                Double.parseDouble(allProductFromFile.get(4).get(i)),
                                Double.parseDouble(allProductFromFile.get(5).get(i)),
                                Integer.parseInt(allProductFromFile.get(6).get(i)),
                                allProductFromFile.get(7).get(i)
                        )
                );
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return allProducts;
    }

//    public static ArrayList<Product> filterProduct(String condition){
//        // TODO i dont think theres a need to filter stuff here, just filter it using at the GUI
//    }

    public static ArrayList<Product> searchProduct(String mode, String keyword){
        ArrayList<Product> allProduct = readAllProduct();
        ArrayList<Product> searchResult = new ArrayList<>();
        switch (mode){
            case "id": // return only one product that correspond to the product id
                for (Product prod :
                        allProduct) {
                    if (prod.product_id.equals(keyword)) {
                        searchResult.add(prod);
                        break;
                    }
                }
                break;
            case "name": // return all product that contain the specified keyword
                for (Product prod :
                        allProduct) {
                    if (prod.product_name.contains(keyword)) {
                        searchResult.add(prod);
                    }
                }
                break;
        }
        return searchResult;
    }

    public void setInventoryCount(int quantity){
        // setter for inventory count
        this.product_inventory_count = quantity;

        try {
            ProductFile.updateEntry(5, rowNumber, Integer.toString(this.product_inventory_count));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setProductName(String name){
        this.product_name = name;
        try {
            ProductFile.updateEntry(1, rowNumber, this.product_name);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setProductType(String type){
        this.product_type = type;

        if (this.product_type.equals("FRAGILE")){
            setProductPackagingCharge(2);
        } else {
            setProductPackagingCharge(0.5);
        }

        try {
            ProductFile.updateEntry(2, rowNumber, this.product_type);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setProductStatus(String status){
        this.product_status = status;
        try {
            ProductFile.updateEntry(6, rowNumber, this.product_status);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setProductUnitPrice(double unitPrice){
        this.product_unit_price = unitPrice;

        try {
            ProductFile.updateEntry(3, rowNumber, Double.toString(this.product_unit_price));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setProductPackagingCharge(double pkgCharge){
        this.product_packaging_charge = pkgCharge;

        try {
            ProductFile.updateEntry(4, rowNumber, Double.toString(this.product_packaging_charge));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getProductID(){
        return this.product_id;
    }

    public String getProductName(){
        return this.product_name;
    }

    public String getProductType(){
        return this.product_type;
    }

    public String getProductStatus(){
        return this.product_status;
    }

    public double getProductUnitPrice(){
        return this.product_unit_price;
    }

    public double getProductPackagingCharge(){
        return this.product_packaging_charge;
    }

    public int getProductInventoryCount(){
        return this.product_inventory_count;
    }


}