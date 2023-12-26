package com.example.parkngo.home.helpers;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.parking.ParkingModel;
import com.example.parkngo.parking.ParkingSelectedFragment;

import java.util.ArrayList;

public class APSRecycleViewAdapter extends RecyclerView.Adapter<APSRecycleViewAdapter.MyViewHolder>{

    Context context;
    ArrayList<AvailableParkingSpaceModel> availableParkingSpaceModelsArr;
    public APSRecycleViewAdapter(Context context, ArrayList<AvailableParkingSpaceModel> availableParkingSpaceModels) {
        this.context = context;
        this.availableParkingSpaceModelsArr = availableParkingSpaceModels;
    }

    @NonNull
    @Override
    public APSRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the layout (giving look to rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.available_parking_item, parent, false);
        return new APSRecycleViewAdapter.MyViewHolder(view, availableParkingSpaceModelsArr);
    }

    @Override
    public void onBindViewHolder(@NonNull APSRecycleViewAdapter.MyViewHolder holder, int position) {
        // assign values to the view
        // based on the position of recycler view
        holder.parkingNameView.setText(availableParkingSpaceModelsArr.get(position).getParkingName());
        holder.freeSlotsView.setText(availableParkingSpaceModelsArr.get(position).getFreeSlots());
        holder.totalSlotsView.setText(availableParkingSpaceModelsArr.get(position).getTotalSlots());
        holder.rateView.setText(availableParkingSpaceModelsArr.get(position).getRate());
        holder.parkingTypeView.setText(availableParkingSpaceModelsArr.get(position).getParkingType());
        if(availableParkingSpaceModelsArr.get(position).getParkingType() !="Public"){
            Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.round_red_circle);
            holder.parkingTypeView.setBackground(drawable);
        }else{
            Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.round_green_circle);
            holder.parkingTypeView.setBackground(drawable);
        }
        holder.distanceView.setText(availableParkingSpaceModelsArr.get(position).getDistance());
        holder.ratingBarView.setRating(availableParkingSpaceModelsArr.get(position).getNoOfStars());
        holder.noOfReviewsView.setText(availableParkingSpaceModelsArr.get(position).getNoOfReviews());
    }

    @Override
    public int getItemCount() {
        // number of items you want to display
        return availableParkingSpaceModelsArr.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {

        TextView parkingNameView;
        TextView freeSlotsView;
        TextView totalSlotsView;
        TextView rateView;
        TextView parkingTypeView;
        TextView distanceView;
        RatingBar ratingBarView;
        TextView noOfReviewsView;
        Context context;
        ArrayList<AvailableParkingSpaceModel> availableParkingSpaceModelsArr;

        public MyViewHolder(@NonNull View itemView, ArrayList<AvailableParkingSpaceModel> availableParkingSpaceModelsArr) {
            super(itemView);
            parkingNameView = itemView.findViewById(R.id.ava_parking_name);
            freeSlotsView = itemView.findViewById(R.id.ava_free_slots);
            totalSlotsView = itemView.findViewById(R.id.ava_total_slots);
            rateView = itemView.findViewById(R.id.ava_rate);
            parkingTypeView = itemView.findViewById(R.id.ava_type);
            distanceView = itemView.findViewById(R.id.ava_distance);
            ratingBarView = itemView.findViewById(R.id.ava_rating_bar);
            noOfReviewsView = itemView.findViewById(R.id.ava_rating_count);
            this.availableParkingSpaceModelsArr = availableParkingSpaceModelsArr;
            this.context = itemView.getContext();

            // Set the OnClickListener for the entire item view
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            // Get the position of the clicked item
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                AvailableParkingSpaceModel clickedItem = availableParkingSpaceModelsArr.get(position);

                String latitude = clickedItem.getLatitude();
                String longitude = clickedItem.getLongitude();

                double sourceLatitude = 6.902727395785716;
                double sourceLongitude = 79.86126018417747;
                double destinationLatitude = Double.parseDouble(latitude);
                double destinationLongitude = Double.parseDouble(longitude);

                String uri = "https://www.google.com/maps/dir/?api=1&origin=" + sourceLatitude + "," + sourceLongitude +
                "&destination=" + destinationLatitude + "," + destinationLongitude;

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.startActivity(intent);
            }
        }
    }
}
