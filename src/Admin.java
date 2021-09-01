import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Admin extends PurchasableUser {

    public Admin (String uid) {
        super(uid);
    }

    public String generateReport(){
        String directory = "./MyReports";
        File dir = new File(directory);
        if (dir.mkdir()) {
            System.out.println("Created directory ./MyReports");
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat("ddMMMMyyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");

        Date now = new Date();
        Date _1dayAgo = new Date(now.getTime() - 24* 3600 * 1000L);
        Date _30daysAgo = new Date(now.getTime() - 30 * 24* 3600 * 1000L);

        ArrayList<Order> allOrders = Order.readAllOrder();
        ArrayList<Product> allProducts = Product.readAllProduct();

        String dest = String.format(
                "%s/Report_(%s - %s).pdf",
                directory, dateFormatter.format(_30daysAgo),
                dateFormatter.format(_1dayAgo)
        );
        String apulogo = "./Images/apuLogo.png";

        try {
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
                    new Paragraph("Sales Report").setTextAlignment(TextAlignment.CENTER).setFontSize(20).setBold().setUnderline()
            );
            Table reportDetails = new Table(UnitValue.createPercentArray(new float[]{3, 1, 1, 3}));
            reportDetails.addCell(
                    new Cell(1,3).add(
                            new Paragraph(
                                    String.format(
                                            "From   :\t%s\n" +
                                                    "To     :\t%s\n" +
                                                    "Report generated by    : %s(%s)\n\n" +
                                                    "Generated on   : %s",
                                            dateFormatter.format(_30daysAgo),
                                            dateFormatter.format(_1dayAgo),
                                            this.user_name, this.user_id,
                                            dateFormatter.format(now)
                                    )
                            )
                    ).setBorder(null).setFontSize(8)
            );
            reportDetails.addCell(new Cell(1,2).setBorder(null));


            Table salesDetails = new Table(UnitValue.createPercentArray(new float[]{1, 2, 5, 1, 1, 3}));
            double totalRevenue = 0;
            for (int i = 0; i < 6; i++) {
                salesDetails.addCell(new Cell().setBorder(null));
            }
            salesDetails.addCell(
                    new Cell().add(
                            new Paragraph("No")
                    ).setBold().setFontSize(10)
            );
            salesDetails.addCell(
                    new Cell().add(
                            new Paragraph("Product ID")
                    ).setBold().setFontSize(10)
            );
            salesDetails.addCell(
                    new Cell().add(
                            new Paragraph("Name")
                    ).setBold().setFontSize(10)
            );
            salesDetails.addCell(
                    new Cell().add(
                            new Paragraph("In-Stock")
                    ).setBold().setFontSize(10)
            );
            salesDetails.addCell(
                    new Cell().add(
                            new Paragraph("Sold")
                    ).setBold().setFontSize(10)
            );
            salesDetails.addCell(
                    new Cell().add(
                            new Paragraph("Revenue (RM)")
                    ).setBold().setFontSize(10)
            );
            int counter = 0;
            double itemTotal = 0, packingTotal = 0;

            for (Product prod : allProducts) {
                counter++;
                String currentPID = prod.getProductID();
                String currentPname = prod.getProductName();
                int currentInv = prod.getProductInventoryCount();
                int cumulativeQuantity = 0;
                double cumulativeRevenue = 0;

                for (Order order : allOrders) {
                    if (!order.getOrderDate().before(_30daysAgo) && !order.getOrderDate().after(_1dayAgo)) {
                        for (OrderItem oi : order.getOrderItems()) {
                            if (oi.getItemProduct().getProductID().equals(currentPID)){
                                cumulativeQuantity += oi.getItemQuantity();
                                cumulativeRevenue += oi.calculateAmount();
                            }
                        }
                    }
                }
                totalRevenue += cumulativeRevenue;
                salesDetails.addCell(
                        new Cell().add(
                                new Paragraph(Integer.toString(counter))
                        ).setFontSize(9)
                );
                salesDetails.addCell(
                        new Cell().add(
                                new Paragraph(currentPID)
                        ).setFontSize(9)
                );
                salesDetails.addCell(
                        new Cell().add(
                                new Paragraph(currentPname)
                        ).setFontSize(9)
                );
                salesDetails.addCell(
                        new Cell().add(
                                new Paragraph(Integer.toString(currentInv))
                        ).setFontSize(9).setTextAlignment(TextAlignment.RIGHT)
                );
                salesDetails.addCell(
                        new Cell().add(
                                new Paragraph(Integer.toString(cumulativeQuantity))
                        ).setFontSize(9).setTextAlignment(TextAlignment.RIGHT)
                );
                salesDetails.addCell(
                        new Cell().add(
                                new Paragraph(String.format("%.2f", cumulativeRevenue))
                        ).setFontSize(9).setTextAlignment(TextAlignment.RIGHT)
                );
            }

            // total revenue
            Paragraph last = new Paragraph(String.format("\n\nTotal revenue: RM %.2f", totalRevenue)).setFontSize(9).setTextAlignment(TextAlignment.RIGHT).setBold();

            document.add(reportDetails);
            document.add(salesDetails);
            document.add(last);
            document.close();

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dest;
    }
}
