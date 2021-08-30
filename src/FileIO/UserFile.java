package FileIO;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author mkcarl
 */
public class UserFile extends MyFile {
    private static final String fileName = "Users.txt", fileDir = MyFile.directory+fileName;
    private static final String[] columns = {"uid", "upassword","uname", "ugender", "uemail", "uphone", "urole", "ustatus"};


    /**
     * Creates Users.txt file
     */
    public static void createUserFile() throws IOException {
        File file;

        MyFile.createDirectory();
        file = new File(fileDir);
        if (file.createNewFile()){
            System.out.println("User file created.");
        }

        PrintWriter f = new PrintWriter(new FileWriter(fileDir));
        f.println(String.join(",", columns));
        f.close();
    }

    /**
     * @param upassword User password
     * @param uname User name
     * @param ugender User gender
     * @param uemail User email
     * @param uphone User phone number
     * @param urole User role (admin or customer)
     * @param ustatus User status (active or inactive)
     */
    public static void addNewUser(String upassword, String uname, String ugender, String uemail, String uphone, String urole, String ustatus){
        try {
            newEntry(getUID(urole), upassword, uname, ugender, uemail, uphone, urole, ustatus);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong while adding user.");
        }
    }

    /**
     * @return All Users from Users.txt in a 2D ArrayList. Each Arraylist is a column of Users.
     */
    public static ArrayList<ArrayList<String>> readAllUsers() throws FileNotFoundException {
        File file = new File(fileDir);
        Scanner f = new Scanner(file);
        ArrayList<ArrayList<String>> allUsers = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            allUsers.add(new ArrayList<>());
        }
        f.nextLine();
        while (true){
            try {
            String current = f.nextLine();
            String[] currentArr = current.split(",");
                for (int i = 0; i < currentArr.length; i++) {
                    allUsers.get(i).add(currentArr[i]);
                }
            } catch (NoSuchElementException e){
                break;
            }
        }
        f.close();
        return allUsers;
    }

    /**
     * @param columnName Column name : uid, upassword, uname, ugender, uemail, uphone, urole, ustatus
     * @return ArrayList of the specified column.
     */
    public static ArrayList<String> readColumn(String columnName) throws IOException, RecordNotFoundException {
        ArrayList<ArrayList<String>> allUsers = readAllUsers();
        switch (columnName){
            case "uid":
                return allUsers.get(0);
            case "upassword":
                return allUsers.get(1);
            case "uname":
                return allUsers.get(2);
            case "ugender":
                return allUsers.get(3);
            case "uemail":
                return allUsers.get(4);
            case "uphone":
                return allUsers.get(5);
            case "urole":
                return allUsers.get(6);
            case "ustatus":
                return allUsers.get(7);
            default:
                throw new RecordNotFoundException("No such column.");
        }
    }

    /**
     * @param columnIndex Index of column. Range 1 to 7
     * @return ArrayList of the spedified column.
     */
    public static ArrayList<String> readColumn(int columnIndex) throws IOException{
        ArrayList<ArrayList<String>> allUsers = readAllUsers();
        return allUsers.get(columnIndex);
    }

    /**
     * @param uid User ID
     * @param upassword User password
     * @param uname User name
     * @param ugender User gender
     * @param uemail User email
     * @param uphone User phone number
     * @param urole User role
     * @param ustatus User status
     */
    private static void newEntry(String uid, String upassword,String uname, String ugender, String uemail, String uphone, String urole, String ustatus) throws IOException {
        FileWriter fw = new FileWriter(fileDir, true);
        try (PrintWriter f = new PrintWriter(fw)) {
            f.println(String.format("%s,%s,%s,%s,%s,%s,%s,%s", uid, upassword, uname, ugender, uemail, uphone, urole, ustatus));
        }
        fw.close();
    }

    /**
     * @param role Role of user
     * @return Auto generated UID based on role and last User ID in Users. txt
     */
    public static String getUID(String role) throws IOException, RecordNotFoundException {
        ArrayList<String> allID = readColumn("uid");
        int intID;
        if (allID.size() != 0) {
            String lastID = allID.get(allID.size() - 1);
            intID = Integer.parseInt(lastID.substring(1));
        } else {
            intID = 0;
        }
        intID++;
        if (role.equals("CUSTOMER")){
            return String.format("C%06d",intID);
        } else {
            return String.format("A%06d",intID);
        }
    }

    /**
     * @param columnIndex Index of column
     * @param entryIndex Index of row
     * @param newValue Value to replace with
     */
    public static void updateEntry(int columnIndex, int entryIndex, String newValue) throws IOException, IndexOutOfBoundsException {
        ArrayList<ArrayList<String>> allUsers = readAllUsers();
        ArrayList<String> targetColumn = readColumn(columnIndex);
        int row = allUsers.get(0).size();
        int col = allUsers.size();

        if (targetColumn.size() > entryIndex) {
            targetColumn.set(entryIndex, newValue);
            allUsers.set(columnIndex, targetColumn);
        } else {
            throw new IndexOutOfBoundsException("entryIndex out of bound");
        }

        createUserFile();
        for (int i = 0; i < row; i++) {
            ArrayList<String> temp = new ArrayList<>();
            for (int j = 0; j < col; j++) {
                temp.add(allUsers.get(j).get(i));
            }
            PrintWriter f = new PrintWriter(new FileWriter(fileDir, true));
            f.println(String.join(",", temp));
            f.close();
        }
    }

    /**
     * @param entryIndex Index of row to delete.
     */
    public static void deleteEntry(int entryIndex) throws IOException{
        ArrayList<ArrayList<String>> allUsers = readAllUsers();
        int row = allUsers.get(0).size();
        int col = allUsers.size();

        for (ArrayList<String> user :
                allUsers) {
            user.remove(entryIndex);
        }

        row--;
        createUserFile();
        for (int i = 0; i < row; i++) {
            ArrayList<String> temp = new ArrayList<>();
            for (int j = 0; j < col; j++) {
                temp.add(allUsers.get(j).get(i));
            }
            PrintWriter f = new PrintWriter(new FileWriter(fileDir, true));
            f.println(String.join(",", temp));
            f.close();
        }
    }

    /**
     * @param uid User ID
     * @return Index of the specified User ID.
     */
    public static int indexOf(String uid) throws IOException, RecordNotFoundException {
        ArrayList<String> uidColumn = readColumn(0);
        int target = -1;
        for (int i = 0; i < uidColumn.size(); i++) {
            if (uid.equals(uidColumn.get(i))){
                target = i;
                break;
            }
        }
        if (target == -1){
            throw new RecordNotFoundException("No such record exist");
        }
        return target;
    }

    /**
     * @param uid User ID
     * @return True if the specified User ID exist in Orders.txt
     */
    public static boolean userExist(String uid) throws RecordNotFoundException, IOException {
        ArrayList<String> allUsers = readColumn("uid");
        if (allUsers.contains(uid)){
            return true;
        } else {
            throw new RecordNotFoundException(String.format("The UID %s does not exist in %s", uid, fileName));
        }
    }
}

