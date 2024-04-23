package com.example.officertestapp.ForceEnd.Helpers;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.officertestapp.ForceEnd.ForceEndedModel;
import com.example.officertestapp.Home.PaymentDetailsFragment;
import com.example.officertestapp.Home.ReleaseASlotFragment;
import com.example.officertestapp.MainActivity;
import com.example.officertestapp.R;
import com.example.officertestapp.Status.ParkingStatusModel;

import java.util.ArrayList;

public class ForceEndedRecycleViewAdapter extends RecyclerView.Adapter<ForceEndedRecycleViewAdapter.MyViewHolder> {
    ArrayList<ForceEndedModel> forceEndedModels;
    Context context;

    public ForceEndedRecycleViewAdapter(ArrayList<ForceEndedModel> forceEndedModels, Context context) {
        this.forceEndedModels = forceEndedModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ForceEndedRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.force_ended_item, parent, false);

        return new ForceEndedRecycleViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForceEndedRecycleViewAdapter.MyViewHolder holder, int position) {
        holder.vehicleNumView.setText(forceEndedModels.get(position).getVehicleNumber());

        holder.vehicleTypeView.setText(forceEndedModels.get(position).getVehicleType());

        holder.startDateTimeView.setText(forceEndedModels.get(position).getStartDateTime());

        holder.endDateTimeView.setText(forceEndedModels.get(position).getEndDateTime());
    }

    @Override
    public int getItemCount() {

        return forceEndedModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView vehicleNumView;
        TextView vehicleTypeView;
        TextView startDateTimeView;
        TextView endDateTimeView;
        ArrayList<ForceEndedModel> forceEndedModels;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.vehicleNumView = itemView.findViewById(R.id.force_end_frag_vehicle_number);
            this.vehicleTypeView = itemView.findViewById(R.id.force_end_frag_vehicle_type);
            this.startDateTimeView = itemView.findViewById(R.id.force_end_frag_session_start);
            this.endDateTimeView = itemView.findViewById(R.id.force_end_frag_session_force_end);
        }

        @Override
        public void onClick(View view) {
            // Get the position of the clicked item
            int position = getAdapterPosition();

            // Ensure the position is valid
            if (position != RecyclerView.NO_POSITION) {
                ForceEndedModel clickedItem = forceEndedModels.get(position);
                String _id = clickedItem.getID();
                // Create a Bundle to pass data to the fragment
                Bundle data = new Bundle();
                data.putString("session_id", _id);
                //MainActivity mainActivity = (MainActivity) context;
            }
        }
    }
}
