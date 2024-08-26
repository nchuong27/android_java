package com.example.java3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java3.Dao.AccontDao;
import com.example.java3.database.DatabaseHelper;
import com.example.java3.view.Admin;
import com.example.java3.view.Home;
import com.example.java3.view.dangky;

public class MainActivity extends AppCompatActivity {
    private TextView txtSignup;
    private Button buttonDangnhap;
    private EditText editTextUser, editTextPassword;
    private AccontDao accontDao;
    private DatabaseHelper databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtSignup = findViewById(R.id.txtSignup);
        buttonDangnhap = findViewById(R.id.buttonLogin);
        editTextUser = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPw);
        accontDao = new AccontDao(this);
        databaseHelper = new DatabaseHelper(this);
        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, dangky.class);
                startActivity(intent);
                finish();
            }
        });
        buttonDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextUser.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                if (validateInputs(name, password)) {
                    login(name, password);
                } else {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên đăng nhập và mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInputs(String name, String password) {
        return !name.isEmpty() && !password.isEmpty();
    }

    private void login(String name, String password) {
        if (accontDao.checkUser(name, password)) {
            int role = accontDao.getUserRole(name, password);
            int userId = accontDao.getUserId(name, password);
            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", true);
            editor.putInt("userRole", role);
            editor.putString("username", name);
            editor.putInt("user_id", userId); // Đảm bảo khóa khớp
            editor.apply();

            if (role == 1) {
                // Người dùng là admin
                Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                Intent adminIntent = new Intent(MainActivity.this, Admin.class);
                startActivity(adminIntent);
            } else if (role == 2) {
                // Người dùng là user
                Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                Intent userIntent = new Intent(MainActivity.this, Home.class);
                startActivity(userIntent);
            }
            finish();
        } else {
            Toast.makeText(MainActivity.this, "Tên đăng nhập hoặc mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }
}