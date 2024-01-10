package com.example.parkngo.session;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.session.helpers.SessionMainButtonHandlers;

public class SessionMainFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View sessionMainView = inflater.inflate(R.layout.fragment_session_main, container, false);

        SessionMainButtonHandlers sessionMainButtonHandlers = new SessionMainButtonHandlers(getContext(), sessionMainView);


        // onclick listeners.................................................................................
        sessionMainButtonHandlers.initVehicleBtnHandlers();

        Button continueBtn = sessionMainView.findViewById(R.id.fragment_session_main_continue_btn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity)getContext();
                Bundle data = new Bundle();
                data.putInt("selectedVehicle", sessionMainButtonHandlers.getSelectedVehicle());
                mainActivity.replaceFragment(new SessionQRFragment(), data);
            }
        });
        // onclick listeners.................................................................................

        return sessionMainView;
    }
}