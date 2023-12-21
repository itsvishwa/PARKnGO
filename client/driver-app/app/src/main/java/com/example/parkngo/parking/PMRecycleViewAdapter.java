package com.example.parkngo.parking;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
        return new PMRecycleViewAdapter.MyViewHolder(view, parkingModels, (MainActivity) context);
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

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView parkingNameView;
        private TextView parkingTypeView;
        private TextView parkingAddressView;
        private TextView noOfReviewsView;
        private RatingBar ratingBarView;
        private TextView parkingStatusView;
        private ArrayList<ParkingModel> parkingModels; // Add a reference to parkingModels
        private Context context;

        private MainActivity mainActivity;

        public MyViewHolder(@NonNull View itemView, ArrayList<ParkingModel> parkingModels, MainActivity mainActivity) {
            super(itemView);
            this.parkingModels = parkingModels; // Assign parkingModels
            parkingNameView = itemView.findViewById(R.id.pi_name);
            parkingTypeView = itemView.findViewById(R.id.pi_type);
            parkingAddressView = itemView.findViewById(R.id.pi_address);
            noOfReviewsView = itemView.findViewById(R.id.parking_Selected_frag_review_count);
            ratingBarView = itemView.findViewById(R.id.pi_ratingbar);
            parkingStatusView = itemView.findViewById(R.id.pi_status);

            // Set the OnClickListener for the entire item view
            itemView.setOnClickListener(this);

            // Set the context
            context = itemView.getContext();

            this.mainActivity = mainActivity;
        }

        @Override
        public void onClick(View view) {
            // Get the position of the clicked item
            int position = getAdapterPosition();

            // Ensure the position is valid
            if (position != RecyclerView.NO_POSITION) {
                // Retrieve the ParkingModel associated with the clicked item
                ParkingModel clickedItem = parkingModels.get(position);

                // Get the ID from the ParkingModel
                int _id = clickedItem.get_id();

                // Create a Bundle to pass data to the fragment
                Bundle data = new Bundle();
                data.putInt("_id", _id);

                // Show a toast message with the ID
//                Toast.makeText(context, "Item ID: " + _id, Toast.LENGTH_LONG).show();

                mainActivity.replaceFragment(new ParkingSelectedFragment(), data);
            }
        }
    }
}