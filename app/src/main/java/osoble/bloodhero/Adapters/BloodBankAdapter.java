package osoble.bloodhero.Adapters;

import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;

import osoble.bloodhero.Models.BloodBank;
import osoble.bloodhero.R;

/**
 * Created by abdulahiosoble on 11/5/17.
 */

public class BloodBankAdapter extends FirebaseRecyclerAdapter<BloodBank, BloodBankAdapter
        .BloodBankViewHolder>{
    private static final String TAG = BloodBankAdapter.class.getSimpleName();
    private int day, month, year, hour, minute;
    private int dayF, monthF, yearF, hourF, minuteF;
    private Context mContext;
    private Calendar c;
    FragmentTransaction fragmentTransaction;

    public BloodBankAdapter(Class<BloodBank> modelClass, int modelLayout, Class<BloodBankViewHolder>
            viewHolderClass, DatabaseReference ref, Context mContext) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.mContext = mContext;
        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        Log.i("Contructor", "Was Called");
    }

    @Override
    protected void populateViewHolder(BloodBankViewHolder viewHolder, final BloodBank model,
                                      int position) {
        viewHolder.bind(model);

    }

    public static class BloodBankViewHolder extends RecyclerView.ViewHolder {
        TextView name, address;

        public BloodBankViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.blood_bank_name);
            address = itemView.findViewById(R.id.blood_bank_address);
        }

        //Bind the bloodbank to the recyclerview
        public void bind(BloodBank bloodBank){
            name.setText(bloodBank.getName());
            address.setText(bloodBank.getAddress());
        }
    }
}

/*


viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, HomeActivity.class);


                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog
                        .OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        yearF = i;
                        monthF = i1;
                        dayF = i2;

                        c = Calendar.getInstance();
                        hour = c.get(Calendar.HOUR);
                        minute = c.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                hourF = i;
                                minuteF = i1;
                                show();
                            }
                        }, hour, minute, false);

                        timePickerDialog.show();
                    }
                },
                        year, month, day);



                datePickerDialog.show();
                intent.putExtra("ID", model.getID());

                //mContext.startActivity(intent);  //Must declare the intent properly later on
            }
        });


 */