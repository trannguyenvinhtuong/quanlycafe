package com.example.doanquanlyquancf.kh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doanquanlyquancf.R;
import com.example.doanquanlyquancf.classdata.CTHD;
import com.example.doanquanlyquancf.classdata.Server;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CTHDAdapter extends ArrayAdapter<CTHD> {
    Context context;
    int layoutHT;
    ArrayList<CTHD> tds;

    public CTHDAdapter(@NonNull Context context, int resource, @NonNull ArrayList<CTHD> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutHT = resource;
        this.tds = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= LayoutInflater.from(context);
        convertView=inflater.inflate(layoutHT,null);

        ImageView img = convertView.findViewById(R.id.imgctdh);
        TextView tenmon = convertView.findViewById(R.id.ctdhtenmon);
        TextView yeucau = convertView.findViewById(R.id.ctdhyecau);
        TextView soluong = convertView.findViewById(R.id.ctdhsoluong);
        TextView thanhtien = convertView.findViewById(R.id.ctdhthanhtien);

        CTHD hd = tds.get(position);

        Picasso.get().load(Server.duongdananhsp+hd.getAnh()).placeholder(R.drawable.user_draw).error(R.drawable.user_draw).into(img);
        tenmon.setText(hd.getTenmon());
        yeucau.setText("Yêu cầu: "+hd.getYeuCau());
        soluong.setText("Số lượng: "+hd.getSoLuong());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        int tien = Integer.parseInt(hd.getThanhTien());
        thanhtien.setText("Thành tiền: "+decimalFormat.format(tien)+" VND");

        return convertView;
    }
}
