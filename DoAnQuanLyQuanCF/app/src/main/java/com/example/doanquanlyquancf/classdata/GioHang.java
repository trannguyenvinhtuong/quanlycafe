package com.example.doanquanlyquancf.classdata;

import java.io.Serializable;

public class GioHang implements Serializable {
    private String MaMon;
    private String TenMon;
    private String Gia;
    private String HinhAnh;
    private String MaLoai;
    private String Topping;
    private String Da,Duong;
    private int soluong;

    public GioHang(String maMon, String tenMon, String gia, String hinhAnh, String maLoai, String topping, String da, String duong, int soluong) {
        MaMon = maMon;
        TenMon = tenMon;
        Gia = gia;
        HinhAnh = hinhAnh;
        MaLoai = maLoai;
        Topping = topping;
        Da = da;
        Duong = duong;
        this.soluong=soluong;
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

    public String getTopping() {
        return Topping;
    }

    public void setTopping(String topping) {
        Topping = topping;
    }

    public String getDa() {
        return Da;
    }

    public void setDa(String da) {
        Da = da;
    }

    public String getDuong() {
        return Duong;
    }

    public void setDuong(String duong) {
        Duong = duong;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
