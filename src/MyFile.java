import java.io.File;

/**
 * @author mkcarl
 */
public abstract class MyFile {

    protected static String directory = "./MyFiles/";

    public static void createDirectory(){
        File dir = new File(directory);

        if (dir.mkdir()){
            System.out.println("Created directory ./MyFiles");
        }
    }
}
