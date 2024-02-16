package com.example.officertestapp.Profile.Helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.officertestapp.R;

import java.util.ArrayList;

public class PPRecycleViewAdapter extends RecyclerView.Adapter<PPRecycleViewAdapter.MyViewHolder> {

    ArrayList<PaymentProfileModel> paymentProfileModels;
    Context context;

    public PPRecycleViewAdapter(ArrayList<PaymentProfileModel> paymentProfileModels, Context context) {
        this.paymentProfileModels = paymentProfileModels;
        this.context = context;
    }

    @NonNull
    @Override
    public PPRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.profile_payment_item, parent, false);

        return new PPRecycleViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PPRecycleViewAdapter.MyViewHolder holder, int position) {
        holder.dateTimeView.setText(paymentProfileModels.get(position).getDateTime());

        holder.amountView.setText(paymentProfileModels.get(position).getAmount());

        holder.vehicleNumView.setText(paymentProfileModels.get(position).getVehicleNum());

        holder.paymentMethodView.setText(paymentProfileModels.get(position).getPaymentMethod());
    }

    @Override
    public int getItemCount() {

        return paymentProfileModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dateTimeView;
        TextView amountView;
        TextView vehicleNumView;
        TextView paymentMethodView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.dateTimeView = itemView.findViewById(R.id.pph_frag_datetime);
            this.amountView = itemView.findViewById(R.id.pph_frag_amount);
            this.vehicleNumView = itemView.findViewById(R.id.pph_frag_vehicleNum);
            this.paymentMethodView = itemView.findViewById(R.id.pph_frag_payment_method);
        }
    }
}
