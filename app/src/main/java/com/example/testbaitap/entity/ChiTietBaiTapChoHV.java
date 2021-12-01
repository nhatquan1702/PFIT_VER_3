package com.example.testbaitap.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChiTietBaiTapChoHV {
    @SerializedName("maHocVien")
    @Expose
    private String maHocVien;

    @SerializedName("maBaiTap")
    @Expose
    private String maBaiTap;

    @SerializedName("ghiChu")
    @Expose
    private String ghiChu;

    @SerializedName("trangThai")
    @Expose
    private Integer trangThai;

    public ChiTietBaiTapChoHV(String maHocVien, String maBaiTap, String ghiChu, Integer trangThai) {
        this.maHocVien = maHocVien;
        this.maBaiTap = maBaiTap;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
    }

    public String getMaHocVien() {
        return maHocVien;
    }

    public void setMaHocVien(String maHocVien) {
        this.maHocVien = maHocVien;
    }

    public String getMaBaiTap() {
        return maBaiTap;
    }

    public void setMaBaiTap(String maBaiTap) {
        this.maBaiTap = maBaiTap;
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
}
