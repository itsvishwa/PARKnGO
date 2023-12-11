package com.example.parkngo.parking;

public class ReviewModel {

    String userName;
    int noOfStars;
    String msg;
    String date;

    public ReviewModel(String name, int noOfStars, String msg, String date) {
        this.userName = name;
        this.noOfStars = noOfStars;
        this.msg = msg;
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public int getNoOfStars() {
        return noOfStars;
    }

    public String getMsg() {
        return msg;
    }

    public String getDate() {
        return date;
    }
}
