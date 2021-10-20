package com.example.testbaitap.process.adapter.model;

import java.sql.Date;

public class TheTrang {
    private String maHocVien;

    public TheTrang(String maHocVien, Date ngay, double chieuCao, double canNang, double soDoV1, double soDoV2, double soDoV3, double soDoVDui, double soDoVTay, int luongNuoc) {
        this.maHocVien = maHocVien;
        this.ngay = ngay;
        this.chieuCao = chieuCao;
        this.canNang = canNang;
        this.soDoV1 = soDoV1;
        this.soDoV2 = soDoV2;
        this.soDoV3 = soDoV3;
        this.soDoVDui = soDoVDui;
        this.soDoVTay = soDoVTay;
        this.luongNuoc = luongNuoc;
    }

    private Date ngay;
    private double chieuCao;
    private double canNang;
    private double soDoV1;
    private double soDoV2;
    private double soDoV3;
    private double soDoVDui;
    private double soDoVTay;
    private int luongNuoc;




    public String getMaHocVien() {
        return maHocVien;
    }

    public void setMaHocVien(String maHocVien) {
        this.maHocVien = maHocVien;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public double getChieuCao() {
        return chieuCao;
    }

    public void setChieuCao(double chieuCao) {
        this.chieuCao = chieuCao;
    }

    public double getCanNang() {
        return canNang;
    }

    public void setCanNang(double canNang) {
        this.canNang = canNang;
    }

    public double getSoDoV1() {
        return soDoV1;
    }

    public void setSoDoV1(double soDoV1) {
        this.soDoV1 = soDoV1;
    }

    public double getSoDoV2() {
        return soDoV2;
    }

    public void setSoDoV2(double soDoV2) {
        this.soDoV2 = soDoV2;
    }

    public double getSoDoV3() {
        return soDoV3;
    }

    public void setSoDoV3(double soDoV3) {
        this.soDoV3 = soDoV3;
    }

    public double getSoDoVDui() {
        return soDoVDui;
    }

    public void setSoDoVDui(double soDoVDui) {
        this.soDoVDui = soDoVDui;
    }

    public double getSoDoVTay() {
        return soDoVTay;
    }

    public void setSoDoVTay(double soDoVTay) {
        this.soDoVTay = soDoVTay;
    }

    public int getLuongNuoc() {
        return luongNuoc;
    }

    public void setLuongNuoc(int luongNuoc) {
        this.luongNuoc = luongNuoc;
    }

    public double getBmi(){
        if(!String.valueOf(getCanNang()).isEmpty() && !String.valueOf(getChieuCao()).isEmpty()) {
            return getCanNang() / (getChieuCao()*2);
        } else{
            return 0;
        }
    }


}
