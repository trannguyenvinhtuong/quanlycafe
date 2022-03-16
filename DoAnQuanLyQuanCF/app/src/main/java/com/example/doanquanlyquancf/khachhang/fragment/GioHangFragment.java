package com.example.doanquanlyquancf.khachhang.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.doanquanlyquancf.khachhangactivity.KhachHangActivity;
import com.example.doanquanlyquancf.R;
import com.example.doanquanlyquancf.kh.adapter.GioHangAdapter;
import com.example.doanquanlyquancf.classdata.Server;
import com.example.doanquanlyquancf.classdata.ThongBao;
import com.example.doanquanlyquancf.classdata.TrangThaiHD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GioHangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GioHangFragment extends Fragment {

    GioHangAdapter adapter;
    ListView lstgh;
    TextView txtthongbao;
    static TextView txttongtien;
    Button btnxoahet, btndatmon;
    TextView txtsoban;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GioHangFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GioHangFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GioHangFragment newInstance(String param1, String param2) {
        GioHangFragment fragment = new GioHangFragment();
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
        View rootview=inflater.inflate(R.layout.fragment_gio_hang, container, false);
        lstgh=rootview.findViewById(R.id.lstgiohang);
        txtthongbao=rootview.findViewById(R.id.ghthongbao);
        txttongtien=rootview.findViewById(R.id.ghtongtien);
        btnxoahet=rootview.findViewById(R.id.btnxoahet);
        btndatmon=rootview.findViewById(R.id.btnthanhtoan);
        txtsoban=rootview.findViewById(R.id.txtsoban);
        btnxoahet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(KhachHangActivity.ghs.size()>0){
                    KhachHangActivity.ghs.clear();
                    adapter.notifyDataSetChanged();
                    getData();
                    checkdata();
                    txttongtien.setText("");
                }
            }
        });
        btndatmon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert=new AlertDialog.Builder(getActivity());
                alert.setTitle("Xác nhận");
                alert.setMessage("Bạn có chắc chắn?");
                if(KhachHangActivity.ghs.size()>0){
                    alert.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getActivity(),"Hủy",Toast.LENGTH_LONG).show();
                            dialog.cancel();
                        }
                    });
                    alert.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
//                        final String idhoadon=Integer.toString(generateID());
                        final String ngay=getTime();
                        final String tong=Integer.toString(tinhtien());
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(TextUtils.isEmpty(txtsoban.getText())){
                                Toast.makeText(getActivity(),"Số bàn không được để trống",Toast.LENGTH_LONG).show();
                            }
                            else{
                                RequestQueue requestQueue=Volley.newRequestQueue(getActivity().getApplicationContext());
                                StringRequest request=new StringRequest(Request.Method.POST, Server.duongdanposthoadon,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(final String madonhang) {
                                                Log.d("madonhang",madonhang);
                                                if(Integer.parseInt(madonhang)>0){
                                                    RequestQueue queue=Volley.newRequestQueue(getActivity().getApplicationContext());
                                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanpostcthd,
                                                            new Response.Listener<String>() {
                                                                @Override
                                                                public void onResponse(String response) {
                                                                if(response.equals("1")){
                                                                    KhachHangActivity.ghs.clear();
                                                                    Toast.makeText(getActivity(),"Đặt thành công",Toast.LENGTH_LONG).show();
                                                                    adapter.notifyDataSetChanged();
                                                                    getData();
                                                                    checkdata();
                                                                    txttongtien.setText("");
                                                                    txtsoban.setText("");
                                                                    txtsoban.clearFocus();
                                                                    KhachHangActivity.tthds.add(new TrangThaiHD(madonhang,0));
                                                                    KhachHangActivity.tbs.add(new ThongBao("Đơn hàng "+madonhang+" đặt thành công",R.drawable.outline_send_24));
                                                                    KhachHangActivity.tbs.add(new ThongBao("Đơn hàng "+madonhang+" chưa thanh toán",R.drawable.outline_do_disturb_on_24));
                                                                }
                                                                else {
                                                                    Toast.makeText(getActivity(),"Lỗi",Toast.LENGTH_LONG).show();
                                                                }
                                                                    Log.d("json",response);
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
                                                            JSONArray jsonArray=new JSONArray();
                                                            for(int i=0; i<KhachHangActivity.ghs.size();i++){
                                                                JSONObject jsonObject=new JSONObject();
                                                                try {
                                                                    String yeucau=KhachHangActivity.ghs.get(i).getTopping()+" "+KhachHangActivity.ghs.get(i).getDa()+" Đá"+" "+KhachHangActivity.ghs.get(i).getDuong()+" Đường";
                                                                    int sol=KhachHangActivity.ghs.get(i).getSoluong();
                                                                    jsonObject.put("MaHD",madonhang);
                                                                    jsonObject.put("MaMon",KhachHangActivity.ghs.get(i).getMaMon());
                                                                    jsonObject.put("YeuCau",yeucau);
                                                                    jsonObject.put("SoLuong",Integer.toString(sol));
                                                                    int gia=Integer.parseInt(KhachHangActivity.ghs.get(i).getGia());
                                                                    jsonObject.put("ThanhTien",Integer.toString(gia));
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }
                                                                jsonArray.put(jsonObject);
                                                            }
                                                            HashMap<String,String>hashMap=new HashMap<String,String>();
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
                                                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
                                            }
                                        }){
                                    @Nullable
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        String maban=txtsoban.getText().toString().trim();
                                        String manv=KhachHangActivity.tks.getManv();
                                        String makh=KhachHangActivity.tks.getMakh();
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
                                            Toast.makeText(getActivity(),"Dữ liệu phải là số",Toast.LENGTH_LONG).show();
                                        }
                                        return hashMap;
                                    }
                                };
                                requestQueue.add(request);
                            }

                        }
                    });

                    AlertDialog a=alert.create();
                    a.show();

                }
                else {
                    Toast.makeText(getActivity(),"Giỏ hàng bạn đang rỗng!",Toast.LENGTH_LONG).show();
                }

            }
        });
        checkdata();
        tongtien();
        getData();
        xoagh();
        return rootview;
    }

    private void getData(){
        adapter=new GioHangAdapter(getActivity(),R.layout.motgh,KhachHangActivity.ghs);
        lstgh.setAdapter(adapter);
    }

    private void checkdata(){
        if(KhachHangActivity.ghs.size()<=0){
//            adapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE);
            txtthongbao.setText("Giỏ hàng đang trống");
            lstgh.setVisibility(View.INVISIBLE);
        }
        else{
//            adapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            lstgh.setVisibility(View.VISIBLE);
        }
    }

    public static int tinhtien(){
        int tong=0;
        for(int i=0; i<KhachHangActivity.ghs.size();i++){
            tong += Integer.parseInt(KhachHangActivity.ghs.get(i).getGia());
        }
        return tong;
    }

    public static void tongtien(){
        long tongtien=0;
        for(int i=0; i<KhachHangActivity.ghs.size();i++){
            tongtien += Integer.parseInt(KhachHangActivity.ghs.get(i).getGia());
        }
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        txttongtien.setText("Tổng tiền: "+decimalFormat.format(tongtien)+" VND");
    }

    private int randomnum(){
        return ThreadLocalRandom.current().nextInt(0,100 +1 );
    }

    private int generateID(){
        String ran = Integer.toString(randomnum()) + Integer.toString(randomnum()) + Integer.toString(randomnum());
        return Integer.parseInt(ran);
    }

    private String getTime(){
        long millis=System.currentTimeMillis();
        Date date = new Date(millis);
        return date.toString();
    }

    private void xoagh(){
        lstgh.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Xóa sản phẩm");
                builder.setMessage("Bạn có chắc chắn?");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(KhachHangActivity.ghs.size()<=0){
                            txtthongbao.setVisibility(View.VISIBLE);
                            txttongtien.setText("");
                        }
                        else {
                            KhachHangActivity.ghs.remove(position);
                            adapter.notifyDataSetChanged();
                            tongtien();
                            if(KhachHangActivity.ghs.size()<=0){
                                txtthongbao.setVisibility(View.VISIBLE);
                                txttongtien.setText("");
                            }
                            else {
                                txtthongbao.setVisibility(View.INVISIBLE);
                                adapter.notifyDataSetChanged();
                                tongtien();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.notifyDataSetChanged();
                        tongtien();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

}