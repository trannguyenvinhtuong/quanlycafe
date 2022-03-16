package com.example.doanquanlyquancf.khachhangactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.doanquanlyquancf.R;
import com.example.doanquanlyquancf.classdata.Server;

import java.util.HashMap;
import java.util.Map;

public class DangKy extends AppCompatActivity {
    EditText hoten, diachi, email, sdt, namsinh, tendn, mk, xnmk;
    Button btndk;
    TextView linkdn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

           anhxa();
        btndk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ht = hoten.getText().toString().trim();
                String dc = diachi.getText().toString().trim();
                String em = email.getText().toString().trim();
                String p = sdt.getText().toString().trim();
                String ns = namsinh.getText().toString().trim();
                String us = tendn.getText().toString().trim();
                String pa = mk.getText().toString().trim();
                String xnpa = xnmk.getText().toString().trim();

                kiemtra(ht,dc,em,p,ns,us,pa,xnpa);
            }
        });

        linkdn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangKy.this,DangNhap.class);
                startActivity(intent);
            }
        });
    }

    private void kiemtra(String ht, String dc, String em, String p, String ns, String us, String pa, String xnpa) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(TextUtils.isEmpty(ht)){
            Toast.makeText(DangKy.this,"Không được để trống họ tên",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(dc)){
            Toast.makeText(DangKy.this,"Không được để trống địa chỉ",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(em)){
            Toast.makeText(DangKy.this,"Không được để trống email",Toast.LENGTH_LONG).show();
        }
        else if(!em.matches(emailPattern)){
            Toast.makeText(DangKy.this,"Email không đúng",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(p)){
            Toast.makeText(DangKy.this,"Không được để trống số điện thoại",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(ns)){
            Toast.makeText(DangKy.this,"Không được để trống năm sinh",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(us)){
            Toast.makeText(DangKy.this,"Không được để trống tên đăng nhập",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(pa)){
            Toast.makeText(DangKy.this,"Không được để trống mật khẩu",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(xnpa)){
            Toast.makeText(DangKy.this,"Không được để trống xác nhận mật khẩu",Toast.LENGTH_LONG).show();
        }
        else {
            if(!pa.equals(xnpa)){
                Toast.makeText(DangKy.this,"Xác nhận mật khẩu không đúng",Toast.LENGTH_LONG).show();
            }
            else {
                dangky(ht,dc,em,p,ns,us,pa,xnpa);
            }
        }
    }

    private void dangky(String ht, String dc, String em, String p, String ns, String us, String pa, String xnpa) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String urlkh = Server.duongdandkkh;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlkh,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(Integer.parseInt(response)>0){
                            RequestQueue queue = Volley.newRequestQueue(DangKy.this);
                            String urltk = Server.duongdandktk;
                            StringRequest request = new StringRequest(Request.Method.POST, urltk,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response2) {
                                            if(response2.equals("1")){
                                                Toast.makeText(DangKy.this,"Đăng ký thành công",Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(DangKy.this, DangNhap.class);
                                                startActivity(intent);
                                            }
                                            else {
                                                Toast.makeText(DangKy.this,"Lỗi",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(DangKy.this,error.toString(),Toast.LENGTH_LONG).show();
                                        }
                                    }){
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String,String>hs=new HashMap<String, String>();
                                    hs.put("TaiKhoan",us);
                                    hs.put("MatKhau",pa);
                                    hs.put("MaCV","KH");
                                    hs.put("MaNV","4");
                                    hs.put("MaKH",response);
                                    hs.put("IMG","user.png");
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
                        Toast.makeText(DangKy.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String>hashMap=new HashMap<String, String>();
                hashMap.put("TenKH",ht);
                hashMap.put("DiaChi",dc);
                hashMap.put("SDT",p);
                hashMap.put("Email",em);
                hashMap.put("NamSinh",ns);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void anhxa(){
        hoten=findViewById(R.id.dkhoten);
        diachi=findViewById(R.id.dkdc);
        email=findViewById(R.id.dkemail);
        sdt=findViewById(R.id.dksdt);
        namsinh=findViewById(R.id.dknamsinh);
        tendn=findViewById(R.id.dktendn);
        mk=findViewById(R.id.dkmk);
        xnmk=findViewById(R.id.dkxnmk);
        btndk=findViewById(R.id.btndk);
        linkdn=findViewById(R.id.linkdn);
    }

}