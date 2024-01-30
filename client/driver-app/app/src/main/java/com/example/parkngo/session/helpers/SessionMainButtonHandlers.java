package com.example.parkngo.session.helpers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.session.AddVehicle;
import com.example.parkngo.session.SessionQRFragment;

public class SessionMainButtonHandlers {
    boolean is1Selected;
    boolean is2Selected;
    boolean is3Selected;
    Context context;
    View sessionMainView;
    Button continueBtn;

    public SessionMainButtonHandlers(Context context, View sessionMainView){
            this.context = context;
            this.sessionMainView = sessionMainView;
            continueBtn = sessionMainView.findViewById(R.id.fragment_session_main_continue_btn);
    }

    public void initVehicleBtnHandlers(){
        is1Selected = false;
        is2Selected = false;
        is3Selected = false;
        Drawable selectedDrawable = ContextCompat.getDrawable(context, R.drawable.radius_6_primary);
        Drawable unselectedDrawable = ContextCompat.getDrawable(context, R.drawable.radius_6_solid);

        ConstraintLayout car1View = sessionMainView.findViewById(R.id.fragment_session_main_vehicle_1);
        ConstraintLayout car2View = sessionMainView.findViewById(R.id.fragment_session_main_vehicle_2);
        ConstraintLayout car3View = sessionMainView.findViewById(R.id.fragment_session_main_vehicle_3);
        continueBtn.setEnabled(false);

        car1View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(is1Selected){
                    is1Selected = false;
                    car1View.setBackground(unselectedDrawable);
                    continueBtn.setEnabled(false);
                }else{
                    is1Selected = true;
                    is2Selected = false;
                    is3Selected = false;
                    car1View.setBackground(selectedDrawable);
                    car2View.setBackground(unselectedDrawable);
                    car3View.setBackground(unselectedDrawable);
                    continueBtn.setEnabled(true);
                }
            }
        });
        car2View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(is2Selected){
                    is2Selected = false;
                    car2View.setBackground(unselectedDrawable);
                    continueBtn.setEnabled(false);
                }else{
                    is1Selected = false;
                    is2Selected = true;
                    is3Selected = false;
                    car1View.setBackground(unselectedDrawable);
                    car2View.setBackground(selectedDrawable);
                    car3View.setBackground(unselectedDrawable);
                    continueBtn.setEnabled(true);
                }
            }
        });
        car3View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(is3Selected){
                    is3Selected = false;
                    car3View.setBackground(unselectedDrawable);
                    continueBtn.setEnabled(false);
                }else{
                    is1Selected = false;
                    is2Selected = false;
                    is3Selected = true;
                    car1View.setBackground(unselectedDrawable);
                    car2View.setBackground(unselectedDrawable);
                    car3View.setBackground(selectedDrawable);
                    continueBtn.setEnabled(true);
                }
            }
        });
    }

    public void initTaptoAddBtnHandlers(){
        View tapToAdd1View = sessionMainView.findViewById(R.id.fragment_session_main_tap_to_add_btn_1);
        View tapToAdd2View = sessionMainView.findViewById(R.id.fragment_session_main_tap_to_add_btn_2);
        View tapToAdd3View = sessionMainView.findViewById(R.id.fragment_session_main_tap_to_add_btn_3);

        tapToAdd1View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity)context;
                Bundle data = new Bundle();
                data.putInt("selectedVehicle", 1);
                mainActivity.replaceFragment(new AddVehicle(), data);
            }
        });

        tapToAdd2View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity)context;
                Bundle data = new Bundle();
                data.putInt("selectedVehicle", 2);
                mainActivity.replaceFragment(new AddVehicle(), data);
            }
        });
        tapToAdd3View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity)context;
                Bundle data = new Bundle();
                data.putInt("selectedVehicle", 3);
                mainActivity.replaceFragment(new AddVehicle(), data);
            }
        });

    }
    public void initContinueBtnHandler(){
        Button continueBtn = sessionMainView.findViewById(R.id.fragment_session_main_continue_btn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity)context;
                Bundle data = new Bundle();
                data.putInt("selectedVehicle", getSelectedVehicle());
                mainActivity.replaceFragment(new SessionQRFragment(), data);
            }
        });
    }
    public int getSelectedVehicle(){
        if(is1Selected){
            return 1;
        }else if(is2Selected){
            return 2;
        }else{
            return 3;
        }
    }
}
