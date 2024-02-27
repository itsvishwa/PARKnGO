package com.example.officertestapp.ForceEnd;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.officertestapp.MainActivity;
import com.example.officertestapp.Profile.Helpers.PPHFetchData;
import com.example.officertestapp.R;

public class ForceEndMainFragment extends Fragment {
    View loadingView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View forceEndsessionsView = inflater.inflate(R.layout.fragment_force_end_main, container, false);

        loadingView = inflater.inflate(R.layout.loading_frag, container, false);

        // fetching data


        return loadingView;
    }
}