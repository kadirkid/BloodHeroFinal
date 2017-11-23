package osoble.bloodhero.Models;

import java.util.UUID;

/**
 * Created by abdulahiosoble on 10/20/17.
 */

public class BloodBank {
    private final String TAG = BloodBank.class.getSimpleName();
    private String ID;
    private String name;
    private String tel_no;
    private String fax_no;
    private String address;
    private String priority;

    public BloodBank(String name, String tel_no, String fax_no, String address) {
        ID = UUID.randomUUID().toString();
        this.name = name;
        this.tel_no = tel_no;
        this.fax_no = fax_no;
        this.address = address;
        this.priority = "";
    }

    public BloodBank() {}

    public String getName() {
        return name;
    }

    public String getTel_no() {
        return tel_no;
    }

    public String getFax_no() {
        return fax_no;
    }

    public String getAddress() {
        return address;
    }

    public String getID() {
        return ID;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}