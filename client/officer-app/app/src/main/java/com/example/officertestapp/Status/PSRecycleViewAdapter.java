package com.example.officertestapp.Status;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.officertestapp.Home.ReleaseASlot01Fragment;
import com.example.officertestapp.Home.ReleaseASlot03Fragment;
import com.example.officertestapp.MainActivity;
import com.example.officertestapp.R;

import java.util.ArrayList;

public class PSRecycleViewAdapter extends RecyclerView.Adapter<PSRecycleViewAdapter.MyViewHolder>{

    ArrayList<ParkingStatusModel> parkingStatusModels;
    Context context;
    View sessionMainView;

    public PSRecycleViewAdapter(ArrayList<ParkingStatusModel> parkingStatusModels, Context context, View sessionMainView) {
        this.parkingStatusModels = parkingStatusModels;
        this.context = context;
        this.sessionMainView = sessionMainView;
    }

    @NonNull
    @Override
    public PSRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.parking_status_item, parent, false);
        return new PSRecycleViewAdapter.MyViewHolder(view, parkingStatusModels, context, sessionMainView);
    }


    // bind values to the views in item
    @Override
    public void onBindViewHolder(@NonNull PSRecycleViewAdapter.MyViewHolder holder, int position) {
        holder.vehicleNumberView.setText(parkingStatusModels.get(position).getVehicleNumber());
        holder.vehicleTypeView.setText(parkingStatusModels.get(position).getVehicleType());
        holder.dateTimeView.setText(parkingStatusModels.get(position).getDateTime());
        holder.parkingStatusView.setText(parkingStatusModels.get(position).getParkingStatus());


        if(parkingStatusModels.get(position).getParkingStatus() == "Payment Due"){
            Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.red_rounded_bg);
            holder.parkingStatusView.setBackground(drawable);
        } else if (parkingStatusModels.get(position).getParkingStatus() == "In Progress") {
            Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.yellow_rounded_bg);
            holder.parkingStatusView.setBackground(drawable);
        }
    }

    // we have to give the length of the items we have
    @Override
    public int getItemCount() {
        return parkingStatusModels.size();
    }

    // hold view of the layout (item)
    public static class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        TextView vehicleNumberView;
        TextView vehicleTypeView;
        TextView dateTimeView;
        TextView parkingStatusView;
        ArrayList<ParkingStatusModel> parkingStatusModels;
        Context context;
        View sessionMainView;

        public MyViewHolder(@NonNull View itemView, ArrayList<ParkingStatusModel> parkingStatusModels, Context context, View sessionMainView) {
            super(itemView);
            this.context = context;
            this.parkingStatusModels = parkingStatusModels;
            vehicleNumberView = itemView.findViewById(R.id.ps_vehicle_number);
            vehicleTypeView = itemView.findViewById(R.id.ps_vehicle_type);
            dateTimeView = itemView.findViewById(R.id.ps_date_time);
            parkingStatusView = itemView.findViewById(R.id.ps_status);
            this.sessionMainView = sessionMainView;

            // Set the OnClickListener for the entire item view
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Get the position of the clicked item
            int position = getAdapterPosition();

            // Ensure the position is valid
            if (position != RecyclerView.NO_POSITION) {
                ParkingStatusModel clickedItem = parkingStatusModels.get(position);
                String _id = clickedItem.getID();
                // Create a Bundle to pass data to the fragment
                Bundle data = new Bundle();
                data.putString("_id", _id);
                MainActivity mainActivity = (MainActivity) context;
                if(clickedItem.getParkingStatus().equals("Payment Due")){
                    mainActivity.replaceFragment(new ReleaseASlot03Fragment(), data, sessionMainView);
                }else{
                    mainActivity.replaceFragment(new ReleaseASlot01Fragment(), data, sessionMainView);
                }
            }
        }
    }
}