package com.example.parkngo.home.helpers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

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
    private LocationManager locationManager;
    Double latitude = 6.919875;
    Double longitude = 79.854209;

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
    }


    // initializing the vehicle spinner
    private void initVehicleSpinner(){
        Spinner spinner = homeFragmentView.findViewById(R.id.home_frag_spinner);

        ArrayList<String> vehicleTypes = new ArrayList<>(Arrays.asList("Car", "TukTuk", "Bicycle", "Mini Van", "Van", "Lorry", "Mini Bus", "Long Vehicles"));

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
                String temp = adapterView.getItemAtPosition(i).toString();
                temp = temp.toLowerCase();
                temp = temp.replace(" ", "_");
                vehicleType = temp;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }


    // getter for vehicle type
    public String getVehicleType(){
        return vehicleType;
    }


    public void getLocationAndContinue() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Get latitude and longitude from location object
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                System.out.println(longitude  + "       " + latitude);
                locationManager.removeUpdates(this);

                Bundle data = new Bundle();
                data.putString("vehicleType", vehicleType);
                // data.putDouble("lat", latitude); // uncomment when GPS is working
                // data.putDouble("long", longitude); // uncomment when GPS is working
                data.putDouble("lat", 6.900662);
                data.putDouble("long", 79.8617228);

                MainActivity mainActivity = (MainActivity) context;
                mainActivity.replaceFragment(new AvailableParkingSpacesFragment(), data);
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }
}
