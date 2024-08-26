package com.example.java2.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.java2.Dao.DaoDanhMuc;
import com.example.java2.R;
import com.example.java2.model.DatabaseHelper;

public class suaDM extends AppCompatActivity {
    private EditText editTextTenDM;
    private Button buttonSuaDM;
    private DatabaseHelper dbHelper;
    private int danhMucId;
    private DaoDanhMuc daoDanhMuc;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_dm);
        daoDanhMuc = new DaoDanhMuc(this);
        dbHelper = new DatabaseHelper(this);
        // Liên kết các view từ giao diện
        editTextTenDM = findViewById(R.id.editTextTenDM);
        buttonSuaDM = findViewById(R.id.buttonSuaDM);
        // Nhận dữ liệu được truyền từ màn hình danh mục chính
        Intent intent = getIntent();
        danhMucId = intent.getIntExtra("id", 0);
        String tenDanhMuc = intent.getStringExtra("ten");
        editTextTenDM.setText(tenDanhMuc);
        // Xử lý sự kiện khi nhấnnút "Sửa danh mục"
        buttonSuaDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenDanhMucMoi = editTextTenDM.getText().toString().trim();
                if (!tenDanhMucMoi.isEmpty()) {
                    // Gửi kết quả trở về cho màn hình danh mục chính
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("id", danhMucId);
                    resultIntent.putExtra("ten", tenDanhMucMoi);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(suaDM.this, "Vui lòng nhập tên danh mục", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
