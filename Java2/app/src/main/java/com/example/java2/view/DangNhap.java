package com.example.java2.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java2.Dao.DaoUser;
import com.example.java2.R;
import com.example.java2.admin.quan_Tri;
import com.example.java2.model.DatabaseHelper;
import com.example.java2.Home;

public class DangNhap extends AppCompatActivity {

    private EditText edtemaillog, edtpasslog;
    private Button btnlog;
    private TextView txtSignup;
    private DatabaseHelper databaseHelper;
    private DaoUser daoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        databaseHelper = new DatabaseHelper(this);
        daoUser = new DaoUser(this);

        edtemaillog = findViewById(R.id.edtemaillog);
        edtpasslog = findViewById(R.id.edtpasslog);
        btnlog =findViewById(R.id.btnlog);
        txtSignup = findViewById(R.id.txtSignup);

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtemaillog.getText().toString().trim();
                String password = edtpasslog.getText().toString().trim();
                if (validateInputs(email, password)) {
                    login(email, password);
                } else {
                    Toast.makeText(DangNhap.this, "Vui lòng nhập tên đăng nhập và mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userIntent = new Intent(DangNhap.this, DangKy.class);
                startActivity(userIntent);
                finish();
            }
        });
    }

    private boolean validateInputs(String email, String password) {
        return !email.isEmpty() && !password.isEmpty();
    }

    private void login(String email, String password) {
        if (daoUser.checkUser(email, password)) {
            int role = daoUser.getUserRole(email, password);
            if (role == 1) {
                // Người dùng là admin
                Toast.makeText(DangNhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                databaseHelper.setLoggedIn(true);
                Intent adminIntent = new Intent(DangNhap.this, quan_Tri.class);
                startActivity(adminIntent);
                finish();
            } else if (role == 2) {
                // Người dùng là user
                Toast.makeText(DangNhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                databaseHelper.setLoggedIn(true);
                Intent userIntent = new Intent(DangNhap.this, Home.class);
                startActivity(userIntent);
                finish();
            }
        } else {
            Toast.makeText(DangNhap.this, "Tên đăng nhập hoặc mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }






    public void setDatabaseHelper(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
}
