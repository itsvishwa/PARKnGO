package com.example.officertestapp.ForceEnd;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.officertestapp.ForceEnd.Helpers.ForceEndedPaymentReceiveHelper;
import com.example.officertestapp.Home.Helpers.HomeFragmentHelper;
import com.example.officertestapp.R;


public class ConfirmForceEndedCashPaymentFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm_force_ended_cash_payment, container, false);

        // Use the helper class to set app bar details
        HomeFragmentHelper.setTopAppBarDetailsInFragment(view, requireContext());


        // Retrieve values from the Bundle
        Bundle args = getArguments();
        if (args != null) {
            String amount = args.getString("amount");

            TextView amountTextView = view.findViewById(R.id.amount_txt);
            // Update TextView with the retrieved values
            amountTextView.setText(amount);
        } else {
            Log.e("force ended payment details", "Arguments (Bundle) is null");
        }


        // Handle the payment confirm button
        Button paymentConfirmBtn = view.findViewById(R.id.release_slot_04_payment_confirm_btn);
        paymentConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ConfirmForceEndedCashPaymentFragment", "Confirm button clicked");

                // Retrieve values from the Bundle
                Bundle args = getArguments();
                if (args != null) {
                    String sessionId = args.getString("session_id");
                    String amount = args.getString("amount_para");

                    // Log the retrieved values
                    Log.d("ConfirmForceEndedCashPaymentFragment", "Session ID: " + sessionId);
                    Log.d("ConfirmForceEndedCashPaymentFragment", "Amount: " + amount);

                    // Invoke the ReleaseASlotHelper helper to release the slot
                    ForceEndedPaymentReceiveHelper forceEndedPaymentReceiveHelper = new ForceEndedPaymentReceiveHelper(view, requireContext(), getFragmentManager());
                    forceEndedPaymentReceiveHelper.paymentReceivedCash(sessionId, amount);
                } else {
                    Log.e("ConfirmForceEndedCashPaymentFragment", "Arguments (Bundle) is null");
                }

            }
        });


        // Handle the payment cancel button
        Button paymentCancelBtn = view.findViewById(R.id.release_vehicle_04_payment_cancel_btn);
        paymentCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ConfirmForceEndedCashPaymentFragment", "Cancel button clicked");

                // Navigate back to the previous fragment
                requireActivity().onBackPressed();
            }
        });

        return view;
    }
}