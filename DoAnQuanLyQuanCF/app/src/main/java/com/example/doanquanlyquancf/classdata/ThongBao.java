package com.example.doanquanlyquancf.classdata;

import java.io.Serializable;

public class ThongBao implements Serializable {
    String tenthongbao;
    int icon;

    public ThongBao(String tenthongbao, int icon) {
        this.tenthongbao = tenthongbao;
        this.icon = icon;
    }

    public String getTenthongbao() {
        return tenthongbao;
    }

    public void setTenthongbao(String tenthongbao) {
        this.tenthongbao = tenthongbao;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
