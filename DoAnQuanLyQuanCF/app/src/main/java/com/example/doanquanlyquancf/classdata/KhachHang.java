package com.example.doanquanlyquancf.classdata;

import java.io.Serializable;

public class KhachHang implements Serializable {
    String makh, tenkh, dc, sdt, email, ns;

    public KhachHang(String makh, String tenkh, String dc, String sdt, String email, String ns) {
        this.makh = makh;
        this.tenkh = tenkh;
        this.dc = dc;
        this.sdt = sdt;
        this.email = email;
        this.ns = ns;
    }

    public String getMakh() {
        return makh;
    }

    public void setMakh(String makh) {
        this.makh = makh;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNs() {
        return ns;
    }

    public void setNs(String ns) {
        this.ns = ns;
    }
}
