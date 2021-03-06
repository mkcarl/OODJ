package FileIO;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author mkcarl
 */
public class ProductFile extends MyFile {
    private static final String fileName = "Products.txt", fileDir = MyFile.directory + fileName;
    private static final String[] columns = {"pid", "pname", "ptype", "punitprice", "ppackagingcharge", "pinventorycount", "pstatus"};

    /**
     * Creates Products.txt
     */
    public static void createProductFile() throws IOException {
        File file;

        MyFile.createDirectory();
        file = new File(fileDir);
        if (file.createNewFile()) {
            System.out.println("Products file created.");
        }

        PrintWriter f = new PrintWriter(new FileWriter(fileDir));
        f.println(String.join(",", columns));
        f.close();
    }

    /**
     * @param name Product name
     * @param type Product type (fragile or non-fragile)
     * @param unitPrice Product Unit Price
     * @param packagingCharge Product Packaging Charge
     * @param inventoryCount Product Inventory count
     * @param status Product status (active or inactive)
     */
    public static void addNewProduct(String name, String type, double unitPrice, double packagingCharge, int inventoryCount, String status) {
        try {
            newEntry(getPID(), name, type, unitPrice, packagingCharge, inventoryCount, status);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong while adding product.");
        }
    }

    /**
     * @return All Products from Products.txt in a 2D ArrayList. Each ArrayList is a column of Products.
     */
    public static ArrayList<ArrayList<String>> readAllProducts() throws FileNotFoundException {
        File file = new File(fileDir);
        Scanner f = new Scanner(file);
        ArrayList<ArrayList<String>> allProducts = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            allProducts.add(new ArrayList<>());
        }
        f.nextLine();
        while (true) {
            try {
                String current = f.nextLine();
                String[] currentArr = current.split(",");
                for (int i = 0; i < currentArr.length; i++) {
                    allProducts.get(i).add(currentArr[i]);
                }
            } catch (NoSuchElementException e) {
                break;
            }
        }
        f.close();
        return allProducts;
    }

    /**
     * @param columnName Column name : pid, pname, ptype, punitprice, ppackagingprice, pinventorycount, pstatus
     * @return Arraylist of specified column.
     */
    public static ArrayList<String> readColumn(String columnName) throws IOException, RecordNotFoundException {
        ArrayList<ArrayList<String>> allProducts = readAllProducts();
        switch (columnName) {
            case "pid":
                return allProducts.get(0);
            case "pname":
                return allProducts.get(1);
            case "ptype":
                return allProducts.get(2);
            case "punitprice":
                return allProducts.get(3);
            case "ppackagingcharge":
                return allProducts.get(4);
            case "pinventorycount":
                return allProducts.get(5);
            case "pstatus":
                return allProducts.get(6);
            default:
                throw new RecordNotFoundException(String.format("Column %s does not exist.", columnName));
        }
    }

    /**
     * @param columnIndex Index of column. Range 1 to 6
     * @return ArrayList of the specified column.
     */
    public static ArrayList<String> readColumn(int columnIndex) throws IOException {
        ArrayList<ArrayList<String>> allProducts = readAllProducts();
        return allProducts.get(columnIndex);
    }


    /**
     * @param id Prouct ID
     * @param name Product name
     * @param type Product type (fragile or non-fragile)
     * @param unitPrice Product unit price
     * @param packagingCharge Product packaging charge
     * @param inventoryCount Product inventory count
     * @param status Product status (active or inactive)
     */
    private static void newEntry(String id, String name, String type, double unitPrice, double packagingCharge,
                                 int inventoryCount, String status) throws IOException {
        FileWriter fw = new FileWriter(fileDir, true);
        try (PrintWriter f = new PrintWriter(fw)) {
            f.println(String.format("%s,%s,%s,%.2f,%.2f,%d,%s", id, name, type, unitPrice, packagingCharge, inventoryCount, status));
        }
        fw.close();
    }

    /**
     * @return Auto generated NEW PID based on the last Product ID in Products. txt
     */
    public static String getPID() throws IOException, RecordNotFoundException {
        ArrayList<String> allID = readColumn("pid");
        int intID;
        if (allID.size() != 0) {
            String lastID = allID.get(allID.size() - 1);
            intID = Integer.parseInt(lastID.substring(1));
        } else {
            intID = 0;
        }
        intID++;
        return String.format("P%06d", intID);
    }

    /**
     * @param columnIndex Index of column
     * @param entryIndex Index of row
     * @param newValue Value to replace with
     */
    public static void updateEntry(int columnIndex, int entryIndex, String newValue) throws IOException, IndexOutOfBoundsException {
        ArrayList<ArrayList<String>> allProducts = readAllProducts();
        ArrayList<String> targetColumn = readColumn(columnIndex);
        int row = allProducts.get(0).size();
        int col = allProducts.size();

        if (targetColumn.size() > entryIndex) {
            targetColumn.set(entryIndex, newValue);
            allProducts.set(columnIndex, targetColumn);
        } else {
            throw new IndexOutOfBoundsException("entryIndex out of bound");
        }

        createProductFile();
        for (int i = 0; i < row; i++) {
            ArrayList<String> temp = new ArrayList<>();
            for (int j = 0; j < col; j++) {
                temp.add(allProducts.get(j).get(i));
            }
            PrintWriter f = new PrintWriter(new FileWriter(fileDir, true));
            f.println(String.join(",", temp));
            f.close();
        }
    }

    /**
     * @param entryIndex Index of row to delete.
     */
    public static void deleteEntry(int entryIndex) throws IOException, IndexOutOfBoundsException {
        ArrayList<ArrayList<String>> allProducts = readAllProducts();
        int row = allProducts.get(0).size();
        int col = allProducts.size();

        if (entryIndex > row) {
            throw new IndexOutOfBoundsException();
        }

        for (ArrayList<String> prod :
                allProducts) {
            prod.remove(entryIndex);
        }

        row--;
        createProductFile();
        for (int i = 0; i < row; i++) {
            ArrayList<String> temp = new ArrayList<>();
            for (int j = 0; j < col; j++) {
                temp.add(allProducts.get(j).get(i));
            }
            PrintWriter f = new PrintWriter(new FileWriter(fileDir, true));
            f.println(String.join(",", temp));
            f.close();
        }
    }

    /**
     * @param pid Product ID
     * @return Index of the specified Product ID.
     */
    public static int indexOf(String pid) throws IOException, RecordNotFoundException {
        ArrayList<String> pidColumn = readColumn(0);
        int target = -1;
        for (int i = 0; i < pidColumn.size(); i++) {
            if (pid.equals(pidColumn.get(i))) {
                target = i;
                break;
            }
        }
        if (target == -1) {
            throw new RecordNotFoundException("No such product exist");
        }
        return target;
    }

    /**
     * @param pid Product ID
     * @return True if the specified Product ID exist in Products.txt
     */
    public static boolean productExist(String pid) throws RecordNotFoundException, IOException {
        ArrayList<String> allProducts = readColumn("pid");
        if (allProducts.contains(pid)){
            return true;
        } else {
            throw new RecordNotFoundException(String.format("The PID %s does not exist in %s", pid, fileName));
        }

    }
}
