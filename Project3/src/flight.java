import java.util.ArrayList;

public class flight implements Comparable<flight>{
    public int admission_time;
    public Integer temp_time;
    public String flight_code;
    public String flight_acc_code;
    public String departure_airp;
    public String landing_airp;
    public ArrayList<Integer> time_everystep;
    public int counter;
    public boolean flag;
    public Integer newly;
    flight(int admission_timek, String flight_codek, String flight_acc_codek,String departure_airpk,String landing_airpk,ArrayList<Integer> time_everystepk,int temp_timek,int counterk,boolean flagk, int newlyk){
        admission_time =admission_timek;
        flight_code=flight_codek;
        flight_acc_code =flight_acc_codek;
        departure_airp=departure_airpk;
        landing_airp=landing_airpk;
        time_everystep=time_everystepk;
        temp_time = temp_timek;
        counter = counterk;
        flag = flagk;
        newly = newlyk;
    }
    @Override
    public int compareTo(flight o) {
        if (this.temp_time.compareTo(o.temp_time) != 0){
            return this.temp_time.compareTo(o.temp_time);
        }
        else if (this.newly.compareTo(o.newly) != 0){
            return this.newly.compareTo(o.newly);
        }
        else {
            return this.flight_code.compareTo(o.flight_code);
        }
    }
    
}
