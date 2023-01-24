import java.util.*;

public class atc {
    public String name;
    public Queue<flight> que;
    public int atc_temp_time;
    atc (String namek, Queue<flight> quek, int atc_temp_timek){
        name = namek;
        que = quek;
        atc_temp_time = atc_temp_timek;
    }
    public void atc_ent(flight e_flight){
        que.add(e_flight);
    }
    public void atc_dep(int i){
        flight d_flight = que.poll();
        int start = d_flight.temp_time;
        if(atc_temp_time>start){
            start = atc_temp_time;
        }
        if(start>atc_temp_time){
            atc_temp_time = start;
        }
        int depart = d_flight.time_everystep.get(i);
        d_flight.temp_time = start + depart;
        atc_temp_time += depart;
        d_flight.counter++;

    }
    public void waiting(flight w_flight, int i){
        w_flight.temp_time += w_flight.time_everystep.get(i);
        w_flight.counter++;

    }

}
