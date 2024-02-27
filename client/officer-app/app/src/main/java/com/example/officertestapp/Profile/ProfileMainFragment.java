package com.example.officertestapp.Profile;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.officertestapp.Attendance.MarkAttendanceOffActivity;
import com.example.officertestapp.Profile.Helpers.ProfileFragmentHelper;
import com.example.officertestapp.R;
import com.example.officertestapp.Helpers.ParkngoStorage;
import com.google.android.material.button.MaterialButton;

public class ProfileMainFragment extends Fragment {
    // Define swipe threshold constant
    private static final int SWIPE_THRESHOLD = 100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile_main, container, false);

        TextView officerIDView = view.findViewById(R.id.profile_main_frag_officer_id);
        TextView nameView = view.findViewById(R.id.profile_main_frag_name);
        TextView phoneNumberView = view.findViewById(R.id.profile_main_frag_mobile_number);
        TextView nicView = view.findViewById(R.id.profile_main_frag_nic);
        TextView parkingNameView = view.findViewById(R.id.profile_main_frag_parking_name);

        ParkngoStorage parkngoStorage = new ParkngoStorage(getContext());
        String firstName = parkngoStorage.getData("firstName");
        String lastName = parkngoStorage.getData("lastName");
        String mobileNumber = parkngoStorage.getData("mobileNumber");
        String nic = parkngoStorage.getData("nic");
        String parkingName = parkngoStorage.getData("parkingName");
        String officerID  = parkngoStorage.getData("officerID");

        officerIDView.setText(officerID);
        nameView.setText(firstName + " " + lastName);
        phoneNumberView.setText("(+94) " + mobileNumber);
        nicView.setText(nic);
        parkingNameView.setText(parkingName);

        // top app bar
        TextView parkingNameAppBarView = view.findViewById(R.id.profile_frag_app_bar_parking_name);
        parkingNameAppBarView.setText(parkingName);

        // Use the helper class to set app bar details
        ProfileFragmentHelper.setTopAppBarDetailsInFragment(view, requireContext());


        MaterialButton swipeButton = view.findViewById(R.id.duty_off_swipe_button);
        ConstraintLayout swipeLayout = view.findViewById(R.id.constraintLayout25);

        final boolean[] hasSwiped = {false}; // Using an array to make it effectively final

        swipeButton.setOnTouchListener(new View.OnTouchListener() {
            private float startX;
            private float initialX;
            private float maxTranslationX;
            private int startColor;
            private int endColor;
            private ArgbEvaluator argbEvaluator;
            private TextView textView; // Reference to the text view in the constraint layout

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (hasSwiped[0]) return false; // If swipe has already occurred, do not handle touch events
                        startX = event.getRawX();
                        initialX = swipeButton.getTranslationX();
                        maxTranslationX = swipeLayout.getWidth() - swipeButton.getWidth(); // Calculate the maximum allowed translation
                        startColor = getResources().getColor(R.color.detailBoxBackground); // Get the start color
                        endColor = getResources().getColor(R.color.primaryColor); // Get the end color
                        argbEvaluator = new ArgbEvaluator(); // Initialize ArgbEvaluator

                        // Get reference to the text view
                        textView = view.findViewById(R.id.swipe_bar_txt);

                        return true;
                    case MotionEvent.ACTION_MOVE:
                        float newX = event.getRawX();
                        float deltaX = newX - startX;
                        float translationX = initialX + deltaX;

                        // Ensure the button stays within the bounds of the constraint layout
                        translationX = Math.max(0, Math.min(translationX, maxTranslationX));

                        swipeButton.setTranslationX(translationX);

                        // Calculate the ratio of button filled
                        float ratio = translationX / maxTranslationX;

                        // Interpolate the color based on the ratio
                        int color = (int) argbEvaluator.evaluate(ratio, startColor, endColor);

                        // Set the background color of the constraint layout
                        swipeLayout.setBackgroundColor(color);

                        // Set text color to transparent
                        textView.setTextColor(Color.TRANSPARENT);

                        return true;
                    case MotionEvent.ACTION_UP:
                        if (hasSwiped[0]) return false; // If swipe has already occurred, do not handle touch events
                        float endX = event.getRawX();
                        float deltaXUp = endX - startX;

                        // Check for swipe within constraintlayout25 boundaries
                        if (isWithinConstraintLayout(event, swipeLayout)) {
                            if (Math.abs(deltaXUp) > SWIPE_THRESHOLD) {
                                if (deltaXUp > 0) {
                                    // Right swipe
                                    // Perform action when swiped right (if needed)
                                    Intent intent = new Intent(requireContext(), MarkAttendanceOffActivity.class);
                                    startActivity(intent);
                                    hasSwiped[0] = true; // Set the flag to true since swipe has occurred
                                } else {
                                    // Left swipe (handle if needed)
                                }
                            } else if (swipeButton.getTranslationX() >= maxTranslationX) {
                                // If the button reaches the end of the constraint layout, start the next activity
                                Intent intent = new Intent(requireContext(), MarkAttendanceOffActivity.class);
                                startActivity(intent);
                                hasSwiped[0] = true; // Set the flag to true since swipe has occurred
                            }
                        }
                        // Reset button position
                        swipeButton.setTranslationX(0);

                        // Reset text color
                        textView.setTextColor(Color.BLACK); // Set it to your desired color

                        return true;
                }
                return false;
            }
        });
        return view;
    }

    // Helper method to check if touch event is within constraintlayout25 bounds
    private boolean isWithinConstraintLayout(MotionEvent event, ConstraintLayout constraintLayout) {
        int[] location = new int[2];
        constraintLayout.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        int width = constraintLayout.getWidth();
        int height = constraintLayout.getHeight();

        float rawX = event.getRawX();
        float rawY = event.getRawY();

        return (rawX >= x && rawX <= x + width) && (rawY >= y && rawY <= y + height);
    }
}