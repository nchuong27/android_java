package com.example.java3.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.java3.Dao.Category_Dao;
import com.example.java3.R;
import com.example.java3.databinding.ActivityAddcateBinding;


public class Addcate extends AppCompatActivity {
    ActivityAddcateBinding binding;
    Category_Dao categoryDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddcateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        categoryDao = new Category_Dao(this);

        binding.addcateDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadData();
            }
        });
        binding.toolbarCate.setTitle("Thêm Danh Mục");

        // Thiết lập Toolbar làm ActionBar
        setSupportActionBar(binding.toolbarCate);

        // Hiển thị mũi tên quay lại
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Xử lý sự kiện khi nhấn vào mũi tên quay lại
        binding.toolbarCate.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void uploadData(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Addcate.this);
        builder.setCancelable(false);
        builder.setView(R.layout.process_layout);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

// Đặt thời gian hiển thị AlertDialog, ví dụ 3 giây (3000 milliseconds)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Kiểm tra nếu AlertDialog vẫn đang hiển thị và Activity chưa bị đóng
                if (alertDialog.isShowing() && !Addcate.this.isFinishing()) {
                    alertDialog.dismiss();
                }
            }
        }, 5000);
        String name = binding.addcateText.getText().toString().trim();
        String img = binding.imgCate.getText().toString().trim();
        if (!name.isEmpty()|| !img.isEmpty()) {
            long result = categoryDao.insertCatagory(name,img);
            if (result != -1) {
                Toast.makeText(Addcate.this, "Category added successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                binding.addcateText.setText("");
                binding.imgCate.setText("");
            } else {
                Toast.makeText(Addcate.this, "Failed to add category.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Addcate.this, "Please enter a category name.", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onBackPressed() {
        // Xử lý logic trước khi quay lại
        // Ví dụ: hiển thị một thông báo trước khi thoát
        // Toast.makeText(this, "Quay lại!", Toast.LENGTH_SHORT).show();

        // Gọi phương thức onBackPressed mặc định để quay lại
        super.onBackPressed();
    }
}