package com.example.parkngo.session;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.parkngo.R;
import com.example.parkngo.session.helpers.PaymentOnGoingModel;
import com.example.parkngo.session.helpers.PaymentOngoingHelper;
import com.example.parkngo.session.helpers.SessionOnGoingModel;

import lk.payhere.androidsdk.PHConfigs;
import lk.payhere.androidsdk.PHConstants;
import lk.payhere.androidsdk.PHMainActivity;
import lk.payhere.androidsdk.PHResponse;
import lk.payhere.androidsdk.model.InitRequest;
import lk.payhere.androidsdk.model.StatusResponse;

public class SessionPaymentFragment extends Fragment {
    PaymentOnGoingModel paymentOnGoingModel;
    private static final int PAYHERE_REQUEST = 11001;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View paymentOnGoingView =  inflater.inflate(R.layout.fragment_session_payment, container, false);

        // getting open-parking-data from the session main fragment
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("paymentOnGoingModelObj")) {
            paymentOnGoingModel = (PaymentOnGoingModel) bundle.getSerializable("paymentOnGoingModelObj");
        }

        // initializing the layout details
        PaymentOngoingHelper paymentOngoingHelper = new PaymentOngoingHelper(paymentOnGoingView, getContext(), paymentOnGoingModel);
        paymentOngoingHelper.initLayout();

        // payment btn handler
        Button button = paymentOnGoingView.findViewById(R.id.session_payment_pay_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InitRequest req = new InitRequest();
                req.setMerchantId("1224851");
                req.setCurrency("LKR");
                req.setAmount(250);
                req.setOrderId(paymentOnGoingModel.getPaymentID());
                req.setItemsDescription(paymentOnGoingModel.getParking_name());
                req.getCustomer().setFirstName("Saman");
                req.getCustomer().setLastName("Pereira");
                req.getCustomer().setEmail("NA");
                req.getCustomer().setPhone("+94771234567");
                req.getCustomer().getAddress().setAddress("NA");
                req.getCustomer().getAddress().setCity("NA");
                req.getCustomer().getAddress().setCountry("Sri Lanka");

                //Optional Params
                req.setNotifyUrl("https://parkngo.azurewebsites.net/?url=payment/notify");

                Intent intent = new Intent(getContext(), PHMainActivity.class);
                intent.putExtra(PHConstants.INTENT_EXTRA_DATA, req);
                PHConfigs.setBaseUrl(PHConfigs.SANDBOX_URL);
                startActivityForResult(intent, PAYHERE_REQUEST); //unique request ID e.g. "11001"
            }
        });

        return paymentOnGoingView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYHERE_REQUEST && data != null && data.hasExtra(PHConstants.INTENT_EXTRA_RESULT)) {
            PHResponse<StatusResponse> response = (PHResponse<StatusResponse>) data.getSerializableExtra(PHConstants.INTENT_EXTRA_RESULT);
            if (resultCode == Activity.RESULT_OK) {
                String msg;
                if (response != null)
                    if (response.isSuccess())
                        msg = "Activity result:" + response.getData().toString();
                    else
                        msg = "Result:" + response.toString();
                else
                    msg = "Result: no response";
                Toast.makeText(requireContext(), "msg 01: " +  msg, Toast.LENGTH_LONG).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (response != null)
                    Toast.makeText(requireContext(), "msg 02: " +  response.toString(), Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(requireContext(), "msg 03: User canceled the request",  Toast.LENGTH_LONG).show();
            }
        }
    }

}