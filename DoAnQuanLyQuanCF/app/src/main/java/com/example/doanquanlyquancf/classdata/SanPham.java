package com.example.doanquanlyquancf.classdata;

import java.io.Serializable;

public class SanPham implements Serializable {
    private String MaMon;
    private String TenMon;
    private String Gia;
    private String HinhAnh;
    private String MaLoai;

    public SanPham(String maMon, String tenMon, String gia, String hinhAnh, String maLoai) {
        MaMon = maMon;
        TenMon = tenMon;
        Gia = gia;
        HinhAnh = hinhAnh;
        MaLoai = maLoai;
    }

    public String getMaMon() {
        return MaMon;
    }

    public void setMaMon(String maMon) {
        MaMon = maMon;
    }

    public String getTenMon() {
        return TenMon;
    }

    public void setTenMon(String tenMon) {
        TenMon = tenMon;
    }

    public String getGia() {
        return Gia;
    }

    public void setGia(String gia) {
        Gia = gia;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(String maLoai) {
        MaLoai = maLoai;
    }
}
