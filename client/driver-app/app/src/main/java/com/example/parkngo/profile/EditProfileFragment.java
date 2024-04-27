package com.example.parkngo.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.parkngo.R;
import com.example.parkngo.profile.helpers.EditProfileRequestHelper;

public class EditProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View editProfileView =  inflater.inflate(R.layout.fragment_edit_profile, container, false);

        EditProfileRequestHelper editProfileRequestHelper = new EditProfileRequestHelper(editProfileView, getContext(), requireActivity().getSupportFragmentManager());
        editProfileRequestHelper.init();

        return editProfileView;
    }
}