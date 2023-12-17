package com.example.parkngo.parking;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkngo.R;

import java.util.ArrayList;

public class PMRecycleViewAdapter extends RecyclerView.Adapter<PMRecycleViewAdapter.MyViewHolder> {

    ArrayList<ParkingModel> parkingModels;
    Context context;

    public PMRecycleViewAdapter(ArrayList<ParkingModel> parkingModels, Context context){
        this.parkingModels = parkingModels;
        this.context = context;
    }

    @NonNull
    @Override
    public PMRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.parking_item, parent, false);
        return new PMRecycleViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PMRecycleViewAdapter.MyViewHolder holder, int position) {
        holder.parkingNameView.setText(parkingModels.get(position).getParkingName());
        holder.parkingTypeView.setText(parkingModels.get(position).getParkingType());
        holder.parkingAddressView.setText(parkingModels.get(position).getParkingAddress());
        holder.noOfReviewsView.setText("(" + parkingModels.get(position).getNoOfReviews() + ")");
        holder.ratingBarView.setRating(parkingModels.get(position).getNoOfStars());
        holder.parkingStatusView.setText(parkingModels.get(position).getParkingStatus());

        if(parkingModels.get(position).getParkingType() !="Public"){
            Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.round_red_circle);
            holder.parkingTypeView.setBackground(drawable);
        }else{
            Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.round_green_circle);
            holder.parkingTypeView.setBackground(drawable);
        }

        if(parkingModels.get(position).getParkingStatus() !="Open"){
            Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.round_red_circle);
            holder.parkingStatusView.setBackground(drawable);
        }else{
            Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.round_green_circle);
            holder.parkingStatusView.setBackground(drawable);
        }
    }

    @Override
    public int getItemCount() {
        return parkingModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView parkingNameView;
        TextView parkingTypeView;
        TextView parkingAddressView;
        TextView noOfReviewsView;
        RatingBar ratingBarView;

        TextView parkingStatusView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            parkingNameView = itemView.findViewById(R.id.pi_name);
            parkingTypeView = itemView.findViewById(R.id.pi_type);
            parkingAddressView = itemView.findViewById(R.id.pi_address);
            noOfReviewsView = itemView.findViewById(R.id.pi_noofreviews);
            ratingBarView = itemView.findViewById(R.id.pi_ratingbar);
            parkingStatusView = itemView.findViewById(R.id.pi_status);
        }
    }
}
