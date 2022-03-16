package com.example.doanquanlyquancf.nhanvienactivity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.doanquanlyquancf.Database;
import com.example.doanquanlyquancf.R;
import com.example.doanquanlyquancf.classdata.Server;
import com.example.doanquanlyquancf.classdata.TaiKhoan;
import com.example.doanquanlyquancf.nhanvien.model.BAN;
import com.example.doanquanlyquancf.nhanvienadapter.GridViewBanAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String DATABASE_NAME="QuanLyCaFe.db";
    GridView grdvBan;
    List<BAN> BanList;
    GridViewBanAdapter adapter;
    SQLiteDatabase database;
    public static TaiKhoan tk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database= Database.TaoDatabase(this,DATABASE_NAME);
        grdvBan= findViewById(R.id.gridViewBan);

        grdvBan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this, OrderActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("MABAN",position+1);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        tk = (TaiKhoan)intent.getSerializableExtra("TaiKhoan");
    }

    @Override
    protected void onStart() {
        super.onStart();
        BanList=new ArrayList<>();
        getdataBan();
        getTrangThai();
        adapter=new GridViewBanAdapter(this,BanList);
        grdvBan.setAdapter(adapter);
    }

    private void getTrangThai() {
//        Cursor cursorTT = database.rawQuery("select MaBan from TamTinh group by MaBan", null);
//        while (cursorTT.moveToNext()) {
//            for (int i=0;i<BanList.size();i++) {
//                if (cursorTT.getInt(0) == BanList.get(i).getMaBan()) {
//                    BanList.get(i).setTrangThai(1);
//                }
//            }
//        }
//        cursorTT.close();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Server.duongdangetallhd;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i<response.length(); i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                int maban = Integer.parseInt(jsonObject.getString("MaBan"));
                                for(int j =0; j<BanList.size(); j++){
                                    if (maban == BanList.get(j).getMaBan()) {
                                        BanList.get(j).setTrangThai(1);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    private void getdataBan() {
//        Cursor cursor=database.rawQuery("Select * from Ban",null);
//        while (cursor.moveToNext()){
//            BAN ban=new BAN();
//            ban.setMaBan(cursor.getInt(0));
//            ban.setTenBan("Bàn "+ban.getMaBan());
//            BanList.add(ban);
//        }
//        cursor.close();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Server.duongdangetban;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i<response.length(); i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                BAN ban = new BAN();
                                int maban = Integer.parseInt(jsonObject.getString("MaBan"));
                                ban.setMaBan(maban);
                                ban.setTenBan("Bàn"+ jsonObject.getString("MaBan"));
                                BanList.add(ban);
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
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

}
