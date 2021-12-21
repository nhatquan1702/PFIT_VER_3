package com.example.testbaitap.api;

import com.example.testbaitap.entity.BaiTap;
import com.example.testbaitap.entity.BaiTapFull;
import com.example.testbaitap.entity.ChiTietBaiTap;
import com.example.testbaitap.entity.ChiTietBaiTapChoHV;
import com.example.testbaitap.entity.DoanhThu;
import com.example.testbaitap.entity.HoaDon;
import com.example.testbaitap.entity.HocVien_KhoaTap;
import com.example.testbaitap.entity.HuanLuyenVien;
import com.example.testbaitap.entity.HocVien;
import com.example.testbaitap.entity.KhoaTap;
import com.example.testbaitap.entity.NgayTap;
import com.example.testbaitap.entity.NhomCo;
import com.example.testbaitap.entity.Notifi;
import com.example.testbaitap.entity.Status;
import com.example.testbaitap.entity.TaiKhoan;
import com.example.testbaitap.entity.TheTrang;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SimpleAPI {
    @POST("api/taikhoan/dangnhap")
    Call<Status> dangNhap(@Query("email") String email, @Query("password") String password);

    @GET("api/nhomco")
    Call<ArrayList<NhomCo>> getListNhomCo();

    @GET("api/hocvien/tthocvien")
    Call<HocVien> getTaiKhoan(@Query(value = "taikhoan") String taiKhoan);

    @GET("api/hocvien/tthocvienheader")
    Call<HocVien> getKhachHang(@Header(value = "taikhoan") String taiKhoan);

    @GET("api/baitap/baitaptheomanhomco")
    Call<ArrayList<BaiTap>> getBaiTapTheoNhomCo(@Query("manhomco") String maNhomCo);

    @GET("api/baitap/baitaptheomabaitap")
    Call<BaiTapFull> getFullBaiTapTheoMa(@Query("mabaitap") String maBaiTap);

    @GET("api/chitietbaitap/ctbtchothanghocvien")
    Call<ChiTietBaiTap> getChiTietBaiTapTheoMa(@Query("mabaitap") String maBaiTap, @Query("mahocvien") String maHocVien);

    @GET("api/chitietbaitap/chitietbaitaptheomabaitap")
    Call<ChiTietBaiTap> getChiTietBaiTapTheoMaNhomCo(@Query("mabaitap") String maBaiTap);

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

    @POST("api/thetrang/insert")
    Call<Status> insertTheTrang (@Body TheTrang theTrang);

    @POST("api/thetrang/updateln")
    Call<Status> updateLuongNuoc (@Query("ngay") String ngay, @Query("mahocvien") String maHocVien, @Query("luongnuoc") Float luongNuoc);

    @POST("api/thetrang/updatett")
    Call<Status> updateTheTrang (@Body TheTrang theTrang);

    @GET("api/chitietbaitap/chitietbaitapchohv")
    Call<ChiTietBaiTapChoHV> getChiTietBaiTapChoHocVien(@Query("mabaitap") String maBaiTap, @Query("mahocvien") String maHocVien);

    @POST("api/chitietbaitap/updatettbtchohv")
    Call<Status> updateTrangThaiChoHocVien (@Query("mabaitap") String maBaiTap, @Query("mahocvien") String maHocVien, @Query("trangthai") Integer trangThai);

    @POST("api/chitietbaitap/updategcbtchohv")
    Call<Status> updateChiChuChoHocVien (@Query("mabaitap") String maBaiTap, @Query("mahocvien") String maHocVien, @Query("ghichu") String ghiChu);

    @GET("api/chitietbaitap/ptbthtngay")
    Call<Status> getPhanTramBTTheoNgay(@Query("makhoatap") String maKhoaTap, @Query("mahocvien") String maHocVien, @Query("ngaytap") Integer ngayTap);

    @GET("api/chitietbaitap/ptbthtkhoa")
    Call<Status> getPhanTramBTTheoKhoa(@Query("makhoatap") String maKhoaTap, @Query("mahocvien") String maHocVien);

    @GET("api/khoatap/allkhoatap")
    Call<ArrayList<KhoaTap>> getAllKhoaTap();

    @POST("api/khoatap/insert")
    Call<Status> insertKhoaTap(@Body KhoaTap khoaTap);

    @POST("api/khoatap/update")
    Call<Status> updateKhoaTap(@Body KhoaTap khoaTap);

    @POST("api/khoatap/updatettkt")
    Call<Status> updateTrangThaiKhoaTap(@Query("makhoatap") String maKhoaTap, @Query("trangthai") int trangThai);

    @GET("api/huanluyenvien/tthlv")
    Call<HuanLuyenVien> getHLV(@Query("mahuanluyenvien") String maHLV);

    @GET("api/khoatap/ttkhoatap")
    Call<KhoaTap> getKhoaTap(@Query("makhoatap") String maKT);

    @POST("api/hocvien/updatetthv")
    Call<Status> updateTrangThaiHocVien(@Query("mahocvien") String maHV, @Query("trangthai") int trangThai);

    @POST("api/hocvien/updatekthv")
    Call<Status> updateKhoaTapChoHocVien(@Query("mahocvien") String maHV, @Query("makhoatap") String maKT);

    @GET("api/hoadon/dttheongay")
    Call<ArrayList<DoanhThu>> getDoanhThuTheoNgay(@Query("ngay") String ngay);

    @GET("api/hoadon/dttheothang")
    Call<ArrayList<DoanhThu>> getDoanhThuTheoThang(@Query("thang") String thang, @Query("nam") String nam);

    @GET("api/hoadon/dttheonam")
    Call<ArrayList<DoanhThu>> getDoanhThuTheoNam(@Query("nam") String nam);

    @GET("api/hoadon/dshoadontrongngay")
    Call<ArrayList<HoaDon>> getHoaDonTrongNgay(@Query("ngay") String ngay);

    @GET("api/taikhoan/tttaikhoan")
    Call<TaiKhoan> getThongTinTaiKhoan(@Query(value = "taikhoan") String taiKhoan);

    @POST("notification")
    Call<Status> pushNotifi(@Body Notifi notifi);

    @POST("api/ngaytap/updatettnt")
    Call<Status> upDateTrangThaiNgayTap(@Query("ngay") String ngay, @Query("trangthai") int trangthai);

    @GET("api/chitietbaitap/getctbttheongay")
    Call<ArrayList<ChiTietBaiTapChoHV>> getListChiTietBTTheoNgay(@Query("makhoatap") String maKhoaTap, @Query("mahocvien") String maHocVien, @Query("ngaytap") Integer ngayTap);
}
