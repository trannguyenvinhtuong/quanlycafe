package com.example.doanquanlyquancf.nhanvienactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.doanquanlyquancf.Database;
import com.example.doanquanlyquancf.R;
import com.example.doanquanlyquancf.classdata.Server;
import com.example.doanquanlyquancf.classdata.ThongBao;
import com.example.doanquanlyquancf.classdata.TrangThaiHD;
import com.example.doanquanlyquancf.khachhangactivity.KhachHangActivity;
import com.example.doanquanlyquancf.nhanvien.model.ThucDon;
import com.example.doanquanlyquancf.nhanvienadapter.ThucDonAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThanhToanActivity extends AppCompatActivity {

    int MaBan;
    ListView lvHoaDon;
    ThucDonAdapter adapter;
    List<ThucDon> HoaDonList;
    TextView tvban,tvTong;
    Button btnThanhToan,btnHuy;
    SQLiteDatabase database;
    Toolbar toolbarThanhtoan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        toolbarThanhtoan = findViewById(R.id.toolbar);
        ActionToolbar();

        lvHoaDon= findViewById(R.id.listViewThanhToan);
        tvban= findViewById(R.id.textViewBanThanhToan);
        tvTong= findViewById(R.id.textViewTong);
        btnHuy= findViewById(R.id.buttonHuy);
        btnThanhToan= findViewById(R.id.buttonThanhToan);

        database= Database.TaoDatabase(this, MainActivity.DATABASE_NAME);

        HoaDonList=new ArrayList<>();
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        MaBan=bundle.getInt("MABAN");
        tvban.setText(MaBan+"");
        HoaDonList= (List<ThucDon>) bundle.getSerializable("HOADON");
        adapter=new ThucDonAdapter(this,R.layout.mon_item,HoaDonList);
        lvHoaDon.setAdapter(adapter);

        int tong=0;
        for(int i=0;i<HoaDonList.size();i++){
            tong+=Integer.valueOf(HoaDonList.get(i).getGia());
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvTong.setText(decimalFormat.format(tong) + " VNĐ");
        final int Tong = tong;
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(ThanhToanActivity.this);
                dialog.setTitle("Xác nhận");
                dialog.setMessage("Hóa đơn này sẽ được thanh toán!!!");
                dialog.setIcon(R.drawable.canhbao);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ghidulieu(Tong);

                    }
                });
                dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void ghidulieu(int tongtien) {
        final String ngay=getTime();
        final String tong = Integer.toString(tongtien);
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest request=new StringRequest(Request.Method.POST, Server.duongdanposthoadon,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String madonhang) {
                        Log.d("madonhang",madonhang);
                        if(Integer.parseInt(madonhang)>0){
                            RequestQueue queue=Volley.newRequestQueue(ThanhToanActivity.this);
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanpostcthd,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if(response.equals("1")){
                                                Toast.makeText(ThanhToanActivity.this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
                                                Intent intent1 = new Intent(ThanhToanActivity.this, MainActivity.class);
                                                Bundle bundle1=new Bundle();
                                                intent1.putExtras(bundle1);
                                                startActivity(intent1);
                                            }
                                            else {
                                                Toast.makeText(ThanhToanActivity.this,"Lỗi",Toast.LENGTH_LONG).show();
                                            }
                                            Log.d("json",response);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(ThanhToanActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                                        }
                                    }){
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    JSONArray jsonArray=new JSONArray();
                                    for(int i=0; i<HoaDonList.size();i++){
                                        JSONObject jsonObject=new JSONObject();
                                        try {
                                            String yeucau="";
                                            jsonObject.put("MaHD",madonhang);
                                            jsonObject.put("MaMon",HoaDonList.get(i).getMaMon());
                                            jsonObject.put("YeuCau",yeucau);
                                            jsonObject.put("SoLuong",Integer.toString(1));
                                            jsonObject.put("ThanhTien",HoaDonList.get(i).getGia());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        jsonArray.put(jsonObject);
                                    }
                                    HashMap<String,String> hashMap=new HashMap<String,String>();
                                    hashMap.put("json",jsonArray.toString());
                                    return hashMap;
                                }
                            };
                            queue.add(stringRequest);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ThanhToanActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String maban=Integer.toString(MaBan);
                String manv= MainActivity.tk.getManv();
                String makh= "1";
                HashMap<String,String>hashMap=new HashMap<String,String>();
                if(TextUtils.isDigitsOnly(maban)){
                    hashMap.put("maban",new String(maban));
                    hashMap.put("manv",manv);
                    hashMap.put("makh",makh);
                    hashMap.put("ngaylap",new String(ngay));
                    hashMap.put("tongtien", new String(tong));
                    hashMap.put("trangthai",new String("0"));
                    Log.d("makh",makh);
                }
                else
                {
                    Toast.makeText(ThanhToanActivity.this,"Dữ liệu phải là số",Toast.LENGTH_LONG).show();
                }
                return hashMap;
            }
        };
        requestQueue.add(request);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarThanhtoan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarThanhtoan.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String getTime(){
        long millis=System.currentTimeMillis();
        Date date = new Date(millis);
        return date.toString();
    }

}
