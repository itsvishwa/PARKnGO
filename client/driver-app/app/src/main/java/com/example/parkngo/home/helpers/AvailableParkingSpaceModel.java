package com.example.parkngo.home.helpers;

public class AvailableParkingSpaceModel {
    String parkingName;
    String freeSlots;
    String totalSlots;
    String rate;
    String parkingType;
    int noOfStars;
    String noOfReviews;
    String distance;
    String latitude;
    String longitude;

    public AvailableParkingSpaceModel(String parkingName, String freeSlots, String totalSlots, String rate, String parkingType, int noOfStars, String noOfReviews, String distance, String latitude, String longitude) {
        this.parkingName = parkingName;
        this.freeSlots = freeSlots;
        this.totalSlots = totalSlots;
        this.rate = rate;
        this.parkingType = parkingType;
        this.noOfStars = noOfStars;
        this.noOfReviews = noOfReviews;
        this.distance = distance;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getParkingName() {
        return parkingName;
    }

    public String getFreeSlots() {
        return freeSlots;
    }

    public String getTotalSlots() {
        return totalSlots;
    }

    public String getRate() {
        return rate;
    }

    public String getParkingType() {
        return parkingType;
    }

    public int getNoOfStars() {
        return noOfStars;
    }

    public String getNoOfReviews() {
        return noOfReviews;
    }

    public String getDistance() {
        return distance;
    }

    public String getLatitude(){ return latitude;}

    public String getLongitude(){return longitude;}
}
