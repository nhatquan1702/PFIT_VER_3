package com.example.testbaitap.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaiKhoan {

    @SerializedName("taiKhoan")
    @Expose
    private String taiKhoan;

    @SerializedName("matKhau")
    @Expose
    private String matKhau;

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
    private int trangThai;

    @SerializedName("quyen")
    @Expose
    private int quyen;

    public TaiKhoan(String taiKhoan, String matKhau, String hoTen, String soDienThoai, String diaChi, String ghiChu, int trangThai, int quyen) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
        this.quyen = quyen;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
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

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public int getQuyen() {
        return quyen;
    }

    public void setQuyen(int quyen) {
        this.quyen = quyen;
    }
}
