package com.example.testbaitap.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class KhachHang {
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
    private LocalDate ngayThamGia;

    @SerializedName("soDienThoai")
    @Expose
    private String soDienThoai;

    @SerializedName("diaChi")
    @Expose
    private String diaChi;

    @SerializedName("ghiChu")
    @Expose
    private String ghiChu;

    @SerializedName("trangThai")
    @Expose
    private Integer trangThai;

    public KhachHang(String maHocVien, String matKhau, String hoTen, Integer tuoi, Integer gioiTinh, LocalDate ngayThamGia, String soDienThoai, String diaChi, String ghiChu, Integer trangThai) {
        this.maHocVien = maHocVien;
        this.matKhau = matKhau;
        this.hoTen = hoTen;
        this.tuoi = tuoi;
        this.gioiTinh = gioiTinh;
        this.ngayThamGia = ngayThamGia;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
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

    public LocalDate getNgayThamGia() {
        return ngayThamGia;
    }

    public void setNgayThamGia(LocalDate ngayThamGia) {
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

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }
}
