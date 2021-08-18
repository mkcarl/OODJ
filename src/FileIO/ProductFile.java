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

    public static void addNewProduct(String name, String type, double unitPrice, double packagingCharge, int inventoryCount, String status) {
        try {
            newEntry(getPID(), name, type, unitPrice, packagingCharge, inventoryCount, status);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong while adding product.");
        }
    }

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

    public static ArrayList<String> readColumn(int columnIndex) throws IOException {
        ArrayList<ArrayList<String>> allProducts = readAllProducts();
        return allProducts.get(columnIndex);
    }


    private static void newEntry(String id, String name, String type, double unitPrice, double packagingCharge,
                                 int inventoryCount, String status) throws IOException {
        FileWriter fw = new FileWriter(fileDir, true);
        try (PrintWriter f = new PrintWriter(fw)) {
            f.println(String.format("%s,%s,%s,%.2f,%.2f,%d,%s", id, name, type, unitPrice, packagingCharge, inventoryCount, status));
        }
        fw.close();
    }

    private static String getPID() throws IOException, RecordNotFoundException {
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

    public static int indexOf(String id) throws IOException, RecordNotFoundException {
        ArrayList<String> pidColumn = readColumn(0);
        int target = -1;
        for (int i = 0; i < pidColumn.size(); i++) {
            if (id.equals(pidColumn.get(i))) {
                target = i;
                break;
            }
        }
        if (target == -1) {
            throw new RecordNotFoundException("No such product exist");
        }
        return target;
    }

    public static boolean productExist(String pid) throws RecordNotFoundException, IOException {
        ArrayList<String> allProducts = readColumn("pid");
        if (allProducts.contains(pid)){
            return true;
        } else {
            throw new RecordNotFoundException(String.format("The PID %s does not exist in %s", pid, fileName));
        }

    }
}
