package src.mygraph;

public class MyGraphException extends Exception{

    public static final int GENERIC = 0;
    public static final int INVALID_ARGUMENT = 1;
    public static final int NO_SUCH_ARCH = 2;
    public static final int NO_SUCH_NODE = 3;
    public static final int ARCH_ALREADY_EXIST = 4;
    public static final int NODE_ALREADY_EXIST = 5;

    public int type;

    public MyGraphException(String message, int type){
        super(message);
        this.type = type;
    }
    public MyGraphException(MyGraphException e){
        super(e.getMessage());
        this.type = e.type;
    }
}
