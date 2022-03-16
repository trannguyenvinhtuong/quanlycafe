package com.example.doanquanlyquancf.khachhang.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanquanlyquancf.khachhangactivity.KhachHangActivity;
import com.example.doanquanlyquancf.R;
import com.example.doanquanlyquancf.classdata.SanPham;
import com.example.doanquanlyquancf.classdata.GioHang;
import com.example.doanquanlyquancf.classdata.Server;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChiTietSPFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChiTietSPFragment extends Fragment {

    TextView txtten, txtgia;
    Spinner stopping, sda, sduong, ssoluong;
    Button btnthemgh;
    ImageView img;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChiTietSPFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChiTietSPFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChiTietSPFragment newInstance(String param1, String param2) {
        ChiTietSPFragment fragment = new ChiTietSPFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview=inflater.inflate(R.layout.fragment_chi_tiet_s_p, container, false);


        SanPham sp = (SanPham) getArguments().getSerializable("sanpham");
        txtten=rootview.findViewById(R.id.tttensp);
        txtgia=rootview.findViewById(R.id.ttgia);
        btnthemgh=rootview.findViewById(R.id.btnmua);

        txtten.setText(sp.getTenMon());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        int gia = Integer.parseInt(sp.getGia());
        txtgia.setText("Gía "+decimalFormat.format(gia)+" VND");

        stopping=rootview.findViewById(R.id.spinnertopping);
        sda=rootview.findViewById(R.id.spinnerda);
        sduong=rootview.findViewById(R.id.spinnerduong);
        img=rootview.findViewById(R.id.ttanhsp);
        ssoluong=rootview.findViewById(R.id.spinnersoluong);

        Picasso.get().load(Server.duongdananhsp+sp.getHinhAnh()).placeholder(R.drawable.user_draw).error(R.drawable.user_draw).into(img);
        img.setScaleType(ImageView.ScaleType.FIT_XY);

        if(sp.getMaLoai().equals("3")||sp.getMaLoai().equals("8")){
            stopping.setVisibility(View.INVISIBLE);
            sda.setVisibility(View.INVISIBLE);
            sduong.setVisibility(View.INVISIBLE);
            TextView lbtopping, lbda, lbduong;
            lbtopping = rootview.findViewById(R.id.txttopping);
            lbda = rootview.findViewById(R.id.txtda);
            lbduong = rootview.findViewById(R.id.txtduong);
            lbtopping.setVisibility(View.INVISIBLE);
            lbda.setVisibility(View.INVISIBLE);
            lbduong.setVisibility(View.INVISIBLE);
        }

        catchspinnertopping();
        catchspinnerda();
        catchspinnerduong();
        catchspinnersoluong();
        
        btnthemgh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(KhachHangActivity.ghs.size()>0){
                    boolean exists=false;
                    int sl = Integer.parseInt(ssoluong.getSelectedItem().toString());
                    for(int i=0; i<KhachHangActivity.ghs.size();i++){
                        if(KhachHangActivity.ghs.get(i).getMaMon().equals(sp.getMaMon())){
                            KhachHangActivity.ghs.get(i).setSoluong(KhachHangActivity.ghs.get(i).getSoluong()+sl);
                            int g=Integer.parseInt(sp.getGia()) * KhachHangActivity.ghs.get(i).getSoluong();
                            KhachHangActivity.ghs.get(i).setGia(Integer.toString(g));
                            exists = true;
                            Toast.makeText(getActivity(),"Thêm thành công",Toast.LENGTH_LONG).show();
                        }
                    }
                    if(exists==false){
                        int slm = Integer.parseInt(ssoluong.getSelectedItem().toString());
                        String tp=stopping.getSelectedItem().toString();
                        String da=sda.getSelectedItem().toString();
                        String duong=sduong.getSelectedItem().toString();
                        int gia=slm*Integer.parseInt(sp.getGia());
                        KhachHangActivity.ghs.add(new GioHang(sp.getMaMon(),sp.getTenMon(),Integer.toString(gia),sp.getHinhAnh(),sp.getMaLoai(),tp,da,duong,slm));
                        Toast.makeText(getActivity(),"Thêm thành công",Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    int sl = Integer.parseInt(ssoluong.getSelectedItem().toString());
                    int gia = sl*Integer.parseInt(sp.getGia());
                    String tp=stopping.getSelectedItem().toString();
                    String da=sda.getSelectedItem().toString();
                    String duong=sduong.getSelectedItem().toString();
                    KhachHangActivity.ghs.add(new GioHang(sp.getMaMon(),sp.getTenMon(),Integer.toString(gia),sp.getHinhAnh(),sp.getMaLoai(),tp,da,duong,sl));
                    Toast.makeText(getActivity(),"Thêm thành công",Toast.LENGTH_LONG).show();
                }
            }
        });

        return rootview;
    }

    private void catchspinnertopping(){
        String[]topping=new String[]{"Thạch rau câu","Thạch trái cây","Thạch dừa","Phô mai"};
        ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,topping);
        stopping.setAdapter(arrayAdapter);
    }
    
    private void catchspinnerda(){
        String[]da=new String[]{"100%","75%","50%","25%"};
        ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,da);
        sda.setAdapter(arrayAdapter);
    }

    private void catchspinnerduong(){
        String[]duong=new String[]{"100%","75%","50%","25%"};
        ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,duong);
        sduong.setAdapter(arrayAdapter);
    }

    private void catchspinnersoluong(){
        String[]soluong=new String[]{"1","2","3","4","5","6","7","8","9","10"};
        ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,soluong);
        ssoluong.setAdapter(arrayAdapter);
    }
}