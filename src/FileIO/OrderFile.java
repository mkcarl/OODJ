package FileIO;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author mkcarl
 */
public class OrderFile extends MyFile {
    private static final String fileName = "Orders.txt", fileDir = MyFile.directory + fileName;
    private static final String[] columns = {"oid", "odate", "ostatus", "uid"};


    /**
     * Creates Orders.txt
     */
    public static void createOrderFile() throws IOException {
        File file;

        MyFile.createDirectory();
        file = new File(fileDir);
        if (file.createNewFile()) {
            System.out.println("Order file created.");
        }

        PrintWriter f = new PrintWriter(new FileWriter(fileDir));
        f.println(String.join(",", columns));
        f.close();
    }

    /**
     * @param odate Order date
     * @param ostatus Order Status
     * @param uid User ID
     */
    public static void addNewOrder(Date odate, String ostatus, String uid) {
        try {
            if (UserFile.userExist(uid)) {
                newEntry(getOID(), odate, ostatus, uid);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong while adding Order.");
        }
    }

    /**
     * @return All Orders from Orders.txt in a 2D ArrayList. Each ArrayList is a column of Orders.
     */
    public static ArrayList<ArrayList<String>> readAllOrders() throws FileNotFoundException {
        File file = new File(fileDir);
        Scanner f = new Scanner(file);
        ArrayList<ArrayList<String>> allUsers = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            allUsers.add(new ArrayList<>());
        }
        f.nextLine();
        while (true) {
            try {
                String current = f.nextLine();
                String[] currentArr = current.split(",");
                for (int i = 0; i < currentArr.length; i++) {
                    allUsers.get(i).add(currentArr[i]);
                }
            } catch (NoSuchElementException e) {
                break;
            }
        }
        f.close();
        return allUsers;
    }

    /**
     * @param columnName Column name : oid, odate, ostatus, uid
     * @return ArrayList of the specified column
     */
    public static ArrayList<String> readColumn(String columnName) throws IOException, RecordNotFoundException {
        ArrayList<ArrayList<String>> allUsers = readAllOrders();
        switch (columnName) {
            case "oid":
                return allUsers.get(0);
            case "odate":
                return allUsers.get(1);
            case "ostatus":
                return allUsers.get(2);
            case "uid":
                return allUsers.get(3);
            default:
                throw new RecordNotFoundException("No such column.");
        }
    }

    /**
     * @param columnIndex Index of column. Range 0 to 3.
     * @return ArrayList of the specified column.
     */
    public static ArrayList<String> readColumn(int columnIndex) throws IOException {
        ArrayList<ArrayList<String>> allUsers = readAllOrders();
        return allUsers.get(columnIndex);
    }

    /**
     * @param oid Order ID.
     * @param odate Order Date.
     * @param ostatus Order Status.
     * @param uid User ID.
     */
    private static void newEntry(String oid, Date odate, String ostatus, String uid) throws IOException {
        FileWriter fw = new FileWriter(fileDir, true);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try (PrintWriter f = new PrintWriter(fw)) {
            f.println(String.format("%s,%s,%s,%s", oid, sdf.format(odate), ostatus, uid));
        }
        fw.close();
    }

    /**
     * @return Auto generated Order ID based on the last Order ID in Orders.txt
     */
    private static String getOID() throws IOException, RecordNotFoundException {
        ArrayList<String> allID = readColumn("oid");
        int intID;
        if (allID.size() != 0) {
            String lastID = allID.get(allID.size() - 1);
            intID = Integer.parseInt(lastID.substring(1));
        } else {
            intID = 0;
        }
        intID++;
        return String.format("O%06d", intID);
    }

    /**
     * @param columnIndex Index of column
     * @param entryIndex Index of row
     * @param newValue Value to replace with
     */
    public static void updateEntry(int columnIndex, int entryIndex, String newValue) throws IOException, IndexOutOfBoundsException {
        ArrayList<ArrayList<String>> allOrders = readAllOrders();
        ArrayList<String> targetColumn = readColumn(columnIndex);
        int row = allOrders.get(0).size();
        int col = allOrders.size();

        if (targetColumn.size() > entryIndex) {
            targetColumn.set(entryIndex, newValue);
            allOrders.set(columnIndex, targetColumn);
        } else {
            throw new IndexOutOfBoundsException("entryIndex out of bound");
        }

        createOrderFile();
        for (int i = 0; i < row; i++) {
            ArrayList<String> temp = new ArrayList<>();
            for (int j = 0; j < col; j++) {
                temp.add(allOrders.get(j).get(i));
            }
            PrintWriter f = new PrintWriter(new FileWriter(fileDir, true));
            f.println(String.join(",", temp));
            f.close();
        }
    }

    /**
     * @param entryIndex Index of row to delete.
     */
    public static void deleteEntry(int entryIndex) throws IOException {
        ArrayList<ArrayList<String>> allOrders = readAllOrders();
        int row = allOrders.get(0).size();
        int col = allOrders.size();

        for (ArrayList<String> order :
                allOrders) {
            order.remove(entryIndex);
        }

        row--;
        createOrderFile();
        for (int i = 0; i < row; i++) {
            ArrayList<String> temp = new ArrayList<>();
            for (int j = 0; j < col; j++) {
                temp.add(allOrders.get(j).get(i));
            }
            PrintWriter f = new PrintWriter(new FileWriter(fileDir, true));
            f.println(String.join(",", temp));
            f.close();
        }
    }

    /**
     * @param oid Order ID.
     * @return Index of the specified Order ID.
     */
    public static int indexOf(String oid) throws IOException, RecordNotFoundException {
        ArrayList<String> oidColumn = readColumn(0);
        int target = -1;
        for (int i = 0; i < oidColumn.size(); i++) {
            if (oid.equals(oidColumn.get(i))) {
                target = i;
                break;
            }
        }
        if (target == -1) {
            throw new RecordNotFoundException("No such order exist");
        }
        return target;
    }

    /**
     * @param oid Order ID.
     * @return True if the specified Order ID exist in Orders.txt
     */
    public static boolean orderExist(String oid) throws RecordNotFoundException, IOException {
        ArrayList<String> allOrders = readColumn("oid");
        if (allOrders.contains(oid)){
            return true;
        } else {
            throw new RecordNotFoundException(String.format("The OID %s does not exist in %s", oid, fileName));
        }
    }
}
