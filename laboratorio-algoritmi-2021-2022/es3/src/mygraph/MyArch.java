package src.mygraph;

public final class MyArch<N, T>{
    private final N nodeA;
    private final N nodeB;

    private T tag;
    
    public MyArch(N nodeA, N nodeB, T tag){
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        this.tag = tag;
    }

    public MyArch(N nodeA, N nodeB){
        this.nodeA = nodeA;
        this.nodeB = nodeB;
    }

    //-------------------------------------------------------------------------//

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        MyArch<N, T> obj = (MyArch<N, T>) o;
        return obj.getNodeA() == getNodeA() && obj.getNodeB() == getNodeB();
    }

    @Override
    public int hashCode(){
        int result = (nodeA == null ? 0 : nodeA.hashCode());
        result = 31 * result + (nodeB == null ? 0 : nodeB.hashCode());

        return result;
    }

    //-------------------------------------------------------------------------//

    public N getNodeA(){
        return nodeA;
    }
    public N getNodeB(){
        return nodeB;
    }
    public T getTag(){
        return tag;
    }

    //-------------------------------------------------------------------------//

}
