package com.example.parkngo.session;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.session.helpers.SessionMainButtonHandlers;
import com.example.parkngo.session.helpers.SessionMainFetchData;

public class SessionMainFragment extends Fragment {

    View sessionMainView;
    View loadingView;
    View errorView;
    SessionMainButtonHandlers sessionMainButtonHandlers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        sessionMainView = inflater.inflate(R.layout.fragment_session_main, container, false);
        loadingView = inflater.inflate(R.layout.loading_frag, container, false);
        errorView = inflater.inflate(R.layout.fragment_error, container, false);

        SessionMainFetchData sessionMainFetchData = new SessionMainFetchData(sessionMainView, loadingView, errorView, getContext());
        sessionMainFetchData.fetchData();
        sessionMainButtonHandlers = new SessionMainButtonHandlers(getContext(), sessionMainView, requireActivity().getSupportFragmentManager());

        // onclick listeners.................................................................................
        sessionMainButtonHandlers.initVehicleBtnHandlers();
        sessionMainButtonHandlers.initTapToAddBtnHandlers();
        sessionMainButtonHandlers.initContinueBtnHandler();
        // onclick listeners.................................................................................

        return loadingView;
    }
}