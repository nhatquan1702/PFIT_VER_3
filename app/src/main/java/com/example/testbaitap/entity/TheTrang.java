package com.example.testbaitap.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class TheTrang {
    @SerializedName("maHocVien")
    @Expose
    private String maHocVien;

    @SerializedName("ngay")
    @Expose
    private String ngay;

    @SerializedName("chieuCao")
    @Expose
    private Float chieuCao;

    @SerializedName("canNang")
    @Expose
    private Float canNang;

    @SerializedName("vong1")
    @Expose
    private Float vong1;

    @SerializedName("vong2")
    @Expose
    private Float vong2;

    @SerializedName("vong3")
    @Expose
    private Float vong3;

    @SerializedName("vongTay")
    @Expose
    private Float vongTay;

    @SerializedName("vongDui")
    @Expose
    private Float vongDui;

    @SerializedName("luongNuoc")
    @Expose
    private Float luongNuoc;

    public TheTrang(String maHocVien, String ngay, Float chieuCao, Float canNang, Float vong1, Float vong2, Float vong3, Float vongTay, Float vongDui, Float luongNuoc) {
        this.maHocVien = maHocVien;
        this.ngay = ngay;
        this.chieuCao = chieuCao;
        this.canNang = canNang;
        this.vong1 = vong1;
        this.vong2 = vong2;
        this.vong3 = vong3;
        this.vongTay = vongTay;
        this.vongDui = vongDui;
        this.luongNuoc = luongNuoc;
    }

    public TheTrang() {

    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getMaHocVien() {
        return maHocVien;
    }

    public void setMaHocVien(String maHocVien) {
        this.maHocVien = maHocVien;
    }

    public Float getChieuCao() {
        return chieuCao;
    }

    public void setChieuCao(Float chieuCao) {
        this.chieuCao = chieuCao;
    }

    public Float getCanNang() {
        return canNang;
    }

    public void setCanNang(Float canNang) {
        this.canNang = canNang;
    }

    public Float getVong1() {
        return vong1;
    }

    public void setVong1(Float vong1) {
        this.vong1 = vong1;
    }

    public Float getVong2() {
        return vong2;
    }

    public void setVong2(Float vong2) {
        this.vong2 = vong2;
    }

    public Float getVong3() {
        return vong3;
    }

    public void setVong3(Float vong3) {
        this.vong3 = vong3;
    }

    public Float getVongTay() {
        return vongTay;
    }

    public void setVongTay(Float vongTay) {
        this.vongTay = vongTay;
    }

    public Float getVongDui() {
        return vongDui;
    }

    public void setVongDui(Float vongDui) {
        this.vongDui = vongDui;
    }

    public Float getLuongNuoc() {
        return luongNuoc;
    }

    public void setLuongNuoc(Float luongNuoc) {
        this.luongNuoc = luongNuoc;
    }

    public float getBmi(){
        float chieuCaoM = chieuCao/100;
        float bmi = canNang / (chieuCaoM*2);
        return bmi;
    }
}
