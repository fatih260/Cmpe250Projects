import java.util.*;

public class acc {
    public String name;
    public int acc_temp_time;
    public ArrayList<String> lst_airports;
    public ArrayList<String> hashed_airports;
    public PriorityQueue<flight> que;
    public int result;
    acc (String namek, ArrayList<String> lst_airportsk, ArrayList<String> hashed_airportsk, PriorityQueue<flight> quek, int acc_temp_timek, int resultk){
        name = namek;
        lst_airports = lst_airportsk;
        hashed_airports = hashed_airportsk;
        que = quek;
        acc_temp_time = acc_temp_timek;
        result = resultk;
    }
    
    public void accqueue_ent(flight e_flight){
        e_flight.flag = true;
        que.add(e_flight);
    }
    public void accqueue_dep(int i){
        flight d_flight = que.poll();                
        int start = d_flight.temp_time;
        if(acc_temp_time>start){
            start = acc_temp_time;
        }
        if(start>acc_temp_time){
            acc_temp_time = start;
        }
        int depart = d_flight.time_everystep.get(i);
        
        if (depart <= 30) {
            d_flight.temp_time = start + depart;
            acc_temp_time += depart;
            d_flight.flag = false;
            d_flight.counter++;
            d_flight.newly=0;
        }
        else{
            d_flight.temp_time = start + 30;
            acc_temp_time += 30;
            d_flight.time_everystep.set(i,depart-30);
            que.add(d_flight);
            d_flight.newly = 1;
        }        

    }
    public void waiting(flight w_flight, int i){
        w_flight.temp_time += w_flight.time_everystep.get(i);
        w_flight.counter++;

    }
}
