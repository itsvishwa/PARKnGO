package com.example.parkngo.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkngo.R;
import com.example.parkngo.profile.helpers.EditMobileNumberRequestHandler;


public class EditMobileNumberFragment extends Fragment {
    String mobileNumber;
    String otp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View editMobileNumberView = inflater.inflate(R.layout.fragment_edit_mobile_number, container, false);
        EditMobileNumberRequestHandler editMobileNumberRequestHandler = new EditMobileNumberRequestHandler(getContext());



        // On click listeners ..............................................................................
        Button sendOTPBtn = editMobileNumberView.findViewById(R.id.edit_mobile_number_frag_send_otp_btn);
        sendOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
                EditText mobileNumberView = editMobileNumberView.findViewById(R.id.edit_mobile_number_frag_mobile_number);
                mobileNumber = mobileNumberView.getText().toString();

                if (mobileNumber.equals("")){
                    Toast.makeText(getContext(), "Mobile Number can't be empty!", Toast.LENGTH_LONG).show();
                }else if(mobileNumber.length() != 9){
                    Toast.makeText(getContext(), "Invalid mobile number", Toast.LENGTH_LONG).show();
                }else{
                    editMobileNumberRequestHandler.executeGetOTPRequest(mobileNumber);
                }
            }
        });

        Button changeMobileNumberBtn = editMobileNumberView.findViewById(R.id.edit_mobile_number_frag_change_mobile_number_btn);
        changeMobileNumberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // getting OTP
                EditText digit1View = editMobileNumberView.findViewById(R.id.edit_mobile_number_frag_digit_1);
                EditText digit2View = editMobileNumberView.findViewById(R.id.edit_mobile_number_frag_digit_2);
                EditText digit3View = editMobileNumberView.findViewById(R.id.edit_mobile_number_frag_digit_3);
                EditText digit4View = editMobileNumberView.findViewById(R.id.edit_mobile_number_frag_digit_4);

                otp = digit1View.getText().toString() + digit2View.getText().toString() + digit3View.getText().toString() + digit4View.getText().toString();

                editMobileNumberRequestHandler.executeChangeMobileNumberRequest(mobileNumber, otp);
            }
        });


        // On click listeners ..............................................................................

        return editMobileNumberView;
    }
}