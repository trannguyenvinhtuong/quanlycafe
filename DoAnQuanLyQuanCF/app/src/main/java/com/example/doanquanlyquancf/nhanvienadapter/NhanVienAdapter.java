package com.example.doanquanlyquancf.nhanvienadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.doanquanlyquancf.NhanVien;
import com.example.doanquanlyquancf.R;

import java.util.List;

public class NhanVienAdapter extends ArrayAdapter<NhanVien> {

    public NhanVienAdapter(Context context, int resource, List<NhanVien> objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View row, ViewGroup parent) {
        View view = row;
        if (view==null) {
            LayoutInflater inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.nhanvien_item, null);
        }
        NhanVien nv = super.getItem(position);
        ((TextView) view.findViewById(R.id.tvMaNV)).setText(nv.getMaNV()+"");
        ((TextView) view.findViewById(R.id.tvDiaChi)).setText(nv.getDiaChi());
        ((TextView) view.findViewById(R.id.tvEmail)).setText(nv.getEmail());
        ((TextView) view.findViewById(R.id.tvHoTen)).setText(nv.getHoTen());
        ((TextView) view.findViewById(R.id.tvNgaySinh)).setText(nv.getNgaySinh());
        ((TextView) view.findViewById(R.id.tvSDT)).setText(nv.getSDT());
        return view;
    }
}
