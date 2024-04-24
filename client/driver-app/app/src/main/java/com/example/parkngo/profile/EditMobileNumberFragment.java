package com.example.parkngo.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parkngo.R;
import com.example.parkngo.profile.helpers.EditMobileNumberRequestHelper;


public class EditMobileNumberFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View editMobileNumberView = inflater.inflate(R.layout.fragment_edit_mobile_number, container, false);

        EditMobileNumberRequestHelper editMobileNumberRequestHandler = new EditMobileNumberRequestHelper(getContext(), editMobileNumberView);
        editMobileNumberRequestHandler.init();

        return editMobileNumberView;
    }
}