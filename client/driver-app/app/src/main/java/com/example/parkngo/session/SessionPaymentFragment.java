package com.example.parkngo.session;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.parkngo.R;
import com.example.parkngo.helpers.ParkngoStorage;
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

                ParkngoStorage parkngoStorage = new ParkngoStorage(requireContext());
                String fName = parkngoStorage.getData("firstName");
                String lName = parkngoStorage.getData("lastName");
                String mobileNumber = parkngoStorage.getData("mobileNumber");

                InitRequest req = new InitRequest();
                req.setMerchantId("1224851");       // Merchant ID
                req.setCurrency("LKR");             // Currency code LKR/USD/GBP/EUR/AUD
                req.setAmount(Double.parseDouble(paymentOnGoingModel.getAmount()));             // Final Amount to be charged
                req.setOrderId(paymentOnGoingModel.getPaymentID());        // Unique Reference ID
                req.setItemsDescription(paymentOnGoingModel.getParking_name());  // Item description title
                req.setCustom1("Parking session ended at : " + paymentOnGoingModel.getEnd_time());
                req.setCustom2("Parking officer" + paymentOnGoingModel.getOfficer_name());
                req.getCustomer().setFirstName(fName);
                req.getCustomer().setLastName(lName);
                req.getCustomer().setEmail("N/A");
                req.getCustomer().setPhone("+94" + mobileNumber);
                req.getCustomer().getAddress().setAddress("N/A");
                req.getCustomer().getAddress().setCity("N/A");
                req.getCustomer().getAddress().setCountry("Sri Lanka");

                //Optional Params
                req.setNotifyUrl("http://parkngo.tech/mobile/payment/notify");

                Intent intent = new Intent(requireContext(), PHMainActivity.class);
                intent.putExtra(PHConstants.INTENT_EXTRA_DATA, req);
                PHConfigs.setBaseUrl(PHConfigs.SANDBOX_URL);
                startActivityForResult(intent, PAYHERE_REQUEST);
            }
        });
//        4916217501611292
        return paymentOnGoingView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                Log.d("payhere:", msg);
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