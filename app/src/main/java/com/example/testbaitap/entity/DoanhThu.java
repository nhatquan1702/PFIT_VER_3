package com.example.testbaitap.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoanhThu {
    @SerializedName("ngay")
    @Expose
    private String ngay;

    @SerializedName("tong_tien")
    @Expose
    private int tongTien;

    public DoanhThu(String ngay, int tongTien) {
        this.ngay = ngay;
        this.tongTien = tongTien;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
}
