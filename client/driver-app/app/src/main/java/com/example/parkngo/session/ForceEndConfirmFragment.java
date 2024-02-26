package com.example.parkngo.session;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parkngo.R;
import com.example.parkngo.session.helpers.ForceEndConfirmHelper;

public class ForceEndConfirmFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View forceEndConfirmView =  inflater.inflate(R.layout.fragment_force_end_confirm, container, false);

        ForceEndConfirmHelper forceEndConfirmHelper = new ForceEndConfirmHelper(getContext(), forceEndConfirmView);
        forceEndConfirmHelper.initAllBtnListeners();

        return forceEndConfirmView;
    }
}