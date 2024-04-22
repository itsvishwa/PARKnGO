package com.example.parkngo.session;

import android.Manifest;
import android.content.pm.PackageManager;
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
import com.example.parkngo.session.helpers.ForceEndConfirmHelper;

public class ForceEndConfirmFragment extends Fragment {
    private static final int PERMISSION_REQUEST_CODE = 1001;
    ForceEndConfirmHelper forceEndConfirmHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View forceEndConfirmView =  inflater.inflate(R.layout.fragment_force_end_confirm, container, false);

        forceEndConfirmHelper = new ForceEndConfirmHelper(getContext(), forceEndConfirmView);
        forceEndConfirmHelper.init();



        // Request location permission if not granted
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        } else {
            forceEndConfirmHelper.getLocation();
        }

        return forceEndConfirmView;
    }

    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, get location
                forceEndConfirmHelper.getLocation();
            } else {
                // Permission denied, show message or handle accordingly
                Toast.makeText(getContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}