package com.example.doanquanlyquancf.kh.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doanquanlyquancf.R;
import com.example.doanquanlyquancf.classdata.GioHang;
import com.example.doanquanlyquancf.classdata.Server;
import com.example.doanquanlyquancf.khachhang.fragment.GioHangFragment;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends ArrayAdapter<GioHang> {
    Context context;
    int layoutht;
    ArrayList<GioHang>ghs;

    public GioHangAdapter(@NonNull Context context, int resource, @NonNull ArrayList<GioHang> objects) {
        super(context, resource, objects);
        this.context=context;
        this.layoutht=resource;
        this.ghs=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        convertView=inflater.inflate(layoutht,null);

        TextView txttensp=convertView.findViewById(R.id.ghtensp);
        TextView txtgias=convertView.findViewById(R.id.ghgiasp);
        ImageView img = convertView.findViewById(R.id.ghanhsp);
        TextView labeltp=convertView.findViewById(R.id.ghtopping);
        TextView labelda=convertView.findViewById(R.id.ghda);
        TextView labelduong=convertView.findViewById(R.id.ghduong);
        Button cong=convertView.findViewById(R.id.btncong);
        Button value=convertView.findViewById(R.id.btngiatri);
        Button tru=convertView.findViewById(R.id.btntru);


        GioHang danght=ghs.get(position);

        txttensp.setText(danght.getTenMon());
        int gia = Integer.parseInt(danght.getGia());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        txtgias.setText("Gía "+decimalFormat.format(gia)+" VND");
        Picasso.get().load(Server.duongdananhsp+danght.getHinhAnh()).placeholder(R.drawable.user_draw).error(R.drawable.user_draw).into(img);
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        labeltp.setText(danght.getTopping());
        labelda.setText(danght.getDa()+" đá");
        labelduong.setText(danght.getDuong()+" đường");
        value.setText(Integer.toString(danght.getSoluong()));
        value.setBackgroundColor(Color.WHITE);
        int sl=Integer.parseInt(value.getText().toString());
        if(sl>=10){
            cong.setVisibility(View.INVISIBLE);
            tru.setVisibility(View.VISIBLE);
        }
        else if(sl<=1){
            cong.setVisibility(View.VISIBLE);
            tru.setVisibility(View.INVISIBLE);
        }
        else if(sl>1){
            cong.setVisibility(View.VISIBLE);
            tru.setVisibility(View.VISIBLE);
        }

        cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slm = Integer.parseInt(value.getText().toString())+1;
                int slc = danght.getSoluong();
                int giacu = Integer.parseInt(danght.getGia());

                ghs.get(position).setSoluong(slm);
                int giamoi = (giacu*slm)/slc;
                ghs.get(position).setGia(Integer.toString(giamoi));
                value.setText(Integer.toString(slm));
                txtgias.setText("Gía "+decimalFormat.format(giamoi)+" VND");
                GioHangFragment.tongtien();
                if(slm>9){
                    cong.setVisibility(View.INVISIBLE);
                    tru.setVisibility(View.VISIBLE);
                }
                else {
                    cong.setVisibility(View.VISIBLE);
                    tru.setVisibility(View.VISIBLE);
                }
            }
        });

        tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slm = Integer.parseInt(value.getText().toString())-1;
                int slc = danght.getSoluong();
                int giacu = Integer.parseInt(danght.getGia());

                ghs.get(position).setSoluong(slm);
                int giamoi = (giacu*slm)/slc;
                ghs.get(position).setGia(Integer.toString(giamoi));
                value.setText(Integer.toString(slm));
                txtgias.setText("Gía "+decimalFormat.format(giamoi)+" VND");
                GioHangFragment.tongtien();
                if(slm<2){
                    cong.setVisibility(View.VISIBLE);
                    tru.setVisibility(View.INVISIBLE);
                }
                else {
                    cong.setVisibility(View.VISIBLE);
                    tru.setVisibility(View.VISIBLE);
                }
            }
        });

        return convertView;

    }
}
