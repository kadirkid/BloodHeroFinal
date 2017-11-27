package osoble.bloodhero.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import osoble.bloodhero.Models.Appointment;
import osoble.bloodhero.Models.BloodBank;
import osoble.bloodhero.R;
import osoble.bloodhero.Utils.Utils;

/**
 * Created by abdulahiosoble on 11/25/17.
 */

public class AppointmentAdapter extends FirebaseRecyclerAdapter<Appointment, AppointmentAdapter
        .AppointmentViewHolder> {
    private Context mContext;

    public AppointmentAdapter(Class<Appointment> modelClass, int modelLayout,
                              Class<AppointmentViewHolder> viewHolderClass, Query ref,
                              Context mContext) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.mContext = mContext;
        Log.i("Appointment Contructor", "Was Called");
    }

//    public AppointmentAdapter(Class<Appointment> modelClass, int modelLayout,
//                              Class<AppointmentViewHolder> viewHolderClass, DatabaseReference ref,
//                              Context mContext) {
//        super(modelClass, modelLayout, viewHolderClass, ref);
//        this.mContext = mContext;
//        Log.i("Appointment Contructor", "Was Called");
//    }

    @Override
    protected void populateViewHolder(AppointmentViewHolder viewHolder, Appointment model, int position) {
        viewHolder.bind(model);
        Log.i("D populateViewHolder", "Was Called");
    }

    public static class AppointmentViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        private BloodBank bloodBank;

        public AppointmentViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
        }

        public void bind(Appointment appointment) {

            bloodBank = new Utils().getBloodBank(appointment.getBloodBank());
            time.setText("At " + appointment.getTime() + ", " + bloodBank.getName());
        }
    }
}
