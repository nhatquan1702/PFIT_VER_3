package com.example.testbaitap.api;

import com.example.testbaitap.entity.KhachHang;
import com.example.testbaitap.entity.NhomCo;
import com.example.testbaitap.entity.Status;
import com.example.testbaitap.entity.TaiKhoan;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SimpleAPI {
    @POST("api/khachhang/dangnhap")
    Call<Status> login(@Query("email") String email, @Query("password") String password);

    @GET("api/nhomco")
    Call<ArrayList<NhomCo>> getListNhomCo();

    @GET("api/khachhang/ttkhachhang")
    Call<TaiKhoan> getTaiKhoan(@Query(value = "taikhoan") String taiKhoan);

    @GET("api/khachhang/ttkhachhangheader")
    Call<KhachHang> getKhachHang(@Header(value = "taikhoan") String taiKhoan);
}
