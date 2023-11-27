package com.example.parkngo.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkngo.R;

import java.util.ArrayList;

public class APSRecycleViewAdapter extends RecyclerView.Adapter<APSRecycleViewAdapter.MyViewHolder>{

    Context context;
    ArrayList<AvailableParkingSpaceModel> availableParkingSpaceModels;
    public APSRecycleViewAdapter(Context context, ArrayList<AvailableParkingSpaceModel> availableParkingSpaceModels) {
        this.context = context;
        this.availableParkingSpaceModels = availableParkingSpaceModels;
    }

    @NonNull
    @Override
    public APSRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the layout (giving look to rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.available_parking_item, parent, false);
        return new APSRecycleViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull APSRecycleViewAdapter.MyViewHolder holder, int position) {
        // assign values to the view
        // based on the position of recycler view
        holder.parkingNameView.setText(availableParkingSpaceModels.get(position).getParkingName());
        holder.freeSlotsView.setText(availableParkingSpaceModels.get(position).getFreeSlots() + "");
        holder.totalSlotsView.setText(availableParkingSpaceModels.get(position).getTotalSlots() + "");
        holder.rateView.setText("Rs. " + availableParkingSpaceModels.get(position).getRate() + "/1h");
        holder.parkingTypeView.setText(availableParkingSpaceModels.get(position).getParkingType());
        if(availableParkingSpaceModels.get(position).getParkingType() !="Public"){
            Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.round_red_circle);
            holder.parkingTypeView.setBackground(drawable);
        }else{
            Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.round_green_circle);
            holder.parkingTypeView.setBackground(drawable);
        }
        holder.distanceView.setText(availableParkingSpaceModels.get(position).getDistance() + " m");
        holder.ratingBarView.setRating(availableParkingSpaceModels.get(position).getNoOfStars() * 1.0f);
        holder.noOfReviewsView.setText("(" + availableParkingSpaceModels.get(position).getNoOfReviews() + ")");
    }

    @Override
    public int getItemCount() {
        // number of items you want to display
        return availableParkingSpaceModels.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView parkingNameView;
        TextView freeSlotsView;
        TextView totalSlotsView;
        TextView rateView;
        TextView parkingTypeView;
        TextView distanceView;
        RatingBar ratingBarView;
        TextView noOfReviewsView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            parkingNameView = itemView.findViewById(R.id.ava_parking_name);
            freeSlotsView = itemView.findViewById(R.id.ava_free_slots);
            totalSlotsView = itemView.findViewById(R.id.ava_total_slots);
            rateView = itemView.findViewById(R.id.ava_rate);
            parkingTypeView = itemView.findViewById(R.id.ava_type);
            distanceView = itemView.findViewById(R.id.ava_distance);
            ratingBarView = itemView.findViewById(R.id.ava_rating_bar);
            noOfReviewsView = itemView.findViewById(R.id.ava_rating_count);
        }
    }
}
