package com.example.testbaitap.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NhomCo {
    @SerializedName("maNhomCo")
    @Expose
    private String maNhomCo;

    @SerializedName("tenNhomCo")
    @Expose
    private String tenNhomCo;

    @SerializedName("hinhMinhHoa")
    @Expose
    private String hinhMinhHoa;

    public String getMaNhomCo() {
        return maNhomCo;
    }

    public void setMaNhomCo(String maNhomCo) {
        this.maNhomCo = maNhomCo;
    }

    public String getTenNhomCo() {
        return tenNhomCo;
    }

    public void setTenNhomCo(String tenNhomCo) {
        this.tenNhomCo = tenNhomCo;
    }

    public String getHinhMinhHoa() {
        return hinhMinhHoa;
    }

    public void setHinhMinhHoa(String hinhMinhHoa) {
        this.hinhMinhHoa = hinhMinhHoa;
    }

    public NhomCo(String maNhomCo, String tenNhomCo, String hinhMinhHoa) {
        this.maNhomCo = maNhomCo;
        this.tenNhomCo = tenNhomCo;
        this.hinhMinhHoa = hinhMinhHoa;
    }
}
