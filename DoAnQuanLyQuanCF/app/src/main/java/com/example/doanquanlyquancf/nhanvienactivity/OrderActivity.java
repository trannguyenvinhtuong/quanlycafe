package com.example.doanquanlyquancf.nhanvienactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doanquanlyquancf.Database;
import com.example.doanquanlyquancf.R;
import com.example.doanquanlyquancf.classdata.Server;
import com.example.doanquanlyquancf.nhanvien.model.ThucDon;
import com.example.doanquanlyquancf.nhanvienadapter.ThucDonAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {

    int MaBan,TongHoaDonDau;
    ListView lvHoaDon;
    Button btnThem,btnThanhToan,btnThemOrder,btnHuy;
    Spinner spinnerLoai,spinnerHuong,spinnerTopping,spinnerDoAn;
    ThucDonAdapter adapter;
    TextView tvBan;
    SQLiteDatabase database;
    ArrayList<String> LoaiList,MonList,ToppingList,DoAnList;
    List<ThucDon> ThucdonList,HoaDonList;
    Toolbar toolbarOrder;
    ArrayAdapter spinnerMonAdapter;
    ArrayAdapter spinnerToppingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        toolbarOrder = findViewById(R.id.toolbar);
        ActionToolbar();
        database= Database.TaoDatabase(OrderActivity.this, MainActivity.DATABASE_NAME);

        lvHoaDon= findViewById(R.id.listViewHoaDon);
        btnThanhToan= findViewById(R.id.buttonThanhToan);
        btnThem= findViewById(R.id.buttonThem);
        tvBan= findViewById(R.id.textViewBan);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        MaBan=bundle.getInt("MABAN");
        tvBan.setText(MaBan+"");

        ThucdonList=new ArrayList<>();
        HoaDonList=new ArrayList<>();
        getDataHoaDon(MaBan);
        TongHoaDonDau=HoaDonList.size();
        adapter=new ThucDonAdapter(OrderActivity.this,R.layout.mon_item,HoaDonList);
        lvHoaDon.setAdapter(adapter);
        if (HoaDonList.size()==0){
            btnThanhToan.setEnabled(false);
        }

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThem();
            }
        });

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentThanhToan=new Intent(OrderActivity.this, ThanhToanActivity.class);
                Bundle bundlett=new Bundle();
                bundlett.putInt("MABAN",MaBan);
                bundlett.putSerializable("HOADON", (Serializable) HoaDonList);
                intentThanhToan.putExtras(bundlett);
                startActivity(intentThanhToan);
            }
        });
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarOrder);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarOrder.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getDataHoaDon(int i) {
//        Cursor cursor=database.rawQuery("SELECT t.MaMon,t.TenMon,t.Gia,t.AnhMon FROM ThucDon t,TamTinh tt Where t.MaMon=tt.MaMon AND tt.MaBan="+i,null);
//        while (cursor.moveToNext()){
//            ThucDon td=new ThucDon();
//            td.setMaMon(cursor.getInt(0));
//            td.setTenMon(cursor.getString(1));
//            td.setGia(cursor.getString(2));
//            td.setHinhAnh(cursor.getInt(3));
//            HoaDonList.add(td);
//        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Server.duongdangetdatahd;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int j=0; j<jsonArray.length();j++){
                                JSONObject jsonObject = jsonArray.getJSONObject(j);
                                ThucDon td=new ThucDon();
                                int mam = Integer.parseInt(jsonObject.getString("MaMon"));
                                td.setMaMon(mam);
                                td.setTenMon(jsonObject.getString("TenMon"));
                                td.setGia(jsonObject.getString("Gia"));
                                td.setHinhAnh(1);
                                HoaDonList.add(td);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrderActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String>hs=new HashMap<String, String>();
                hs.put("MaBan",Integer.toString(i));
                return hs;
            }
        };
        requestQueue.add(stringRequest);


    }

    private void DialogThem() {
        Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.them_mon);
        dialog.setCanceledOnTouchOutside(false);
        int w = (int)(getResources().getDisplayMetrics().widthPixels*0.9);
        int h = (int)(getResources().getDisplayMetrics().heightPixels*0.9);
        dialog.getWindow().setLayout(w,h);


        spinnerLoai= dialog.findViewById(R.id.spinnerLoai);
        spinnerHuong= dialog.findViewById(R.id.spinnerHuong);
        spinnerTopping= dialog.findViewById(R.id.spinnerThem);
        spinnerDoAn= dialog.findViewById(R.id.spinnerDoAn);
        btnThemOrder= dialog.findViewById(R.id.buttonThemOrder);
        btnHuy= dialog.findViewById(R.id.buttonHuyOrder);

        LoaiList=new ArrayList<>();
        ArrayAdapter spinnerLoaiAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,LoaiList);
        spinnerLoaiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getdataLoai();
        spinnerLoai.setAdapter(spinnerLoaiAdapter);

        ToppingList =new ArrayList<>();
        spinnerToppingAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,ToppingList);
        getdataTopping();
        spinnerTopping.setAdapter(spinnerToppingAdapter);

        DoAnList=new ArrayList<>();
        ArrayAdapter spinnerDoAnAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,DoAnList);
        getdataDoAn();
        spinnerDoAn.setAdapter(spinnerDoAnAdapter);

        MonList=new ArrayList<>();
        MonList.add("Chọn Loại....");

        spinnerLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    MonList.clear();
                    getdataMon(position);
                    spinnerMonAdapter=new ArrayAdapter(OrderActivity.this,android.R.layout.simple_spinner_item,MonList);

                    spinnerHuong.setAdapter(spinnerMonAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        spinnerHuong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                parent.setSelection(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnThemOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinnerTopping.getSelectedItemPosition()!=0||spinnerDoAn.getSelectedItemPosition()!=0||spinnerLoai.getSelectedItemPosition()!=0){
                    if(spinnerLoai.getSelectedItemPosition()!=0){
                        for(int i=0;i<ThucdonList.size();i++){
                            if(spinnerHuong.getSelectedItem().equals(ThucdonList.get(i).getTenMon())){
                                HoaDonList.add(ThucdonList.get(i));
                                break;
                            }
                        }
                    }
                    if(spinnerTopping.getSelectedItemPosition()!=0){
                        for(int i=0;i<ThucdonList.size();i++){
                            if(spinnerTopping.getSelectedItem().equals(ThucdonList.get(i).getTenMon())){
                                HoaDonList.add(ThucdonList.get(i));
                                break;
                            }
                        }
                    }
                    if(spinnerDoAn.getSelectedItemPosition()!=0){
                        for(int i=0;i<ThucdonList.size();i++){
                            if(spinnerDoAn.getSelectedItem().toString().equals(ThucdonList.get(i).getTenMon())){
                                HoaDonList.add(ThucdonList.get(i));
                                break;
                            }
                        }
                    }
                    btnThanhToan.setEnabled(true);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    Toast.makeText(OrderActivity.this, "Vui lòng chọn đồ ăn hoặc nước uống", Toast.LENGTH_SHORT).show();
                    spinnerLoai.forceLayout();
                }
            }
        });
        dialog.show();
    }

    private void getdataMon(int position) {
//        Cursor cursor=database.rawQuery("SELECT MaMon,TenMon,Gia,AnhMon FROM ThucDon WHERE MaLoai="+position,null);
//        for (int i=0;i<cursor.getCount();i++){
//            cursor.moveToPosition(i);
//            ThucDon td=new ThucDon();
//            td.setMaMon(cursor.getInt(0));
//            td.setTenMon(cursor.getString(1));
//            td.setGia(cursor.getString(2));
//            td.setHinhAnh(cursor.getInt(3));
//            ThucdonList.add(td);
//            MonList.add(cursor.getString(1));
//        }
//        cursor.close();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Server.duongdansptheoloai;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                ThucDon td=new ThucDon();
                                int mam = Integer.parseInt(jsonObject.getString("MaMon"));
                                td.setMaMon(mam);
                                td.setTenMon(jsonObject.getString("TenMon").trim());
                                td.setGia(jsonObject.getString("Gia"));
                                td.setHinhAnh(12);
                                ThucdonList.add(td);
                                MonList.add(jsonObject.getString("TenMon"));
                            }
//                            adapter.notifyDataSetChanged();
                            spinnerMonAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrderActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String>hs = new HashMap<String, String>();
                hs.put("MaLoai",Integer.toString(position));
                return hs;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void getdataTopping() {
        ToppingList.add("Thêm...");
//        Cursor cursor=database.rawQuery("SELECT MaMon,TenMon,Gia,AnhMon FROM ThucDon WHERE MaLoai=5",null);
//        for (int i=0;i<cursor.getCount();i++){
//            cursor.moveToPosition(i);
//            ThucDon td=new ThucDon();
//            td.setMaMon(cursor.getInt(0));
//            td.setTenMon(cursor.getString(1));
//            td.setGia(cursor.getString(2));
//            td.setHinhAnh(cursor.getInt(3));
//            ThucdonList.add(td);s
//            ToppingList.add(cursor.getString(1));
//        }
//        cursor.close();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Server.duongdansptheoloai;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0; i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                ThucDon td=new ThucDon();
                                int ma = Integer.parseInt(jsonObject.getString("MaMon"));
                                td.setMaMon(ma);
                                td.setTenMon(jsonObject.getString("TenMon"));
                                td.setGia(jsonObject.getString("Gia"));
                                td.setHinhAnh(19);
                                ThucdonList.add(td);
                                ToppingList.add(jsonObject.getString("TenMon"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        spinnerToppingAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrderActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String>hs=new HashMap<String, String>();
                hs.put("MaLoai","8");
                return hs;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void getdataLoai() {
        LoaiList.add("Chọn loại đồ uống...");
//        Cursor cursor=database.rawQuery("SELECT TenLoai FROM Loai",null);
////        for (int i=0;i<4;i++){
////            cursor.moveToPosition(i);
////            LoaiList.add(cursor.getString(0));
////        }
////        cursor.close();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Server.duongdannganhhang;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i<response.length(); i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                LoaiList.add(jsonObject.getString("TenLoai"));
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
                        Toast.makeText(OrderActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    private void getdataDoAn() {
        DoAnList.add("Chọn đồ ăn...");
//        Cursor cursor=database.rawQuery("SELECT MaMon,TenMon,Gia,AnhMon FROM ThucDon WHERE MaLoai=6",null);
//        for (int i=0;i<cursor.getCount();i++){
//            cursor.moveToPosition(i);
//            ThucDon td=new ThucDon();
//            td.setMaMon(cursor.getInt(0));
//            td.setTenMon(cursor.getString(1));
//            td.setGia(cursor.getString(2));
//            td.setHinhAnh(cursor.getInt(3));
//            ThucdonList.add(td);
//            DoAnList.add(cursor.getString(1));
//        }
//        cursor.close();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Server.duongdansptheoloai;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                ThucDon td=new ThucDon();
                                int mam = Integer.parseInt(jsonObject.getString("MaMon"));
                                td.setMaMon(mam);
                                td.setTenMon(jsonObject.getString("TenMon"));
                                td.setGia(jsonObject.getString("Gia"));
                                td.setHinhAnh(12);
                                ThucdonList.add(td);
                                DoAnList.add(jsonObject.getString("TenMon"));
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrderActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String>hs = new HashMap<String, String>();
                hs.put("MaLoai","3");
                return hs;
            }
        };
        requestQueue.add(stringRequest);
    }

}
