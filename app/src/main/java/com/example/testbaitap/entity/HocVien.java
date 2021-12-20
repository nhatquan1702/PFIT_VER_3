package com.example.testbaitap.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.time.LocalDate;

public class HocVien {
    @SerializedName("maHocVien")
    @Expose
    private String maHocVien;

    @SerializedName("tuoi")
    @Expose
    private Integer tuoi;

    @SerializedName("gioiTinh")
    @Expose
    private Integer gioiTinh;

    @SerializedName("ngayThamGia")
    @Expose
    private String ngayThamGia;

    @SerializedName("capDo")
    @Expose
    private Integer capDo;

    @SerializedName("trangThai")
    @Expose
    private Integer trangThai;

    @SerializedName("maKhoaTap")
    @Expose
    private String maKhoaTap;

    public HocVien(String maHocVien, Integer tuoi, Integer gioiTinh, String ngayThamGia, Integer capDo, Integer trangThai, String maKhoaTap) {
        this.maHocVien = maHocVien;
        this.tuoi = tuoi;
        this.gioiTinh = gioiTinh;
        this.ngayThamGia = ngayThamGia;
        this.capDo = capDo;
        this.trangThai = trangThai;
        this.maKhoaTap = maKhoaTap;
    }

    public String getMaHocVien() {
        return maHocVien;
    }

    public void setMaHocVien(String maHocVien) {
        this.maHocVien = maHocVien;
    }

    public Integer getTuoi() {
        return tuoi;
    }

    public void setTuoi(Integer tuoi) {
        this.tuoi = tuoi;
    }

    public Integer getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(Integer gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgayThamGia() {
        return ngayThamGia;
    }

    public void setNgayThamGia(String ngayThamGia) {
        this.ngayThamGia = ngayThamGia;
    }

    public Integer getCapDo() {
        return capDo;
    }

    public void setCapDo(Integer capDo) {
        this.capDo = capDo;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaKhoaTap() {
        return maKhoaTap;
    }

    public void setMaKhoaTap(String maKhoaTap) {
        this.maKhoaTap = maKhoaTap;
    }
}
