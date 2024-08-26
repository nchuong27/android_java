package com.example.java2.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.java2.KhoiTao.SanPham;
import com.example.java2.R;

public class ChiTietSanPham extends AppCompatActivity {
    private ImageView imgProductDetail;
    private TextView txtNameDetail, txtPriceDetail, txtDescriptionDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sp);
        // Liên kết các thành phần giao diện
        imgProductDetail = findViewById(R.id.imgProductDetail);
        txtNameDetail = findViewById(R.id.txtNameDetail);
        txtPriceDetail = findViewById(R.id.txtPriceDetail);
        txtDescriptionDetail = findViewById(R.id.txtDescriptionDetail);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            SanPham sanPham = intent.getParcelableExtra("SanPham");

            // Hiển thị dữ liệu
            if (sanPham != null) {
                // Set hình ảnh
                Glide.with(this)
                        .load(sanPham.getAnh())
                        .into(imgProductDetail);

                // Set tên sản phẩm
                txtNameDetail.setText(sanPham.getTen());

                // Set giá cả
                txtPriceDetail.setText("Giá: " + sanPham.getGia());

                // Set mô tả
                txtDescriptionDetail.setText(sanPham.getMo_ta());
            }
        }
    }
}