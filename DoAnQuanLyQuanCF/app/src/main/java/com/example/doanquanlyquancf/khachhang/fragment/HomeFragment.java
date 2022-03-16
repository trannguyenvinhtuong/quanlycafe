package com.example.doanquanlyquancf.khachhang.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.doanquanlyquancf.R;
import com.example.doanquanlyquancf.classdata.SanPham;
import com.example.doanquanlyquancf.kh.adapter.SanPhamAdapter;
import com.example.doanquanlyquancf.classdata.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    ListView lst;
    ArrayList<SanPham> tds;
    SanPhamAdapter adapter;
    ViewFlipper slide;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        lst = (ListView) rootView.findViewById(R.id.lstsp);
        slide = rootView.findViewById(R.id.slidehome);
        tds=new ArrayList<SanPham>();
        ActionViewFlipper();
        getdata();

        adapter=new SanPhamAdapter(getActivity(),R.layout.motmathang,tds);
        lst.setAdapter(adapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        return rootView;
    }



    private void getdata(){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = Server.duongdandatasanpham;
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_LONG).show();
                        for(int i=0; i<response.length();i++){
                            try {
                                JSONObject obj=response.getJSONObject(i);
                                String mamon=obj.getString("MaMon");
                                String tenmon=obj.getString("TenMon");
                                String gia=obj.getString("Gia");
                                String anh=obj.getString("AnhMon");
                                String maloai=obj.getString("MaLoai");
                                    SanPham m=new SanPham(mamon,tenmon,gia,anh,maloai);
                                    tds.add(m);

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
                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFlipper(){
        ArrayList<String>mang=new ArrayList<String>();
        mang.add(Server.duongdananhslide+"quangcao1.jpg");
        mang.add(Server.duongdananhslide+"quangcao2.jpg");
        mang.add(Server.duongdananhslide+"quangcao3.jpg");
        for (int i=0;i<mang.size();i++){
            ImageView imageView=new ImageView(getActivity().getApplicationContext());
            Picasso.get().load(mang.get(i)).placeholder(R.drawable.user_draw).error(R.drawable.user_draw).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            slide.addView(imageView);
        }
        slide.setFlipInterval(5000);
        slide.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.slight_slide);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.slight_out);
        slide.setInAnimation(animation_slide_in);
        slide.setOutAnimation(animation_slide_out);
    }
}