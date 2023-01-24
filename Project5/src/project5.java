import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class project5 {
    static Map<String, Node> str_to_node = new HashMap<String, Node>();
    static ArrayList<Node> vertex_list = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        FileReader inFile = new FileReader("cases/input_200.txt");
        BufferedReader scan = new BufferedReader(inFile);

        File output = new File("outm");
		FileWriter fw = new FileWriter(output);

        int city_number = Integer.parseInt(scan.readLine());
        
        String[] troop_num_str = scan.readLine().split(" ");
        Node source = new Node("s",new HashMap<Node,Integer>());
        vertex_list.add(source);
        str_to_node.put("s", source);

        for (int j=0;j<6;j++){
            String code = "r"+j;
            Node minisource = new Node(code,new HashMap<Node,Integer>());
            vertex_list.add(minisource);
            str_to_node.put(code, minisource);

        }

        for(int i=0;i<6;i++){
            
            String key = "r"+i;
                 
            source.adjacencyMap.put(str_to_node.get(key), Integer.parseInt(troop_num_str[i]));
        }

        for(int i=0;i<6;i++){
            String key = "r"+i;
            String[] neighbours = scan.readLine().split(" ");
            
            for(int k=1; k<neighbours.length;k+=2){
                Node des;
                if (str_to_node.containsKey(neighbours[k])){
                    des = str_to_node.get(neighbours[k]);
                }
                else{
                    des = new Node(neighbours[k], new HashMap<Node,Integer>());
                    vertex_list.add(des);
                    str_to_node.put(neighbours[k], des);
                }
                
                str_to_node.get(key).adjacencyMap.put(des,Integer.parseInt(neighbours[k+1]));

            }
        
        }
        for (int p=0; p<city_number;p++){
            String[] neighbours = scan.readLine().split(" ");
            Node from;
            if (str_to_node.containsKey(neighbours[0])){
                from = str_to_node.get(neighbours[0]);
            }
            else{
                from = new Node(neighbours[0], new HashMap<Node,Integer>());
                vertex_list.add(from);
                str_to_node.put(neighbours[0], from);
            }
            for (int m=1;m<neighbours.length;m+=2){
                Node des;
                if (str_to_node.containsKey(neighbours[m])){
                    des = str_to_node.get(neighbours[m]);
                }
                else{
                    des = new Node(neighbours[m], new HashMap<Node,Integer>());
                    vertex_list.add(des);
                    str_to_node.put(neighbours[m], des);
                }
                
                from.adjacencyMap.put(des,Integer.parseInt(neighbours[m+1]));
            }
        }
        fw.write(fordFulkerson(source,str_to_node.get("KL"))+"\n");

        fw.write(mincutedges(source));

        scan.close();
        fw.close();

    }

    public static boolean bfs(Node start, Node finish)
    {

        for (int i = 0; i < vertex_list.size(); ++i){
            vertex_list.get(i).bfsFlag = false;
            vertex_list.get(i).bfsParent = null;
        } 
        
        LinkedList<Node> queue = new LinkedList<Node>();
        queue.add(start);
        start.bfsFlag = true;
        start.bfsParent = null;
 
        while (queue.size() != 0) {
            Node u = queue.poll();
            for(Map.Entry<Node, Integer> set : u.adjacencyMap.entrySet()){
                Node neigh = (Node) set.getKey();
                int value = (int) set.getValue();
                if(value >0 && neigh.bfsFlag == false){

                    if (neigh == finish){
                        neigh.bfsParent = u;
                        return true;
                    }
                    queue.add(neigh);
                    neigh.bfsParent = u;
                    neigh.bfsFlag = true;
                }
            }
            
        }
 
   
        return false;
    }

    public static int fordFulkerson(Node start, Node finish)
    {
        
 
        int max_flow = 0; 
        while (bfs(start, finish)) {
            

            int path_flow = Integer.MAX_VALUE;
            
            for (Node v = finish; v != start; v = v.bfsParent) {
                Node parent_v = v.bfsParent;
                path_flow = Math.min(path_flow, parent_v.adjacencyMap.get(v));
            }
 
            
            for (Node v = finish; v != start; v = v.bfsParent) {
                Node parent_v = v.bfsParent;
                // u = parent_v
                if(parent_v.adjacencyMap.get(v)-path_flow > 0){
                    parent_v.adjacencyMap.put(v,parent_v.adjacencyMap.get(v)-path_flow);
                }else{
                    parent_v.adjacencyMap.put(v,0);
                }

                if (v.adjacencyMap.containsKey(parent_v)){
                    v.adjacencyMap.put(parent_v, v.adjacencyMap.get(parent_v)+path_flow);
                }else{
                    v.adjacencyMap.put(parent_v,path_flow);
                }
            }
 
            max_flow += path_flow;
        }
 
        return max_flow;
    }

    public static String mincutedges(Node start){
        String res="";
        for (int i = 0; i < vertex_list.size(); ++i){

            vertex_list.get(i).cutFlag = false;
        } 

        
        LinkedList<Node> queue = new LinkedList<Node>();
        queue.add(start);

        start.cutFlag = true;


        while (queue.size() != 0) {
            Node u = queue.poll();
            for(Map.Entry<Node, Integer> set : u.adjacencyMap.entrySet()){
                Node neigh = (Node) set.getKey();
                int value = (int) set.getValue();
                if(value > 0 && neigh.cutFlag==false){

                    neigh.cutFlag = true;
                    queue.add(neigh);
                }
            }
            
        }

        for (Node node : vertex_list) {
            for (Map.Entry<Node, Integer> entry : node.adjacencyMap.entrySet()) {
              Node neighbor = entry.getKey();
              if (node.cutFlag && !neighbor.cutFlag) {
                if(node.name=="s"){
                    res += neighbor.name+"\n";
                }else{
                    res += node.name + " " + neighbor.name + "\n";
                }

              }
            }
        }
        res+="\n";
        res+="\n";
        res+="\n";

        for (Node node : vertex_list) {
            if(node.cutFlag==true){
                res+=node.name+"\n";
            }
        }
        return res;
    }

}
