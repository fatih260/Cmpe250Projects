import java.util.*;
public class Node implements Comparable<Node>{
    String name;
    ArrayList<ArrayList<Object>> adjacency_list;
    Boolean flag;
    Integer d_distance;
    Boolean dj_in;
    Boolean pr_in;
    Boolean pr_pq_in;
    Boolean mdj_in;

    Node(String name, ArrayList<ArrayList<Object>> adjacency_list, Boolean flag){
        this.name = name;
        this.adjacency_list = adjacency_list;
        this.flag = flag;
    }
    @Override
    public int compareTo(Node o) {
        if (this.d_distance.compareTo(o.d_distance) != 0){
            return this.d_distance.compareTo(o.d_distance);
        } else {
            return this.name.compareTo(o.name);
        }
    }
}
