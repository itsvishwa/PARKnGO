package com.example.parkngo.home;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.helpers.ParkngoStorage;
import com.example.parkngo.home.helpers.HomeHelper;
import com.google.android.material.imageview.ShapeableImageView;

public class HomeFragment extends Fragment {

    String vehicleType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        // initialize the helper
        new HomeHelper(view, getContext());

        // onclick listeners
        Spinner spinner = view.findViewById(R.id.home_frag_spinner); // spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vehicleType = adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(getContext(), item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        ShapeableImageView shapeableImageView = view.findViewById(R.id.home_frag_main_img); // main button in home fragment
        shapeableImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data = new Bundle();
                data.putString("vehicleType", vehicleType);
                MainActivity mainActivity = (MainActivity)requireContext();
                mainActivity.replaceFragment(new AvailableParkingSpacesFragment(), data);
            }
        });

        return view;
    }
}
