package com.example.parkngo.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.helpers.LocationHelper;
import com.example.parkngo.helpers.ParkngoStorage;
import com.example.parkngo.home.helpers.HomeHelper;
import com.google.android.material.imageview.ShapeableImageView;

public class HomeFragment extends Fragment {

    View homeView;
    HomeHelper homeHelper;
    private static final int PERMISSION_REQUEST_CODE = 1002;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeView =  inflater.inflate(R.layout.fragment_home, container, false);

        homeHelper = new HomeHelper(homeView, getContext());
        homeHelper.init();
        initMainBtnListener();

        return homeView;
    }


    // initializing main button handler - contain a location permission invoke
    private void initMainBtnListener(){
        ShapeableImageView shapeableImageView = homeView.findViewById(R.id.home_frag_main_img);
        shapeableImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // Request location permission if not granted
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
                } else {
                    // fetch location and continue to next fragment
                    homeHelper.getLocationAndContinue();
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
