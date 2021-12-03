package com.example.testbaitap.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KhoaTap {
    @SerializedName("maKhoaTap")
    @Expose
    private String maKhoaTap;

    @SerializedName("tenKhoaTap")
    @Expose
    private String tenKhoaTap;

    @SerializedName("hinhQuangCao")
    @Expose
    private String hinhQuangCao;

    @SerializedName("giaTheoThang")
    @Expose
    private int giaTheoThang;

    @SerializedName("choDoiTuong")
    @Expose
    private int choDoiTuong;

    @SerializedName("trangThai")
    @Expose
    private int trangThai;

    @SerializedName("maNhanVien")
    @Expose
    private String maNhanVien;

    @SerializedName("maHuanLuyenVien")
    @Expose
    private String maHuanLuyenVien;

    public KhoaTap(String maKhoaTap, String tenKhoaTap, String hinhQuangCao, int giaTheoThang, int choDoiTuong, int trangThai, String maNhanVien, String maHuanLuyenVien) {
        this.maKhoaTap = maKhoaTap;
        this.tenKhoaTap = tenKhoaTap;
        this.hinhQuangCao = hinhQuangCao;
        this.giaTheoThang = giaTheoThang;
        this.choDoiTuong = choDoiTuong;
        this.trangThai = trangThai;
        this.maNhanVien = maNhanVien;
        this.maHuanLuyenVien = maHuanLuyenVien;
    }

    public String getMaKhoaTap() {
        return maKhoaTap;
    }

    public void setMaKhoaTap(String maKhoaTap) {
        this.maKhoaTap = maKhoaTap;
    }

    public String getTenKhoaTap() {
        return tenKhoaTap;
    }

    public void setTenKhoaTap(String tenKhoaTap) {
        this.tenKhoaTap = tenKhoaTap;
    }

    public String getHinhQuangCao() {
        return hinhQuangCao;
    }

    public void setHinhQuangCao(String hinhQuangCao) {
        this.hinhQuangCao = hinhQuangCao;
    }

    public int getGiaTheoThang() {
        return giaTheoThang;
    }

    public void setGiaTheoThang(int giaTheoThang) {
        this.giaTheoThang = giaTheoThang;
    }

    public int getChoDoiTuong() {
        return choDoiTuong;
    }

    public void setChoDoiTuong(int choDoiTuong) {
        this.choDoiTuong = choDoiTuong;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getMaHuanLuyenVien() {
        return maHuanLuyenVien;
    }

    public void setMaHuanLuyenVien(String maHuanLuyenVien) {
        this.maHuanLuyenVien = maHuanLuyenVien;
    }
}
