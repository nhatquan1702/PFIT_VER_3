package com.example.testbaitap.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NgayTap {
    @SerializedName("ngayTap")
    @Expose
    private Integer ngayTap;

    public NgayTap(Integer ngayTap) {
        this.ngayTap = ngayTap;
    }

    public Integer getNgayTap() {
        return ngayTap;
    }

    public void setNgayTap(Integer ngayTap) {
        this.ngayTap = ngayTap;
    }
}
