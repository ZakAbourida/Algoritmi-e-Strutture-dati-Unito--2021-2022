package src.mygraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class MyGraph<N, T>{
    private int nonPairedArch = 0;
    private int nodesNum = 0;

    private HashMap<N, Integer> nodes;
    private Vector<N> nodesVec;

    private HashMap<N, HashMap<N, T>> nodeNeighbours;

    private Map<MyArch<N, T>, Integer> arches;

    /**
     * creation of an empty graph O(1)
     */
    public MyGraph(){
        nodes = new HashMap<>(0);
        nodesVec  = new Vector<>(0);

        nodeNeighbours = new HashMap<>(0);

        arches = new HashMap<>(0);
    }

    //-------------------------------------------------------------------------//

    /**
     * add a new node O(1)
     * @param node is the <N> node to add
     * @throws MyGraphException.INVALID_ARGUMENT
     * @throws MyGraphException.NODE_ALREADY_EXIST
     */
    public void addNode(N node) throws MyGraphException{
        if(node == null)
            throw new MyGraphException("addNode - null argument", MyGraphException.INVALID_ARGUMENT);
        if(containNode(node))
            throw new MyGraphException("addNode - invalid argument", MyGraphException.NODE_ALREADY_EXIST);
        
        //add node
        nodes.put(node, nodesNum);
        nodesVec.add(nodesNum, node);
        nodesNum++;
    }

    /**
     * add a new arch O(1)
     * @param nodeA is the source <N> node of the arch
     * @param nodeB is the destination <N> node of the arch
     * @param weight is the weight assigned to the arch
     * @throws MyGraphException.INVALID_ARGUMENT
     * @throws MyGraphException.NO_SUCH_NODE
     */
    public void addArch(N nodeA, N nodeB, T tag) throws MyGraphException{
        if(nodeA == null || nodeB == null || tag == null)
            throw new MyGraphException("addArch - null argument", MyGraphException.INVALID_ARGUMENT);
        if((!containNode(nodeA)) || (!containNode(nodeB)))
            throw new MyGraphException("addArch - invalid argument - no node", MyGraphException.NO_SUCH_NODE);
        if(nodeA.equals(nodeB))
            throw new MyGraphException("addArch - invalid argument - same node", MyGraphException.INVALID_ARGUMENT);
        if(containArch(new MyArch<>(nodeA, nodeB)))
            throw new MyGraphException("addArch - invalid argument - arch exist", MyGraphException.ARCH_ALREADY_EXIST);
        
        //add the arch
        HashMap<N, T> neighbours = getNodeNeighbours(nodeA);
        if(neighbours == null){
            neighbours = new HashMap<>(0);
            nodeNeighbours.put(getNodeReferenceOnHashcode(nodeA), neighbours);
        }
        neighbours.put(getNodeReferenceOnHashcode(nodeB), tag);
        arches.put(new MyArch<>(nodeA, nodeB, tag), 0);

        //orientation update
        if((!containArch(new MyArch<>(nodeB, nodeA))) && nonPairedArch >= 0)
            nonPairedArch++;
        else
            nonPairedArch--;
        
    }

    /**
     * check is the graph is oriented O(1)
     * @return true if the graph is oriented
     */
    public boolean isOriented(){
        return nonPairedArch != 0;
    }
    public int getNonPairedArchNum(){
        return nonPairedArch;
    }

    public N getNodeReferenceOnHashcode(N node) throws MyGraphException{
        if(node == null)
            throw new MyGraphException("getNodeReference - null argument", MyGraphException.INVALID_ARGUMENT);
        if(!containNode(node))
            throw new MyGraphException("getNodeReference - no such node", MyGraphException.NO_SUCH_NODE);
        
        return nodesVec.get( nodes.get(node) );
    }

    /**
     * check if the graph contain a certain <N> node O(1)
     * @param node is the <N> node to check for
     * @return true if the <N> node is present
     * @throws MyGraphException.INVALID_ARGUMENT
     */
    public boolean containNode(N node) throws MyGraphException{
        if(node == null)
            throw new MyGraphException("containNode - null argument", MyGraphException.INVALID_ARGUMENT);
        
        return nodes.containsKey(node);
    }

    /**
     * check if the graph contain a certain arch O(1)*
     * @param arch is the MyArch<N> arch to check for
     * @return true if the specified arch is contained
     * @throws MyGraphException.INVALID_ARGUMENT
     * @throws MyGraphException.NO_SUCH_NODE
     */
    public boolean containArch(MyArch<N, T> arch) throws MyGraphException{
        if(arch == null)
            throw new MyGraphException("containArch - null argument", MyGraphException.INVALID_ARGUMENT);
        if((!containNode(arch.getNodeA())) || (!containNode(arch.getNodeB())))
            throw new MyGraphException("containArch - invalid argument", MyGraphException.NO_SUCH_NODE);
        
        HashMap<N, T> tmp = getNodeNeighbours(arch.getNodeA());
        if(tmp == null)
            return false;
        return tmp.containsKey(arch.getNodeB());
    }
    
    /**
     * remove a specified <N> node with complexity O(n)
     * @param node is the <N> node to remove
     * @throws MyGraphException.INVALID_ARGUMENT
     * @throws MyGraphException.NO_SUCH_NODE
     */
    public void removeNode(N node) throws MyGraphException{
        if(node == null)
            throw new MyGraphException("removeNode - null argument", MyGraphException.INVALID_ARGUMENT);
        if(!containNode(node))
            throw new MyGraphException("removeNode - invalid argument", MyGraphException.NO_SUCH_NODE);
        
        //remove all arches that use that node
        for (N n : nodesVec) {
            try {
                removeArch(new MyArch<>(node, n));
                removeArch(new MyArch<>(n, node));
            } catch (MyGraphException e) {
                if(e.type == MyGraphException.NO_SUCH_ARCH)
                    continue;
                throw new RuntimeException(e + " type=" + e.type);
            }
        }
        //remove the node
        int nodeIndex = nodes.remove(node);
        N lastNode = nodesVec.remove(getNodesCount()-1);
        if(nodeIndex != getNodesCount()-1){
            nodes.replace(lastNode, nodeIndex);
            nodesVec.set(nodeIndex, lastNode);
        }        
        nodesNum--;

    }

    /**
     * remove a specified MyArch<N> arch with complexity O(1)*
     * @param arch is the MyArch<N> to remove
     * @throws MyGraphException.INVALID_ARGUMENT
     * @throws MyGraphException.NO_SUCH_ARCH
     */
    public void removeArch(MyArch<N, T> arch) throws MyGraphException{
        if(arch == null)
            throw new MyGraphException("removeArch - null argument", MyGraphException.INVALID_ARGUMENT);
        if(!containArch(arch))
            throw new MyGraphException("removeArch - invalid argument", MyGraphException.NO_SUCH_ARCH);
        
        //remove arch
        getNodeNeighbours(arch.getNodeA()).remove(arch.getNodeB());
        arches.remove(arch);

        //orientation update
        if(containArch(new MyArch<>(arch.getNodeB(), arch.getNodeA())))
            nonPairedArch++;
        else
            nonPairedArch--;
    }

    /**
     * with complexity O(1)
     * @return the number of nodes
     * @throws MyGraphException
     */
    public int getNodesCount(){
        return nodesNum;
    }

    /**
     * with complexity O(n)
     * @return the number of arches
     * @throws MyGraphException
     */
    public int getArchsCount() throws MyGraphException{
        int ret = 0;
        for (N node : nodesVec) {
            HashMap<N, T> tmp = getNodeNeighbours(node);
            if(tmp != null)
                ret += tmp.size();
        }
        return ret;
    }

    /**
     * get all the nodes of the graph with complexity O(n)
     * @return
     */
    public List<N> nodesToList(){
        if(getNodesCount() == 0)
            return null;
        
        return Collections.list(nodesVec.elements());
    }
    
    /**
     * get all the arches in the graph with complexity O(n)
     * @return
     * @throws MyGraphException
     */
    public List<MyArch<N, T>> archesToList() throws MyGraphException{
        if(getArchsCount() == 0)
            return null;

        return new ArrayList<MyArch<N, T>>( arches.keySet() );
    }

    /**
     * get all the neighbours <N> nodes of a specified <N> node with complexity O(1)*
     * @param node is the <N> node of which to get the neighbours
     * @return an HashMap<N, T> with all neighbours and distances
     * @throws MyGraphException.INVALID_ARGUMENT
     * @throws MyGraphException.NO_SUCH_NODE
     */
    public HashMap<N, T> getNodeNeighbours(N node) throws MyGraphException{
        if(node == null)
            throw new MyGraphException("getNodeNeighbours - null argument", MyGraphException.INVALID_ARGUMENT);
        if(!containNode(node))
            throw new MyGraphException("getNodeNeighbours - invalid argument", MyGraphException.NO_SUCH_NODE);

        return nodeNeighbours.get(node);
    }

    /**
     * get the weight associated with a specified <N> nodes pair with complexity O(1)
     * @param nodeA is the <N> node from where the arch start 
     * @param nodeB is the <N> node where the arch end
     * @return the tag associated with specified node pair if the arch exist
     * @throws MyGraphException.INVALID_ARGUMENT
     * @throws MyGraphException.NO_SUCH_ARCH
     * @throws MyGraphException.NO_SUCH_NODE
     */
    public T getArchTag(N nodeA, N nodeB) throws MyGraphException{
        if(nodeA == null || nodeB == null)
            throw new MyGraphException("getArchWeight - null parameter", MyGraphException.INVALID_ARGUMENT);
        if((!containNode(nodeA)) || (!containNode(nodeB)))
            throw new MyGraphException("getArchWeight - invalid parameter", MyGraphException.NO_SUCH_NODE);
        if(!containArch(new MyArch<>(nodeA, nodeB)))
            throw new MyGraphException("getArchWeight - invalid parameter", MyGraphException.NO_SUCH_ARCH);

        return getNodeNeighbours(nodeA).get(nodeB);
    }

    //---------------------------------------------------------//
    public void printNodes() throws MyGraphException{
        List<N> nodes = nodesToList();
        System.out.println("---NODES---");
        if(nodes != null){
            nodes.forEach((elem) -> {
                System.out.println(elem);
            });
        }
        System.out.println("-----------");
    }

}