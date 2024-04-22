package com.example.parkngo.session;

import android.Manifest;


import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.parkngo.R;
import com.example.parkngo.session.helpers.SessionOnGoingHelper;
import com.example.parkngo.session.helpers.SessionOnGoingModel;

public class SessionOnGoingFragment extends Fragment {
    SessionOnGoingModel sessionOnGoingModel;
    private LocationManager locationManager;
    private static final int PERMISSION_REQUEST_CODE = 1001;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View sessionOnGoingView =  inflater.inflate(R.layout.fragment_session_on_going, container, false);

        // getting open-parking-data from the session main fragment
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("sessionOnGoingModelObj")) {
            sessionOnGoingModel = (SessionOnGoingModel) bundle.getSerializable("sessionOnGoingModelObj");
        }

        SessionOnGoingHelper sessionOnGoingHelper = new SessionOnGoingHelper(getContext(), sessionOnGoingView, sessionOnGoingModel);
        sessionOnGoingHelper.initLayout();

        // button handlers
        sessionOnGoingHelper.showQRBtnHandler();
        sessionOnGoingHelper.forceStopBtnHandler();

        // write code here to print a toast of gps location
        // Request location permission if not granted
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted
//            getLocation();
        }

        return sessionOnGoingView;
    }


    private void getLocation() {
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Get latitude and longitude from location object
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                // Show toast with location information
                Toast.makeText(getContext(), "Latitude: " + latitude + ", Longitude: " + longitude, Toast.LENGTH_SHORT).show();
                System.out.println("lat " + latitude);
                System.out.println("long " + longitude);
                // Don't forget to remove updates to prevent continuous listening
                locationManager.removeUpdates(this);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, get location
                getLocation();
            } else {
                // Permission denied, show message or handle accordingly
                Toast.makeText(getContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}