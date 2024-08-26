package com.example.a21111064571_nguyenvanchuong_baiktso1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText edt_log, edtpass_log;
    private Button btn_log, btnlog_res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_log = findViewById(R.id.edt_log);
        edtpass_log = findViewById(R.id.edtpass_log);
        btn_log = findViewById(R.id.btn_log);
        btnlog_res = findViewById(R.id.btnlog_res);

        btnlog_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edt_log.getText().toString().trim();
                String password = edtpass_log.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên đăng nhập và mật khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    // Lấy dữ liệu đăng ký từ Intent
                    Bundle bundle = getIntent().getExtras();
                    if (bundle != null) {
                        String username1 = bundle.getString("username");
                        String password1 = bundle.getString("password");

                        Boolean u = username1.equals(edt_log.getText().toString());
                        Boolean p = password1.equals(edtpass_log.getText().toString());

                        if (u&p) {
                            Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, Home.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }
}