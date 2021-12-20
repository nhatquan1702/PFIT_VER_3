package com.example.testbaitap.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NgayTap {
    @SerializedName("ngayTap")
    @Expose
    private Integer ngayTap;

    @SerializedName("trangThai")
    @Expose
    private Integer trangThai;

    public NgayTap(Integer ngayTap, Integer trangThai) {
        this.ngayTap = ngayTap;
        this.trangThai = trangThai;
    }

    public Integer getNgayTap() {
        return ngayTap;
    }

    public void setNgayTap(Integer ngayTap) {
        this.ngayTap = ngayTap;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }
}
