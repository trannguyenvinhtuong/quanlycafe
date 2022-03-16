package com.example.doanquanlyquancf.khachhang.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.doanquanlyquancf.classdata.Server;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThayDoiMKFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThayDoiMKFragment extends Fragment {
    TextView txtmkc, txtmkm, txtxnmk;
    Button btnxn;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThayDoiMKFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThayDoiMKFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThayDoiMKFragment newInstance(String param1, String param2) {
        ThayDoiMKFragment fragment = new ThayDoiMKFragment();
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
        View rootview =  inflater.inflate(R.layout.fragment_thay_doi_m_k, container, false);
        txtmkc = rootview.findViewById(R.id.txtmkc);
        txtmkm = rootview.findViewById(R.id.txtmkm);
        txtxnmk = rootview.findViewById(R.id.txtmkmxn);
        btnxn = rootview.findViewById(R.id.btndoimk);

        btnxn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String tk = KhachHangActivity.tks.getTendn();
               String mkc = txtmkc.getText().toString().trim();
               String mkm = txtmkm.getText().toString().trim();
               String xnmk = txtxnmk.getText().toString().trim();

               if(TextUtils.isEmpty(mkc)){
                   Toast.makeText(getActivity(),"Không để trống mật khẩu cũ", Toast.LENGTH_LONG).show();
               }
               else if(TextUtils.isEmpty(mkm)){
                   Toast.makeText(getActivity(),"Không để trống mật khẩu mới", Toast.LENGTH_LONG).show();
               }
               else if(TextUtils.isEmpty(xnmk)){
                   Toast.makeText(getActivity(),"Không để trống xác nhận mật khẩu", Toast.LENGTH_LONG).show();
               }
               else if(!mkc.equals(KhachHangActivity.tks.getMatkhau())){
                   Toast.makeText(getActivity(),"Mật khẩu cũ không đúng",Toast.LENGTH_LONG).show();
               }
               else if(!mkm.equals(xnmk)){
                   Toast.makeText(getActivity(),"Xác nhận mật khẩu không đúng",Toast.LENGTH_LONG).show();
               }
               else {
                   AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                   builder.setTitle("Xác nhận");
                   builder.setMessage("Bạn có chắc chắn?");
                   builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           thaydoimk(mkc,mkm,xnmk,tk);
                       }
                   });
                   builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           Toast.makeText(getActivity(),"Hủy",Toast.LENGTH_LONG).show();
                           dialog.cancel();
                       }
                   });
                   AlertDialog a=builder.create();
                   a.show();
               }
            }
        });


        return rootview;
    }

    private void thaydoimk(String mkc, String mkm, String xnmk, String tk) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = Server.duongdanupdatemk;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("1")){
                            Toast.makeText(getActivity(),"Thành công",Toast.LENGTH_LONG).show();
                            KhachHangActivity.khs = null;
                            KhachHangActivity.tks = null;
                            KhachHangActivity.ghs.clear();
                            Intent intent = new Intent(getActivity(), DangNhap.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(getActivity(),"Thất bại",Toast.LENGTH_LONG).show();
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
                hs.put("TK",tk);
                hs.put("MK",mkm);
                return hs;
            }
        };
        requestQueue.add(stringRequest);
    }
}