package com.example.testbaitap.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaiTap {
    public BaiTap(String maBaiTap, String tenBaiTap, String hinhMinhHoa, String video, String moTa, Integer capDo) {
        this.maBaiTap = maBaiTap;
        this.tenBaiTap = tenBaiTap;
        this.hinhMinhHoa = hinhMinhHoa;
        this.video = video;
        this.moTa = moTa;
        this.capDo = capDo;
    }

    @SerializedName("maBaiTap")
    @Expose
    private String maBaiTap;

    @SerializedName("tenBaiTap")
    @Expose
    private String tenBaiTap;

    @SerializedName("hinhMinhHoa")
    @Expose
    private String hinhMinhHoa;

    @SerializedName("video")
    @Expose
    private String video;

    @SerializedName("moTa")
    @Expose
    private String moTa;

    @SerializedName("capDo")
    @Expose
    private Integer capDo;

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

    public String getHinhMinhHoa() {
        return hinhMinhHoa;
    }

    public void setHinhMinhHoa(String hinhMinhHoa) {
        this.hinhMinhHoa = hinhMinhHoa;
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

    public Integer getCapDo() {
        return capDo;
    }

    public void setCapDo(Integer capDo) {
        this.capDo = capDo;
    }
}
