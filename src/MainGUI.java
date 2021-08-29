import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

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
    private JButton btnAddToCart_ProductListing;
    private JButton btnDelete_ManageProduct;
    private JButton btnEdit_ManageProduct;
    private JButton btnAdd_ManageProduct;
    private JTextField txtSearch_ManageProduct;
    private JLabel lblSurchargeOutput;
    private JLabel lblItemTotalOutput;
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
    private JLabel lblGrandTotal;

    private User currentUser;
    ArrayList<Product> allProducts;
    DefaultTableModel productModel;
    DefaultTableModel cartModel;

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
                showUpdatedProductListingTable();
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
                showUpdatedProductListingTable();
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
                showUpdatedCartTable();

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
        btnAddToCart_ProductListing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (tblProductListing.getSelectedRow() != -1){
                    int index = tblProductListing.getSelectedRow();
                    System.out.println(index);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an item to be added to cart!");
                }
            }
        });

        txtSearch_ProductListing.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                super.keyReleased(keyEvent);
                searchProductListingTable();
            }
        });
        txtSearch_ProductListing.addKeyListener(new KeyAdapter() {
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                currentUser = new Customer("C000001");
            }
        });
        txtSearch_Cart.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                super.keyReleased(keyEvent);
                searchCartTable();
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
        mainGUI.setSize(1280, 720);
        mainGUI.setResizable(false);
        mainGUI.setVisible(true);
        mainGUI.setLocationRelativeTo(null); // middle of screen
        mainGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    private void createUIComponents() {
        // Product listing page table initialisation
        Object[] columnNames = {"Product ID", "Name", "Type", "Unit price", "Packaging charge", "Stock available"};
        productModel = new DefaultTableModel(0, columnNames.length){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        productModel.setColumnIdentifiers(columnNames);
        tblProductListing = new JTable(productModel);
        tblProductListing.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblProductListing.setFont(tblProductListing.getFont().deriveFont((float) 16));
        tblProductListing.setRowHeight(tblProductListing.getRowHeight()+8);
        tblProductListing.getTableHeader().setFont(
                tblProductListing.getTableHeader().getFont().deriveFont(Font.BOLD, (float) 16)
        );
        // Cart page table initialisation
        columnNames = new Object[]{"Product Name", "Unit price", "Packaging charge", "Quantity", "Sub-total"};
        cartModel = new DefaultTableModel(0, columnNames.length){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        cartModel.setColumnIdentifiers(columnNames);
        tblCart = new JTable(cartModel);
        tblCart.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblCart.setFont(tblCart.getFont().deriveFont((float) 16));
        tblCart.setRowHeight(tblCart.getRowHeight()+8);
        tblCart.getTableHeader().setFont(
                tblCart.getTableHeader().getFont().deriveFont(Font.BOLD, (float) 16)
        );
    }

    private void showUpdatedProductListingTable(){
        productModel.setRowCount(0); // clear all rows
        txtSearch_ProductListing.setText(""); // clear search box

        allProducts = Product.readAllProduct();

        // display all products if active and have stock
        for (Product prod :
                allProducts) {
            if (prod.getProductStatus().equals("ACTIVE") && prod.getProductInventoryCount() > 0){
                Object[] details = {
                        prod.getProductID(),
                        prod.getProductName(),
                        prod.getProductType(),
                        prod.getProductUnitPrice(),
                        prod.getProductPackagingCharge(),
                        prod.getProductInventoryCount()
                };
                productModel.insertRow(productModel.getRowCount(), details);
            }
        }
    }

    private void searchProductListingTable(){
        productModel.setRowCount(0); // clear all rows

        for (Product prod :
                allProducts) {
            if (
                    prod.getProductName().toLowerCase().contains(txtSearch_ProductListing.getText().toLowerCase())
                            && prod.getProductStatus().equals("ACTIVE")
                            && prod.getProductInventoryCount() > 0
            ) {
                Object[] details = {
                        prod.getProductID(),
                        prod.getProductName(),
                        prod.getProductType(),
                        prod.getProductUnitPrice(),
                        prod.getProductPackagingCharge(),
                        prod.getProductInventoryCount()
                };
                productModel.insertRow(productModel.getRowCount(), details);
            }
        }
    }

    private void showUpdatedCartTable(){
        cartModel.setRowCount(0);
        txtSearch_Cart.setText("");

        for (OrderItem oi :
                ((Customer) currentUser).getOrder_cart().getOrderItems()) {
            Object[] details = {
                    oi.getItemProduct().getProductName(),
                    oi.getItemProduct().getProductUnitPrice(),
                    oi.getItemProduct().getProductPackagingCharge(),
                    oi.getItemQuantity(),
                    oi.getItemAmount()
            };
            cartModel.insertRow(cartModel.getRowCount(), details);
        }
    }

    private void searchCartTable(){
        cartModel.setRowCount(0);

        for (OrderItem oi :
                ((Customer) currentUser).getOrder_cart().getOrderItems()) {
            if(oi.getItemProduct().getProductName().toLowerCase().contains(txtSearch_Cart.getText().toLowerCase())) {
                Object[] details = {
                        oi.getItemProduct().getProductName(),
                        oi.getItemProduct().getProductUnitPrice(),
                        oi.getItemProduct().getProductPackagingCharge(),
                        oi.getItemQuantity(),
                        oi.getItemAmount()
                };
                cartModel.insertRow(cartModel.getRowCount(), details);
            }
        }
    }


}
