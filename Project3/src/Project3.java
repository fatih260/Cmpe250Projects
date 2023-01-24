import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Project3 {
    public static void main(String[] args) throws IOException {
        File inFile = new File(args[0]);
        Scanner scan = new Scanner(inFile);
        
        File output = new File(args[1]);
		FileWriter fWriter = new FileWriter(output);

        String first_line = scan.nextLine();
        int num_acc = Integer.parseInt(first_line.split(" ")[0]);
        int num_airports = Integer.parseInt(first_line.split(" ")[1]);
        Map<String,acc> code_to_acc = new HashMap<>();
        ArrayList<acc> acc_lst = new ArrayList<>();
        Map<String,atc> code_to_atc = new HashMap<>();
        Map<acc, PriorityQueue<flight>> acc_map_arrl_flight = new HashMap<>() ;

        for (int i=0; i<num_acc; i++){
            String[] hash_table = new String[1000];
            String[] new_line_arr = scan.nextLine().split(" ");
            String acc_code = new_line_arr[0];
            ArrayList<String> acc_arrlst = new ArrayList<>();
            ArrayList<String> acc_hshlst = new ArrayList<>();
            for (int j=1; j<new_line_arr.length;j++){
                String code = new_line_arr[j];
                Deque<flight> que_atc = new ArrayDeque<flight>();
                atc new_atc = new atc(code, que_atc, 0);
                code_to_atc.put(code, new_atc);
                acc_arrlst.add(code);
                int val = 0;
                for (int p=0; p<3; p++){
                    int ascii_val = code.charAt(p);
                    val += (Math.pow(31, p)*ascii_val);
                } 
                if (val>=1000) {
                    val = val % 1000;
                }
                
                while(hash_table[val]!=null){
                    val = (val + 1)%1000;
                }
                hash_table[val] = code;
            }
            for (int t=0; t<1000; t++){
                if (hash_table[t]!=null){
                    String hsh;
                    if (t < 10){
                        hsh = hash_table[t] + "00" + Integer.toString(t);
                    }
                    else if (10<=t && t < 100) {
                        hsh = hash_table[t] + "0" + Integer.toString(t);
                    }
                    else{
                        hsh = hash_table[t] + Integer.toString(t);
                    }
                    acc_hshlst.add(hsh);
                }
            }            
            PriorityQueue<flight> que_acc = new PriorityQueue<flight>();
            acc new_acc = new acc(acc_code, acc_arrlst,acc_hshlst, que_acc,0,0);
            acc_lst.add(new_acc);
            code_to_acc.put(acc_code, new_acc);
            acc_map_arrl_flight.put(new_acc,new PriorityQueue<flight>());
        }
        
        ArrayList<flight> flight_lst = new ArrayList<>();

        for (int k=0; k<num_airports; k++){
            String[] new_line_ar = scan.nextLine().split(" ");
            int admission_time = Integer.parseInt(new_line_ar[0]);
            String flight_code = new_line_ar[1];
            String flight_acc_code = new_line_ar[2];
            String departure_airp = new_line_ar[3];
            String landing_airp = new_line_ar[4];
            ArrayList<Integer> time_everystep = new ArrayList<>();
            for (int l=5; l<new_line_ar.length; l++){
                time_everystep.add(Integer.parseInt(new_line_ar[l]));
            }

            flight new_flight = new flight(admission_time, flight_code, flight_acc_code, departure_airp, landing_airp, time_everystep, admission_time, 1, false, 0);
            flight_lst.add(new_flight);

            PriorityQueue<flight> newtobeinserted = acc_map_arrl_flight.get(code_to_acc.get(flight_acc_code));
            newtobeinserted.add(new_flight);
            acc_map_arrl_flight.put(code_to_acc.get(flight_acc_code), newtobeinserted);
        }

        for(acc e_acc : acc_lst){
            PriorityQueue<flight> accs_flights = acc_map_arrl_flight.get(e_acc); 
            
            
            while (!accs_flights.isEmpty() ) {
                flight cur_flight = accs_flights.poll();
                String depart_airport = cur_flight.departure_airp;
                atc dep_atc = code_to_atc.get(depart_airport);

                String landing_airport = cur_flight.landing_airp;
                atc lan_atc = code_to_atc.get(landing_airport);

                int test = cur_flight.counter;
                if(test == 22){
                    if (accs_flights.size() == 0){
                        e_acc.result = cur_flight.temp_time;
                    };
                    continue;
                }
                if (test==1 || test==3 || test==11 || test==13 || test==21){
                    if (cur_flight.flag == false){
                        e_acc.accqueue_ent(cur_flight);
                    }
                    e_acc.accqueue_dep(test-1);
                    
                }
                if (test==2||test==12){
                    e_acc.waiting(cur_flight, test-1);
                }
                if (test==4||test==6||test==8||test==10){
                    dep_atc.atc_ent(cur_flight);
                    dep_atc.atc_dep(test-1);
                }
                if (test==14||test==16||test==18||test==20){
                    lan_atc.atc_ent(cur_flight);
                    lan_atc.atc_dep(test-1);
                }
                if (test==5||test==7||test==9){
                    dep_atc.waiting(cur_flight, test-1);
                }
                if (test==15||test==17||test==19){
                    dep_atc.waiting(cur_flight, test-1);
                }
                
                accs_flights.add(cur_flight);

            }
            
            
        }
        

        for (acc one_acc : acc_lst){
            fWriter.write(one_acc.name + " ");
            fWriter.write(one_acc.result + " ");
            for (int i=0; i<one_acc.hashed_airports.size();i++){
                String e = one_acc.hashed_airports.get(i);
                fWriter.write(e);
                if (i != one_acc.hashed_airports.size()-1){
                    fWriter.write(" ");
                }
            }
            fWriter.write("\n");
        }
        scan.close();
        fWriter.close();
    }
}