package com.example.java3.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.java3.MainActivity;
import com.example.java3.R;
import com.example.java3.database.DatabaseHelper;

public class Splash extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        databaseHelper = new DatabaseHelper(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Kiểm tra trạng thái đăng nhập
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
                int role = sharedPreferences.getInt("userRole", 2); // 2 là mặc định cho user

                if (isLoggedIn) {
                    if (role == 1) {
                        Intent intent = new Intent(Splash.this, Admin.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(Splash.this, Home.class);
                        startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 2000); // 2000ms = 2 giây
    }
}