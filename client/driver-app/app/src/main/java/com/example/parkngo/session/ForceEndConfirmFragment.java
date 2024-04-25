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
import android.widget.Button;
import android.widget.Toast;

import com.example.parkngo.R;
import com.example.parkngo.session.helpers.ForceEndConfirmHelper;

public class ForceEndConfirmFragment extends Fragment {
    private static final int PERMISSION_REQUEST_CODE = 1001;
    ForceEndConfirmHelper forceEndConfirmHelper;
    View forceEndConfirmView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        forceEndConfirmView =  inflater.inflate(R.layout.fragment_force_end_confirm, container, false);

        forceEndConfirmHelper = new ForceEndConfirmHelper(getContext(), forceEndConfirmView);
        confirmBtnHandler();

        return forceEndConfirmView;
    }


    // button which ask the location permission
    private void confirmBtnHandler(){
        Button button = forceEndConfirmView.findViewById(R.id.force_end_confirm_fragment_confirm_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // Request location permission if not granted
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
                } else {
                    // fetch the location and send the request
                    forceEndConfirmHelper.getLocationAndSendReq();
                }
            }
        });
    }


    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Permission denied, show message or handle accordingly
                Toast.makeText(getContext(), "Access to your location is required to proceed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}