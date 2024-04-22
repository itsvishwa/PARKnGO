package com.example.officertestapp.Profile.Helpers;

import com.example.officertestapp.Helpers.VehicleNumberHelper;

public class PaymentProfileModel {
    String dateTime;
    String amount;
    String vehicleNum;
    String paymentMethod;

    public PaymentProfileModel(String dateTime, String amount, String vehicleNum, String paymentMethod) {
        this.dateTime = dateTime;
        this.amount = amount;
        this.vehicleNum = vehicleNum;
        this.paymentMethod = paymentMethod;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getAmount() {
        return amount;
    }

    public String getVehicleNum() {
        return vehicleNum;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
}
