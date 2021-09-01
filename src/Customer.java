import FileIO.OrderFile;
import FileIO.ProductFile;
import FileIO.RecordNotFoundException;
import FileIO.UserFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Customer extends PurchasableUser{
    private static int userRowNum; //row number in txt file

    public Customer(String uid, String pw, String uname, String gender, String email, String phone_num, String role, String status){
        super( uid,  pw, uname, gender,  email,  phone_num,  role,  status);
    }

    public Customer (String uid) {
        super(uid);
    }

    public static void deleteCustomer(String customerID){
        try{
            int userRowNum = UserFile.indexOf(customerID);
                if(UserFile.readColumn("ustatus").get(userRowNum).equals("ACTIVE")){ // If it is originally active
                    UserFile.updateEntry(7, userRowNum, "INACTIVE"); // change it to inactive
                }
        } catch (RecordNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Customer> readAllCustomer(){
        ArrayList<Customer> allCustomer = new ArrayList<>();
        try {
            ArrayList<ArrayList<String>> allUsersFromFile = UserFile.readAllUsers();
            int numOfEntries = allUsersFromFile.get(0).size();
            for (int i = 0; i < numOfEntries; i++) {
                if (allUsersFromFile.get(6).get(i).equals("CUSTOMER")){
                    allCustomer.add(
                            new Customer(
                                    allUsersFromFile.get(0).get(i),
                                    allUsersFromFile.get(1).get(i),
                                    allUsersFromFile.get(2).get(i),
                                    allUsersFromFile.get(3).get(i),
                                    allUsersFromFile.get(4).get(i),
                                    allUsersFromFile.get(5).get(i),
                                    allUsersFromFile.get(6).get(i),
                                    allUsersFromFile.get(7).get(i)
                                    )
                    );
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return allCustomer;
    }
}
