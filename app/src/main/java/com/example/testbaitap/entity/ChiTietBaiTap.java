package com.example.testbaitap.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChiTietBaiTap {
    @SerializedName("maBaiTap")
    @Expose
    private String maBaiTap;

    @SerializedName("khoiLuong")
    @Expose
    private Float khoiLuong;

    @SerializedName("soHiep")
    @Expose
    private String soHiep;

    @SerializedName("soLanLap")
    @Expose
    private String soLanLap;

    @SerializedName("tocDo")
    @Expose
    private String tocDo;

    @SerializedName("thoiGianNghi")
    @Expose
    private String thoiGianNghi;

    @SerializedName("ghiChu")
    @Expose
    private String ghiChu;

    @SerializedName("trangThai")
    @Expose
    private Integer trangThai;

    public ChiTietBaiTap(String maBaiTap, Float khoiLuong, String soHiep, String soLanLap, String tocDo, String thoiGianNghi, String ghiChu, Integer trangThai) {
        this.maBaiTap = maBaiTap;
        this.khoiLuong = khoiLuong;
        this.soHiep = soHiep;
        this.soLanLap = soLanLap;
        this.tocDo = tocDo;
        this.thoiGianNghi = thoiGianNghi;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
    }

    public String getMaBaiTap() {
        return maBaiTap;
    }

    public void setMaBaiTap(String maBaiTap) {
        this.maBaiTap = maBaiTap;
    }

    public Float getKhoiLuong() {
        return khoiLuong;
    }

    public void setKhoiLuong(Float khoiLuong) {
        this.khoiLuong = khoiLuong;
    }

    public String getSoHiep() {
        return soHiep;
    }

    public void setSoHiep(String soHiep) {
        this.soHiep = soHiep;
    }

    public String getSoLanLap() {
        return soLanLap;
    }

    public void setSoLanLap(String soLanLap) {
        this.soLanLap = soLanLap;
    }

    public String getTocDo() {
        return tocDo;
    }

    public void setTocDo(String tocDo) {
        this.tocDo = tocDo;
    }

    public String getThoiGianNghi() {
        return thoiGianNghi;
    }

    public void setThoiGianNghi(String thoiGianNghi) {
        this.thoiGianNghi = thoiGianNghi;
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
