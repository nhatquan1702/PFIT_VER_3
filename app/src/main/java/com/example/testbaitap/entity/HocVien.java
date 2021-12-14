package com.example.testbaitap.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.time.LocalDate;

public class HocVien {
    @SerializedName("maHocVien")
    @Expose
    private String maHocVien;

    @SerializedName("matKhau")
    @Expose
    private String matKhau;

    @SerializedName("hoTen")
    @Expose
    private String hoTen;

    @SerializedName("tuoi")
    @Expose
    private Integer tuoi;

    @SerializedName("gioiTinh")
    @Expose
    private Integer gioiTinh;

    @SerializedName("ngayThamGia")
    @Expose
    private String ngayThamGia;

    @SerializedName("soDienThoai")
    @Expose
    private String soDienThoai;

    @SerializedName("diaChi")
    @Expose
    private String diaChi;

    @SerializedName("capDo")
    @Expose
    private Integer capDo;

    @SerializedName("trangThai")
    @Expose
    private Integer trangThai;

    @SerializedName("ghiChu")
    @Expose
    private String ghiChu;

    @SerializedName("maKhoaTap")
    @Expose
    private String maKhoaTap;

    public HocVien(String maHocVien, String matKhau, String hoTen, Integer tuoi, Integer gioiTinh, String ngayThamGia, String soDienThoai, String diaChi, Integer capDo, Integer trangThai, String ghiChu, String maKhoaTap) {
        this.maHocVien = maHocVien;
        this.matKhau = matKhau;
        this.hoTen = hoTen;
        this.tuoi = tuoi;
        this.gioiTinh = gioiTinh;
        this.ngayThamGia = ngayThamGia;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.capDo = capDo;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
        this.maKhoaTap = maKhoaTap;
    }

    public String getMaHocVien() {
        return maHocVien;
    }

    public void setMaHocVien(String maHocVien) {
        this.maHocVien = maHocVien;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
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

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
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

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMaKhoaTap() {
        return maKhoaTap;
    }

    public void setMaKhoaTap(String maKhoaTap) {
        this.maKhoaTap = maKhoaTap;
    }
}