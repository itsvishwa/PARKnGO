package com.example.officertestapp.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.officertestapp.Home.Helpers.HomeFragmentHelper;
import com.example.officertestapp.Home.Helpers.PaymentReceiveCashHelper;
import com.example.officertestapp.R;


public class ReleaseASlot04Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_release_a_slot04, container, false);

        // Use the helper class to set app bar details
        HomeFragmentHelper.setTopAppBarDetailsInFragment(view, requireContext());


        // Retrieve values from the Bundle
        Bundle args = getArguments();
        if (args != null) {
            String amount = args.getString("Amount");

            TextView amountTextView = view.findViewById(R.id.amount_txt);
            // Update TextView with the retrieved values
            amountTextView.setText(amount);
        } else {
            Log.e("ReleaseASlot04Fragment", "Arguments (Bundle) is null");
        }


        // Handle the payment confirm button
        Button paymentConfirmBtn = view.findViewById(R.id.release_slot_04_payment_confirm_btn);
        paymentConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ReleaseASlot04Fragment", "Confirm button clicked");

                // Retrieve values from the Bundle
                Bundle args = getArguments();
                if (args != null) {
                    String paymentId = args.getString("PaymentID");

                    // Invoke the ReleaseASlotHelper helper to release the slot
                    PaymentReceiveCashHelper paymentReceiveCashHelper = new PaymentReceiveCashHelper(view, requireContext(), getFragmentManager());
                    paymentReceiveCashHelper.paymentReceivedCash(paymentId);
                } else {
                    Log.e("ReleaseASlot04Fragment", "Arguments (Bundle) is null");
                }

            }
        });


        // Handle the payment cancel button
        Button paymentCancelBtn = view.findViewById(R.id.release_vehicle_04_payment_cancel_btn);
        paymentCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ReleaseASlot04Fragment", "Cancel button clicked");

                // Navigate back to the previous fragment
                requireActivity().onBackPressed();
            }
        });

        return view;
    }
}