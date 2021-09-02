import FileIO.RecordNotFoundException;
import FileIO.UserFile;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public abstract class User {
    private String user_id;
    private String user_password;
    private String user_name;
    private String user_gender;
    private String user_email;
    private String user_phone_number;
    private String user_role;
    private String user_status;

    public User(){}

    public User(String uid, String upassword, String uname, String ugender, String uemail, String uphonenumber, String urole, String ustatus){
        this.user_id = uid;
        this.user_password = upassword;
        this.user_name = uname;
        this.user_gender = ugender;
        this.user_email = uemail;
        this.user_phone_number = uphonenumber;
        this.user_role = urole;
        this.user_status = ustatus;
    }


    public User(String uid){
        try{
            ArrayList<ArrayList<String>> allUserFromFile = UserFile.readAllUsers();
            int userNumber = allUserFromFile.get(0).indexOf(uid);
            this.user_id = allUserFromFile.get(0).get(userNumber);
            this.user_password = allUserFromFile.get(1).get(userNumber);
            this.user_name = allUserFromFile.get(2).get(userNumber);
            this.user_gender = allUserFromFile.get(3).get(userNumber);
            this.user_email = allUserFromFile.get(4).get(userNumber);
            this.user_phone_number = allUserFromFile.get(5).get(userNumber);
            this.user_role = allUserFromFile.get(6).get(userNumber);
            this.user_status = allUserFromFile.get(7).get(userNumber);

        } catch (IOException e) {
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
        try {
            UserFile.updateEntry(1, UserFile.indexOf(this.user_id), user_password);
        } catch (IOException | RecordNotFoundException e) {
            e.printStackTrace();
        }

    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_phone_number() {
        return user_phone_number;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
        try {
            UserFile.updateEntry(2, UserFile.indexOf(this.user_id), user_name);
        } catch (IOException | RecordNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
        try {
            UserFile.updateEntry(4, UserFile.indexOf(this.user_id), user_email);
        } catch (IOException | RecordNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setUser_phone_number(String user_phone_number) {
        this.user_phone_number = user_phone_number;
        try {
            UserFile.updateEntry(5, UserFile.indexOf(this.user_id), user_phone_number);
        } catch (IOException | RecordNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setUser_status(String user_status){
        this.user_status = user_status;
        try {
            UserFile.updateEntry(7, UserFile.indexOf(this.user_id), user_status);
        } catch (IOException | RecordNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean isValid (String uid, String pw) throws FileNotFoundException {
    String user_id;
    String user_password;
    boolean check = false;
        try{
            ArrayList<ArrayList<String>> allUserFromFile = UserFile.readAllUsers();
            int unumber = allUserFromFile.get(0).indexOf(uid);
            if (unumber != -1) {
                user_id = allUserFromFile.get(0).get(unumber);
                user_password = allUserFromFile.get(1).get(unumber);

                if (UserFile.userExist(uid)) {
                    if (user_id.equals(uid)) {
                        if (user_password.equals(pw)) {
                            check = true;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public static String accountTypeOf(String user_id){
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

    public String getUser_status() {
        return user_status;
    }

    public static void resetPassword(String user_id, String resetCode, String newPw){
        try{
            if(resetCode.equals("177013")){
                UserFile.updateEntry(1,UserFile.indexOf(user_id),newPw);
                JOptionPane.showMessageDialog(null, "Password has been reset.");


            }else{
                JOptionPane.showMessageDialog(null,"Reset failed, check reset code and try again");
            }

        } catch (IOException | RecordNotFoundException e) {
            e.printStackTrace();
        }

    }
    public static boolean isActive(String uid){
        String user_status = "";
        try{
            ArrayList<ArrayList<String>> allUserFromFile = UserFile.readAllUsers();
            int unumber = allUserFromFile.get(0).indexOf(uid);
            if (unumber != -1) {
                user_status = allUserFromFile.get(7).get(unumber);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user_status.equals("ACTIVE");
    }
}
