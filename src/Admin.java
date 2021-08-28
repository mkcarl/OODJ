public class Admin extends PurchasableUser {

    public Admin(String uid, String pw, String uname, String gender, String email, String phone_num, String role, String status){
        super(uid, pw, uname, gender, email, phone_num, role, status);
    }
    public void generateReport(){
        // TODO add report generation
    }
}
