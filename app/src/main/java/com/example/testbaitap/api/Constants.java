package com.example.testbaitap.api;

public class Constants {
    private Constants(){}
    public static final String BASE_URL = "http://192.168.1.3:8989/";
    public static SimpleAPI instance() {
        return RetrofitInstance.GetClient(BASE_URL).create(SimpleAPI.class);
    }
}
