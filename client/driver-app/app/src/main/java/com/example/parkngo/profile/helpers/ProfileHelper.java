package com.example.parkngo.profile.helpers;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.example.parkngo.R;
import com.example.parkngo.helpers.ParkngoStorage;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileHelper {
    boolean isAllFabVisible = false;
    Context context;
    View profileView;
    FloatingActionButton mainFab;
    ExtendedFloatingActionButton paymentFab, editProfileFab;
    public ProfileHelper(Context context, View profileView){
                this.context = context;
                this.profileView = profileView;
    }

    public void init(){
        initLayout();
        initFab();
    }

    private void initLayout(){
        // fetch data from storage
        ParkngoStorage parkngoStorage = new ParkngoStorage(context);
        String firstName = parkngoStorage.getData("firstName");
        String lastName = parkngoStorage.getData("lastName");
        String mobileNumber = parkngoStorage.getData("mobileNumber");

        // add fetched data to the views
        TextView nameView = profileView.findViewById(R.id.profile_frag_name);
        TextView mobileNumberView = profileView.findViewById(R.id.profile_frag_mobile_number);
        TextView nameTopBarView = profileView.findViewById(R.id.profile_frag_name_text);
        nameTopBarView.setText(firstName);
        nameView.setText(firstName + " " + lastName);
        mobileNumberView.setText("(+94) " + mobileNumber);
    }


    private void initFab(){
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


        // fab btn
        mainFab = profileView.findViewById(R.id.fab_btn_main);
        paymentFab = profileView.findViewById(R.id.fab_btn_payment_history);
        editProfileFab = profileView.findViewById(R.id.fab_btn_edit_profile);

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
                            mainFab.startAnimation(rotateAnimation);
                            isAllFabVisible = true;
                        } else {

                            paymentFab.hide();
                            editProfileFab.hide();
                            mainFab.startAnimation(reverseRotateAnimation);
                            isAllFabVisible = false;
                        }

                    }
                });
    }
}
