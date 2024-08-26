package com.example.java2.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.java2.Dao.DaoDanhMuc;
import com.example.java2.Dao.DaoUser;
import com.example.java2.R;
import com.example.java2.model.DatabaseHelper;

public class themDm extends AppCompatActivity {
    private EditText editTextTenDM;
    private Button buttonThemDM;
    private DatabaseHelper dbHelper;
    private DaoDanhMuc daoDanhMuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_dm);

        dbHelper = new DatabaseHelper(this);
        daoDanhMuc = new DaoDanhMuc(this);

        // Liên kết các view từ giao diện
        editTextTenDM = findViewById(R.id.editTextTenDMt);
        buttonThemDM = findViewById(R.id.buttonThemDM);

        // Xử lý sự kiện khi nhấn nút "Thêm danh mục"
        buttonThemDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDM = editTextTenDM.getText().toString().trim();

                if (tenDM.isEmpty()) {
                    Toast.makeText(themDm.this, "Vui lòng nhập tên danh mục", Toast.LENGTH_SHORT).show();
                } else {
                    long result = daoDanhMuc.insertDanhMuc(tenDM);
                    if (result != -1) {
                        Toast.makeText(themDm.this, "Thêm danh mục thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        Toast.makeText(themDm.this, "Thêm danh mục thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}