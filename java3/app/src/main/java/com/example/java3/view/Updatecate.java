package com.example.java3.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.java3.databinding.ActivityUpdatecateBinding;

public class Updatecate extends AppCompatActivity {
    ActivityUpdatecateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdatecateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String img = intent.getStringExtra("imgDM");
        int id = intent.getIntExtra("id", -1);

        binding.updateCateText.setText(name);
        binding.imgCate.setText(img);

        binding.updateDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = binding.updateCateText.getText().toString().trim();
                String newImg= binding.imgCate.getText().toString().trim();
                if (!newName.isEmpty()|| !newImg.isEmpty()) {
                    // Send the updated data back to the fragment
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("id", id);
                    resultIntent.putExtra("name", newName);
                    resultIntent.putExtra("imgDM", newImg);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(Updatecate.this, "Vui lòng nhập tên danh mục", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.toolbarCate.setTitle("Sửa Danh Mục");

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
    @Override
    public void onBackPressed() {
        // Xử lý logic trước khi quay lại
        // Ví dụ: hiển thị một thông báo trước khi thoát
        // Toast.makeText(this, "Quay lại!", Toast.LENGTH_SHORT).show();

        // Gọi phương thức onBackPressed mặc định để quay lại
        super.onBackPressed();
    }
}
