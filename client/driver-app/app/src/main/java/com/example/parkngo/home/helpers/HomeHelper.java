package com.example.parkngo.home.helpers;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.helpers.ParkngoStorage;
import com.example.parkngo.home.AvailableParkingSpacesFragment;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeHelper {

    View homeFragmentView;
    Context context;
    String vehicleType;

    public HomeHelper(View homeFragmentView, Context context){
        this.homeFragmentView = homeFragmentView;
        this.context = context;
    }


    // initializing the layout and button listeners
    public void init(){
        initVehicleSpinner();
        initPulseAnimation();
        initTopBarData();
        initVehicleSpinnerBtnListener();
        initMainBtnListener();
    }


    // initializing the vehicle spinner
    private void initVehicleSpinner(){
        Spinner spinner = homeFragmentView.findViewById(R.id.home_frag_spinner);

        ArrayList<String> vehicleTypes = new ArrayList<>(Arrays.asList("Car", "Bike", "Van", "Lorry", "Bus"));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, vehicleTypes);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);
    }

    // initializing the main button pulse-animation
    private void initPulseAnimation(){
        ConstraintLayout constraintLayout = homeFragmentView.findViewById(R.id.home_frag_cons_layout);
        Animation pulse = AnimationUtils.loadAnimation(context, R.anim.anim_pulse);
        constraintLayout.startAnimation(pulse);
    }

    // initializing the top bar data values
    private void initTopBarData(){
        ParkngoStorage parkngoStorage = new ParkngoStorage(context);
        String firstName = parkngoStorage.getData("firstName");
        TextView nameView = homeFragmentView.findViewById(R.id.home_frag_user_name);
        nameView.setText(firstName);
    }

    // initializing vehicle spinner btn handler
    private void initVehicleSpinnerBtnListener(){
        Spinner spinner = homeFragmentView.findViewById(R.id.home_frag_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vehicleType = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    // initializing main button handler
    private void initMainBtnListener(){
        ShapeableImageView shapeableImageView = homeFragmentView.findViewById(R.id.home_frag_main_img);
        shapeableImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data = new Bundle();
                data.putString("vehicleType", vehicleType);
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.replaceFragment(new AvailableParkingSpacesFragment(), data);
            }
        });
    }
}
