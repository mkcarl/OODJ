package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author mkcarl
 */
public class MainGUI extends JFrame{
    private JPanel parentPanel;
    private JPanel loginPanel;
    private JLabel lblLogin;
    private JTextField txtUsername;
    private JPasswordField passPassword;
    private JButton loginButton;
    private JButton forgotPasswordButton;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JPanel customerPanel;
    private JLabel lblCustomer;
    private JTextField txtSearch_ManageCustomer;
    private JButton btnGo_ManageCustomer;
    private JScrollPane JScrollCustomer;
    private JTable tblCustomer;
    private JButton btnAdd;
    private JButton btnHistory_ManageCustomer;
    private JButton btnEdit_ManageCustomer;
    private JButton btnDelete_ManageCustomer;
    private JButton btnBack_ManageCustomer;
    private JPanel cartPanel;
    private JLabel lblSearch;
    private JTextField txtSearch_Cart;
    private JScrollPane JScrollCart;
    private JTable tblCart;
    private JButton btnRemove_Cart;
    private JButton btnCheckout_Cart;
    private JLabel lblSurcharge;
    private JLabel lblTotalAmount;
    private JLabel lblOrderID;
    private JLabel lblCart;
    private JPanel manageProductPanel;
    private JLabel lblManageProduct;
    private JScrollPane JScrollManageProduct;
    private JTable tblManageProduct;
    private JPanel productListingPanel;
    private JButton btnCart_ProductListing;
    private JLabel lblProductListing;
    private JScrollPane JScrollProductListing;
    private JTable tblProductListing;
    private JPanel adminWelcomePanel;
    private JButton btnCustomer_Admin;
    private JButton btnProduct_Admin;
    private JButton btnShop_Admin;
    private JButton btnGenerateReport_Admin;
    private JLabel lblWelcome_Admin;
    private JButton btnLogout_Admin;
    private JButton btnBack_ManageProduct;
    private JButton btnBack_ProductListing;
    private JButton btnBack_Cart;
    private JPanel customerWelcomePanel;
    private JButton btnShop_Customer;
    private JLabel lblWelcome_Customer;
    private JButton btnLogout_Customer;
    private JTextField txtSearch_ProductListing;
    private JButton btnGo_ProductListing;
    private JButton btnAddToCart_ProductListing;
    private JButton btnDelete_ManageProduct;
    private JButton btnEdit_ManageProduct;
    private JButton btnAdd_ManageProduct;
    private JButton btnGo_ManageProduct;
    private JTextField txtSearch_ManageProduct;
    private JButton btnGo_Cart;
    private JLabel lblSurchargeOutput;
    private JLabel lblAmountOutput;
    private JLabel lblOidOutput;
    private JButton btnPlusOne;
    private JButton btnMinusOne;
    private JPanel newCustomerPanel;
    private JTextField txtUID;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JButton btnCancel_NewCustomer;
    private JButton btnSave_NewCustomer;
    private JTextField txtPassword;
    private JTextField txtName;
    private JTextField txtEmail;
    private JTextField txtPhoneNumber;
    private JTextField txtPID;
    private JTextField txtItemName;
    private JTextField txtUnitPrice;
    private JTextField txtInventoryCount;
    private JTextField txtPackagingCharge;
    private JPanel newProductPanel;
    private JButton btnCancel_NewProduct;
    private JButton btnSave_NewProduct;
    private JLabel lblTitle_NewCustomer;
    private JLabel lblTitle_NewProduct;

    // private User currentUser;

    public MainGUI(){
        setContentPane(parentPanel);

        parentPanel.add(loginPanel, "loginPanel");
        parentPanel.add(customerPanel, "customerPanel");
        parentPanel.add(cartPanel, "cartPanel");
        parentPanel.add(manageProductPanel, "manageProductPanel");
        parentPanel.add(productListingPanel, "productListingPanel");
        parentPanel.add(adminWelcomePanel, "adminWelcomePanel");
        parentPanel.add(customerWelcomePanel, "customerWelcomePanel");
        parentPanel.add(newCustomerPanel, "newCustomerPanel");
        parentPanel.add(newProductPanel, "newProductPanel");

        final CardLayout cl = (CardLayout) parentPanel.getLayout();
        cl.show(parentPanel, "loginPanel");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cl.show(parentPanel, "customerWelcomePanel");
            }
        });
        btnLogout_Admin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cl.show(parentPanel, "loginPanel");
            }
        });
        btnCustomer_Admin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cl.show(parentPanel, "customerPanel");
            }
        });
        btnProduct_Admin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cl.show(parentPanel, "manageProductPanel");
            }
        });
        btnShop_Admin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cl.show(parentPanel, "productListingPanel");
            }
        });
        btnBack_Cart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cl.show(parentPanel, "productListingPanel");
            }
        });
        btnBack_ManageProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cl.show(parentPanel, "adminWelcomePanel");
            }
        });
        btnBack_ProductListing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cl.show(parentPanel, "adminWelcomePanel");
            }
        });
        btnBack_ManageCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cl.show(parentPanel, "adminWelcomePanel");
            }
        });
        btnShop_Customer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cl.show(parentPanel, "productListingPanel");

            }
        });
        btnLogout_Customer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cl.show(parentPanel, "loginPanel");

            }
        });
        btnCart_ProductListing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cl.show(parentPanel, "cartPanel");

            }
        });
        btnCancel_NewCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cl.show(parentPanel, "customerPanel");
            }
        });
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cl.show(parentPanel, "newCustomerPanel");
            }
        });
        btnCancel_NewProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cl.show(parentPanel, "manageProductPanel");

            }
        });
        btnAdd_ManageProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cl.show(parentPanel, "newProductPanel");
            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        MainGUI mainGUI = new MainGUI();
        mainGUI.pack();
        mainGUI.setVisible(true);
        mainGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}
