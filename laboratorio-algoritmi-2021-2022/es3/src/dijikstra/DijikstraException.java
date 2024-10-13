package src.dijikstra;

public class DijikstraException extends Exception{

    public static final int GENERIC = 0;
    public static final int NEGATIVE_WEIGHT = 1;
    public static final int ORIENTATION_COMPROMISED = 2;
    public static final int NO_SUCH_NODE = 3;
    public static final int INVALID_INPUT = 4;

    public int type;

    public DijikstraException(String message, int type){
        super(message);
        this.type = type;
    }
}
