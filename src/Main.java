/**
 * @author mkcarl
 */
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = null;
        try {
            d1 = sdf.parse("2021-08-29 21:08:17");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(d1.toString());
    }
}
