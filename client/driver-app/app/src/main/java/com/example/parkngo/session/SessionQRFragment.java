package com.example.parkngo.session;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.parkngo.R;
import com.example.parkngo.session.helpers.SessionQRHandler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class SessionQRFragment extends Fragment {

    int selectedVehicle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View sessionQRView = inflater.inflate(R.layout.fragment_session_qr, container, false);
        View loadingView = inflater.inflate(R.layout.loading_frag, container, false);
        View errorView = inflater.inflate(R.layout.fragment_error, container, false);
        if (getArguments() != null) {
            selectedVehicle = getArguments().getInt("selectedVehicle", -1);
        }

        SessionQRHandler sessionQRHandler = new SessionQRHandler(getContext(), selectedVehicle, sessionQRView, loadingView, errorView);
        return loadingView;
    }
}