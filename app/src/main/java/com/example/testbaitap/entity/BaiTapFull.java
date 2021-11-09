package com.example.testbaitap.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaiTapFull {
    @SerializedName("maBaiTap")
    @Expose
    private String maBaiTap;

    @SerializedName("tenBaiTap")
    @Expose
    private String tenBaiTap;

    @SerializedName("video")
    @Expose
    private String video;

    @SerializedName("moTa")
    @Expose
    private String moTa;

    @SerializedName("tenDungCu")
    @Expose
    private String tenDungCu;

    public BaiTapFull(String maBaiTap, String tenBaiTap, String video, String moTa, String tenDungCu) {
        this.maBaiTap = maBaiTap;
        this.tenBaiTap = tenBaiTap;
        this.video = video;
        this.moTa = moTa;
        this.tenDungCu = tenDungCu;
    }

    public String getMaBaiTap() {
        return maBaiTap;
    }

    public void setMaBaiTap(String maBaiTap) {
        this.maBaiTap = maBaiTap;
    }

    public String getTenBaiTap() {
        return tenBaiTap;
    }

    public void setTenBaiTap(String tenBaiTap) {
        this.tenBaiTap = tenBaiTap;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getTenDungCu() {
        return tenDungCu;
    }

    public void setTenDungCu(String tenDungCu) {
        this.tenDungCu = tenDungCu;
    }
}
