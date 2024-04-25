package com.example.parkngo.logout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.parkngo.R;
import com.example.parkngo.helpers.ParkngoStorage;

public class LogoutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_logout, container, false);

        ParkngoStorage parkngoStorage = new ParkngoStorage(getContext());
        String firstName = parkngoStorage.getData("firstName");

        TextView nameView = view.findViewById(R.id.appbar_main_text);
        nameView.setText("Hi, " + firstName);
        return view;
    }
}