public class PurchasableUser extends User implements Purchasable{
    private Order order_cart;

    public PurchasableUser(String uid, String pw, String uname, String gender, String email, String phone_num, String role, String status){
        super( uid,  pw, uname ,gender,  email,  phone_num,  role,  status);



    }

    public Order getOrder_cart() {
        return order_cart;
    }
    public void setOrder_cart(Order order_cart) {
        this.order_cart = order_cart;
    }

    public void generateInvoice(){
        //pdf
    }
    public double balance(double amountPaid){
        //ignore dulu (will use OrderItems)
    }
    public void checkOut(){

    }
}
