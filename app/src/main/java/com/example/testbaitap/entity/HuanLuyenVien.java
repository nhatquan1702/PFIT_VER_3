package com.example.testbaitap.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HuanLuyenVien {
    @SerializedName("maHLV")
    @Expose
    private String maHLV;

    @SerializedName("hoTen")
    @Expose
    private String hoTen;

    @SerializedName("soDienThoai")
    @Expose
    private String soDienThoai;

    @SerializedName("diaChi")
    @Expose
    private String diaChi;

    @SerializedName("ghiChu")
    @Expose
    private String ghiChu;

    @SerializedName("trangThai")
    @Expose
    private Integer trangThai;

    public HuanLuyenVien(String maHLV, String hoTen, String soDienThoai, String diaChi, String ghiChu, Integer trangThai) {
        this.maHLV = maHLV;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
    }

    public String getMaHLV() {
        return maHLV;
    }

    public void setMaHLV(String maHLV) {
        this.maHLV = maHLV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
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
