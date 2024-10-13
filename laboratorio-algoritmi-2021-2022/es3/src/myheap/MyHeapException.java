package src.myheap;




public class MyHeapException extends Exception{

    public static final int GENERIC = 0;
    public static final int NO_LEFT_CHILD = 1;
    public static final int NO_RIGHT_CHILD = 2;
    public static final int ITEM_ALREADY_EXIST = 3;
    public static final int INVALID_ARGUMENT = 4;
    
    private int type;

    public MyHeapException(String message){
        super(message);
        this.type = GENERIC;
    }

    public MyHeapException(String message, int type){
        super(message);
        this.type = type;
    }

    public int getType(){
        return type;
    }
}
