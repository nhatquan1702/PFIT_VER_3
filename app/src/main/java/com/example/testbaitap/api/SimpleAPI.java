package com.example.testbaitap.api;

import com.example.testbaitap.entity.NhomCo;
import com.example.testbaitap.entity.Status;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SimpleAPI {
    @POST("api/khachhang/dangnhap")
    Call<Status> login(@Query("email") String email, @Query("password") String password);

    @GET("api/nhomco")
    Call<ArrayList<NhomCo>> getListNhomCo();
}
