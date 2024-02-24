package com.example.parkngo.session.helpers;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.session.ForceEndConfirmFragment;
import com.example.parkngo.session.SessionEndQRFragment;

public class SessionOnGoingHelper {

    Context context;
    View sessionOnGoingView;
    View loadingView;
    SessionOnGoingModel sessionOnGoingModel;

    public SessionOnGoingHelper(Context context, View sessionOnGoingView, SessionOnGoingModel sessionOnGoingModel) {
        this.context = context;
        this.sessionOnGoingView = sessionOnGoingView;
        this.sessionOnGoingModel = sessionOnGoingModel;
    }

    public void initLayout(){
        TextView rateView = sessionOnGoingView.findViewById(R.id.session_on_going_parking_rate);
        TextView parkingNameView = sessionOnGoingView.findViewById(R.id.session_on_going_parking_name);
        TextView startTimeView = sessionOnGoingView.findViewById(R.id.session_on_going_started_time);
        TextView vehicleNumberView = sessionOnGoingView.findViewById(R.id.session_on_going_vehicle_number);
        TextView vehicleTypeView = sessionOnGoingView.findViewById(R.id.session_on_going_vehicle_type);
        TextView officerNameView = sessionOnGoingView.findViewById(R.id.session_on_going_officer_name);
        TextView officerIDView = sessionOnGoingView.findViewById(R.id.session_on_going_officer_id);
        TextView hoursWentView = sessionOnGoingView.findViewById(R.id.session_on_going_hours_went);
        TextView minutesWentView = sessionOnGoingView.findViewById(R.id.session_on_going_minutes_went);


        rateView.setText("Rs. " + sessionOnGoingModel.getParking_rate() + "/1H");
        parkingNameView.setText(sessionOnGoingModel.getParking_name());
        startTimeView.setText(sessionOnGoingModel.getStart_time());
        vehicleNumberView.setText(sessionOnGoingModel.getVehicle_number());
        vehicleTypeView.setText(sessionOnGoingModel.getVehicle_type());
        officerNameView.setText(sessionOnGoingModel.getOfficer_name());
        officerIDView.setText(sessionOnGoingModel.getOfficer_id());
        hoursWentView.setText(sessionOnGoingModel.getHours());
        minutesWentView.setText(sessionOnGoingModel.getMinutes());
    }

    public void showQRBtnHandler(){
        Button button = sessionOnGoingView.findViewById(R.id.session_on_going_show_qr_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) context;
                Bundle data = new Bundle();
                data.putString("sessionID", sessionOnGoingModel.getSessionID());
                mainActivity.replaceFragment(new SessionEndQRFragment(), data);
            }
        });
    }

    public void forceStopBtnHandler(){
        Button button = sessionOnGoingView.findViewById(R.id.session_on_going_force_stop_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) context;
                Bundle data = new Bundle();
                data.putString("sessionID", sessionOnGoingModel.getSessionID());
                mainActivity.replaceFragment(new ForceEndConfirmFragment(), data);
            }
        });
    }
}
