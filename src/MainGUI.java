import FileIO.ProductFile;
import FileIO.RecordNotFoundException;
import FileIO.UserFile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author mkcarl
 */
public class MainGUI extends JFrame {
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
    private JPanel forgetPasswordPanel;
    private JLabel lblTitle_ResetPassword;
    private JTextField txtResetCode;
    private JTextField txtNewPassword;
    private JButton confirmButton;
    private JButton backButton;
    private JRadioButton fragileRadioButton;
    private JRadioButton nonFragileRadioButton;

    private User currentUser;
    ArrayList<Product> allProducts;
    ArrayList<Customer> allCustomer;
    DefaultTableModel productModel;
    DefaultTableModel cartModel;
    DefaultTableModel manageCustomerModel;
    DefaultTableModel manageProductModel;
    ButtonGroup genderGrp;
    ButtonGroup prodTypeGrp;


    public MainGUI() {
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
        parentPanel.add(forgetPasswordPanel, "forgetPasswordPanel");
        genderGrp = new ButtonGroup();
        genderGrp.add(maleRadioButton);
        genderGrp.add(femaleRadioButton);
        prodTypeGrp = new ButtonGroup();
        prodTypeGrp.add(fragileRadioButton);
        prodTypeGrp.add(nonFragileRadioButton);

        final CardLayout cl = (CardLayout) parentPanel.getLayout();
        cl.show(parentPanel, "loginPanel");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String getUID = txtUsername.getText();
                String getPass = String.valueOf(passPassword.getPassword());

                try {
                    if (User.isValid(getUID, getPass)) {
                        if (User.accountTypeOf(getUID).equals("CUSTOMER")) {
                            currentUser = new Customer(getUID);
                            if (User.isActive(getUID)) {
                                JOptionPane.showMessageDialog(null, "Login Successful! Welcome Customer !");
                                cl.show(parentPanel, "customerWelcomePanel");
                            } else {
                                JOptionPane.showMessageDialog(null, "Inactive User! Try Again");
                            }
                        } else {
                            currentUser = new Admin(getUID);
                            if (User.isActive(getUID)) {
                                JOptionPane.showMessageDialog(null, "Login Successful! Welcome Admin !");
                                cl.show(parentPanel, "adminWelcomePanel");
                            } else {
                                JOptionPane.showMessageDialog(null, "Inactive User! Try Again");
                            }
                        }
                        txtUsername.setText("");
                        passPassword.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Login Unsuccessful, Please Try Again!");
                        txtUsername.setText("");
                        passPassword.setText("");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Input Fields are empty!, Please Enter Your Login Details and Try Again.");
                }
            }
        });
        btnLogout_Admin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cl.show(parentPanel, "loginPanel");
                currentUser = null;


            }
        });
        btnCustomer_Admin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cl.show(parentPanel, "customerPanel");
                showUpdatedCustomerTable();
            }
        });
        btnProduct_Admin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cl.show(parentPanel, "manageProductPanel");
                showUpdatedProductTable();
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
                showUpdatedProductListingTable();
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
                if (currentUser instanceof Customer) {
                    cl.show(parentPanel, "customerWelcomePanel");

                } else if (currentUser instanceof Admin) {
                    cl.show(parentPanel, "adminWelcomePanel");
                }
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
                currentUser = null;

            }
        });
        btnCart_ProductListing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cl.show(parentPanel, "cartPanel");
                showUpdatedCartTable();

                for (int i = 0; i < ((PurchasableUser) currentUser).getOrder_cart().getOrderItems().size(); i++) {
                    OrderItem currentOrderItem = ((PurchasableUser) currentUser).getOrder_cart().getOrderItems().get(i);
                    if (! currentOrderItem.isAvailable()) {
                        JOptionPane.showMessageDialog(
                                null,
                                String.format(
                                        "Item (%s) has been discontinued. It will be removed from your cart.",
                                        currentOrderItem.getItemProduct().getProductName()
                                ),
                                "Warning",
                                JOptionPane.WARNING_MESSAGE);
                        ((PurchasableUser) currentUser).getOrder_cart().removeItem(i);
                        showUpdatedCartTable();
                    }
                }
            }
        });
        btnCancel_NewCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cl.show(parentPanel, "customerPanel");
                txtUID.setText("");
                txtPassword.setText("");
                txtName.setText("");
                txtEmail.setText("");
                txtPhoneNumber.setText("");
                genderGrp.clearSelection();
                cl.show(parentPanel, "customerPanel");


            }
        });
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                lblTitle_NewCustomer.setText("New Customer");
                cl.show(parentPanel, "newCustomerPanel");
                try {
                    String displayUID = UserFile.getUID("CUSTOMER");
                    txtUID.setText(displayUID);
                    txtUID.setEnabled(false);
                    femaleRadioButton.setEnabled(true);
                    maleRadioButton.setEnabled(true);
                } catch (IOException | RecordNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        btnCancel_NewProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cl.show(parentPanel, "manageProductPanel");
                txtPID.setText("");
                txtItemName.setText("");
                txtUnitPrice.setText("");
                txtInventoryCount.setText("");
                txtPackagingCharge.setText("");
                prodTypeGrp.clearSelection();
                cl.show(parentPanel, "manageProductPanel");
                showUpdatedProductTable();

            }
        });
        btnAdd_ManageProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                lblTitle_NewProduct.setText("New Product");
                cl.show(parentPanel, "newProductPanel");
                try {
                    String displayPID = ProductFile.getPID();
                    txtPID.setText(displayPID);
                    txtPID.setEnabled(false);
                    txtPackagingCharge.setEnabled(false);
                    fragileRadioButton.setEnabled(true);
                    nonFragileRadioButton.setEnabled(true);

                } catch (IOException | RecordNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        btnAddToCart_ProductListing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (tblProductListing.getSelectedRow() != -1) {
                    int index = tblProductListing.getSelectedRow();
                    String pid = (String) tblProductListing.getValueAt(index, 0);
                    boolean alreadyInCart = false;
                    for (OrderItem oi :
                            ((PurchasableUser) currentUser).getOrder_cart().getOrderItems()) {
                        if (oi.getItemProduct().getProductID().equals(pid)) {
                            alreadyInCart = true;
                            break;
                        }
                    }
                    if (alreadyInCart) {
                        JOptionPane.showMessageDialog(null, "This item is already in cart!", "Warning", JOptionPane.WARNING_MESSAGE);

                    } else {
                        for (Product prod :
                                allProducts) {
                            if (prod.getProductID().equals(pid)) {
                                ((PurchasableUser) currentUser).getOrder_cart().addItem(prod);
                                JOptionPane.showMessageDialog(null, "Successfully added to cart!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            }

                        }
                    }


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


        txtSearch_Cart.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                super.keyReleased(keyEvent);
                searchCartTable();
            }
        });
        forgotPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (UserFile.userExist(txtUsername.getText())) {
                        cl.show(parentPanel, "forgetPasswordPanel");
                    }

                } catch (RecordNotFoundException | IOException e) {
                    JOptionPane.showMessageDialog(null, "User ID invalid, Please Check and Try Again");
                }
            }
        });
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String resetCode = txtResetCode.getText();
                String newPw = txtNewPassword.getText();
                String getUID = txtUsername.getText();
                User.resetPassword(getUID, resetCode, newPw);
                JOptionPane.showMessageDialog(null, "Password has been reset.");
                txtResetCode.setText("");
                txtNewPassword.setText("");
                cl.show(parentPanel, "loginPanel");
            }
        });
        txtSearch_ManageCustomer.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                super.keyReleased(keyEvent);
                searchCustomer();

            }
        });
        txtSearch_Cart.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                super.keyReleased(keyEvent);
                searchCartTable();
            }
        });
        txtSearch_ManageProduct.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                super.keyReleased(keyEvent);
                searchProduct();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cl.show(parentPanel, "loginPanel");
            }
        });
        btnSave_NewCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String PATTERN = "^[A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                Pattern pattern = Pattern.compile(PATTERN);
                Matcher matcher = pattern.matcher(txtEmail.getText());
                if (matcher.matches()) {
                    if (lblTitle_NewCustomer.getText().contains("Edit")) {
                        JOptionPane.showMessageDialog(null, "User Details have been updated!");
                        editCustomer();


                    } else if (lblTitle_NewCustomer.getText().contains("New")) {
                        setNewCustomer();
                        JOptionPane.showMessageDialog(null, "New Customer Created!");

                    }
                    txtUID.setText("");
                    txtPassword.setText("");
                    txtName.setText("");
                    txtEmail.setText("");
                    txtPhoneNumber.setText("");
                    genderGrp.clearSelection();
                    cl.show(parentPanel, "customerPanel");
                    showUpdatedCustomerTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect Email Format! Please Check and Try Again", "WARNING", JOptionPane.WARNING_MESSAGE);
                    txtEmail.setText("");
                }

            }
        });
        btnEdit_ManageCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int index = tblCustomer.getSelectedRow();
                if (index != -1) {
                    txtUID.setEnabled(false);
                    String uid = (String) tblCustomer.getValueAt(index, 0);
                    txtUID.setText(uid);
                    String userName = (String) tblCustomer.getValueAt(index, 2);
                    txtName.setText(userName);
                    String password = (String) tblCustomer.getValueAt(index, 1);
                    txtPassword.setText(password);
                    String gender = (String) tblCustomer.getValueAt(index, 3);
                    if (gender.equals("MALE")) {
                        maleRadioButton.doClick();
                    } else if (gender.equals("FEMALE")) {
                        femaleRadioButton.doClick();
                    }
                    maleRadioButton.setEnabled(false);
                    femaleRadioButton.setEnabled(false);
                    String email = (String) tblCustomer.getValueAt(index, 4);
                    txtEmail.setText(email);
                    String phoneNo = (String) tblCustomer.getValueAt(index, 5);
                    txtPhoneNumber.setText(phoneNo);
                    lblTitle_NewCustomer.setText("Edit Customer");
                    cl.show(parentPanel, "newCustomerPanel");
                } else {
                    JOptionPane.showMessageDialog(null, "Please Select an Item", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        btnSave_NewProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if (lblTitle_NewProduct.getText().contains("Edit")) {
                    JOptionPane.showMessageDialog(null, "Product Has Been Edited!");
                    editProduct();

                } else if (lblTitle_NewProduct.getText().contains("New")) {
                    JOptionPane.showMessageDialog(null, "New Product Created!");
                    setNewProduct();
                }
                txtPID.setText("");
                txtItemName.setText("");
                txtUnitPrice.setText("");
                txtInventoryCount.setText("");
                txtPackagingCharge.setText("");
                prodTypeGrp.clearSelection();
                cl.show(parentPanel, "manageProductPanel");
                showUpdatedProductTable();
            }
        });
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (fragileRadioButton.isSelected()) {
                    txtPackagingCharge.setText("1.5");
                } else if (nonFragileRadioButton.isSelected()) {
                    txtPackagingCharge.setText("0.5");
                }
            }

        };
        fragileRadioButton.addActionListener(listener);
        nonFragileRadioButton.addActionListener(listener);

        btnEdit_ManageProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int index = tblManageProduct.getSelectedRow();
                if (index != -1) {
                    txtPID.setEnabled(false);
                    txtPackagingCharge.setEnabled(false);
                    String pid = (String) tblManageProduct.getValueAt(index, 0);
                    txtPID.setText(pid);
                    String itemName = (String) tblManageProduct.getValueAt(index, 1);
                    txtItemName.setText(itemName);
                    String unitPrice = Double.toString((Double) tblManageProduct.getValueAt(index, 3));
                    txtUnitPrice.setText(unitPrice);
                    String inventoryCount = Integer.toString((Integer) tblManageProduct.getValueAt(index, 5));
                    txtInventoryCount.setText(inventoryCount);
                    String ptype = (String) tblManageProduct.getValueAt(index, 2);
                    if (ptype.equals("FRAGILE")) {
                        fragileRadioButton.doClick();
                    } else if (ptype.equals("NOT_FRAGILE")) {
                        nonFragileRadioButton.doClick();
                    }
                    String pkgCharge = Double.toString((Double) tblManageProduct.getValueAt(index, 4));
                    txtPackagingCharge.setText(pkgCharge);

                    lblTitle_NewProduct.setText("Edit Product");
                    cl.show(parentPanel, "newProductPanel");
                } else {
                    JOptionPane.showMessageDialog(null, "Please Select an Item", "Warning", JOptionPane.WARNING_MESSAGE);
                }


            }
        });
        btnDelete_ManageProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int index = tblManageProduct.getSelectedRow();
                if (index != -1) {
                    String col = (String) tblManageProduct.getValueAt(index, 0);
                    Product.deleteProduct(col);
                    JOptionPane.showMessageDialog(null, "Product Deleted !");
                    showUpdatedProductTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Please Select an Item", "Warning", JOptionPane.WARNING_MESSAGE);
                }

            }
        });
        btnDelete_ManageCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int index = tblCustomer.getSelectedRow();
                if (index != -1) {
                    String col = (String) tblCustomer.getValueAt(index, 0);
                    Customer.deleteCustomer(col);
                    JOptionPane.showMessageDialog(null, "Customer Deleted !");
                    showUpdatedCustomerTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Please Select an Item", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        btnPlusOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int index = tblCart.getSelectedRow();
                if (index != -1) {
                    OrderItem currentOrderItem = ((PurchasableUser) currentUser).getOrder_cart().getOrderItems().get(index);
                    int currentQuantity = currentOrderItem.getItemQuantity();
                    if (
                            currentOrderItem.isEnough(currentQuantity + 1)
                    ) {
                        ((PurchasableUser) currentUser).getOrder_cart().getOrderItems().get(index).modifyQuantity(currentQuantity + 1);
                        ((PurchasableUser) currentUser).getOrder_cart().updateQuantityOf(index, currentQuantity + 1);
                        showUpdatedCartTable();
                        tblCart.setRowSelectionInterval(index, index);

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an item!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        btnMinusOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int index = tblCart.getSelectedRow();
                if (index != -1) {
                    OrderItem currentOrderItem = ((PurchasableUser) currentUser).getOrder_cart().getOrderItems().get(index);
                    int currentQuantity = currentOrderItem.getItemQuantity();
                    if (
                            currentOrderItem.isEnough(currentQuantity - 1)
                    ) {
                        ((PurchasableUser) currentUser).getOrder_cart().getOrderItems().get(index).modifyQuantity(currentQuantity - 1);
                        ((PurchasableUser) currentUser).getOrder_cart().updateQuantityOf(index, currentQuantity - 1);
                        showUpdatedCartTable();
                        tblCart.setRowSelectionInterval(index, index);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an item!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        btnRemove_Cart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int index = tblCart.getSelectedRow();
                if (index != -1) {
                    ((PurchasableUser) currentUser).getOrder_cart().removeItem(index);
                    showUpdatedCartTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an item!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        btnCheckout_Cart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (tblCart.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "There is nothing to check out!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    String[] buttons = {"Yes", "No"};
                    int choice = JOptionPane.showOptionDialog(null, "Checking out. Do you want an invoice?", "Confirmation",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[0]);

                    class EmailWorker extends SwingWorker<Integer, Integer> {
                        protected Integer doInBackground() throws Exception {
                            // Do a time-consuming task.
                            ((PurchasableUser) currentUser).checkOut(choice == 0);
                            return 42;
                        }

                        protected void done() {
                        }
                    }
                    new EmailWorker().execute();


                    if (choice == 0){
                        JOptionPane.showMessageDialog(
                                null,
                                String.format(
                                        "Paid RM %.2f. Copy of invoice is sent to %s",
                                        ((PurchasableUser) currentUser).getOrder_cart().calculateFinal(),
                                        currentUser.user_email
                                ),
                                "Transaction successful",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                String.format(
                                        "Paid RM %.2f. Please come again",
                                        ((PurchasableUser) currentUser).getOrder_cart().calculateFinal()
                                ),
                                "Transaction successful",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                    if (currentUser instanceof Admin) {
                        currentUser = new Admin(currentUser.user_id);
                    } else {
                        currentUser = new Customer(currentUser.user_id);
                    }
                    showUpdatedCartTable();
                }
            }
        });
        btnGenerateReport_Admin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String[] buttons = {"Yes", "No"};
                int choice = JOptionPane.showOptionDialog(null, "Create a sales report for the last 30 days?", "Confirmation",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[0]);

                if (choice == 0) {
                    String pdfDir = ((Admin) currentUser).generateReport();
                    JOptionPane.showMessageDialog(null, "Report generated", "Information", JOptionPane.INFORMATION_MESSAGE);
                    if (Desktop.isDesktopSupported()) {
                        try {
                            File myFile = new File(pdfDir);
                            Desktop.getDesktop().open(myFile);
                        } catch (IOException ex) {
                            // no application registered for PDFs
                        }
                    }

                }
            }
        });
    }

    private void createUIComponents() {
        // Product listing page table initialisation
        Object[] columnNames = {"Product ID", "Name", "Type", "Unit price (RM)", "Packaging charge (RM)", "Stock available"};
        productModel = new DefaultTableModel(0, columnNames.length) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productModel.setColumnIdentifiers(columnNames);
        tblProductListing = new JTable(productModel);
        tblProductListing.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblProductListing.setFont(tblProductListing.getFont().deriveFont((float) 16));
        tblProductListing.setRowHeight(tblProductListing.getRowHeight() + 8);
        tblProductListing.getTableHeader().setFont(
                tblProductListing.getTableHeader().getFont().deriveFont(Font.BOLD, (float) 16)
        );
        tblProductListing.getTableHeader().setReorderingAllowed(false);

        // Cart page table initialisation
        columnNames = new Object[]{"Product Name", "Unit price (RM)", "Packaging charge (RM)", "Quantity", "Sub-total"};
        cartModel = new DefaultTableModel(0, columnNames.length) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        cartModel.setColumnIdentifiers(columnNames);
        tblCart = new JTable(cartModel);
        tblCart.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblCart.setFont(tblCart.getFont().deriveFont((float) 16));
        tblCart.setRowHeight(tblCart.getRowHeight() + 8);
        tblCart.getTableHeader().setFont(
                tblCart.getTableHeader().getFont().deriveFont(Font.BOLD, (float) 16)
        );
        tblCart.getTableHeader().setReorderingAllowed(false);

        // Manage Customer page table initialisation
        columnNames = new Object[]{"User ID", "User Password", "User Name", "Gender", "Email", "Phone Number"};
        manageCustomerModel = new DefaultTableModel(0, columnNames.length) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        manageCustomerModel.setColumnIdentifiers(columnNames);
        tblCustomer = new JTable(manageCustomerModel);
        tblCustomer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblCustomer.setFont(tblCustomer.getFont().deriveFont((float) 16));
        tblCustomer.setRowHeight(tblCustomer.getRowHeight() + 8);
        tblCustomer.getTableHeader().setFont(
                tblCustomer.getTableHeader().getFont().deriveFont(Font.BOLD, (float) 16)
        );
        // Manage Product table initialisation
        columnNames = new Object[]{"Product ID", "Product Name", "Product Type", "Unit Price", "Packaging Charge", "Inventory Count"};
        manageProductModel = new DefaultTableModel(0, columnNames.length) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        manageProductModel.setColumnIdentifiers(columnNames);
        tblManageProduct = new JTable(manageProductModel);
        tblManageProduct.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblManageProduct.setFont(tblManageProduct.getFont().deriveFont((float) 16));
        tblManageProduct.setRowHeight(tblManageProduct.getRowHeight() + 8);
        tblManageProduct.getTableHeader().setFont(
                tblManageProduct.getTableHeader().getFont().deriveFont(Font.BOLD, (float) 16)
        );
    }

    private void showUpdatedProductListingTable() {
        productModel.setRowCount(0); // clear all rows
        txtSearch_ProductListing.setText(""); // clear search box

        allProducts = Product.readAllProduct();

        // display all products if active and have stock
        for (Product prod :
                allProducts) {
            if (prod.getProductStatus().equals("ACTIVE") && prod.getProductInventoryCount() > 0) {
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

    private void searchProductListingTable() {
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

    private void showUpdatedCartTable() {
        cartModel.setRowCount(0);
        txtSearch_Cart.setText("");

        for (OrderItem oi :
                ((PurchasableUser) currentUser).getOrder_cart().getOrderItems()) {
            Object[] details = {
                    oi.getItemProduct().getProductName(),
                    oi.getItemProduct().getProductUnitPrice(),
                    oi.getItemProduct().getProductPackagingCharge(),
                    oi.getItemQuantity(),
                    oi.getItemAmount()
            };
            cartModel.insertRow(cartModel.getRowCount(), details);
        }
        showUpdatedCheckOutDetails();

    }

    private void searchCartTable() {
        cartModel.setRowCount(0);

        for (OrderItem oi :
                ((PurchasableUser) currentUser).getOrder_cart().getOrderItems()) {
            if (oi.getItemProduct().getProductName().toLowerCase().contains(txtSearch_Cart.getText().toLowerCase())) {
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

        if (txtSearch_Cart.getText().isEmpty()) {
            btnPlusOne.setEnabled(true);
            btnMinusOne.setEnabled(true);
        } else {
            btnPlusOne.setEnabled(false);
            btnMinusOne.setEnabled(false);
        }
    }

    private void showUpdatedCheckOutDetails() {
        lblOidOutput.setText(((PurchasableUser) currentUser).getOrder_cart().getOrderID());
        lblGrandTotal.setText(String.format("RM %.2f", ((PurchasableUser) currentUser).getOrder_cart().calculateFinal()));
        double itemTotal = 0, packingTotal = 0;
        for (OrderItem oi :
                (((PurchasableUser) currentUser).getOrder_cart().getOrderItems())) {
            itemTotal += oi.getItemProduct().getProductUnitPrice() * oi.getItemQuantity();
            packingTotal += oi.getItemProduct().getProductPackagingCharge() * oi.getItemQuantity();
        }
        lblItemTotalOutput.setText(String.format("RM %.2f", itemTotal));
        lblSurchargeOutput.setText(String.format("RM %.2f", packingTotal));
    }

    private void showUpdatedCustomerTable() {
        manageCustomerModel.setRowCount(0);
        txtSearch_ManageCustomer.setText("");
        allCustomer = Customer.readAllCustomer();

        for (Customer cust :
                allCustomer) {
            if (cust.getUser_status().equals("ACTIVE")) {
                Object[] customerList = {
                        cust.getUser_id(),
                        cust.getUser_password(),
                        cust.getUser_name(),
                        cust.getUser_gender(),
                        cust.getUser_email(),
                        cust.getUser_phone_number(),
                        cust.getUser_status()
                };
                manageCustomerModel.insertRow(manageCustomerModel.getRowCount(), customerList);
            }
        }

    }


    private void showUpdatedProductTable() {
        manageProductModel.setRowCount(0);
        txtSearch_ManageProduct.setText("");
        allProducts = Product.readAllProduct();

        for (Product prod :
                allProducts) {
            if (prod.getProductStatus().equals("ACTIVE")) {
                Object[] productList = {
                        prod.getProductID(),
                        prod.getProductName(),
                        prod.getProductType(),
                        prod.getProductUnitPrice(),
                        prod.getProductPackagingCharge(),
                        prod.getProductInventoryCount(),
                        prod.getProductStatus()
                };
                manageProductModel.insertRow(manageProductModel.getRowCount(), productList);
            }
        }
    }

    private void searchCustomer() {
        manageCustomerModel.setRowCount(0);
        for (Customer cust :
                allCustomer) {
            if (cust.getUser_name().toLowerCase().contains(txtSearch_ManageCustomer.getText().toLowerCase())
                    && cust.getUser_status().equals("ACTIVE")) {
                Object[] customerList = {
                        cust.getUser_id(),
                        cust.getUser_password(),
                        cust.getUser_name(),
                        cust.getUser_gender(),
                        cust.getUser_email(),
                        cust.getUser_phone_number(),
                        cust.getUser_status()
                };
                manageCustomerModel.insertRow(manageCustomerModel.getRowCount(), customerList);
            }
        }
    }

    private void searchProduct() {
        manageProductModel.setRowCount(0);
        for (Product prod :
                allProducts) {
            if (prod.getProductName().toLowerCase().contains(txtSearch_ManageProduct.getText().toLowerCase())
                    && prod.getProductStatus().equals("ACTIVE")
                    && prod.getProductInventoryCount() > 0) {
                Object[] productList = {
                        prod.getProductID(),
                        prod.getProductName(),
                        prod.getProductType(),
                        prod.getProductUnitPrice(),
                        prod.getProductPackagingCharge(),
                        prod.getProductInventoryCount(),
                        prod.getProductStatus()
                };
                manageProductModel.insertRow(manageProductModel.getRowCount(), productList);
            }

        }
    }

    private void setNewCustomer() {
        UserFile newUser = new UserFile();
        String gender = "";
        String getPassword = txtPassword.getText();
        String getName = txtName.getText();
        String getEmail = txtEmail.getText();
        String getPhoneNumber = txtPhoneNumber.getText();
        maleRadioButton.setActionCommand(maleRadioButton.getText());
        femaleRadioButton.setActionCommand(femaleRadioButton.getText());

        if (femaleRadioButton.isSelected()) {
            gender = "FEMALE";
        } else {
            gender = "MALE";
        }

        UserFile.addNewUser(getPassword, getName, gender, getEmail, getPhoneNumber, "CUSTOMER", "ACTIVE");

    }

    private void setNewProduct() {
        ProductFile newProduct = new ProductFile();
        String getItemName = txtItemName.getText();
        String ptype = "";
        double getUnitPrice = Double.parseDouble(txtUnitPrice.getText());
        double packagingCharge = Double.parseDouble(txtPackagingCharge.getText());
        int getInventoryCount = Integer.parseInt(txtInventoryCount.getText());
        String status = "";
        fragileRadioButton.setActionCommand(fragileRadioButton.getText());
        nonFragileRadioButton.setActionCommand(nonFragileRadioButton.getText());

        if (fragileRadioButton.isSelected()) {
            ptype = "FRAGILE";
        } else {
            ptype = "NOT_FRAGILE";

        }
        status = "ACTIVE";
        Product.addProduct(getItemName, ptype, getUnitPrice, packagingCharge, getInventoryCount, status);
    }

    private void editProduct() {
        int index = tblManageProduct.getSelectedRow();
        if (index != -1) {
            String pid = (String) tblManageProduct.getValueAt(index, 0);
            for (Product prod :
                    allProducts) {
                if (pid.equals(prod.getProductID())) {
                    prod.setProductName(txtItemName.getText());
                    prod.setProductType(
                            fragileRadioButton.isSelected() ? "FRAGILE" :
                                    nonFragileRadioButton.isSelected() ? "NOT_FRAGILE" : ""
                    );
                    prod.setProductUnitPrice(Double.parseDouble(txtUnitPrice.getText()));
                    prod.setInventoryCount(Integer.parseInt(txtInventoryCount.getText()));


                }
            }
        }


    }

    private void editCustomer() {
        int index = tblCustomer.getSelectedRow();
        if (index != -1) {
            String uid = (String) tblCustomer.getValueAt(index, 0);
            for (Customer customer :
                    allCustomer) {
                if (uid.equals(customer.getUser_id())) {
                    customer.setUser_password(txtPassword.getText());
                    customer.setUser_name(txtName.getText());
                    customer.setUser_email(txtEmail.getText());
                    customer.setUser_phone_number(txtPhoneNumber.getText());
                }

            }
        }
    }

}
