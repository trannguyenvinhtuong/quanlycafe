package com.example.doanquanlyquancf;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.example.doanquanlyquancf.classdata.Server;
import com.example.doanquanlyquancf.classdata.TaiKhoan;
import com.example.doanquanlyquancf.khachhangactivity.DangKy;
import com.example.doanquanlyquancf.khachhangactivity.KhachHangActivity;
import com.example.doanquanlyquancf.nhanvienactivity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DangNhap extends AppCompatActivity {
    EditText txttendn, txtmk;
    Button btndn;
    ArrayList<TaiKhoan>tks;
    CheckBox checkB;
    TextView linkdk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        txttendn=findViewById(R.id.txttendn);
        txtmk=findViewById(R.id.txtmatkhau);
        btndn=findViewById(R.id.btnlogin);
        checkB=findViewById(R.id.checkremeber);
        tks=new ArrayList<TaiKhoan>();

        btndn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=txttendn.getText().toString().trim();
                String pass=txtmk.getText().toString().trim();
                kiemtradn(user,pass);
            }
        });

        linkdk = findViewById(R.id.linkdk);
        linkdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhap.this, DangKy.class);
                startActivity(intent);
            }
        });
    }

    private void kiemtradn(String user, String pass) {
        RequestQueue requestQueue= Volley.newRequestQueue(this.getApplicationContext());
        String url= Server.duongdandangnhap;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.length()>2){
                            try {
                                JSONArray jsonArray=new JSONArray(response);
                                for (int i=0; i<jsonArray.length(); i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    String tk=jsonObject.getString("TK");
                                    String mk=jsonObject.getString("MK");
                                    String macv=jsonObject.getString("MaCV");
                                    String manv=jsonObject.getString("MaNV");
                                    String makh=jsonObject.getString("MaKH");
                                    String img=jsonObject.getString("IMG");
                                    TaiKhoan k=new TaiKhoan(tk,mk,macv,manv,makh,img);
                                    tks.add(k);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if(tks.get(0).getMacv().equals("KH")){
                                Intent intent=new Intent(DangNhap.this, KhachHangActivity.class);
                                intent.putExtra("TaiKhoan",tks.get(0));
                                startActivity(intent);
                            }
                            else if(tks.get(0).getMacv().equals("NVPV")){
                                Intent intent = new Intent(DangNhap.this, MainActivity.class);
                                intent.putExtra("TaiKhoan",tks.get(0));
                                startActivity(intent);
                            }
                            else if(tks.get(0).getMacv().equals("NVQL")){
                                Intent intent = new Intent(DangNhap.this, MainAdminActivity.class);
                                intent.putExtra("TaiKhoan",tks.get(0));
                                startActivity(intent);
                            }
                            else {
                                Log.d("Loi",tks.get(0).getMacv());
                            }
                        }
                        else {
                            Toast.makeText(DangNhap.this,"Sai tên đăng nhập hoặc mật khẩu",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Loi",error.toString());
                        Toast.makeText(DangNhap.this, error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String>hs=new HashMap<String, String>();
                hs.put("User",user);
                hs.put("Pass",pass);
                return hs;
            }
        };
        requestQueue.add(stringRequest);

    }
}