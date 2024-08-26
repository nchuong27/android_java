package com.example.java2.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.java2.Dao.DaoUser;
import com.example.java2.model.DatabaseHelper;
import com.example.java2.R;

public class DangKy extends AppCompatActivity {
    private EditText edtemailres, edtpassres, edtcofpassres, edtmaxacnhan;
    private Button btnsign;
    private DatabaseHelper databaseHelper;
    private DaoUser daoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        databaseHelper = new DatabaseHelper(this);
        daoUser = new DaoUser(this);
        edtemailres = findViewById(R.id.edtemailres);
        edtpassres = findViewById(R.id.edtpassres);
        edtcofpassres = findViewById(R.id.edtcofpassres);
        edtmaxacnhan = findViewById(R.id.edtmaxacthuc);
        btnsign = findViewById(R.id.btnsignup);

        btnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtemailres.getText().toString().trim();
                String password = edtpassres.getText().toString().trim();
                String confirmPassword = edtcofpassres.getText().toString().trim();
                String Code = edtmaxacnhan.getText().toString().trim();

                if (validateInputs(email, password, confirmPassword)) {
                    if (Code.equals("admin123")) {
                        register(email, password, true); // Đăng ký với vai trò quản trị viên
                    } else {
                        register(email, password, false); // Đăng ký với vai trò người dùng
                    }
                } else {
                    Toast.makeText(DangKy.this, "Vui lòng nhập tên đăng nhập, mật khẩu và xác nhận mật khẩu chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInputs(String email, String password, String confirmPassword) {
        return !email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty() && password.equals(confirmPassword);
    }

    private void register(String email, String password, boolean isAdmin) {
        int role = isAdmin ? 1 : 2;
        long result = daoUser.insertUser(email, password, role);
        if (result != -1) {
            Toast.makeText(DangKy.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            Intent loginIntent = new Intent(DangKy.this, DangNhap.class);
            startActivity(loginIntent);
            finish();
        } else {
            Toast.makeText(DangKy.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}