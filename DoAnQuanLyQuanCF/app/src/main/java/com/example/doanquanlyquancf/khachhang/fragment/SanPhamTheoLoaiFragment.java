package com.example.doanquanlyquancf.khachhang.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doanquanlyquancf.R;
import com.example.doanquanlyquancf.kh.adapter.SanPhamAdapter;
import com.example.doanquanlyquancf.classdata.Loai;
import com.example.doanquanlyquancf.classdata.SanPham;
import com.example.doanquanlyquancf.classdata.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SanPhamTheoLoaiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SanPhamTheoLoaiFragment extends Fragment {
    String ml="";
    ListView lstsptl;
    SanPhamAdapter adapter;
    ArrayList<SanPham>tds;
    TextView txttieude;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SanPhamTheoLoaiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SanPhamTheoLoaiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SanPhamTheoLoaiFragment newInstance(String param1, String param2) {
        SanPhamTheoLoaiFragment fragment = new SanPhamTheoLoaiFragment();
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
        View rootview=inflater.inflate(R.layout.fragment_san_pham_theo_loai, container, false);
        Loai l=(Loai)getArguments().getSerializable("sptheoloai");
        ml = l.getMaloai();

        lstsptl=rootview.findViewById(R.id.lstsptheoloai);
        txttieude=rootview.findViewById(R.id.tieude);
        txttieude.setText(l.getTenLoai());
        tds=new ArrayList<SanPham>();
        adapter=new SanPhamAdapter(getActivity(),R.layout.motmathang,tds);
        lstsptl.setAdapter(adapter);
        getdata();
        lstsptl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SanPham sp=tds.get(position);

                Bundle bundle = new Bundle();
                bundle.putSerializable("sanpham",sp);

                ChiTietSPFragment ctsp=new ChiTietSPFragment();

                ctsp.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame_container, ctsp).commit();
            }
        });
        return rootview;
    }

    private void getdata(){
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        String url= Server.duongdansptheoloai;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i=0; i<jsonArray.length();i++){
                                JSONObject obj=jsonArray.getJSONObject(i);
                                String mamon=obj.getString("MaMon");
                                String tenmon=obj.getString("TenMon");
                                String gia=obj.getString("Gia");
                                String anh=obj.getString("AnhMon");
                                String maloai=obj.getString("MaLoai");
                                SanPham m=new SanPham(mamon,tenmon,gia,anh,maloai);
                                tds.add(m);
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

                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String>hashMap=new HashMap<String, String>();
                hashMap.put("MaLoai",ml);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}