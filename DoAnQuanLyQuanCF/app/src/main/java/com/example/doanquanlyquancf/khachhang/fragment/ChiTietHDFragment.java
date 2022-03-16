package com.example.doanquanlyquancf.khachhang.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doanquanlyquancf.R;
import com.example.doanquanlyquancf.kh.adapter.CTHDAdapter;
import com.example.doanquanlyquancf.classdata.CTHD;
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
 * Use the {@link ChiTietHDFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChiTietHDFragment extends Fragment {
    ListView lstht;
    ArrayList<CTHD>cthds;
    CTHDAdapter adapter;
    HoaDon hd;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChiTietHDFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChiTietHDFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChiTietHDFragment newInstance(String param1, String param2) {
        ChiTietHDFragment fragment = new ChiTietHDFragment();
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
        View rootview = inflater.inflate(R.layout.fragment_chi_tiet_h_d, container, false);
        hd = (HoaDon) getArguments().getSerializable("HoaDon");
        lstht = rootview.findViewById(R.id.lstcthd);
        cthds = new ArrayList<CTHD>();
        getdata();
        adapter = new CTHDAdapter(getActivity(),R.layout.motcthd,cthds);
        lstht.setAdapter(adapter);
        return rootview;
    }

    private void getdata() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = Server.duongdangetcthd;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("ID");
                                String mahd = jsonObject.getString("MaHD");
                                String mamon = jsonObject.getString("MaMon");
                                String yeucau = jsonObject.getString("YeuCau");
                                String soluong = jsonObject.getString("SoLuong");
                                String thanhtien = jsonObject.getString("ThanhTien");
                                String anh = jsonObject.getString("AnhMon");
                                String tenmon = jsonObject.getString("TenMon");
                                CTHD ct = new CTHD(id,mahd,mamon,yeucau,soluong,thanhtien,anh,tenmon);
                                cthds.add(ct);
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
                String ma = hd.getMaHD();
                HashMap<String,String> hs = new HashMap<String, String>();
                hs.put("MaHD",ma);
                return hs;
            }
        };
        requestQueue.add(stringRequest);
    }
}