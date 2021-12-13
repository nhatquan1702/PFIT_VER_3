package com.example.testbaitap.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class HoaDon {
    @SerializedName("maHoaDon")
    @Expose
    private String maHoaDon;

    @SerializedName("ngayGioThanhToan")
    @Expose
    private String ngayGioThanhToan;

    @SerializedName("tongThanhToan")
    @Expose
    private Float tongThanhToan;

    @SerializedName("hinhThucThanhToan")
    @Expose
    private Integer hinhThucThanhToan;

    @SerializedName("ghiChu")
    @Expose
    private String ghiChu;

    @SerializedName("trangThai")
    @Expose
    private Integer trangThai;

    @SerializedName("maHocVien")
    @Expose
    private String maHocVien;

    @SerializedName("hoTen")
    @Expose
    private String hoTen;

    @SerializedName("maKhoaTap")
    @Expose
    private String maKhoaTap;

    @SerializedName("tenKhoaTap")
    @Expose
    private String tenKhoaTap;

    public HoaDon(String maHoaDon, String ngayGioThanhToan, Float tongThanhToan, Integer hinhThucThanhToan, String ghiChu, Integer trangThai, String maHocVien, String hoTen, String maKhoaTap, String tenKhoaTap) {
        this.maHoaDon = maHoaDon;
        this.ngayGioThanhToan = ngayGioThanhToan;
        this.tongThanhToan = tongThanhToan;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
        this.maHocVien = maHocVien;
        this.hoTen = hoTen;
        this.maKhoaTap = maKhoaTap;
        this.tenKhoaTap = tenKhoaTap;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getNgayGioThanhToan() {
        return ngayGioThanhToan;
    }

    public void setNgayGioThanhToan(String ngayGioThanhToan) {
        this.ngayGioThanhToan = ngayGioThanhToan;
    }

    public Float getTongThanhToan() {
        return tongThanhToan;
    }

    public void setTongThanhToan(Float tongThanhToan) {
        this.tongThanhToan = tongThanhToan;
    }

    public Integer getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public void setHinhThucThanhToan(Integer hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaHocVien() {
        return maHocVien;
    }

    public void setMaHocVien(String maHocVien) {
        this.maHocVien = maHocVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMaKhoaTap() {
        return maKhoaTap;
    }

    public void setMaKhoaTap(String maKhoaTap) {
        this.maKhoaTap = maKhoaTap;
    }

    public String getTenKhoaTap() {
        return tenKhoaTap;
    }

    public void setTenKhoaTap(String tenKhoaTap) {
        this.tenKhoaTap = tenKhoaTap;
    }
}
