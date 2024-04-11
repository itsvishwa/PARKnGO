package com.example.parkngo.session.helpers;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkngo.MainActivity;
import com.example.parkngo.R;

import lk.payhere.androidsdk.PHConfigs;
import lk.payhere.androidsdk.PHConstants;
import lk.payhere.androidsdk.PHMainActivity;
import lk.payhere.androidsdk.model.InitRequest;

public class PaymentOngoingHelper {
    View paymentOnGoingView;
    Context context;
    PaymentOnGoingModel paymentOnGoingModel;

    public PaymentOngoingHelper(View paymentOnGoingView, Context context, PaymentOnGoingModel paymentOnGoingModel) {
        this.paymentOnGoingView = paymentOnGoingView;
        this.context = context;
        this.paymentOnGoingModel = paymentOnGoingModel;
    }

    public void initLayout(){
        TextView rateView = paymentOnGoingView.findViewById(R.id.payment_on_going_frag_parking_rate);
        TextView parkingNameView = paymentOnGoingView.findViewById(R.id.payment_on_going_frag_parking_name);
        TextView timeWentView = paymentOnGoingView.findViewById(R.id.payment_on_going_frag_time_went);
        TextView startTimeView = paymentOnGoingView.findViewById(R.id.payment_on_going_frag_start_time);
        TextView endTimeView = paymentOnGoingView.findViewById(R.id.payment_on_going_frag_end_time);
        TextView vehicleNumberView = paymentOnGoingView.findViewById(R.id.payment_on_going_frag_vehicle_number);
        TextView vehicleTypeView = paymentOnGoingView.findViewById(R.id.payment_on_going_frag_vehicle_type);
        TextView officerNameView = paymentOnGoingView.findViewById(R.id.payment_on_going_frag_officer_name);
        TextView officerIDView = paymentOnGoingView.findViewById(R.id.payment_on_going_frag_officer_id);
        TextView amountView = paymentOnGoingView.findViewById(R.id.payment_on_going_frag_amount);


        rateView.setText("Rs. " + paymentOnGoingModel.getParking_rate() + "/1H");
        parkingNameView.setText(paymentOnGoingModel.getParking_name());
        startTimeView.setText(paymentOnGoingModel.getStart_time());
        vehicleNumberView.setText(paymentOnGoingModel.getVehicle_number());
        vehicleTypeView.setText(paymentOnGoingModel.getVehicle_type().toUpperCase());
        officerNameView.setText(paymentOnGoingModel.getOfficer_name());
        officerIDView.setText(paymentOnGoingModel.getOfficer_id());
        timeWentView.setText(paymentOnGoingModel.getHours() + " Hours " + paymentOnGoingModel.getMinutes() + " Minutes");
        endTimeView.setText(paymentOnGoingModel.getEnd_time());
        amountView.setText("Rs. " + paymentOnGoingModel.getAmount());
    }
}
