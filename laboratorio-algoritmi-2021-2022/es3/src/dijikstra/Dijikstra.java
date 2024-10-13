package src.dijikstra;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import src.mygraph.MyArch;
import src.mygraph.MyGraph;
import src.mygraph.MyGraphException;
import src.myheap.MyHeap;
import src.myheap.MyHeapException;

public class Dijikstra<N extends Comparable<N>, T extends Number & Comparable<T>> {
    MyGraph<Node<N, T>, T> graph;

    public Dijikstra(){
        graph = new MyGraph<>();
    }

    public void addArch(Node<N, T> nodeA, Node<N, T> nodeB, T weight) throws DijikstraException, MyGraphException{
        if(weight.floatValue() < 0)
            throw new DijikstraException("addArch - negative weight", DijikstraException.NEGATIVE_WEIGHT);

        //add nodes
        if(!graph.containNode(nodeA))
            graph.addNode(nodeA);
        if(!graph.containNode(nodeB))
            graph.addNode(nodeB);
        
        if(graph.containArch(new MyArch<>(nodeA, nodeB))){
            return;
        }
        //add arches for both direction
        graph.addArch(nodeA, nodeB, weight);
        graph.addArch(nodeB, nodeA, weight);

        //assert orientation integrity
        if(graph.isOriented()){
            throw new DijikstraException("addArch - graph is oriented", DijikstraException.ORIENTATION_COMPROMISED);
        }

    }

    public boolean containNode(Node<N, T> node) throws MyGraphException{
        return graph.containNode(node);
    }

    @SuppressWarnings("unchecked")
    public void camminoMinmo(Node<N, T> startingNode) throws MyHeapException, MyGraphException, DijikstraException{
        //berfore : for ∀v ∈ V do v.d← ∞, v.π ← nil

        List<Node<N, T>> nodes = graph.nodesToList();

        //s.d← 0    s.π← nil
        Float zero = 0f;
        graph.getNodeReferenceOnHashcode(startingNode).setDistance((T)(zero));

        //Q ← V
        MyHeap<Node<N, T>> Q = new MyHeap<>(true);
        for (Node<N, T> node : nodes) {
            Q.addItem(node);
        }
        
        //while Q != ∅ do
        while( !Q.isEmpty() ){
            //u ← togli nodo con d minimo da Q
            Node<N, T> u = Q.removeRootItem();

            if(u.getDistance() == null)
                break;

            //for ∀v ∈ adj[u] do
            for (Map.Entry<Node<N, T>, T> node : graph.getNodeNeighbours(u).entrySet()){
                Node<N, T> v = node.getKey();

                //if v ∈ Q
                if(!Q.contains(v))
                    continue;

                float distance_u_v = graph.getArchTag(u, v).floatValue();

                //if u.d + W(u, v) < v.d then
                if(v.getDistance() == null || u.getDistance().floatValue() + distance_u_v < v.getDistance().floatValue()){
                    //v.d ← u.d + W(u, v)
                    //v.π ← u
                    Float newDistande = u.getDistance().floatValue() + distance_u_v; 
                    Q.changeItem(v, new Node<>(v.getNode(), (T)newDistande, u));

                    v.setDistance((T)newDistande);
                    v.setParent(u);
                }
                
            }
            
        }

    }

    //-----------------------------------------------------------------------------//
    
    /**
     * 
     * @param destinationNode is the Node<N, T> from which to get the distance between this and the node calculated with camminoMinimo()
     * @return null if the "destinationNode" is not reachable from the "startingNode" or if the distance si not calculated, the distance otherwise
     * @throws DijikstraException.NO_SUCH_NODE
     * @throws DijikstraException.INVALID_INPUT
     * @throws MyGraphException
     */
    public T getDistance(Node<N, T> destinationNode) throws DijikstraException, MyGraphException{
        if(destinationNode == null)
            throw new DijikstraException("getDistance - null input", DijikstraException.INVALID_INPUT);
        if(graph.getNodeReferenceOnHashcode(destinationNode)==null)
            throw new DijikstraException("getDistance - no such node", DijikstraException.NO_SUCH_NODE);
        return graph.getNodeReferenceOnHashcode(destinationNode).getDistance();
    }

    //-----------------------------------------------------------------------------//
    public void printArches() throws MyGraphException{
        List<MyArch<Node<N, T>, T>> arches = graph.archesToList();
        System.out.println("---ARCHES---");
        for(MyArch<Node<N, T>, T> a : arches){
            System.out.println(a.getNodeA() + "\t" + a.getNodeB() + "\t" +a.getTag());
        }
        System.out.println("------------");
    }
    public void printNodes() throws MyGraphException{
        List<Node<N, T>> nodes = graph.nodesToList();
        System.out.println("---NODES---");
        nodes.forEach((elem) -> {
            System.out.println(elem+"\t");
        });
        System.out.println("-----------");
    }
    public List<Node<N, T>> getNodeNeighboursAsList(Node<N, T> n) throws MyGraphException{
        List<Node<N, T>> ret = new ArrayList<>();
        for (Map.Entry<Node<N, T>, T> node : graph.getNodeNeighbours(n).entrySet()) {
            ret.add(node.getKey());
        }
        return ret;
    }
    public void printNeighbours(Node<N, T> n) throws MyGraphException{
        List<Node<N, T>> l = getNodeNeighboursAsList(n);
        System.out.println(l.toString());
    }

}
