package com.example.parkngo.profile;

public class PaymentHistoryModel {
    String dataTime;
    String amount;
    String duration;
    String vehicleType;

    public PaymentHistoryModel(String dataTime, String amount, String duration, String vehicleType) {
        this.dataTime = dataTime;
        this.amount = amount;
        this.duration = duration;
        this.vehicleType = vehicleType;
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
}
