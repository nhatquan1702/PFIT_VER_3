package com.example.testbaitap.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaiTap {
    public BaiTap(String maBaiTap, String tenBaiTap, String hinhMinhHoa) {
        this.maBaiTap = maBaiTap;
        this.tenBaiTap = tenBaiTap;
        this.hinhMinhHoa = hinhMinhHoa;
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
}
