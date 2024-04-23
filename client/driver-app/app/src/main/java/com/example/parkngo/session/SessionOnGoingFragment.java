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
    SessionOnGoingHelper sessionOnGoingHelper;

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

        sessionOnGoingHelper = new SessionOnGoingHelper(getContext(), sessionOnGoingView, sessionOnGoingModel);
        sessionOnGoingHelper.init();

        return sessionOnGoingView;
    }


}