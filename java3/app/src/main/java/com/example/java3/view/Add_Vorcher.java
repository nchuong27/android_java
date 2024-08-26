package com.example.java3.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.java3.Dao.VoucherDao;
import com.example.java3.R;
import com.example.java3.databinding.ActivityAddVorcherBinding;

public class Add_Vorcher extends AppCompatActivity {
    ActivityAddVorcherBinding binding;
    private VoucherDao voucherDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddVorcherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        voucherDao = new VoucherDao(this);

        binding.addVucherDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadData();
            }
        });
        binding.toolbarVC.setTitle("Thêm Voucher");
        // Thiết lập Toolbar làm ActionBar
        setSupportActionBar(binding.toolbarVC);

        // Hiển thị mũi tên quay lại
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Xử lý sự kiện khi nhấn vào mũi tên quay lại
        binding.toolbarVC.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
    @Override
    public void onBackPressed() {
        // Xử lý logic trước khi quay lại
        // Ví dụ: hiển thị một thông báo trước khi thoát
        // Toast.makeText(this, "Quay lại!", Toast.LENGTH_SHORT).show();

        // Gọi phương thức onBackPressed mặc định để quay lại
        super.onBackPressed();
    }

    private void uploadData() {
        String name = binding.addnameVC.getText().toString().trim();
        String des = binding.addDesVC.getText().toString().trim();
        int giam = Integer.parseInt(binding.addGiamVC.getText().toString().trim());
        int min = Integer.parseInt(binding.addMinVC.getText().toString().trim());

        if (!name.isEmpty()) {
            long result = voucherDao.insertVoucher(name, des, giam, min);
            if (result != -1) {
                Toast.makeText(Add_Vorcher.this, "Voucher added successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(Add_Vorcher.this, "Failed to add voucher.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Add_Vorcher.this, "Please enter a voucher name.", Toast.LENGTH_SHORT).show();
        }
    }
}