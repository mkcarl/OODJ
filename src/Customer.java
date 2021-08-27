import FileIO.OrderFile;
import FileIO.ProductFile;
import FileIO.RecordNotFoundException;
import FileIO.UserFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Customer extends User{
    private static int userRowNum; //row number in txt file

    public Customer(String uid, String pw, String uname, String gender, String email, String phone_num, String role, String status){
        super( uid,  pw, uname, gender,  email,  phone_num,  role,  status);
    }

    public static void addCustomer(String uid, String pw, String gender, String email, String phone_num, String role, String status){
        UserFile.addNewUser(uid,pw,gender,email,phone_num,role,status);
    }
    public static void deleteCustomer(String customerID){
        try{
            int userRowNum = UserFile.indexOf(customerID);
                if(UserFile.readColumn("uid").get(userRowNum).equals("INACTIVE")){
                    UserFile.readColumn(UserStatus.INACTIVE.name());
                }
        } catch (RecordNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public Customer (String uid){
        try{
            ArrayList<ArrayList<String>> allUserFromFile = UserFile.readAllUsers();
            int entryNumber = allUserFromFile.get(0).indexOf(uid);

            this.user_id = allUserFromFile.get(1).get(entryNumber);
            this.user_password = allUserFromFile.get(2).get(entryNumber);
            this.user_name = allUserFromFile.get(3).get(entryNumber);
            this.user_gender = allUserFromFile.get(4).get(entryNumber);
            this.user_email = allUserFromFile.get(5).get(entryNumber);
            this.user_phone_number = allUserFromFile.get(6).get(entryNumber);
            this.user_role = allUserFromFile.get(7).get(entryNumber);
            this.user_status = allUserFromFile.get(8).get(entryNumber);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
//    public static ArrayList<User> searchCustomer(String mode, String keyword) {
//        ArrayList<ArrayList<String>> allUser = null;
//        ArrayList<User> searchResult =new ArrayList<>();
//        try {
//            allUser = UserFile.readAllUsers();
//            switch (mode) {
//                case "uid":
//                    for (User user :
//                            allUser) {
//                        if (user.user_id.equals(keyword)) {
//                            searchResult.add(user);
//                            break;
//                        }
//                    }
//                    break;
//                case "uname":
//                    for (User user :
//                            allUser) {
//                        if (user.user_name.contains(keyword)) {
//                            searchResult.add(user);
//                        }
//                    }
//                    break;
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        return searchResult;
//    }
/*
    public void queryOrderHistory(String uid){
        try{
//            ArrayList<ArrayList<String>> allUserFromFile = UserFile.readAllUsers(); //user file
//            int userNo = allUserFromFile.get(0).indexOf(uid);

            ArrayList<Integer> allOID = OrderFile.indicesOf(uid); //order file
            for

            ArrayList<ArrayList<String>> allProductInOrder = ProductFile.readAllProducts(); //order product file
            int product = allProductInOrder.get(0).indexOf(uid);
            this. = allProductInOrder.get(1).get(prodOrder);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

 */
}
