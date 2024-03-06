package com.example.parkngo.home;

import android.graphics.drawable.Icon;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.parkngo.R;
import com.example.parkngo.home.helpers.AvailableParkingSpaceModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class ViewMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {



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

        return viewMapView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        addMarker();
        googleMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        // Show a toast message when a marker is clicked
        Toast.makeText(getContext(), "Marker Clicked: " + marker.getTitle(), Toast.LENGTH_SHORT).show();
        return false;
    }

    private void addMarker() {
        if (googleMap != null) {

            for(int i = 0; i<availableParkingSpaceModelsArr.size(); i++){
                AvailableParkingSpaceModel availableParkingSpaceModel = availableParkingSpaceModelsArr.get(i);
                LatLng pos = new LatLng( Double.valueOf(availableParkingSpaceModel.getLatitude()),  Double.valueOf(availableParkingSpaceModel.getLongitude()));
                googleMap.addMarker(new MarkerOptions()
                        .position(pos)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_red))
                        .title(availableParkingSpaceModel.getParkingName())
                        .snippet(availableParkingSpaceModel.getFreeSlots() + " Free slots, From " + availableParkingSpaceModel.getTotalSlots())
                );
            }
            LatLng posColombo = new LatLng(6.921587, 79.870966);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posColombo, 10));
        }
    }
}