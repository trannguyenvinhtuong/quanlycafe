package com.example.doanquanlyquancf;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doanquanlyquancf.classdata.Server;
import com.example.doanquanlyquancf.nhanvien.model.ThucDon;
import com.example.doanquanlyquancf.nhanvienadapter.ThucDonAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Update_TD_Activity extends AppCompatActivity {
    EditText edtGia;
    TextView txtMon;
    Button btnLuu, btnHuy;
    ListView lv_updatethucdon;
    ThucDonAdapter thucDonAdapter;
    ArrayList<ThucDon> lstThucDon;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_td);
        anhxa();
        getThucDon();
        getFullMon();
        lv_updatethucdon.setAdapter(thucDonAdapter);
        lv_updatethucdon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DialogUpdate(lstThucDon.get(position));
            }
        });
    }

    private void getThucDon() {
//        Cursor cursor = database.rawQuery("Select * from THUCDON", null);
//        while (cursor.moveToNext()) {
//            ThucDon thucDon = new ThucDon();
//            thucDon.setTenMon(cursor.getString(0));
//            thucDon.setTenMon(cursor.getString(1));
//            thucDon.setGia(cursor.getString(2));
//            thucDon.setHinhAnh(cursor.getInt(3));
//            thucDon.setMaLoai(cursor.getInt(4));
//            lstThucDon.add(thucDon);
//        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Server.duongdandatasanpham;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i<response.length();i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                ThucDon thucDon = new ThucDon();
                                int ma = Integer.parseInt(jsonObject.getString("MaMon"));
                                thucDon.setMaMon(ma);
                                thucDon.setTenMon(jsonObject.getString("TenMon"));
                                thucDon.setGia(jsonObject.getString("Gia"));
                                thucDon.setHinhAnh(12);
                                int mal = Integer.parseInt(jsonObject.getString("MaLoai"));
                                thucDon.setMaLoai(mal);
                                lstThucDon.add(thucDon);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        thucDonAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Update_TD_Activity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    private void DialogUpdate(final ThucDon thucDon) {
        final Dialog dialog = new Dialog(Update_TD_Activity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sua_thucdon);
        dialog.setCanceledOnTouchOutside(false);
        txtMon = dialog.findViewById(R.id.txtMon);
        edtGia = dialog.findViewById(R.id.edtGiaTien);
        btnHuy = dialog.findViewById(R.id.btn_Huy);
        btnLuu = dialog.findViewById(R.id.btn_Luu);
        edtGia.setText(thucDon.getGia());
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gia = edtGia.getText().toString();
                if (TextUtils.isEmpty(gia)) {
                    Toast.makeText(Update_TD_Activity.this, "Vui lòng nhập giá tiền cần thay đổi", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    thucDon.setGia(gia);
                    RequestQueue requestQueue = Volley.newRequestQueue(Update_TD_Activity.this);
                    String url = Server.duongdanupdategia;
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("1")){
                                        Toast.makeText(Update_TD_Activity.this,"Sửa thành công",Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Toast.makeText(Update_TD_Activity.this,"Thất bại",Toast.LENGTH_LONG).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(Update_TD_Activity.this,error.toString(),Toast.LENGTH_LONG).show();
                                }
                            }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hs = new HashMap<String, String>();
                            String ma = Integer.toString(thucDon.getMaMon());
                            hs.put("MaMon",ma);
                            hs.put("Gia",thucDon.getGia());
                            return hs;
                        }
                    };
                    requestQueue.add(stringRequest);
                }

//                ContentValues contentValues = new ContentValues();
//                contentValues.put("TenMon", thucDon.getTenMon());
//                contentValues.put("Gia", thucDon.getGia());
//                contentValues.put("AnhMon", thucDon.getHinhAnh());
//                contentValues.put("MaLoai", thucDon.getMaLoai());
//
//                database.update("ThucDon", contentValues, "MaMon=?", new String[]{thucDon.getMaMon() + ""});



//                thucDonAdapter.notifyDataSetChanged();
                getFullMon();
                dialog.dismiss();

            }
        });
        dialog.show();

    }

    private void getFullMon() {
        lstThucDon.clear();
//        Cursor cursor = database.rawQuery("Select MaMon,TenMon,Gia,AnhMon,MaLoai from ThucDon", null);
//        while (cursor.moveToNext()) {
//            ThucDon thucDon = new ThucDon();
//            thucDon.setMaMon(cursor.getInt(0));
//            thucDon.setTenMon(cursor.getString(1));
//            thucDon.setGia(cursor.getString(2));
//            //thucDon.setHinhAnh(Integer.valueOf(cursor.getString(3)));
//            thucDon.setMaLoai(Integer.valueOf(cursor.getString(4)));
//            lstThucDon.add(thucDon);
//
//        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Server.duongdandatasanpham;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i<response.length();i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                ThucDon thucDon = new ThucDon();
                                int ma = Integer.parseInt(jsonObject.getString("MaMon"));
                                thucDon.setMaMon(ma);
                                thucDon.setTenMon(jsonObject.getString("TenMon"));
                                thucDon.setGia(jsonObject.getString("Gia"));
                                thucDon.setHinhAnh(12);
                                int mal = Integer.parseInt(jsonObject.getString("MaLoai"));
                                thucDon.setMaLoai(mal);
                                lstThucDon.add(thucDon);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        thucDonAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Update_TD_Activity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);

}


    private void anhxa() {
        lv_updatethucdon = findViewById(R.id.lv_updateThucDon);
        lstThucDon = new ArrayList<>();
        thucDonAdapter = new ThucDonAdapter(Update_TD_Activity.this, R.layout.mon_item,lstThucDon);
//        database = Database.TaoDatabase(Update_TD_Activity.this,MainActivity.DATABASE_NAME);

    }
}



