package com.example.parkngo.home;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View homeView =  inflater.inflate(R.layout.fragment_home, container, false);

        HomeHelper homeHelper = new HomeHelper(homeView, getContext());
        homeHelper.init();

        return homeView;
    }
}
