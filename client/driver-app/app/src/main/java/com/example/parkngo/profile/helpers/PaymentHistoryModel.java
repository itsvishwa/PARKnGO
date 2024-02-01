package com.example.parkngo.profile.helpers;

public class PaymentHistoryModel {
    String dataTime;
    String amount;
    String duration;
    String vehicleType;
    String vehicleNumber;
    String parkingSpaceName;
    String paymentType;

    public PaymentHistoryModel(String dataTime, String amount, String duration, String vehicleType, String vehicleNumber, String parkingSpaceName, String paymentType) {
        this.dataTime = dataTime;
        this.amount = amount;
        this.duration = duration;
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
        this.parkingSpaceName = parkingSpaceName;
        this.paymentType = paymentType;
    }

    public String getDataTime() {
        return dataTime;
    }

    public String getAmount() {
        return amount;
    }

    public String getDuration() {
        return duration;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getVehicleNumber(){return vehicleNumber;}

    public String getParkingSpaceName(){return parkingSpaceName;}

    public String getPaymentType(){return paymentType;}
}
