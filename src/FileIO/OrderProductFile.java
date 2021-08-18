package FileIO;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author mkcarl
 */
public class OrderProductFile extends MyFile {
    private static final String fileName = "OrdersProducts.txt", fileDir = MyFile.directory+fileName;
    private static final String[] columns = {"oid", "pid", "qty"};


    public static void createOrderFile() throws IOException {
        File file;

        MyFile.createDirectory();
        file = new File(fileDir);
        if (file.createNewFile()){
            System.out.println("Order file created.");
        }

        PrintWriter f = new PrintWriter(new FileWriter(fileDir));
        f.println(String.join(",", columns));
        f.close();
    }

    public static void addNewOrderProduct(String oid, String pid, int qty){
        try {
            if (ProductFile.productExist(pid) && OrderFile.orderExist(oid)) {
                newEntry(oid, pid, qty);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong while adding Order.");
        }
    }

    public static ArrayList<ArrayList<String>> readAllOrdersProducts() throws FileNotFoundException {
        File file = new File(fileDir);
        Scanner f = new Scanner(file);
        ArrayList<ArrayList<String>> allOrdersProducts = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            allOrdersProducts.add(new ArrayList<>());
        }
        f.nextLine();
        while (true){
            try {
                String current = f.nextLine();
                String[] currentArr = current.split(",");
                for (int i = 0; i < currentArr.length; i++) {
                    allOrdersProducts.get(i).add(currentArr[i]);
                }
            } catch (NoSuchElementException e){
                break;
            }
        }
        f.close();
        return allOrdersProducts;
    }

    public static ArrayList<String> readColumn(String columnName) throws IOException, RecordNotFoundException {
        ArrayList<ArrayList<String>> allOrdersProducts = readAllOrdersProducts();
        switch (columnName){
            case "oid":
                return allOrdersProducts.get(0);
            case "pid":
                return allOrdersProducts.get(1);
            case "qty":
                return allOrdersProducts.get(2);
            default:
                throw new RecordNotFoundException("No such column.");
        }
    }

    public static ArrayList<String> readColumn(int columnIndex) throws IOException{
        ArrayList<ArrayList<String>> allUsers = readAllOrdersProducts();
        return allUsers.get(columnIndex);
    }

    private static void newEntry(String oid, String pid, int qty) throws IOException {
        FileWriter fw = new FileWriter(fileDir, true);
        try (PrintWriter f = new PrintWriter(fw)) {
            f.println(String.format("%s,%s,%d", oid, pid, qty));
        }
        fw.close();
    }

    private static ArrayList<String> getPIDs(String oid) throws IOException, RecordNotFoundException {
        ArrayList<String> allPID = readColumn("pid");
        ArrayList<String> targetPIDs = new ArrayList<>();

        for (int i :
                indicesOf(oid)) {
            targetPIDs.add(allPID.get(i));
        }

        if (targetPIDs.size() == 0){
            throw new RecordNotFoundException("No such Order ID");
        }

        return targetPIDs;

    }

    public static void updateEntry(int columnIndex, int entryIndex, String newValue) throws IOException, IndexOutOfBoundsException {
        ArrayList<ArrayList<String>> allOrdersProducts = readAllOrdersProducts();
        ArrayList<String> targetColumn = readColumn(columnIndex);
        int row = allOrdersProducts.get(0).size();
        int col = allOrdersProducts.size();

        if (targetColumn.size() > entryIndex) {
            targetColumn.set(entryIndex, newValue);
            allOrdersProducts.set(columnIndex, targetColumn);
        } else {
            throw new IndexOutOfBoundsException("entryIndex out of bound");
        }

        createOrderFile();
        for (int i = 0; i < row; i++) {
            ArrayList<String> temp = new ArrayList<>();
            for (int j = 0; j < col; j++) {
                temp.add(allOrdersProducts.get(j).get(i));
            }
            PrintWriter f = new PrintWriter(new FileWriter(fileDir, true));
            f.println(String.join(",", temp));
            f.close();
        }
    }

    public static void deleteEntry(int entryIndex) throws IOException{
        ArrayList<ArrayList<String>> allOrdersProducts = readAllOrdersProducts();
        int row = allOrdersProducts.get(0).size();
        int col = allOrdersProducts.size();

        for (ArrayList<String> order :
                allOrdersProducts) {
            order.remove(entryIndex);
        }

        row--;
        createOrderFile();
        for (int i = 0; i < row; i++) {
            ArrayList<String> temp = new ArrayList<>();
            for (int j = 0; j < col; j++) {
                temp.add(allOrdersProducts.get(j).get(i));
            }
            PrintWriter f = new PrintWriter(new FileWriter(fileDir, true));
            f.println(String.join(",", temp));
            f.close();
        }
    }

    public static ArrayList<Integer> indicesOf(String oid) throws IOException, RecordNotFoundException {
        ArrayList<String> allOID = readColumn("oid");
        ArrayList<Integer> targetIndices = new ArrayList<>();

        for (int i = 0; i < allOID.size(); i++) {
            if (allOID.get(i).equals(oid)){
                targetIndices.add(i);
            }
        }

        if (targetIndices.size() == 0){
            throw new RecordNotFoundException("No such Order ID");
        }
        return targetIndices;
    }


}
