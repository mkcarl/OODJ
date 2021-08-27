import FileIO.RecordNotFoundException;
import FileIO.UserFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public abstract class User {
    private static int rowNo;
    protected String user_id;
    protected String user_password;
    protected String user_name;
    protected String user_gender;
    protected String user_email;
    protected String user_phone_number;
    protected String user_role;
    protected String user_status;

//    private int rowNo;

    public User(){
    }

    public User(String uid, String upassword, String uname, String ugender, String uemail, String uphonenumber, String urole, String ustatus){
        this.user_id = uid;
        this.user_password = upassword;
        this.user_name = uname;
        this.user_gender = ugender;
        this.user_email = uemail;
        this.user_phone_number = uphonenumber;
        this.user_role = urole;
        this.user_status = ustatus;

        try{
            this.rowNo = UserFile.indexOf(user_id);
        } catch (RecordNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
    public User(String uid){
        try{
            ArrayList<ArrayList<String>>allUserFromFile = UserFile.readAllUsers();
            int userNumber = allUserFromFile.get(0).indexOf(uid);
            this.user_id = allUserFromFile.get(1).get(userNumber);
            this.user_password = allUserFromFile.get(2).get(userNumber);
            this.user_name = allUserFromFile.get(3).get(userNumber);
            this.user_gender = allUserFromFile.get(4).get(userNumber);
            this.user_email = allUserFromFile.get(5).get(userNumber);
            this.user_phone_number = allUserFromFile.get(6).get(userNumber);
            this.user_role = allUserFromFile.get(7).get(userNumber);
            this.user_status = allUserFromFile.get(8).get(userNumber);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getUser_id() {
        return user_id;
    }


    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;

    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_phone_number() {
        return user_phone_number;
    }

    public void setUser_phone_number(String user_phone_number) {
        this.user_phone_number = user_phone_number;
    }

    public static boolean isValid (String uid, String pw) throws FileNotFoundException {
    String user_id = "";
    String user_password = "";
    boolean check = false;
        try{
            ArrayList<ArrayList<String>> allUserFromFile = UserFile.readAllUsers();
            int unumber = allUserFromFile.get(0).indexOf(uid);
            user_id = allUserFromFile.get(1).get(unumber);
            user_password = allUserFromFile.get(2).get(unumber);

            if (UserFile.userExist(uid)){
                rowNo = UserFile.indexOf(uid);
                if (user_id.equals(uid)){
                    if (user_password.equals(pw)){
                        return check = true;
                    }else{
                        return check = false;
                    }
                }
            }
/*            if(user_id.equals(uid) && user_password.equals(pw)){
                check = true;
            }else{
                check = false;
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    private static void readAllUsers() {
    }


    public static String accountTypeOf(String user_id){
        //TODO check first prefix of user_id and return Admin or Customer
        String role = "";
        try{
            if (user_id.startsWith("C")){
                role = "CUSTOMER";
            }else{
                role =  "ADMIN";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return role;
    }

    public static void resetPassword(String user_id, String resetCode, String newPw){
        try{
            if(resetCode.equals("177013")){
                UserFile.updateEntry(1,UserFile.indexOf(user_id),newPw);

            }

        } catch (IOException | RecordNotFoundException e) {
            e.printStackTrace();
        }

    }
}
