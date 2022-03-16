package com.example.doanquanlyquancf.khachhangactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.doanquanlyquancf.R;
import com.example.doanquanlyquancf.classdata.GioHang;
import com.example.doanquanlyquancf.classdata.KhachHang;
import com.example.doanquanlyquancf.classdata.TaiKhoan;
import com.example.doanquanlyquancf.classdata.ThongBao;
import com.example.doanquanlyquancf.classdata.TrangThaiHD;
import com.example.doanquanlyquancf.khachhang.fragment.FlashSaleFragment;
import com.example.doanquanlyquancf.khachhang.fragment.GioHangFragment;
import com.example.doanquanlyquancf.khachhang.fragment.HomeFragment;
import com.example.doanquanlyquancf.khachhang.fragment.NganhHangFragment;
import com.example.doanquanlyquancf.khachhang.fragment.TaiKhoanFragment;
import com.example.doanquanlyquancf.khachhang.fragment.ThongBaoFragment;
import com.example.doanquanlyquancf.khachhang.fragment.TimKiemFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class KhachHangActivity extends AppCompatActivity {
    private ActionBar toolbar;
    private Toolbar toolbartop;
    TextView txttilte;

    private static final int FRAGMENT_HOME = 1;
    private static final int FRAGMENT_NGANHHANG = 2;
    private static final int FRAGMENT_FLASHSALE = 3;
    private static final int FRAGMENT_THONGBAO = 4;
    private static final int FRAGMENT_TAIKHOAN = 5;
    private static final int FRAGMENT_GIOHANG = 6;
    private static final int FRAGMENT_TIMKIEM = 7;

    private int currentFrag = FRAGMENT_HOME;

    public static ArrayList<GioHang>ghs;

    public static TaiKhoan tks;

    public static KhachHang khs;

    public static ArrayList<TrangThaiHD> tthds;

    public static ArrayList<ThongBao>tbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);

        txttilte=findViewById(R.id.titletoolbar);

        toolbar=getSupportActionBar();
        BottomNavigationView navigationView=(BottomNavigationView)findViewById(R.id.bottomnav);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        replace(new HomeFragment());

        toolbartop = findViewById(R.id.menutop);
        toolbartop.setTitle("");
        setSupportActionBar(toolbartop);

        Intent intent = getIntent();
        tks = (TaiKhoan)intent.getSerializableExtra("TaiKhoan");

        if(ghs!=null){

        }
        else {
            ghs=new ArrayList<GioHang>();
        }

        if(tthds != null){

        }
        else {
            tthds = new ArrayList<TrangThaiHD>();
        }

        if(tbs != null){

        }
        else {
            tbs = new ArrayList<ThongBao>();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toptoolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.search){
            if(FRAGMENT_TIMKIEM!=currentFrag){
                replace(new TimKiemFragment());
                currentFrag=FRAGMENT_TIMKIEM;
                txttilte.setText("Tìm kiếm");
            }
        }
        else if(item.getItemId()==R.id.cart){
            if(FRAGMENT_GIOHANG!=currentFrag){
                replace(new GioHangFragment());
                currentFrag=FRAGMENT_GIOHANG;
                txttilte.setText("Giỏ hàng");
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
           = new BottomNavigationView.OnNavigationItemSelectedListener() {
       public boolean onNavigationItemSelected(@NonNull MenuItem item) {
           switch (item.getItemId()) {
               case R.id.navigation_all:
                   if(FRAGMENT_HOME!=currentFrag){
                       replace(new HomeFragment());
                       currentFrag=FRAGMENT_HOME;
                       txttilte.setText("Home");
                   }
                   return true;
               case R.id.navigation_loaisp:
                   if(FRAGMENT_NGANHHANG!=currentFrag){
                       replace(new NganhHangFragment());
                       currentFrag=FRAGMENT_NGANHHANG;
                       txttilte.setText("Tất cả ngành hàng");
                   }
                   return true;
               case R.id.navigation_search:
                   if(FRAGMENT_FLASHSALE!=currentFrag){
                       replace(new FlashSaleFragment());
                       currentFrag=FRAGMENT_FLASHSALE;
                       txttilte.setText("Flash Sale");
                   }
                   return true;
               case R.id.navigation_giam:
                   if(FRAGMENT_THONGBAO!=currentFrag){
                       replace(new ThongBaoFragment());
                       currentFrag=FRAGMENT_THONGBAO;
                       txttilte.setText("Thông báo");
                   }
                   return true;
               case R.id.navigation_qua:
                   if(FRAGMENT_TAIKHOAN!=currentFrag){
                       replace(new TaiKhoanFragment());
                       currentFrag=FRAGMENT_TAIKHOAN;
                       txttilte.setText("Tài khoản");
                   }
                   return true;
           }
           return false;
       }
   };

    private void replace(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container,fragment);
        fragmentTransaction.commit();
    }
}