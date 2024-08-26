package com.example.java2.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java2.Home;
import com.example.java2.R;
import com.example.java2.model.DatabaseHelper;
import com.example.java2.view.DangNhap;

public class quan_Tri extends AppCompatActivity {
    private TextView txtqltai_khoan,txtql_danhmuc,txtql_sanpham;
    private Button btnlogout1;
    private DatabaseHelper databaseHelper;


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_tri);
        this.databaseHelper = new DatabaseHelper(this);
        txtqltai_khoan= findViewById(R.id.txtql_taikhoan);
        txtql_danhmuc = findViewById(R.id.txtql_danhmuc);
        txtql_sanpham = findViewById(R.id.txtql_sanpham);
        btnlogout1 = findViewById(R.id.btnlogout1);
        Home home = new Home();

        txtql_sanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(quan_Tri.this, Ql_sanpham.class);
                startActivity(i);
            }
        });

        txtqltai_khoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(quan_Tri.this,QL_taikhoan.class);
                startActivity(i);
            }
        });

        txtql_danhmuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(quan_Tri.this,QLDanhMuc.class);
                startActivity(i);
            }
        });
        btnlogout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               logout();
            }
        });



    }
    public void logout() {
        if (this != null && databaseHelper != null) {
            databaseHelper.logout();
            Toast.makeText(getApplicationContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Lỗi: databaseHelper chưa được thiết lập", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this, DangNhap.class);
        startActivity(intent);
        finish();
    }

}