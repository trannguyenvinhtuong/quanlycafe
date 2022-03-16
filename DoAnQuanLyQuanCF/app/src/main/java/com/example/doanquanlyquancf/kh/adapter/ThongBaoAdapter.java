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
import com.example.doanquanlyquancf.classdata.ThongBao;

import java.util.ArrayList;

public class ThongBaoAdapter extends ArrayAdapter<ThongBao> {
    Context context;
    int layoutht;
    ArrayList<ThongBao>tbs;

    public ThongBaoAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ThongBao> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutht = resource;
        this.tbs = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        convertView=inflater.inflate(layoutht,null);

        ThongBao danght = tbs.get(position);

        ImageView img = convertView.findViewById(R.id.icontb);
        TextView tb = convertView.findViewById(R.id.labeltb);

        img.setImageResource(danght.getIcon());
        tb.setText(danght.getTenthongbao());
        return convertView;
    }
}
