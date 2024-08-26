package com.example.java3.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java3.Dao.AccontDao;
import com.example.java3.MainActivity;
import com.example.java3.R;

public class dangky extends AppCompatActivity {
    private TextView txtSignin;
    private EditText editTextName,editTextEmai2,editTextPw2,editTextCpw,editTextKey;
    private Button buttonDky1;
    private AccontDao accontDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        accontDao = new AccontDao(this);
        txtSignin = findViewById(R.id.txtSignin);
        editTextName = findViewById(R.id.editTextName);
        editTextEmai2 = findViewById(R.id.editTextEmail2);
        editTextPw2 = findViewById(R.id.editTextPw2);
        editTextCpw = findViewById(R.id.editTextCpw);
        editTextKey = findViewById(R.id.editTextNull);
        buttonDky1= findViewById(R.id.buttonDKy1);
        buttonDky1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String email = editTextEmai2.getText().toString().trim();
                String password = editTextPw2.getText().toString().trim();
                String cPass = editTextCpw.getText().toString().trim();
                String key = editTextKey.getText().toString().trim();
                if (validateInputs(name,email,password,cPass)){
                    if (key.equals("admin123")){
                        dangky(name,email,password,true);
                    }else {
                        dangky(name,email,password,false);
                    }
                }else {
                    Toast.makeText(dangky.this,"Nhập thông tin phải chính xác",Toast.LENGTH_SHORT).show();
                }
            }
        });
        txtSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dangky.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private boolean validateInputs(String name, String email,String password, String cPass ){
        return !email.isEmpty() && !name.isEmpty() && !password.isEmpty()
                && !cPass.isEmpty() && password.equals(cPass);
    }
    private void dangky(String name, String email,String password, boolean isAdmin){
        int role = isAdmin ? 1: 2;
        long result = accontDao.insertUser(name,email,password,role);
        if (result!= -1){
            Toast.makeText(this,"Đăng ký thành công",Toast.LENGTH_SHORT).show();
            Intent dangnhap = new Intent(this,MainActivity.class);
            startActivity(dangnhap);
            finish();
        }else
            Toast.makeText(this,"Đăng ký thất bại",Toast.LENGTH_SHORT).show();
    }
}