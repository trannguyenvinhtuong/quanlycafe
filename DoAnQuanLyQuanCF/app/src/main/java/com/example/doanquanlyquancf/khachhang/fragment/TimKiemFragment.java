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
import android.widget.Button;
import android.widget.EditText;
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
import com.example.doanquanlyquancf.R;
import com.example.doanquanlyquancf.kh.adapter.SanPhamAdapter;
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
 * Use the {@link TimKiemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimKiemFragment extends Fragment {
    ListView lsttk;
    SanPhamAdapter adapter;
    TextView labeltb, labelkq;
    EditText txttk;
    Button btntk;
    ArrayList<SanPham>tds;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TimKiemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimKiemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimKiemFragment newInstance(String param1, String param2) {
        TimKiemFragment fragment = new TimKiemFragment();
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
        View rootview =  inflater.inflate(R.layout.fragment_tim_kiem, container, false);
        lsttk = rootview.findViewById(R.id.lsttk);
        labelkq = rootview.findViewById(R.id.labeltk);
        labeltb = rootview.findViewById(R.id.labeltbtk);
        btntk = rootview.findViewById(R.id.btntk);
        txttk = rootview.findViewById(R.id.txttk);

        labeltb.setVisibility(View.INVISIBLE);
        labelkq.setVisibility(View.INVISIBLE);


        btntk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tds = new ArrayList<SanPham>();
                String dulieu = txttk.getText().toString().trim();
                timdulieu(dulieu);
                adapter = new SanPhamAdapter(getActivity(),R.layout.motmathang,tds);
                lsttk.setAdapter(adapter);
            }
        });

        lsttk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    private void timdulieu(String dulieu) {
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity().getApplicationContext());
        String urlsp= Server.duongdantimkiemtheosp;
        String urlloai = Server.duongdantimkiemtheoloai;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlsp,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.d("ketqua",response);
//                        Log.d("dodai",Integer.toString(response.length()));
                        if(response.length()>2){
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
                            labelkq.setVisibility(View.VISIBLE);
                            labeltb.setVisibility(View.INVISIBLE);
                        }
                        else {
                            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                            StringRequest request = new StringRequest(Request.Method.POST, urlloai,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String tra) {
                                            Log.d("tra",tra);
                                            if(tra.length()>2){
                                                JSONArray jarray = null;
                                                try {
                                                    jarray = new JSONArray(tra);
                                                    for(int i=0; i<jarray.length();i++){
                                                        JSONObject obj=jarray.getJSONObject(i);
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
                                                labelkq.setVisibility(View.VISIBLE);
                                                labeltb.setVisibility(View.INVISIBLE);
                                            }
                                            else {
                                                labeltb.setVisibility(View.VISIBLE);
                                                labelkq.setVisibility(View.INVISIBLE);
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError loi) {
                                            Toast.makeText(getActivity(),loi.toString(),Toast.LENGTH_LONG).show();
                                        }
                                    }){
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String,String>hs=new HashMap<String, String>();
                                    hs.put("DuLieu",dulieu);
                                    return hs;
                                }
                            };
                            queue.add(request);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Log.d("Loi",error.toString());
                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String>hashMap = new HashMap<String, String>();
                hashMap.put("DuLieu",dulieu);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void cleardata(){

    }
}