package com.example.doanquanlyquancf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;

import androidx.cardview.widget.CardView;

import com.example.doanquanlyquancf.HoaDonActivity;
import com.example.doanquanlyquancf.classdata.TaiKhoan;

public class MainAdminActivity extends Activity {
    TaiKhoan tk;
    GridLayout grlMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        Intent intentLogin=getIntent();

        tk= (TaiKhoan) intentLogin.getSerializableExtra("TAIKHOAN");
        grlMain=(GridLayout) findViewById(R.id.mainGrid);
        SuKien(grlMain);

    }

    private void SuKien(GridLayout grlMain) {
        for (int i = 0; i < grlMain.getChildCount(); i++) {
            CardView cardView = (CardView) grlMain.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (finalI){
                        case 0:
                            Intent intent0=new Intent(MainAdminActivity.this,QuanLyNhanVienActivity.class);
                            startActivity(intent0);
                            break;
                        case 1:
                            Intent intent1=new Intent(MainAdminActivity.this, HoaDonActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("TAIKHOAN",tk);
                            intent1.putExtras(bundle);
                            startActivity(intent1);
                            break;
                        case 2:
                            startActivity(new Intent(MainAdminActivity.this,Update_TD_Activity.class));
                            break;
                        case 3:
                            startActivity(new Intent(MainAdminActivity.this,DangNhap.class));
                            break;
                    }
                }
            });
        }
    }

}