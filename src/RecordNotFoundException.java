/**
 * @author mkcarl
 */
public class RecordNotFoundException extends Exception{

    public RecordNotFoundException(){}

    public RecordNotFoundException(String var1) {
        super(var1);
    }

    public RecordNotFoundException(String var1, Throwable var2) {
        super(var1, var2);
    }

    public RecordNotFoundException(Throwable var1) {
        super(var1);
    }

    protected RecordNotFoundException(String var1, Throwable var2, boolean var3, boolean var4) {
        super(var1, var2, var3, var4);
    }
}
