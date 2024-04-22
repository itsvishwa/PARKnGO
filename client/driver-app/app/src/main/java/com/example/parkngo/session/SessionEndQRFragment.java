package com.example.parkngo.session;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parkngo.R;
import com.example.parkngo.session.helpers.SessionEndQrHelper;

public class SessionEndQRFragment extends Fragment {

    String sessionID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View sessionEndQrView = inflater.inflate(R.layout.fragment_session_end_qr, container, false);
        View loadingView = inflater.inflate(R.layout.loading_frag, container, false);

        if (getArguments() != null) {
            sessionID = getArguments().getString("sessionID", "");
        }

        SessionEndQrHelper sessionEndQrHelper = new SessionEndQrHelper(sessionID, getContext(), sessionEndQrView, loadingView);
        sessionEndQrHelper.init();

        return sessionEndQrView;
    }
}