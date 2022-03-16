package com.example.doanquanlyquancf.classdata;

import java.io.Serializable;

public class Loai implements Serializable {
    private String maloai;
    private String TenLoai;
    private String AnhLoai;

    public Loai(String maloai, String tenLoai, String anhLoai) {
        this.maloai = maloai;
        TenLoai = tenLoai;
        AnhLoai = anhLoai;
    }

    public String getMaloai() {
        return maloai;
    }

    public void setMaloai(String maloai) {
        this.maloai = maloai;
    }

    public String getTenLoai() {
        return TenLoai;
    }

    public void setTenLoai(String tenLoai) {
        TenLoai = tenLoai;
    }

    public String getAnhLoai() {
        return AnhLoai;
    }

    public void setAnhLoai(String anhLoai) {
        AnhLoai = anhLoai;
    }
}
