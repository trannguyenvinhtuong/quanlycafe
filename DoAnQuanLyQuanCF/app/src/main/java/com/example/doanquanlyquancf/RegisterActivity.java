package com.example.doanquanlyquancf;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanquanlyquancf.nhanvienactivity.MainActivity;

public class RegisterActivity extends AppCompatActivity {
    EditText edtTenNV, edtSDT, edtNamSinh, edtEmail, edtTenDN, edtMK, edtNhapLaiMK;
    RadioButton radioButtonQuanLy, radioButtonNV;
    RadioGroup rdgr;
    Button btnDangKy;
    SQLiteDatabase database;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        anhxa();
        xuLyDangKy();
    }

    private void xuLyDangKy() {
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tennv = edtTenNV.getText().toString();
                String sdt = edtSDT.getText().toString();
                String namsinh = edtNamSinh.getText().toString();
                String email = edtEmail.getText().toString().trim();
                String tendn = edtTenDN.getText().toString();
                String mk = edtMK.getText().toString();
                String nhaplaimk = edtNhapLaiMK.getText().toString();
                String CV = "";

                if (tennv.equals(""))
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập tên nhân viên", Toast.LENGTH_LONG).show();
                else if (!(sdt.matches("0[0-9]{9}")))
                    Toast.makeText(RegisterActivity.this, "Bạn nhập sai định dạng số điện thoại", Toast.LENGTH_LONG).show();
                else if (sdt.length() < 10) {
                    edtSDT.setError("Bạn nhập sai định dạng số điện thoại");
                } else if (namsinh.equals(""))
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập năm sinh", Toast.LENGTH_LONG).show();
                else if (email.equals(""))
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập email", Toast.LENGTH_LONG).show();
                else if (!email.trim().matches(emailPattern)) {
                    Toast.makeText(RegisterActivity.this, "Email không hợp lệ", Toast.LENGTH_LONG).show();
                } else if (tendn.equals(""))
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập tên đăng nhập", Toast.LENGTH_LONG).show();
                else if (mk.equals(""))
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_LONG).show();
                else if (nhaplaimk.equals(""))
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập lại mật khẩu", Toast.LENGTH_LONG).show();
                else if (radioButtonNV.isChecked() == false && radioButtonQuanLy.isChecked() == false)
                    Toast.makeText(RegisterActivity.this, "Vui lòng chọn chức vụ", Toast.LENGTH_LONG).show();
                /*else if (radioButtonQuanLy.isChecked()==false)
                    Toast.makeText(RegisterActivity.this, "Vui lòng chọn chức vụ", Toast.LENGTH_LONG).show();*/

                else {
                    if (radioButtonNV.isChecked()) {
                        CV = "NVPV";
                    }
                    if (radioButtonQuanLy.isChecked()) {
                        CV = "NVQL";
                    }


                    if (mk.equals(nhaplaimk) && mk != null && nhaplaimk != null) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("TenNV", tennv);
                        contentValues.put("SDT", sdt);
                        contentValues.put("NamSinh", namsinh);
                        //contentValues.put("Email",email);
                        database.insert("NhanVien", null, contentValues);

                        int ma = -1;
                        Cursor cursor = database.rawQuery("select *from NhanVien where SDT=" + sdt, null);
                        while (cursor.moveToNext()) {
                            ma = cursor.getInt(0);

                        }

                        ContentValues contentValues1 = new ContentValues();
                        contentValues1.put("TK", tendn);
                        contentValues1.put("MK", mk);
                        contentValues1.put("MaCV", CV);
                        contentValues1.put("MaNV", ma);
                        
                        database.insert("TaiKhoan", null, contentValues1);

                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                        startActivity(intent);

                    } else {
                        Toast.makeText(RegisterActivity.this, "Bạn nhập lại mật khẩu sai", Toast.LENGTH_LONG).show();
                    }
                }
            }

        });
    }

    private void anhxa() {
        edtTenNV = findViewById(R.id.edtTenNV);
        edtSDT = findViewById(R.id.edtSDT);
        edtNamSinh = findViewById(R.id.edtNamSinh);
        edtEmail = findViewById(R.id.edtEmail);
        edtTenDN = findViewById(R.id.edtTenDangNhap);
        edtMK = findViewById(R.id.edtPassword);
        edtNhapLaiMK = findViewById(R.id.edtNhapLaiPassword);
        radioButtonQuanLy = findViewById(R.id.rdQuanLy);
        radioButtonNV = findViewById(R.id.rdNhanVien);
        btnDangKy = findViewById(R.id.btnDangKy);
        rdgr = findViewById(R.id.rdgr);
        database=Database.TaoDatabase(this, MainActivity.DATABASE_NAME);

    }
}



