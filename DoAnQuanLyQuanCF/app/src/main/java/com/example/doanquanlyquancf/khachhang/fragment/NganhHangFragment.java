package com.example.doanquanlyquancf.khachhang.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.doanquanlyquancf.classdata.Loai;
import com.example.doanquanlyquancf.kh.adapter.LoaiAdapter;
import com.example.doanquanlyquancf.R;
import com.example.doanquanlyquancf.classdata.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NganhHangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NganhHangFragment extends Fragment {
    GridView gvloai;
    ArrayList<Loai> dsloai;
    LoaiAdapter adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NganhHangFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NganhHangFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NganhHangFragment newInstance(String param1, String param2) {
        NganhHangFragment fragment = new NganhHangFragment();
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
        View rootview = inflater.inflate(R.layout.fragment_nganh_hang, container, false);
        gvloai = rootview.findViewById(R.id.gridloai);
        dsloai = new ArrayList<Loai>();
        loaddata();
        adapter = new LoaiAdapter(getActivity(), R.layout.motnganhhang, dsloai);
        gvloai.setAdapter(adapter);

        gvloai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Loai l = dsloai.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("sptheoloai",l);

                SanPhamTheoLoaiFragment sptl = new SanPhamTheoLoaiFragment();
                sptl.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame_container, sptl).commit();

            }
        });

        return rootview;
    }

    private void loaddata() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = Server.duongdannganhhang;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_LONG).show();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                String maloai = obj.getString("MaLoai");
                                String tenloai = obj.getString("TenLoai");
                                String anhloai = obj.getString("AnhLoai");
                                Loai l = new Loai(maloai, tenloai, anhloai);
                                dsloai.add(l);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }
}