package com.example.doanquanlyquancf.khachhang.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doanquanlyquancf.DangNhap;
import com.example.doanquanlyquancf.khachhangactivity.KhachHangActivity;
import com.example.doanquanlyquancf.R;
import com.example.doanquanlyquancf.kh.adapter.ChucNangTKadapter;
import com.example.doanquanlyquancf.classdata.ChucNangTK;
import com.example.doanquanlyquancf.classdata.KhachHang;
import com.example.doanquanlyquancf.classdata.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaiKhoanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaiKhoanFragment extends Fragment {

    ListView lstcntk;
    TextView labeltentk, labeldiachi, labelsdt, labelns, labelemail;
    ArrayList<ChucNangTK>chucNangTKS;
    ChucNangTKadapter kadapter;
    ImageView imgtk;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TaiKhoanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaiKhoanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaiKhoanFragment newInstance(String param1, String param2) {
        TaiKhoanFragment fragment = new TaiKhoanFragment();
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
        View rootview=inflater.inflate(R.layout.fragment_tai_khoan, container, false);
        lstcntk=rootview.findViewById(R.id.lsttttk);
        labeltentk=rootview.findViewById(R.id.labeltentk);
        labeldiachi=rootview.findViewById(R.id.labeldiachitk);
        labelsdt=rootview.findViewById(R.id.labelsdttk);
        labelemail=rootview.findViewById(R.id.labelemail);
        labelns=rootview.findViewById(R.id.labelnamsinh);
        imgtk=rootview.findViewById(R.id.imgtk);

        Picasso.get().load(Server.duongdananhTK +KhachHangActivity.tks.getImg()).placeholder(R.drawable.user_draw).error(R.drawable.user_draw).into(imgtk);
        imgtk.setScaleType(ImageView.ScaleType.FIT_XY);
        loaddata();

        chucNangTKS = new ArrayList<ChucNangTK>();
        chucNangTKS.add(new ChucNangTK(R.drawable.outline_feed_24,"Lịch sử mua hàng"));
        chucNangTKS.add(new ChucNangTK(R.drawable.outline_receipt_24,"Thay đổi thông tin"));
        chucNangTKS.add(new ChucNangTK(R.drawable.outline_password_24,"Thay đổi mật khẩu"));
        chucNangTKS.add(new ChucNangTK(R.drawable.outline_logout_24,"Đăng xuất"));

        kadapter = new ChucNangTKadapter(getActivity(),R.layout.motthongtintk,chucNangTKS);
        lstcntk.setAdapter(kadapter);

        lstcntk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==3){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle("Xác nhận đăng xuất");
                    alertDialog.setMessage("Bạn có muốn đăng xuất?");
                    alertDialog.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            KhachHangActivity.ghs.clear();
                            KhachHangActivity.tks = null;
                            KhachHangActivity.khs = null;
                            KhachHangActivity.tthds = null;
                            KhachHangActivity.tbs.clear();
                            Intent intent = new Intent(getActivity(), DangNhap.class);
                            startActivity(intent);
                        }
                    });
                    alertDialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getActivity(),"Hủy",Toast.LENGTH_LONG).show();
                            dialog.cancel();
                        }
                    });
                    AlertDialog a=alertDialog.create();
                    a.show();
                }
                if(position==0){
                    LichSuDonHangFragment ls = new LichSuDonHangFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_container, ls).commit();
                }
                if(position==1){
                    ThayDoiTTFragment thaydoi = new ThayDoiTTFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_container, thaydoi).commit();
                }
                if(position==2){
                    ThayDoiMKFragment doi = new ThayDoiMKFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_container, doi).commit();
                }
            }
        });

        return rootview;
    }

    private void loaddata() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = Server.duongdankhachhang;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            labeltentk.setText(jsonObject.getString("TenKH"));
                            labeldiachi.setText(jsonObject.getString("DiaChi"));
                            labelsdt.setText(jsonObject.getString("SDT"));
                            labelemail.setText(jsonObject.getString("Email"));
                            labelns.setText(jsonObject.getString("NamSinh"));
                            KhachHangActivity.khs = new KhachHang(KhachHangActivity.tks.getMakh(),
                                    jsonObject.getString("TenKH"),jsonObject.getString("DiaChi"),
                                    jsonObject.getString("SDT"),jsonObject.getString("Email"),
                                    jsonObject.getString("NamSinh"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String>hashMap = new HashMap<String, String>();
                String ma = KhachHangActivity.tks.getMakh();
                hashMap.put("MaKH",ma);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);

    }
}