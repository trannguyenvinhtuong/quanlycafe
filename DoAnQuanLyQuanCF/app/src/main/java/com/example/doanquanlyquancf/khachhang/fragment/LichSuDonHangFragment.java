package com.example.doanquanlyquancf.khachhang.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doanquanlyquancf.khachhangactivity.KhachHangActivity;
import com.example.doanquanlyquancf.R;
import com.example.doanquanlyquancf.kh.adapter.HoaDonAdapter;
import com.example.doanquanlyquancf.classdata.HoaDon;
import com.example.doanquanlyquancf.classdata.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LichSuDonHangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LichSuDonHangFragment extends Fragment {
    ListView lsthd;
    ArrayList<HoaDon>hds;
    HoaDonAdapter adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LichSuDonHangFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LichSuDonHangFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LichSuDonHangFragment newInstance(String param1, String param2) {
        LichSuDonHangFragment fragment = new LichSuDonHangFragment();
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
        View rootview = inflater.inflate(R.layout.fragment_lich_su_don_hang, container, false);
        lsthd = rootview.findViewById(R.id.lstttdh);
        hds = new ArrayList<HoaDon>();
        getdata();
        adapter=new HoaDonAdapter(getActivity(),R.layout.motttdh,hds);
        lsthd.setAdapter(adapter);
        lsthd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HoaDon hd = hds.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("HoaDon",hd);
                ChiTietHDFragment chiTietHDFragment = new ChiTietHDFragment();
                chiTietHDFragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame_container, chiTietHDFragment).commit();
            }
        });
        return rootview;
    }

    private void getdata() {
        String makh = KhachHangActivity.khs.getMakh();

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = Server.duongdangettthd;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("dl",response);
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String mahd = jsonObject.getString("MaHD");
                                String maban = jsonObject.getString("MaBan");
                                String manv = jsonObject.getString("MaNV");
                                String mak = jsonObject.getString("MaKH");
                                String ngaylap = jsonObject.getString("NgayLap");
                                String tongtien = jsonObject.getString("TongTien");
                                String trangthai = jsonObject.getString("TrangThai");
                                HoaDon d = new HoaDon(mahd,maban,manv,mak,ngaylap,tongtien,trangthai);
                                hds.add(d);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
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
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("MaKH",makh);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}