package com.example.parkngo.helpers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parkngo.R;

public class ErrorFragment extends Fragment {
    String appBarMainText;
    String appBarSubText;
    int bodyImg;
    String bodyMainText;
    String bodySubText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View errorFragmentView = inflater.inflate(R.layout.fragment_error, container, false);

        if (getArguments() != null) {
            appBarMainText = getArguments().getString("MainText1");
            appBarSubText = getArguments().getString("subText1");
            bodyImg = getArguments().getInt("img");
            bodyMainText = getArguments().getString("MainText2");
            bodySubText = getArguments().getString("subText2");
        }

        ErrorFragmentHelper errorFragmentHelper =  new ErrorFragmentHelper(appBarMainText, appBarSubText,  bodyImg, bodyMainText, bodySubText, errorFragmentView);
        errorFragmentHelper.initLayout();

        return errorFragmentView;
    }
}