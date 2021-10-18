package com.example.testbaitap.excercise;

public class NhomCo {
    int maNhomCo;
    String tenNhomCo;
    String img;

    public NhomCo(int maNhomCo, String tenNhomCo, String img) {
        this.maNhomCo = maNhomCo;
        this.tenNhomCo = tenNhomCo;
        this.img = img;
    }


    public int getMaNhomCo() {
        return maNhomCo;
    }

    public void setMaNhomCo(int maNhomCo) {
        this.maNhomCo = maNhomCo;
    }

    public String getTenNhomCo() {
        return tenNhomCo;
    }

    public void setTenNhomCo(String tenNhomCo) {
        this.tenNhomCo = tenNhomCo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
