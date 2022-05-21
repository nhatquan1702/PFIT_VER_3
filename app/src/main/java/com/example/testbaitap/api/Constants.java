package com.example.testbaitap.api;

public class Constants {
    private Constants(){}
    //public static final String BASE_URL = "http://10.0.1.36:7777/";
    public static final String BASE_URL = "http://192.168.1.4:7777";
    public static SimpleAPI instance() {
        return RetrofitInstance.GetClient(BASE_URL).create(SimpleAPI.class);
    }
}
