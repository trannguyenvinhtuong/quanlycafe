package com.example.doanquanlyquancf.khachhang.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.doanquanlyquancf.classdata.Server;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThayDoiTTFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThayDoiTTFragment extends Fragment {

    EditText txtten, txtdc, txtsdt, txtemail, txtns;
    Button btnsub;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThayDoiTTFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThayDoiTTFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThayDoiTTFragment newInstance(String param1, String param2) {
        ThayDoiTTFragment fragment = new ThayDoiTTFragment();
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
        View rootview = inflater.inflate(R.layout.fragment_thay_doi_t_t, container, false);
        txtten = rootview.findViewById(R.id.txtttten);
        txtdc = rootview.findViewById(R.id.txtttdc);
        txtsdt = rootview.findViewById(R.id.txtttsdt);
        txtemail = rootview.findViewById(R.id.txtttem);
        txtns = rootview.findViewById(R.id.txtttns);
        btnsub = rootview.findViewById(R.id.btnthaydoi);

        btnsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = "";
                String dc = "";
                String sdt = "";
                String email = "";
                String ns = "";
                if(txtten.getText().toString().trim().equals(""))
                {
                    ten = KhachHangActivity.khs.getTenkh();
                }
                else
                {
                    ten = txtten.getText().toString().trim();
                }
                if(txtdc.getText().toString().trim().equals("")){
                    dc = KhachHangActivity.khs.getDc();
                }
                else {
                    dc = txtdc.getText().toString().trim();
                }
                if(txtsdt.getText().toString().trim().equals("")){
                    sdt = KhachHangActivity.khs.getSdt();
                }
                else {
                    sdt = txtsdt.getText().toString().trim();
                }
                if(txtemail.getText().toString().trim().equals("")){
                    email = KhachHangActivity.khs.getEmail();
                }
                else {
                    email = txtemail.getText().toString().trim();
                }
                if (txtns.getText().toString().trim().equals("")){
                    ns = KhachHangActivity.khs.getNs();
                }
                else {
                    ns = txtns.getText().toString().trim();
                }
                thaymau(ten,dc,sdt,email,ns);
            }
        });

        return rootview;
    }

    private void thaymau(String ten, String dc, String sdt, String email, String ns) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có chắc chắn?");
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(),"Hủy",Toast.LENGTH_LONG).show();
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                String url = Server.duongdanupdatekh;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("1")){
                                    Toast.makeText(getActivity(),"Thành công",Toast.LENGTH_LONG).show();
                                    TaiKhoanFragment tk = new TaiKhoanFragment();
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    fragmentManager.beginTransaction().replace(R.id.frame_container, tk).commit();
                                }
                                else {
                                    Toast.makeText(getActivity(),"Hãy thử lại sau",Toast.LENGTH_LONG).show();
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
                        HashMap<String,String>hs = new HashMap<String, String>();
                        hs.put("MaKH", KhachHangActivity.tks.getMakh());
                        hs.put("TenKH",ten);
                        hs.put("DiaChi",dc);
                        hs.put("SDT",sdt);
                        hs.put("Email",email);
                        hs.put("NamSinh",ns);
                        return hs;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
        AlertDialog a=builder.create();
        a.show();

    }
}