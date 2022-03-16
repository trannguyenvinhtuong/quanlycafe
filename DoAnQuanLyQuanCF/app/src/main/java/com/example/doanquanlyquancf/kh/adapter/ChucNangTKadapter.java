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
import com.example.doanquanlyquancf.classdata.ChucNangTK;

import java.util.ArrayList;

public class ChucNangTKadapter extends ArrayAdapter<ChucNangTK> {
    Context context;
    int layoutht;
    ArrayList<ChucNangTK>chucNangTKS;

    public ChucNangTKadapter(@NonNull Context context, int resource, @NonNull ArrayList<ChucNangTK> objects) {
        super(context, resource, objects);
        this.context=context;
        this.layoutht=resource;
        this.chucNangTKS=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        convertView=inflater.inflate(layoutht,null);

        ImageView icon = convertView.findViewById(R.id.icontk);
        TextView labelten = convertView.findViewById(R.id.labeltencn);

        ChucNangTK danght = chucNangTKS.get(position);

        icon.setImageResource(danght.getIcon());
        labelten.setText(danght.getTenchucnang());

        return convertView;
    }
}
