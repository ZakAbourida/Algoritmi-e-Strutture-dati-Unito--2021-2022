package src.dijikstra;

public class Node<N extends Comparable<N>, T extends Number & Comparable<T>> implements Comparable<Node<N, T>> {
    private N node;
    private T distance;
    private Node<N, T> parent;
    Float zero = 0.0f;

    public Node(N node){
        this.node = node;
        distance = null;
        parent = null;
    }
    public Node(N node, T distance, Node<N, T> parent){
        this.node = node;
        this.distance = distance;
        this.parent = parent;
    }

    @SuppressWarnings("unchecked")
    @Override
    public int compareTo(Node<N, T> o){     //compare Node<> on distance
        if(o == null)
            return -1;
        T o_distance = (T)(((Node<N, T>)o).getDistance());
        if(o_distance == null)
            return -1;
        if(o_distance.equals((T)zero))
            return 1;
        if(distance != null && distance.equals((T)zero))
            return -1;
        if(distance == null)
            return 1;
        return distance.compareTo((T) o.getDistance());
    }
    
    @Override
    public boolean equals(Object o){        //compare Node<> on node and (if have) on distance
        if( o instanceof Node){
            Node<?, ?> c = (Node<?, ?>) o;
            if(distance == null)
                return node.equals(c.getNode());
            else
                return node.equals(c.getNode()) && distance.equals(c.getDistance());
        }
        return false;
    }

    @Override
    public String toString(){
        String ret = "";
        ret += node.toString();
        if(distance != null)
            ret += "|"+distance.toString();
        else
            ret += "|"+"null";
        if(parent != null)
            ret += "|"+parent.getNode().toString();
        else
            ret += "|"+"null";

        return ret;
    }

    @Override
    public int hashCode(){                  //hash code based on node value .toString
        int hash = 0;
        hash = 31 * hash + node.toString().hashCode();
        return hash;
    }

    public void printPath(){
        if(parent != null){
            System.out.println("to "+node.toString()+ "\tfrom "+parent.getNode());
            parent.printPath();
        }
    }

    public N getNode(){
        return node;
    }
    public void setDistance(T distance){
        this.distance = distance;
    }
    public T getDistance(){
        return distance;
    }
    public void setParent(Node<N, T> parent){
        this.parent = parent;
    }
    public Node<N, T> getParent(){
        return parent;
    }
}
