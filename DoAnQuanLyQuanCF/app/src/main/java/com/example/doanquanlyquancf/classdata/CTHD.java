package com.example.doanquanlyquancf.classdata;

import java.io.Serializable;

public class CTHD implements Serializable {
    String ID, MaHD, MaMon, YeuCau, SoLuong, ThanhTien;
    String anh, tenmon;

    public CTHD(String ID, String maHD, String maMon, String yeuCau, String soLuong, String thanhTien, String anh, String tenmon) {
        this.ID = ID;
        MaHD = maHD;
        MaMon = maMon;
        YeuCau = yeuCau;
        SoLuong = soLuong;
        ThanhTien = thanhTien;
        this.anh = anh;
        this.tenmon = tenmon;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String maHD) {
        MaHD = maHD;
    }

    public String getMaMon() {
        return MaMon;
    }

    public void setMaMon(String maMon) {
        MaMon = maMon;
    }

    public String getYeuCau() {
        return YeuCau;
    }

    public void setYeuCau(String yeuCau) {
        YeuCau = yeuCau;
    }

    public String getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(String soLuong) {
        SoLuong = soLuong;
    }

    public String getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(String thanhTien) {
        ThanhTien = thanhTien;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }
}
