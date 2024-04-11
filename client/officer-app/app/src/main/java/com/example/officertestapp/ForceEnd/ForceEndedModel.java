package com.example.officertestapp.ForceEnd;

public class ForceEndedModel {
    String vehicleNumber;
    String vehicleType;
    String startDateTime;
    String endDateTime;

    public ForceEndedModel(String vehicleNumber, String vehicleType, String startDateTime, String endDateTime) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getVehicleType() {
        return vehicleType.toUpperCase();
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

}
