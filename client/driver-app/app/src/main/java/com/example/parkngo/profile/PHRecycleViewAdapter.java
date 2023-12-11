package com.example.parkngo.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkngo.R;

import java.util.ArrayList;

public class PHRecycleViewAdapter extends RecyclerView.Adapter<PHRecycleViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<PaymentHistoryModel> paymentHistoryModels;

    public PHRecycleViewAdapter(Context context, ArrayList<PaymentHistoryModel> paymentHistoryModels) {
        this.context = context;
        this.paymentHistoryModels = paymentHistoryModels;
    }


    @NonNull
    @Override
    public PHRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.payment_history_item, parent, false);
        return new PHRecycleViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PHRecycleViewAdapter.MyViewHolder holder, int position) {
        holder.dateTimeView.setText(paymentHistoryModels.get(position).getDataTime());
        holder.amountView.setText(paymentHistoryModels.get(position).getAmount());
        holder.durationView.setText(paymentHistoryModels.get(position).getDuration());
        holder.vehicleTypeView.setText(paymentHistoryModels.get(position).getVehicleType());

    }

    @Override
    public int getItemCount() {
        return paymentHistoryModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView dateTimeView;
        TextView amountView;
        TextView durationView;
        TextView vehicleTypeView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.dateTimeView = itemView.findViewById(R.id.ph_frag_datetime);
            this.amountView = itemView.findViewById(R.id.ph_frag_rate);
            this.durationView = itemView.findViewById(R.id.ph_frag_duration);
            this.vehicleTypeView = itemView.findViewById(R.id.ph_frag_type);
        }
    }
}
