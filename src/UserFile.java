import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author mkcarl
 */
public class UserFile extends MyFile {
    private static final String fileName = "Users.txt", fileDir = directory+fileName;
    private static final String[] columns = {"uid", "upassword","uname", "ugender", "uemail", "uphone", "urole"};


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

    public static void addNewUser(String password, String name, String gender, String email, String phone, String role){
        try {
            newEntry(getUID(role), password, name, gender, email, phone, role);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong while adding user.");
        }
    }

    public static ArrayList<ArrayList<String>> readAllUsers() throws FileNotFoundException {
        File file = new File(fileDir);
        Scanner f = new Scanner(file);
        ArrayList<ArrayList<String>> allUsers = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
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

        return allUsers;
    }

    public static ArrayList<String> readColumn(String columnName) throws IOException, RecordNotFoundException{
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
            default:
                throw new RecordNotFoundException("No such column.");
        }
    }

    public static ArrayList<String> readColumn(int columnIndex) throws IOException{
        ArrayList<ArrayList<String>> allUsers = readAllUsers();
        return allUsers.get(columnIndex);
    }

    private static void newEntry(String id, String password,String name, String gender, String email, String phone, String role) throws IOException {
        FileWriter fw = new FileWriter(fileDir, true);
        try (PrintWriter f = new PrintWriter(fw)) {
            f.println(String.format("%s,%s,%s,%s,%s,%s,%s", id, password, name, gender, email, phone, role));
        }
    }

    private static String getUID(String role) throws IOException, RecordNotFoundException {
        ArrayList<String> allID = readColumn("id");
        int intID;
        if (allID.size() != 0) {
            String lastID = allID.get(allID.size() - 1);
            intID = Integer.parseInt(lastID.substring(1));
        } else {
            intID = 0;
        }
        intID++;
        if (role.equals(Role.CUSTOMER.name())){
            return String.format("C%06d",intID);
        } else {
            return String.format("A%06d",intID);
        }
    }

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

    public static int indexOf(String id) throws IOException, RecordNotFoundException {
        ArrayList<String> uidColumn = readColumn(0);
        int target = -1;
        for (int i = 0; i < uidColumn.size(); i++) {
            if (id.equals(uidColumn.get(i))){
                target = i;
                break;
            }
        }
        if (target == -1){
            throw new RecordNotFoundException("No such record exist");
        }
        return target;
    }
}

