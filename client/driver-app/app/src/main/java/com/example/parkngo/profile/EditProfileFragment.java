package com.example.parkngo.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.parkngo.R;
import com.example.parkngo.profile.helpers.EditProfileRequestHandler;

public class EditProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View editProfileView =  inflater.inflate(R.layout.fragment_edit_profile, container, false);
        EditProfileRequestHandler editProfileRequestHandler = new EditProfileRequestHandler(editProfileView, getContext(), requireActivity().getSupportFragmentManager());

        // onclick listeners ......................................................................................................
        Button saveChangesBtn = editProfileView.findViewById(R.id.edit_prof_frag_save_btn);
        saveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfileRequestHandler.updateName();
            }
        });


        Button discardBtn = editProfileView.findViewById(R.id.edit_prof_frag_discard_btn);
        discardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfileRequestHandler.setUpDefaultValues();
            }
        });

        // onclick listeners ......................................................................................................

        return editProfileView;
    }
}