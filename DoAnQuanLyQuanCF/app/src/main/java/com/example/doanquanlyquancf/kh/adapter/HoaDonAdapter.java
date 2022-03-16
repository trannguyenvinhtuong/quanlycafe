package com.example.doanquanlyquancf.kh.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doanquanlyquancf.R;
import com.example.doanquanlyquancf.classdata.HoaDon;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HoaDonAdapter extends ArrayAdapter<HoaDon> {
    Context context;
    int layoutht;
    ArrayList<HoaDon>hds;

    public HoaDonAdapter(@NonNull Context context, int resource, @NonNull ArrayList<HoaDon> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutht = resource;
        this.hds = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        convertView=inflater.inflate(layoutht,null);

        TextView labelmhd = convertView.findViewById(R.id.tthdmahd);
        TextView labeltt = convertView.findViewById(R.id.tthdtrangthai);
        TextView labelmb = convertView.findViewById(R.id.tthdmaban);
        TextView labelngay = convertView.findViewById(R.id.tthdngaylap);
        TextView labeltongtien = convertView.findViewById(R.id.tthdtongtien);

        HoaDon danght = hds.get(position);

        labelmhd.setText("Mã hóa đơn: "+danght.getMaHD());
        labelmb.setText("Bàn: "+danght.getMaBan());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        int tong = Integer.parseInt(danght.getTongTien());
        labeltongtien.setText("Tổng tiền: "+decimalFormat.format(tong)+" VND");
        labelngay.setText("Ngày đặt "+danght.getNgayLap());

        if(danght.getTrangThai().equals("0")){
            labeltt.setText("Chưa thanh toán");
            labeltt.setTextColor(Color.parseColor("#FF0000"));
        }
        else if(danght.getTrangThai().equals("1")){
            labeltt.setText("Đã thanh toán");
            labeltt.setTextColor(Color.parseColor("#4CAF50"));
        }
        return convertView;
    }
}
