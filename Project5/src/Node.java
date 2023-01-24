import java.util.*;

public class Node {
    String name;
    HashMap<Node, Integer> adjacencyMap;
    //ArrayList<ArrayList<Object>> adjacency_list;
    Boolean bfsFlag;
    Boolean cutFlag;
    Node bfsParent;
    Node(String name, HashMap<Node, Integer> adjacencyMap){
        this.name = name;
        this.adjacencyMap = adjacencyMap;
    }
}
