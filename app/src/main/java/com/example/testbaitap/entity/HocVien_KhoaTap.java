package com.example.testbaitap.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HocVien_KhoaTap {
    @SerializedName("maHocVien")
    @Expose
    private String maHocVien;

    @SerializedName("maKhoaTap")
    @Expose
    private String maKhoaTap;

    public HocVien_KhoaTap(String maHocVien, String maKhoaTap) {
        this.maHocVien = maHocVien;
        this.maKhoaTap = maKhoaTap;
    }

    public String getMaHocVien() {
        return maHocVien;
    }

    public void setMaHocVien(String maHocVien) {
        this.maHocVien = maHocVien;
    }

    public String getMaKhoaTap() {
        return maKhoaTap;
    }

    public void setMaKhoaTap(String maKhoaTap) {
        this.maKhoaTap = maKhoaTap;
    }
}
