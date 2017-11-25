package osoble.bloodhero.Models;

/**
 * Created by abdulahiosoble on 11/25/17.
 */

public class Appointment {
    private String id;
    private String bloodbank_id;
    private String time;
    private String date;

    public Appointment(String bloodBank, String date, String time, String id) {
        this.id = id;
        this.bloodbank_id = bloodBank;
        this.date = date;
        this.time = time;
    }

    public String getBloodBank() {
        return bloodbank_id;
    }

    public String getTime(){
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getID(){
        return id;
    }
}
