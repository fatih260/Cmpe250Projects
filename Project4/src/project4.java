import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import javax.print.FlavorException;

public class project4 {
    static ArrayList<Node> vertex_lst = new ArrayList<>();
    static ArrayList<Node> flagged_lst = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        FileReader inFile = new FileReader("input/inp9.in");
        BufferedReader bf = new BufferedReader(inFile);

        File output = new File("outp.txt");
		FileWriter fw = new FileWriter(output);

        Map<String, Node> str_to_node = new HashMap<String, Node>();
        int num_vertices = Integer.parseInt(bf.readLine());
        
        
        int num_flags = Integer.parseInt(bf.readLine());

        
        String[] start_finish = bf.readLine().split(" ");
        String start_str = start_finish[0];
        String finish_str = start_finish[1];

        
        String[] withflags = bf.readLine().split(" ");
        

        for (int k=0; k<num_vertices; k++){
            String[] dists_line = bf.readLine().split(" ");
            Node init;
            if (str_to_node.containsKey(dists_line[0])){
                init = str_to_node.get(dists_line[0]);
            }else{
                init = new Node(dists_line[0],new ArrayList<ArrayList<Object>>(),false);
                vertex_lst.add(init);
                str_to_node.put(dists_line[0], init);
            }            
            for (int p=1; p<dists_line.length; p+=2){
                Node dest;
                if (str_to_node.containsKey(dists_line[p])){
                    dest = str_to_node.get(dists_line[p]);
                }else{
                    dest = new Node(dists_line[p],new ArrayList<ArrayList<Object>>(),false);
                    vertex_lst.add(dest);
                    str_to_node.put(dists_line[p], dest);
                }
                int distance = Integer.parseInt(dists_line[p+1]);
                
                ArrayList<Object> new_arrlst = new ArrayList<Object>();
                new_arrlst.add(dest);
                new_arrlst.add(distance);
                init.adjacency_list.add(new_arrlst);

                ArrayList<Object> new_arrlst_reversed = new ArrayList<Object>();
                new_arrlst_reversed.add(init);
                new_arrlst_reversed.add(distance);
                dest.adjacency_list.add(new_arrlst_reversed);

            }
            
        }
        for (String each_code : withflags){
            Node flagged = str_to_node.get(each_code);
            flagged.flag = true;  
        }
        

        Node start_vertex = str_to_node.get(start_str);
        Node finish_vertex = str_to_node.get(finish_str);
        fw.write(dijkstra_alg(start_vertex,finish_vertex)+"\n");


        Integer val = modified_dijkstra_alg(str_to_node.get(withflags[0]), num_flags);
        fw.write(val+"");
                
        bf.close();
        fw.close();  
    }
    public static int dijkstra_alg(Node start,Node finish){
        PriorityQueue<Node> pq_dj = new PriorityQueue<Node>();
        for (Node e : vertex_lst){
            e.d_distance = Integer.MAX_VALUE;
            e.dj_in = false;
        }
        start.d_distance = 0;
        pq_dj.add(start);
        

        while (!pq_dj.isEmpty()){
            Node updated = pq_dj.poll();
            updated.dj_in = true;
            int x = updated.d_distance;

            for(int j=0; j<updated.adjacency_list.size(); j++){
                ArrayList<Object> arrlist = updated.adjacency_list.get(j);
                Node neigh = (Node) arrlist.get(0);
                int value = (int) arrlist.get(1);
                if (neigh.dj_in == false && neigh.d_distance > x + value){
                    neigh.d_distance = x + value;
                    pq_dj.add(neigh);
                }
                
            }

        }
        if (finish.d_distance == Integer.MAX_VALUE){
            return -1;
        }
        return finish.d_distance;
    }

    public static int modified_dijkstra_alg(Node start, int limit){
        PriorityQueue<Node> pq_dj = new PriorityQueue<Node>();
        for (Node e : vertex_lst){
            e.d_distance = Integer.MAX_VALUE;
            e.dj_in = false;
            e.mdj_in = false;
        }
        start.d_distance = 0;
        pq_dj.add(start);
        start.mdj_in = true;
        
        int counter = 0;

        while (counter < limit){
            if (pq_dj.isEmpty()){
                return -1;
            }
            Node updated = pq_dj.poll();
            updated.mdj_in = false;
            
            int x = updated.d_distance;
            if(updated.flag == true){
                updated.dj_in = true;
                x = 0;
                counter++;
            }


            for(int j=0; j<updated.adjacency_list.size(); j++){
                ArrayList<Object> arrlist = updated.adjacency_list.get(j);
                Node neigh = (Node) arrlist.get(0);
                int value = (int) arrlist.get(1);
                if (neigh.dj_in == false && neigh.d_distance > x + value){
                    neigh.d_distance = x + value;
                    if (neigh.mdj_in == true){
                        pq_dj.remove(neigh);
                        neigh.mdj_in = false;
                    }
                    pq_dj.add(neigh);
                    neigh.mdj_in = true;
                }
                
            }

        }
        int res=0;

        for(Node e : vertex_lst){
            
            if(e.flag==true){
                if(e.d_distance == Integer.MAX_VALUE){
                    return -1;
                }
                res+=e.d_distance;
            }
        }

        return res;
    }
}