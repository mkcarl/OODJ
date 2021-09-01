import FileIO.OrderFile;
import FileIO.RecordNotFoundException;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public abstract class PurchasableUser extends User implements Purchasable {
    private Order order_cart;

    public PurchasableUser(String uid, String pw, String uname, String gender, String email, String phone_num, String role, String status) {
        super(uid, pw, uname, gender, email, phone_num, role, status);
        // if they have a cart, and if the cart is not cheched out, use old cart, or else, give them new cart

        ArrayList<Integer> indexOfAllOrderID;
        ArrayList<ArrayList<String>> allOrderFromFile;
        String latestOID;

        try {
            // if there is existing orders
            indexOfAllOrderID = OrderFile.indicesOf(this.user_id); // all the order id of this particular user
            allOrderFromFile = OrderFile.readAllOrders(); // all the orders
            latestOID = allOrderFromFile.get(0).get(indexOfAllOrderID.get(indexOfAllOrderID.size() - 1)); // get the last order id of this dude

            if (allOrderFromFile.get(2).get(indexOfAllOrderID.get(indexOfAllOrderID.size() - 1)).equals("PENDING")) {
                this.order_cart = new Order(latestOID);
            } else {
                throw new RecordNotFoundException();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (RecordNotFoundException e) { // if there are no records for them
            Order.addOrder(this.user_id); // create a new entry for them first
            try {
                // basically same thing as above
                indexOfAllOrderID = OrderFile.indicesOf(this.user_id);
                allOrderFromFile = OrderFile.readAllOrders();
                latestOID = allOrderFromFile.get(0).get(indexOfAllOrderID.get(indexOfAllOrderID.size() - 1));

                this.order_cart = new Order(latestOID);
            } catch (IOException | RecordNotFoundException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public PurchasableUser(String uid) {
        super(uid);
        // if they have a cart, and if the cart is not cheched out, use old cart, or else, give them new cart

        ArrayList<Integer> indexOfAllOrderID;
        ArrayList<ArrayList<String>> allOrderFromFile;
        String latestOID;

        try {
            // if there is existing orders
            indexOfAllOrderID = OrderFile.indicesOf(this.user_id); // all the order id of this particular user
            allOrderFromFile = OrderFile.readAllOrders(); // all the orders
            latestOID = allOrderFromFile.get(0).get(indexOfAllOrderID.get(indexOfAllOrderID.size() - 1)); // get the last order id of this dude

            if (allOrderFromFile.get(2).get(indexOfAllOrderID.get(indexOfAllOrderID.size() - 1)).equals("PENDING")) {
                this.order_cart = new Order(latestOID);
            } else {
                throw new RecordNotFoundException();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (RecordNotFoundException e) { // if there are no records for them
            OrderFile.addNewOrder(
                    new Date(),
                    "PENDING",
                    this.user_id
            ); // create a new entry for them first
            try {
                // basically same thing as above
                indexOfAllOrderID = OrderFile.indicesOf(this.user_id);
                allOrderFromFile = OrderFile.readAllOrders();
                latestOID = allOrderFromFile.get(0).get(indexOfAllOrderID.get(indexOfAllOrderID.size() - 1));

                this.order_cart = new Order(latestOID);
            } catch (IOException | RecordNotFoundException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public Order getOrder_cart() {
        return order_cart;
    }

    public void setOrder_cart(Order order_cart) {
        this.order_cart = order_cart;
    }

    public void generateInvoice() {
        String directory = "./MyInvoices";
        File dir = new File(directory);
        if (dir.mkdir()) {
            System.out.println("Created directory ./MyInvoices");
        }


        String dest = String.format("%s/Invoice_%s.pdf", directory, this.order_cart.getOrderID());
        String apulogo = "./Images/apuLogo.png";

        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
            Date now = new Date();

            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdfDoc = new PdfDocument(writer);
            pdfDoc.setDefaultPageSize(new PageSize(PageSize.A5));

            pdfDoc.addNewPage();
            Document document = new Document(pdfDoc);
            document.setFont(PdfFontFactory.createFont("Courier"));
            Image im = new Image(ImageDataFactory.create(apulogo));
            im.scale(0.08F, 0.08F);

            Table heading = new Table(UnitValue.createPercentArray(new float[]{1, 2}));
            heading.addCell(new Cell().add(im).setBorder(null));
            heading.addCell(
                    new Cell().add(
                            new Paragraph(
                                    "Leysu Inori Trading SDN BHD"
                            ).setFontSize(14).setBold()
                    ).add(
                            new Paragraph(
                                    "Jalan Teknologi 5,\n" +
                                            "Taman Teknologi Malaysia,\n" +
                                            "57000, Kuala Lumpur.\n" +
                                            "Email : leysuinori.trading@gmail.com"
                            ).setFontSize(8)
                    ).setBorder(null)
            );
            document.add(heading);
            document.add(
                    new Paragraph("Invoice").setTextAlignment(TextAlignment.CENTER).setFontSize(20).setBold().setUnderline()
            );
            Table cusomterDetails = new Table(UnitValue.createPercentArray(new float[]{3, 1, 1, 3}));
            cusomterDetails.addCell(
                    new Cell(1, 2).add(
                            new Paragraph(
                                    String.format(
                                            "Bill to  :\t%s\n" +
                                            "ID       :\t%s\n" +
                                            "Email    :\t%s\n" +
                                            "Phone    :\t%s\n", this.user_name, this.user_id, this.user_email, this.user_phone_number)
                            )
                    ).setBorder(null).setFontSize(8)
            );
            cusomterDetails.addCell(new Cell().setBorder(null));
            cusomterDetails.addCell(
                    new Cell().add(
                            new Paragraph(
                                    String.format("Order:\t%s\n" +
                                            "Date :\t%s\n" +
                                            "Time :\t%s\n", this.order_cart.getOrderID(), dateFormatter.format(now), timeFormatter.format(now))
                            )
                    ).setBorder(null).setFontSize(8)
            );
            Table invoiceDetails = new Table(UnitValue.createPercentArray(new float[]{1, 2, 5, 1, 3}));
            for (int i = 0; i < 5; i++) {
                invoiceDetails.addCell(new Cell().setBorder(null));
            }
            invoiceDetails.addCell(
                    new Cell().add(
                            new Paragraph("No")
                    ).setBold().setFontSize(10)
            );
            invoiceDetails.addCell(
                    new Cell().add(
                            new Paragraph("Product ID")
                    ).setBold().setFontSize(10)
            );
            invoiceDetails.addCell(
                    new Cell().add(
                            new Paragraph("Name")
                    ).setBold().setFontSize(10)
            );
            invoiceDetails.addCell(
                    new Cell().add(
                            new Paragraph("Quantity")
                    ).setBold().setFontSize(10)
            );
            invoiceDetails.addCell(
                    new Cell().add(
                            new Paragraph("Unit Price")
                    ).setBold().setFontSize(10)
            );
            int counter = 0;
            double itemTotal = 0, packingTotal = 0;
            for (OrderItem oi : this.order_cart.getOrderItems()) {
                counter++;
                itemTotal += oi.getItemProduct().getProductUnitPrice() * oi.getItemQuantity();
                packingTotal += oi.getItemProduct().getProductPackagingCharge() * oi.getItemQuantity();
                invoiceDetails.addCell(
                        new Cell().add(
                                new Paragraph(Integer.toString(counter))
                        ).setFontSize(9)
                );
                invoiceDetails.addCell(
                        new Cell().add(
                                new Paragraph(oi.getItemProduct().getProductID())
                        ).setFontSize(9)
                );
                invoiceDetails.addCell(
                        new Cell().add(
                                new Paragraph(oi.getItemProduct().getProductName())
                        ).setFontSize(9)
                );
                invoiceDetails.addCell(
                        new Cell().add(
                                new Paragraph(Integer.toString(oi.getItemQuantity()))
                        ).setFontSize(9).setTextAlignment(TextAlignment.RIGHT)
                );
                invoiceDetails.addCell(
                        new Cell().add(
                                new Paragraph(Double.toString(oi.getItemProduct().getProductUnitPrice()))
                        ).setFontSize(9)
                );
            }
            for (int i = 0; i < 2; i++) {
                invoiceDetails.addCell(new Cell().setBorder(null));
            }
            invoiceDetails.addCell(
                    new Cell(1, 2).add(
                            new Paragraph("Item Total")
                    ).setFontSize(10).setBorder(null).setTextAlignment(TextAlignment.RIGHT)
            );
            invoiceDetails.addCell(
                    new Cell().add(
                            new Paragraph(String.format("RM %.2f", itemTotal))
                    ).setFontSize(10).setBorder(null).setTextAlignment(TextAlignment.RIGHT)
            );
            for (int i = 0; i < 2; i++) {
                invoiceDetails.addCell(new Cell().setBorder(null));
            }
            invoiceDetails.addCell(
                    new Cell(1, 2).add(
                            new Paragraph("Packing Total")
                    ).setFontSize(10).setBorder(null).setTextAlignment(TextAlignment.RIGHT)
            );
            invoiceDetails.addCell(
                    new Cell().add(
                            new Paragraph(String.format("RM %.2f", packingTotal))
                    ).setFontSize(10).setBorder(null).setTextAlignment(TextAlignment.RIGHT)
            );
            for (int i = 0; i < 2; i++) {
                invoiceDetails.addCell(new Cell().setBorder(null));
            }
            invoiceDetails.addCell(
                    new Cell(1, 2).add(
                            new Paragraph("Grand Total")
                    ).setFontSize(12).setBold().setBorder(null).setTextAlignment(TextAlignment.RIGHT)
            );
            invoiceDetails.addCell(
                    new Cell().add(
                            new Paragraph(String.format("RM %.2f", this.order_cart.calculateFinal()))
                    ).setFontSize(12).setBold().setBorder(null).setTextAlignment(TextAlignment.RIGHT)
            );

            document.add(cusomterDetails);
            document.add(invoiceDetails);
            document.close();
            emailFile(
                    this.user_email,
                    String.format("Invoice %s", this.order_cart.getOrderID()),
                    String.format("Attach is the invoice for Order %s. Please do not reply.", this.order_cart.getOrderID()),
                    String.format("Invoice_%s.pdf", this.order_cart.getOrderID()),
                    dest
                    );


        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void checkOut(boolean invoice) {
        this.order_cart.checkOutCart();

        if (invoice) {
            generateInvoice();
        }
    }

    public void emailFile(String recipientEmail, String header, String body, String fileName, String fileSource) {
        String email = "leysuinori.trading@gmail.com";
        String password = "goecismemrjymfld";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            BodyPart msgBody = new MimeBodyPart();
            msgBody.setText(body);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(msgBody);
            msgBody = new MimeBodyPart();
            DataSource source = new FileDataSource(fileSource);
            msgBody.setDataHandler(new DataHandler(source));
            msgBody.setFileName(fileName);
            multipart.addBodyPart(msgBody);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail)
            );
            message.setSubject(header);
            message.setContent(multipart);

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}