package com.example.parkngo.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.helpers.ParkngoStorage;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileFragment extends Fragment {


    // floating action button
    FloatingActionButton mainFab;
    ExtendedFloatingActionButton paymentFab, editProfileFab;
    Boolean isAllFabVisible;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // fetch data from storage
        ParkngoStorage parkngoStorage = new ParkngoStorage(getContext());
        String firstName = parkngoStorage.getData("firstName");
        String lastName = parkngoStorage.getData("lastName");
        String mobileNumber = parkngoStorage.getData("mobileNumber");

        // add fetched data to the views
        TextView nameView = view.findViewById(R.id.profile_frag_name);
        TextView mobileNumberView = view.findViewById(R.id.profile_frag_mobile_number);
        TextView nameTopBarView = view.findViewById(R.id.profile_frag_name_text);
        nameTopBarView.setText(firstName);
        nameView.setText(firstName + " " + lastName);
        mobileNumberView.setText("(+94) " + mobileNumber);


        // Create a RotateAnimation to rotate the icon by 180 degrees
        RotateAnimation rotateAnimation = new RotateAnimation(0, 180,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        // Set animation properties, e.g., duration and fillAfter
        rotateAnimation.setDuration(300); // 1 second
        rotateAnimation.setFillAfter(true);

        // Reverse the icon rotation by 180 degrees
        RotateAnimation reverseRotateAnimation = new RotateAnimation(180, 0,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        reverseRotateAnimation.setDuration(300);
        reverseRotateAnimation.setFillAfter(true);


        // fab
        mainFab = view.findViewById(R.id.fab_btn_main);
        paymentFab = view.findViewById(R.id.fab_btn_payment_history);
        editProfileFab = view.findViewById(R.id.fab_btn_edit_profile);

        paymentFab.setVisibility(View.GONE);
        editProfileFab.setVisibility(View.GONE);

        isAllFabVisible = false;

        mainFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isAllFabVisible) {
                            paymentFab.show();
                            editProfileFab.show();
//                            addAlarmActionText.setVisibility(View.VISIBLE);
//                            addPersonActionText.setVisibility(View.VISIBLE);
                            mainFab.startAnimation(rotateAnimation);
                            isAllFabVisible = true;
                        } else {

                            paymentFab.hide();
                            editProfileFab.hide();
//                            addAlarmActionText.setVisibility(View.GONE);
//                            addPersonActionText.setVisibility(View.GONE);
                            mainFab.startAnimation(reverseRotateAnimation);
                            isAllFabVisible = false;
                        }

                    }
                });
        return view;
    }
}
