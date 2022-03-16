package com.example.doanquanlyquancf.classdata;

import java.io.Serializable;

public class TaiKhoan implements Serializable {
    String tendn,matkhau,macv,manv,makh,img;

    public TaiKhoan(String tendn, String matkhau, String macv, String manv, String makh, String img) {
        this.tendn = tendn;
        this.matkhau = matkhau;
        this.macv = macv;
        this.manv = manv;
        this.makh = makh;
        this.img = img;
    }

    public String getTendn() {
        return tendn;
    }

    public void setTendn(String tendn) {
        this.tendn = tendn;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getMacv() {
        return macv;
    }

    public void setMacv(String macv) {
        this.macv = macv;
    }

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public String getMakh() {
        return makh;
    }

    public void setMakh(String makh) {
        this.makh = makh;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
