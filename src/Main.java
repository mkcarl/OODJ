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
        Date now = new Date();
        Date _30daysAgo = new Date(now.getTime() - 30 * 24* 3600 * 1000L);
        try {
            d1 = sdf.parse("2021-08-29 21:08:17");
            System.out.println(sdf.format(now));
            System.out.println(sdf.format(_30daysAgo));
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        System.out.println(d1.toString());
    }
}
