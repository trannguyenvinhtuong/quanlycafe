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
import com.example.doanquanlyquancf.classdata.SanPham;
import com.example.doanquanlyquancf.classdata.Server;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends ArrayAdapter<SanPham> {
    Context context;
    int layoutHT;
    ArrayList<SanPham> tds;

    public SanPhamAdapter(@NonNull Context context, int resource, ArrayList<SanPham> objects) {
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

        SanPham dangHT=tds.get(position);

        TextView lbten=convertView.findViewById(R.id.txttensp);
        TextView lbgia=convertView.findViewById(R.id.txtgia);
        ImageView hinh=convertView.findViewById(R.id.hinhsanpham);
        lbten.setText(dangHT.getTenMon());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        int gia=Integer.parseInt(dangHT.getGia());
        lbgia.setText("Giá "+decimalFormat.format(gia)+"VND");
        Picasso.get().load(Server.duongdananhsp +dangHT.getHinhAnh()).placeholder(R.drawable.user_draw).error(R.drawable.user_draw).into(hinh);
        hinh.setScaleType(ImageView.ScaleType.FIT_XY);

        return convertView;
    }

}
