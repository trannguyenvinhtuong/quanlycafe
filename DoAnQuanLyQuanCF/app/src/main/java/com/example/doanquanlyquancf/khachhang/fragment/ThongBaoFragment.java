package com.example.doanquanlyquancf.khachhang.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.doanquanlyquancf.khachhangactivity.KhachHangActivity;
import com.example.doanquanlyquancf.R;
import com.example.doanquanlyquancf.kh.adapter.ThongBaoAdapter;
import com.example.doanquanlyquancf.classdata.Server;
import com.example.doanquanlyquancf.classdata.ThongBao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThongBaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThongBaoFragment extends Fragment {
    ListView lstht;
    TextView labeltb;
    ThongBaoAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThongBaoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThongBaoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThongBaoFragment newInstance(String param1, String param2) {
        ThongBaoFragment fragment = new ThongBaoFragment();
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
        View rootview = inflater.inflate(R.layout.fragment_thong_bao, container, false);
        lstht = rootview.findViewById(R.id.lsttb);
        labeltb = rootview.findViewById(R.id.tblsttb);
        if(KhachHangActivity.tbs.size()<=0){
            labeltb.setVisibility(View.VISIBLE);
            lstht.setVisibility(View.INVISIBLE);
        }
        else {
            kiemtratinhtrang();
            adapter = new ThongBaoAdapter(getActivity(),R.layout.motthongbao, KhachHangActivity.tbs);
            lstht.setAdapter(adapter);
            labeltb.setVisibility(View.INVISIBLE);
        }
        return rootview;
    }

    private void kiemtratinhtrang() {
        for(int i=0;i<KhachHangActivity.tthds.size();i++){
            String mahd = KhachHangActivity.tthds.get(i).getMahd();
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String url = Server.duongdangettinhtrang;
            int finalI = i;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String strangthai = jsonObject.getString("TinhTrang");
                                int tt = Integer.parseInt(strangthai);
                                if(tt == KhachHangActivity.tthds.get(finalI).getTrangthai()){

                                }
                                else if(tt != KhachHangActivity.tthds.get(finalI).getTrangthai()){
                                    KhachHangActivity.tthds.get(finalI).setTrangthai(tt);
                                    KhachHangActivity.tbs.add(new ThongBao("Đơn hàng " +mahd+" đã thanh toán",R.drawable.outline_done_all_24));
                                }
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
                    HashMap<String,String> hs = new HashMap<String, String>();
                    hs.put("MaHD",mahd);
                    return hs;
                }
            };
            requestQueue.add(stringRequest);
        }

    }
}