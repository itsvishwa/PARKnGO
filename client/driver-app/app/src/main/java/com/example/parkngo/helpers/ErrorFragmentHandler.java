package com.example.parkngo.helpers;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.home.AvailableParkingSpacesFragment;

public class ErrorFragmentHandler {

    String appBarMainText;
    String appBarSubText;
    int bodyImg;
    String bodyMainText;
    String bodySubText;
    View errorView;

    public ErrorFragmentHandler(String appBarMainText, String appBarSubText, int bodyImg, String bodyMainText, String bodySubText, View errorView){
        this.appBarMainText = appBarMainText;
        this.appBarSubText = appBarSubText;
        this.bodyImg = bodyImg;
        this.bodyMainText = bodyMainText;
        this.bodySubText = bodySubText;
        this.errorView = errorView;
    }

    public View setupView(){
        TextView appBarMainTextView = errorView.findViewById(R.id.error_frag_appbar_main_title);
        TextView appBarSubTextView = errorView.findViewById(R.id.error_frag_appbar_sub_title);
        ImageView bodyImgView = errorView.findViewById(R.id.error_frag_img);
        TextView bodyMainTextView = errorView.findViewById(R.id.error_frag_body_main_text);
        TextView bodySubTextView = errorView.findViewById(R.id.error_frag_body_sub_text);

        appBarMainTextView.setText(appBarMainText);
        appBarSubTextView.setText(appBarSubText);
        bodyImgView.setImageResource(bodyImg);
        bodyMainTextView.setText(bodyMainText);
        bodySubTextView.setText(bodySubText);

        return errorView;
    }
}
