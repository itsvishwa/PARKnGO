package com.example.officertestapp.Status;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.officertestapp.R;

import java.util.ArrayList;

public class PSRecycleViewAdapter extends RecyclerView.Adapter<PSRecycleViewAdapter.MyViewHolder> {

    ArrayList<ParkingStatusModel> parkingStatusModels;
    Context context;

    public PSRecycleViewAdapter( ArrayList<ParkingStatusModel> parkingStatusModels, Context context) {
        this.parkingStatusModels = parkingStatusModels;
        this.context = context;
    }

    @NonNull
    @Override
    public PSRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the layout(Giving a look to our rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.parking_status_item, parent, false);
        return new PSRecycleViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PSRecycleViewAdapter.MyViewHolder holder, int position) {
        // assigning values to the views we created in the item layout file
        // based on the position of the recycler view

        holder.parkingIDView.setText(parkingStatusModels.get(position).getParkingID());
        holder.parkingStatusView.setText(parkingStatusModels.get(position).getParkingStatus());
        holder.vehicleNumberView.setText(parkingStatusModels.get(position).getVehicleNumber());

        if(parkingStatusModels.get(position).getParkingStatus() == "Filled"){
            Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.red_rounded_bg);
            holder.parkingStatusView.setBackground(drawable);
        } else if (parkingStatusModels.get(position).getParkingStatus() == "Payment Due") {
            Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.yellow_rounded_bg);
            holder.parkingStatusView.setBackground(drawable);
        }else{
            Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.green_rounded_bg);
            holder.parkingStatusView.setBackground(drawable);
        }
    }


    @Override
    public int getItemCount() {
        // number of items that want to display

        return parkingStatusModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView parkingIDView;
        TextView parkingStatusView;
        TextView vehicleNumberView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            parkingIDView = itemView.findViewById(R.id.ps_parking_id);
            parkingStatusView = itemView.findViewById(R.id.ps_status);
            vehicleNumberView = itemView.findViewById(R.id.ps_vehicle_number);

        }
    }
}
