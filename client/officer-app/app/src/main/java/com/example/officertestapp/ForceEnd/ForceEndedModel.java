package com.example.officertestapp.ForceEnd;

import com.example.officertestapp.Helpers.VehicleNumberHelper;

public class ForceEndedModel {
    private String vehicleNumber;
    private String vehicleType;
    private String startDateTime;
    private String endDateTime;
    private String _id;

    public ForceEndedModel(String _id, String vehicleNumber, String vehicleType, String startDateTime, String endDateTime) {
        this._id = _id;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getVehicleType() {
        return VehicleNumberHelper.formatVehicleType(vehicleType);
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public String getID(){
        return _id;
    }

}
