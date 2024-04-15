package com.example.parkngo.parking.helpers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.parkngo.R;

public class SessionEndQrHelper {
    String sessionID;
    Context context;
    View sessionEndQrView;
    View loadingView;

    public SessionEndQrHelper(String sessionID, Context context, View sessionEndQrView, View loadingView){
            this.sessionID = sessionID;
            this.context = context;
            this.sessionEndQrView = sessionEndQrView;
            this.loadingView = loadingView;
    }

    public void initQR(){

        // load the qr
        ImageView qrImageView = sessionEndQrView.findViewById(R.id.fragment_session_end_qr_imageView);
        Glide.with(context).load("https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=" + sessionID).into(qrImageView);

        // replace the loading view
        ViewGroup parent = (ViewGroup) loadingView.getParent();
        if (parent != null) {
            int index = parent.indexOfChild(loadingView);
            parent.removeView(loadingView);
            parent.addView(sessionEndQrView, index);
        }
    }
}

