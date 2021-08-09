import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author mkcarl
 */
public class Test {

    public static void main(String[] args) {
        try {
//            UserFile.updateEntry(1, 1, Integer.toString(3498579));
//            UserFile.deleteEntry(1);
//            createDummyUsers();
            System.out.println(UserFile.indexOf("A000005"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createDummyUsers(){
        try {
            UserFile.createUserFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserFile.addNewUser("123", "Carl", Gender.MALE.name(), "123@gmail.com", "111222333", Role.CUSTOMER.name());
        UserFile.addNewUser("1234", "Bob", Gender.MALE.name(), "1234@gmail.com", "111222333", Role.ADMIN.name());
        UserFile.addNewUser("1235", "Jane", Gender.FEMALE.name(), "1235@gmail.com", "111222333", Role.CUSTOMER.name());
        UserFile.addNewUser("1236", "Mary", Gender.FEMALE.name(), "1236@gmail.com", "111222333", Role.ADMIN.name());

    }
}
