package com.example.testbaitap.api;

import com.example.testbaitap.entity.BaiTap;
import com.example.testbaitap.entity.BaiTapFull;
import com.example.testbaitap.entity.ChiTietBaiTap;
import com.example.testbaitap.entity.HocVien_KhoaTap;
import com.example.testbaitap.entity.KhachHang;
import com.example.testbaitap.entity.NgayTap;
import com.example.testbaitap.entity.NhomCo;
import com.example.testbaitap.entity.Status;
import com.example.testbaitap.entity.TaiKhoan;
import com.example.testbaitap.entity.TheTrang;

import java.sql.Date;
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

    @GET("api/baitap/baitaptheomanhomco")
    Call<ArrayList<BaiTap>> getBaiTapTheoNhomCo(@Query("manhomco") String maNhomCo);

    @GET("api/baitap/baitaptheomabaitap")
    Call<BaiTapFull> getFullBaiTapTheoMa(@Query("mabaitap") String maBaiTap);

    @GET("api/chitietbaitap/chitietbaitaptheomabaitap")
    Call<ChiTietBaiTap> getChiTietBaiTapTheoMa(@Query("mabaitap") String maBaiTap);

    @GET("api/ngaytap")
    Call<ArrayList<NgayTap>> getListNgayTap();

    @GET("api/hocvien/makhoatap")
    Call<HocVien_KhoaTap> getMaKhoaTapTheoMaHocVien(@Query("mahocvien") String maHocVien);

    @GET("api/baitap/baitaptheokhoavangay")
    Call<ArrayList<BaiTap>> getBaiTapTheoKhoaVaNgay(@Query("makhoatap") String maKhoaTap, @Query("ngaytap") String ngayTap);

    @GET("api/thetrang/thetranghocvien")
    Call<TheTrang> getTheTrangHVTheoNgay(@Header("mahocvien") String maHocVien, @Header("ngay") String ngay);

    @GET("api/thetrang/thetranghocvientheothang")
    Call<ArrayList<TheTrang>> getTheTrangHVTheoThang(@Query("mahocvien") String maHocVien, @Query("thang") String thang, @Query("nam") String nam);

    @GET("api/thetrang/thetranghocvientheonam")
    Call<ArrayList<TheTrang>> getTheTrangHVTheoNam(@Query("mahocvien") String maHocVien, @Query("nam") String nam);

    @GET("api/thetrang/thetranggannhat")
    Call<TheTrang> getTheTrangHVGanNhat(@Query("mahocvien") String maHocVien);

}
