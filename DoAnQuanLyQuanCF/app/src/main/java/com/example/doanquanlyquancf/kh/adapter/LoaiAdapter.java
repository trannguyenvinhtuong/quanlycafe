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
import com.example.doanquanlyquancf.classdata.Loai;
import com.example.doanquanlyquancf.classdata.Server;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiAdapter extends ArrayAdapter<Loai> {
    Context context;
    int layoutHTloai;
    ArrayList<Loai> loais;

    public LoaiAdapter(@NonNull Context context, int resource, ArrayList<Loai> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutHTloai = resource;
        this.loais = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= LayoutInflater.from(context);
        convertView=inflater.inflate(layoutHTloai,null);

        Loai danght=loais.get(position);
        ImageView anh=convertView.findViewById(R.id.anhloai);
        TextView lbten=convertView.findViewById(R.id.tenloai);

        lbten.setText(danght.getTenLoai());
        Picasso.get().load(Server.duongdananhloai+danght.getAnhLoai()).placeholder(R.drawable.user_draw).error(R.drawable.user_draw).into(anh);
        anh.setScaleType(ImageView.ScaleType.FIT_XY);
        return convertView;
    }

}
