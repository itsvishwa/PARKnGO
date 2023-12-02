package com.example.parkngo.parking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkngo.R;

import java.util.ArrayList;

public class RMRecycleViewAdapter extends RecyclerView.Adapter<RMRecycleViewAdapter.MyViewHolder> {
    ArrayList<ReviewModel> reviewModels;
    Context context;
    public RMRecycleViewAdapter(ArrayList<ReviewModel> reviewModels, Context context) {
        this.reviewModels = reviewModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RMRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.review_item, parent, false);
        return new RMRecycleViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RMRecycleViewAdapter.MyViewHolder holder, int position) {
        holder.nameView.setText(reviewModels.get(position).getUserName());
        holder.dateView.setText(reviewModels.get(position).getDate());
        holder.msgView.setText(reviewModels.get(position).getMsg());
        holder.ratingBarView.setRating(reviewModels.get(position).getNoOfStars());
    }

    @Override
    public int getItemCount() {
        return reviewModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameView;
        RatingBar ratingBarView;
        TextView msgView;
        TextView dateView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameView = itemView.findViewById(R.id.ri_name);
            ratingBarView = itemView.findViewById(R.id.ri_rating_bar);
            msgView = itemView.findViewById(R.id.ri_msg);
            dateView = itemView.findViewById(R.id.ri_date);
        }
    }
}
