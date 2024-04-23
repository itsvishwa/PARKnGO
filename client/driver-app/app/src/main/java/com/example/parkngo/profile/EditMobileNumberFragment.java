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

        EditText otpDigit1View = editMobileNumberView.findViewById(R.id.edit_mobile_number_frag_digit_1);
        EditText otpDigit2View = editMobileNumberView.findViewById(R.id.edit_mobile_number_frag_digit_2);
        EditText otpDigit3View = editMobileNumberView.findViewById(R.id.edit_mobile_number_frag_digit_3);
        EditText otpDigit4View = editMobileNumberView.findViewById(R.id.edit_mobile_number_frag_digit_4);

        setEditTextListener(otpDigit1View, otpDigit2View);
        setEditTextListener(otpDigit2View, otpDigit3View);
        setEditTextListener(otpDigit3View, otpDigit4View);
        setEditTextListener(otpDigit4View, null);


        // On click listeners ..............................................................................
        Button sendOTPBtn = editMobileNumberView.findViewById(R.id.edit_mobile_number_frag_send_otp_btn);
        sendOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText mobileNumberView = editMobileNumberView.findViewById(R.id.edit_mobile_number_frag_mobile_number);
                mobileNumber = mobileNumberView.getText().toString();

                int result = checkMobileNumberInput(mobileNumber);

                if (result == 1){
                    Toast.makeText(getContext(), "Mobile Number can't be empty!", Toast.LENGTH_LONG).show();
                }else if(result == 2){
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

                int result = checkMobileNumberInput(mobileNumber);
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
    private void setEditTextListener (final EditText currentEditText, final EditText nextEditText) {
        currentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 1 && nextEditText != null) {
                    nextEditText.requestFocus();
                }
            }
        });
    }

    private int checkMobileNumberInput(String mobileNumber){
        if (mobileNumber.equals("")){
            return 1;
        }else if(mobileNumber.length() != 9){
            return 2;
        }else{
            return 3;
        }
    }
}