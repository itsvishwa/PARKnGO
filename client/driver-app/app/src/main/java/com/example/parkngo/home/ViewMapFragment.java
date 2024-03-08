package com.example.parkngo.home;

import android.graphics.drawable.Icon;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkngo.R;
import com.example.parkngo.home.helpers.AvailableParkingSpaceModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
//GoogleMap.OnMarkerClickListener add this if u adding marker listener

public class ViewMapFragment extends Fragment implements OnMapReadyCallback {
    String vehicleType = "Car";
    private GoogleMap googleMap;
    ArrayList<AvailableParkingSpaceModel> availableParkingSpaceModelsArr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewMapView = inflater.inflate(R.layout.fragment_navigate, container, false);

        // get the selected vehicle type from prev fragment
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("availableParkingSpaceModelsArr")) {
            availableParkingSpaceModelsArr = (ArrayList<AvailableParkingSpaceModel>) bundle.getSerializable("availableParkingSpaceModelsArr");
        }
        System.out.println(availableParkingSpaceModelsArr.get(0).getParkingName());


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.navigate_frag_map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // changing the top bar text - vehicle type

        TextView textView = viewMapView.findViewById(R.id.navigate_frag_vehicle_type);
        textView.setText(vehicleType);

        return viewMapView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        addMarker();
//        googleMap.setOnMarkerClickListener(this);
    }

//    @Override
//    public boolean onMarkerClick(Marker marker) {
//        // Show a toast message when a marker is clicked
//        Toast.makeText(getContext(), "Marker Clicked: " + marker.getTitle(), Toast.LENGTH_SHORT).show();
//        return false;
//    }

    private void addMarker() {
        if (googleMap != null) {

            for(int i = 0; i<availableParkingSpaceModelsArr.size(); i++){
                AvailableParkingSpaceModel availableParkingSpaceModel = availableParkingSpaceModelsArr.get(i);
                LatLng pos = new LatLng( Double.valueOf(availableParkingSpaceModel.getLatitude()),  Double.valueOf(availableParkingSpaceModel.getLongitude()));

                double slotRatio = (availableParkingSpaceModel.getFreeSlots() * 1.0) / Integer.parseInt(availableParkingSpaceModel.getTotalSlots()) ;
                System.out.println("RATIOOOOOO " + slotRatio);
                BitmapDescriptor markerIcon;

                if(slotRatio <= 0.3){
                    markerIcon = BitmapDescriptorFactory.fromResource(R.drawable.map_red);
                } else if (slotRatio <=0.7) {
                    markerIcon = BitmapDescriptorFactory.fromResource(R.drawable.map_yellow);
                }else{
                    markerIcon = BitmapDescriptorFactory.fromResource(R.drawable.map_blue);
                }

                String snippetMsg = availableParkingSpaceModel.getFreeSlots() + " Free slots, out of " + availableParkingSpaceModel.getTotalSlots();

                googleMap.addMarker(new MarkerOptions()
                        .position(pos)
                        .icon(markerIcon)
                        .title(availableParkingSpaceModel.getParkingName())
                        .snippet(snippetMsg)
                );
            }
            LatLng posColombo = new LatLng(6.921587, 79.870966);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posColombo, 10));
        }
    }
}