package com.example.doanquanlyquancf;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanquanlyquancf.nhanvienactivity.MainActivity;
import com.example.doanquanlyquancf.nhanvienadapter.NhanVienAdapter;

import java.util.ArrayList;
import java.util.List;

public class QuanLyNhanVienActivity extends AppCompatActivity {
    SQLiteDatabase database;
    NhanVienAdapter adapter;
    List<NhanVien> NhanVienList;
    ListView lvNhanVien;
    Button btnThem;
    TextView hoten,ngaysinh, diachi, sdt, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_nhan_vien);
        database=Database.TaoDatabase(QuanLyNhanVienActivity.this, MainActivity.DATABASE_NAME);
        lvNhanVien= findViewById(R.id.ListViewNhanVien);
        NhanVienList=new ArrayList<>();
        GetDataNhanVien();
        adapter=new NhanVienAdapter(QuanLyNhanVienActivity.this,android.R.layout.simple_list_item_1,NhanVienList);
        lvNhanVien.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        /*lvNhanVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(QuanLyNhanVienActivity.this,UpdateNVActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("NHANVIEN",NhanVienList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });*/

        btnThem= findViewById(R.id.Btn_ThemNV);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuanLyNhanVienActivity.this,RegisterActivity.class));
            }
        });
        lvNhanVien.setLongClickable(true);

        lvNhanVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SuaNhanVien(NhanVienList.get(position));
            }
        });

        lvNhanVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(QuanLyNhanVienActivity.this);
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                builder1.setMessage("Bạn có muốn xóa không ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Có",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                database.delete("NhanVien","MaNV="+NhanVienList.get(position).getMaNV(),null);
                                database.delete("TaiKhoan","MaNV="+NhanVienList.get(position).getMaNV(),null);
                                adapter.remove(NhanVienList.get(position));
                                adapter.notifyDataSetChanged();
                            }
                        });

                builder1.setNegativeButton(
                        "Không",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                return true;
            }
        });


    }

    private void SuaNhanVien(final NhanVien nhanVien) {
        final Dialog dialog=new Dialog(QuanLyNhanVienActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sua_nhanvien);
        dialog.setCanceledOnTouchOutside(false);

        hoten =  dialog.findViewById(R.id.tvHoTen);
        ngaysinh = dialog.findViewById(R.id.tvNgaySinh);
        diachi =  dialog.findViewById(R.id.tvDiaChi);
        sdt =  dialog.findViewById(R.id.tvSDT);
        email = dialog.findViewById(R.id.tvEmail);
    }

    private void GetDataNhanVien(){
        Cursor cursor=database.rawQuery("select * from NhanVien",null);

        while (cursor.moveToNext()){
            NhanVien td=new NhanVien();
            td.setMaNV(cursor.getInt(0));
            td.setHoTen(cursor.getString(1));
            td.setSDT(cursor.getString(2));
            td.setNgaySinh(cursor.getString(3));
            td.setEmail(cursor.getString(4));
            td.setDiaChi(cursor.getString(5));
            NhanVienList.add(td);
        }
    }
}