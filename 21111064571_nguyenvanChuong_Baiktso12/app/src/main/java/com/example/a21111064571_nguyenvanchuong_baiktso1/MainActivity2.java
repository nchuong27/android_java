package com.example.a21111064571_nguyenvanchuong_baiktso1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    private EditText edtuser_res, edtrespass, edtcomfimpass;
    private Button btnres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        edtuser_res = findViewById(R.id.edtuser_res);
        edtrespass = findViewById(R.id.edtrespass);
        edtcomfimpass = findViewById(R.id.edtcomfimpass);
        btnres = findViewById(R.id.btnres);

        btnres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtuser_res.getText().toString().trim();
                String password = edtrespass.getText().toString().trim();
                String confirmPassword = edtcomfimpass.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(MainActivity2.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(MainActivity2.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity2.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    bundle.putString("password", password);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }
}