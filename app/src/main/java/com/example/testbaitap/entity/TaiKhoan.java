package com.example.testbaitap.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaiKhoan {
    @SerializedName("taiKhoan")
    @Expose
    private String maHocVien;

    @SerializedName("matKhau")
    @Expose
    private String matKhau;

    @SerializedName("trangThai")
    @Expose
    private Integer trangThai;

    public TaiKhoan(String maHocVien, String matKhau, Integer trangThai) {
        this.maHocVien = maHocVien;
        this.matKhau = matKhau;
        this.trangThai = trangThai;
    }

    public String getMaHocVien() {
        return maHocVien;
    }

    public void setMaHocVien(String maHocVien) {
        this.maHocVien = maHocVien;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }
}
