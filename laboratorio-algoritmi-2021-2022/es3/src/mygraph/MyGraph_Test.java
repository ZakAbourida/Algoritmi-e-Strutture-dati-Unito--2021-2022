package src.mygraph;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class MyGraph_Test {

    @SuppressWarnings("unused")
    private void printNodes(){
        List<String> nodes = graph.nodesToList();
        System.out.println("---NODES---");
        nodes.forEach((elem) -> {
            System.out.println(elem);
        });
        System.out.println("-----------");
    }
    @SuppressWarnings("unused")
    private void printArches() throws MyGraphException{
        List<MyArch<String, Integer>> arches = graph.archesToList();
        System.out.println("---ARCHES---");
        for(MyArch<String, Integer> a : arches){
            System.out.println(a.getNodeA() + "\t" + a.getNodeB() + "\t" + a.getTag());
        }
        System.out.println("------------");
    }

    private MyGraph<String, Integer> graph;

    @Before
    public void initialize(){
        graph = new MyGraph<>();
    }

    @Test
    public void addNodes() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");

        assertTrue(true);
    }
    @Test
    public void addNodeAlreadyExist() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");

        try {
            graph.addNode("C");
        } catch (MyGraphException e) {
            if(e.type == MyGraphException.NODE_ALREADY_EXIST) {
                assertTrue(true);
                return;
            }
            assertTrue(false);
        }
    }

    @Test
    public void addArches() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");

        graph.addArch("A", "B", 5);
        graph.addArch("B", "C", 10);
        graph.addArch("E", "A", 5);
        graph.addArch("A", "E", 8);

        assertTrue(true);
    }
    @Test
    public void addArchesAlreadyExist() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");

        graph.addArch("A", "B", 5);
        graph.addArch("B", "C", 10);
        graph.addArch("E", "A", 5);
        graph.addArch("A", "E", 8);

        try {
            graph.addArch("B", "C", 7);
            assertTrue(false);
            return;
        } catch (MyGraphException e) {
            if(e.type == MyGraphException.ARCH_ALREADY_EXIST) {
                assertTrue(true);
                return;
            }
            assertTrue(false);
        }
    }
    @Test
    public void addArchOnNonExistingNodes() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");

        graph.addArch("A", "B", 5);
        graph.addArch("B", "C", 10);
        graph.addArch("E", "A", 5);
        graph.addArch("A", "E", 8);

        try {
            graph.addArch("E", "G", 2);
        } catch (MyGraphException e) {
            if(e.type == MyGraphException.NO_SUCH_NODE){
                assertTrue(true);
                return;
            }
            assertTrue(false);
        }
        
    }
    @Test
    public void addArchOnSameNode() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");

        graph.addArch("A", "B", 5);
        graph.addArch("B", "C", 10);
        graph.addArch("E", "A", 5);
        graph.addArch("A", "E", 8);

        try {
            graph.addArch("E", "E", 2);
        } catch (MyGraphException e) {
            if(e.type == MyGraphException.INVALID_ARGUMENT){
                assertTrue(true);
                return;
            }
            assertTrue(false);
        }
    }

    @Test
    public void addArchesCheckIfOrientedFalse() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");
        graph.addNode("G");

        graph.addArch("E", "G", 2);
        graph.addArch("G", "E", 3);
        graph.addArch("A", "E", 5);
        graph.addArch("B", "F", 9);
        graph.addArch("F", "B", 1);
        
        assertTrue(graph.isOriented());
    }
    @Test
    public void addArchesCheckIfOrientedTrue() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");
        graph.addNode("G");

        graph.addArch("E", "G", 2);
        graph.addArch("G", "E", 3);
        graph.addArch("A", "E", 5);
        graph.addArch("B", "F", 9);
        graph.addArch("F", "B", 1);
        graph.addArch("E", "A", 7);

        assertFalse(graph.isOriented());
    }
    @Test
    public void addArchesCheckIfOrientedTrueAfterEdit() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");
        graph.addNode("G");

        graph.addArch("E", "G", 2);
        graph.addArch("E", "A", 7);
        graph.addArch("G", "E", 3);
        graph.addArch("F", "B", 1);
        graph.addArch("A", "E", 5);
        graph.addArch("B", "F", 9);

        graph.removeArch(new MyArch<>("A", "E"));

        assertTrue(graph.isOriented());
    }

    @Test
    public void checkIfContainExistingNode() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");

        assertTrue(graph.containNode("D"));
    }
    @Test
    public void checkIfContainNonExistingNode() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");

        assertFalse(graph.containNode("H"));
    }

    @Test
    public void checkIfContainExistingArch() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");
        graph.addNode("G");

        graph.addArch("E", "G", 2);
        graph.addArch("G", "E", 3);
        graph.addArch("E", "A", 7);
        graph.addArch("B", "F", 9);
        graph.addArch("F", "B", 1);
        graph.addArch("A", "B", 2);
        graph.addArch("F", "D", 6);
        graph.addArch("C", "B", 2);
        graph.addArch("B", "C", 0);

        assertTrue(graph.containArch(new MyArch<>("F", "B")));
    }
    @Test
    public void checkIfContainNonExistingArch() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");
        graph.addNode("G");

        graph.addArch("E", "G", 2);
        graph.addArch("G", "E", 3);
        graph.addArch("E", "A", 7);
        graph.addArch("B", "F", 9);
        graph.addArch("F", "B", 1);
        graph.addArch("A", "B", 2);
        graph.addArch("F", "D", 6);
        graph.addArch("C", "B", 2);
        graph.addArch("B", "C", 0);

        assertFalse(graph.containArch(new MyArch<>("F", "E")));
    }
    @Test
    public void checkIfContainArchOnNonExistingNode() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");
        graph.addNode("G");

        graph.addArch("E", "G", 2);
        graph.addArch("G", "E", 3);
        graph.addArch("E", "A", 7);
        graph.addArch("B", "F", 9);
        graph.addArch("F", "B", 1);
        graph.addArch("A", "B", 2);
        graph.addArch("F", "D", 6);
        graph.addArch("C", "B", 2);
        graph.addArch("B", "C", 0);

        try {
            graph.containArch(new MyArch<>("F", "H"));
        } catch (MyGraphException e) {
            if(e.type == MyGraphException.NO_SUCH_NODE){
                assertTrue(true);
                return;
            }
        }
        assertTrue(false);
    }

    @Test
    public void removeNode() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");
        graph.addNode("G");

        graph.removeNode("D");

        assertTrue(true);
    }  
    @Test
    public void removeNonExistingNode()throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");
        graph.addNode("G");

        try {
            graph.removeNode("H");
        } catch (MyGraphException e) {
            if(e.type == MyGraphException.NO_SUCH_NODE){
                assertTrue(true);
                return;
            }
        }

        assertTrue(false);
    }

    @Test
    public void removeArch() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");
        graph.addNode("G");

        graph.addArch("E", "G", 2);
        graph.addArch("G", "E", 3);
        graph.addArch("E", "A", 7);
        graph.addArch("B", "F", 9);
        graph.addArch("F", "B", 1);
        graph.addArch("A", "B", 2);
        graph.addArch("F", "D", 6);
        graph.addArch("C", "B", 2);
        graph.addArch("B", "C", 0);

        graph.removeArch(new MyArch<>("G", "E"));

        assertTrue(true);
    }
    @Test
    public void removeNonExistingArch() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");
        graph.addNode("G");

        graph.addArch("E", "G", 2);
        graph.addArch("G", "E", 3);
        graph.addArch("E", "A", 7);
        graph.addArch("B", "F", 9);
        graph.addArch("F", "B", 1);
        graph.addArch("A", "B", 2);
        graph.addArch("F", "D", 6);
        graph.addArch("C", "B", 2);
        graph.addArch("B", "C", 0);

        try {
            graph.removeArch(new MyArch<>("A", "E"));
        } catch (MyGraphException e) {
            if(e.type == MyGraphException.NO_SUCH_ARCH){
                assertTrue(true);
                return;
            }
        }

        assertTrue(false);

    }
    @Test
    public void removeArchOnNonExistingNode() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");
        graph.addNode("G");

        graph.addArch("E", "G", 2);
        graph.addArch("G", "E", 3);
        graph.addArch("E", "A", 7);
        graph.addArch("B", "F", 9);
        graph.addArch("F", "B", 1);
        graph.addArch("A", "B", 2);
        graph.addArch("F", "D", 6);
        graph.addArch("C", "B", 2);
        graph.addArch("B", "C", 0);

        try {
            graph.removeArch(new MyArch<>("W", "A"));
        } catch (MyGraphException e) {
            if(e.type == MyGraphException.NO_SUCH_NODE){
                assertTrue(true);
                return;
            }
        }

        assertTrue(false);
    }

    @Test
    public void assertNodesNum() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");

        assertEquals(5, graph.getNodesCount());
    }
    @Test
    public void assertNodesNumOnEmpty() throws MyGraphException{
        assertEquals(0, graph.getNodesCount());
    }
    @Test
    public void assertNodesNumAfterRemove() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");

        graph.removeNode("B");
        graph.removeNode("D");

        assertEquals(3, graph.getNodesCount());
    }
    
    @Test
    public void assertArchesNum() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");
        graph.addNode("G");

        graph.addArch("E", "G", 2);
        graph.addArch("G", "E", 3);
        graph.addArch("E", "A", 7);
        graph.addArch("B", "F", 9);
        graph.addArch("F", "B", 1);
        graph.addArch("A", "B", 2);
        graph.addArch("F", "D", 6);
        graph.addArch("C", "B", 2);
        graph.addArch("B", "C", 0);

        assertEquals(9, graph.getArchsCount());

    }
    @Test
    public void assertArchesNumOnEmpty() throws MyGraphException{
        assertEquals(0, graph.getArchsCount());
    }
    @Test
    public void assertArchesNumAfterRemove() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");
        graph.addNode("G");

        graph.addArch("E", "G", 2);
        graph.addArch("G", "E", 3);
        graph.addArch("E", "A", 7);
        graph.addArch("B", "F", 9);
        graph.addArch("F", "B", 1);
        graph.addArch("A", "B", 2);
        graph.addArch("F", "D", 6);
        graph.addArch("C", "B", 2);
        graph.addArch("B", "C", 0);

        graph.removeArch(new MyArch<>("E", "G"));
        graph.removeArch(new MyArch<>("E", "A"));
        graph.removeArch(new MyArch<>("A", "B"));

        assertEquals(6, graph.getArchsCount());

    }

    @Test
    public void getNodes() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");

        List<String> exp = new ArrayList<String>();
        exp.add("A");
        exp.add("B");
        exp.add("C");
        exp.add("D");
        exp.add("E");
        
        assertEquals(exp, graph.nodesToList());

    }
    @Test
    public void getNodesOfEmptyGraph(){
        assertEquals(null, graph.nodesToList());
    }
    
    @Test
    public void getArches() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");
        graph.addNode("G");

        List<MyArch<String, Integer>> exp = new ArrayList<>();
        exp.add( new MyArch<>("E", "G", 2) );
        exp.add( new MyArch<>("G", "E", 3) );
        exp.add( new MyArch<>("E", "A", 7) );
        exp.add( new MyArch<>("B", "F", 9) );
        exp.add( new MyArch<>("F", "B", 1) );
        exp.add( new MyArch<>("A", "B", 2) );
        exp.add( new MyArch<>("F", "D", 6) );
        exp.add( new MyArch<>("C", "B", 2) );
        exp.add( new MyArch<>("B", "C", 0) );

        graph.addArch("E", "G", 2);
        graph.addArch("G", "E", 3);
        graph.addArch("E", "A", 7);
        graph.addArch("B", "F", 9);
        graph.addArch("F", "B", 1);
        graph.addArch("A", "B", 2);
        graph.addArch("F", "D", 6);
        graph.addArch("C", "B", 2);
        graph.addArch("B", "C", 0);

        assertTrue( new HashSet<>(exp).equals(new HashSet<>(graph.archesToList())) );

    }
    @Test
    public void getArchesOfEmptyGraph() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");

        assertEquals(null, graph.archesToList());
    }

    @Test
    public void getNeighboursOfNodeThatHave() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");
        graph.addNode("G");

        graph.addArch("E", "G", 2);
        graph.addArch("G", "E", 3);
        graph.addArch("E", "A", 7);
        graph.addArch("B", "F", 9);
        graph.addArch("F", "B", 1);
        graph.addArch("A", "B", 2);
        graph.addArch("F", "D", 6);
        graph.addArch("C", "B", 2);
        graph.addArch("B", "C", 0);

        HashMap<String, Integer> exp = new HashMap<>(0);    //for node "B"
        exp.put("F", 9);
        exp.put("C", 0);

        String exp_a[] = {};
        exp.keySet().toArray(exp_a);

        String act_a[] = {};
        graph.getNodeNeighbours("B").keySet().toArray(act_a);
        
        assertArrayEquals(exp_a, act_a);

    }
    @Test
    public void getNeighboursOfNodeThatDontHaveAny() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");
        graph.addNode("G");

        graph.addArch("E", "G", 2);
        graph.addArch("G", "E", 3);
        graph.addArch("E", "A", 7);
        graph.addArch("B", "F", 9);
        graph.addArch("F", "B", 1);
        graph.addArch("A", "B", 2);
        graph.addArch("F", "D", 6);
        graph.addArch("C", "B", 2);
        graph.addArch("B", "C", 0);

        assertEquals(null, graph.getNodeNeighbours("D"));
    }
    @Test
    public void getNodeNeighboursOfNonExistingNode() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");
        graph.addNode("G");

        graph.addArch("E", "G", 2);
        graph.addArch("G", "E", 3);
        graph.addArch("E", "A", 7);
        graph.addArch("B", "F", 9);
        graph.addArch("F", "B", 1);
        graph.addArch("A", "B", 2);
        graph.addArch("F", "D", 6);
        graph.addArch("C", "B", 2);
        graph.addArch("B", "C", 0);

        try {
            graph.getNodeNeighbours("X");
        } catch (MyGraphException e) {
            if(e.type == MyGraphException.NO_SUCH_NODE){
                assertTrue(true);
                return;
            }
        }
        assertTrue(false);
    }

    @Test
    public void getTagFromNodesPair() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");
        graph.addNode("G");

        graph.addArch("E", "G", 2);
        graph.addArch("G", "E", 3);
        graph.addArch("E", "A", 7);
        graph.addArch("B", "F", 9);
        graph.addArch("F", "B", 1);
        graph.addArch("A", "B", 2);
        graph.addArch("F", "D", 6);
        graph.addArch("C", "B", 2);
        graph.addArch("B", "C", 0);

        assertTrue(1 == graph.getArchTag("F", "B"));
    }
    @Test
    public void getTagFromNodesPairNodeDontExist() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");
        graph.addNode("G");

        graph.addArch("E", "G", 2);
        graph.addArch("G", "E", 3);
        graph.addArch("E", "A", 7);
        graph.addArch("B", "F", 9);
        graph.addArch("F", "B", 1);
        graph.addArch("A", "B", 2);
        graph.addArch("F", "D", 6);
        graph.addArch("C", "B", 2);
        graph.addArch("B", "C", 0);

        try {
            graph.getArchTag("F", "Z");
        } catch (MyGraphException e) {
            if(e.type == MyGraphException.NO_SUCH_NODE){
                assertTrue(true);
                return;
            }
        }
        assertTrue(false);
    }
    @Test
    public void getTagFromNodesPairArchDonExist() throws MyGraphException{
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");
        graph.addNode("G");

        graph.addArch("E", "G", 2);
        graph.addArch("G", "E", 3);
        graph.addArch("E", "A", 7);
        graph.addArch("B", "F", 9);
        graph.addArch("F", "B", 1);
        graph.addArch("A", "B", 2);
        graph.addArch("F", "D", 6);
        graph.addArch("C", "B", 2);
        graph.addArch("B", "C", 0);

        try {
            graph.getArchTag("F", "A");
        } catch (MyGraphException e) {
            if(e.type == MyGraphException.NO_SUCH_ARCH){
                assertTrue(true);
                return;
            }
        }
        assertTrue(false);
    }

}
