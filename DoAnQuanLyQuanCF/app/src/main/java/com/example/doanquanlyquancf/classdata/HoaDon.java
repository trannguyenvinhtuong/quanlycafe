package com.example.doanquanlyquancf.classdata;

import java.io.Serializable;

public class HoaDon implements Serializable {
    String MaHD, MaBan, MaNV, MaKH, NgayLap, TongTien, TrangThai;

    public HoaDon(String maHD, String maBan, String maNV, String maKH, String ngayLap, String tongTien, String trangThai) {
        MaHD = maHD;
        MaBan = maBan;
        MaNV = maNV;
        MaKH = maKH;
        NgayLap = ngayLap;
        TongTien = tongTien;
        TrangThai = trangThai;
    }

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String maHD) {
        MaHD = maHD;
    }

    public String getMaBan() {
        return MaBan;
    }

    public void setMaBan(String maBan) {
        MaBan = maBan;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String maKH) {
        MaKH = maKH;
    }

    public String getNgayLap() {
        return NgayLap;
    }

    public void setNgayLap(String ngayLap) {
        NgayLap = ngayLap;
    }

    public String getTongTien() {
        return TongTien;
    }

    public void setTongTien(String tongTien) {
        TongTien = tongTien;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }
}
