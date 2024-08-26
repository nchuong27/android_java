package com.example.java3.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.java3.Dao.Product_Dao;
import com.example.java3.R;
import com.example.java3.database.DatabaseHelper;
import com.example.java3.databinding.ActivityAddproductBinding;
import com.example.java3.model.Category;

import java.text.DecimalFormat;
import java.util.List;
public class Addproduct extends AppCompatActivity {
    ActivityAddproductBinding binding;
    private String outstandingStatus;
    private DatabaseHelper databaseHelper;
    private Product_Dao productDao;
    private List<Category> danhMucList; // Danh sách danh mục sản phẩm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddproductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);
        productDao = new Product_Dao(this);
        outstandingStatus = "Không";

        // Lấy danh sách danh mục từ cơ sở dữ liệu
        danhMucList = databaseHelper.getAllDanhMuc();

        // Tạo adapter cho spinner danh mục
        ArrayAdapter<Category> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, danhMucList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerCate.setAdapter(spinnerAdapter);

        binding.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            outstandingStatus = isChecked ? "Có" : "Không";
        });

        binding.addproDone.setOnClickListener(view -> uploadData());
        binding.toolbarProduct.setTitle("Thêm Sản Phẩm");

        // Thiết lập Toolbar làm ActionBar
        setSupportActionBar(binding.toolbarProduct);

        // Hiển thị mũi tên quay lại
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Xử lý sự kiện khi nhấn vào mũi tên quay lại
        binding.toolbarProduct.setNavigationOnClickListener(new View.OnClickListener() {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(Addproduct.this);
        builder.setCancelable(false);
        builder.setView(R.layout.process_layout);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        // Đặt thời gian hiển thị AlertDialog, ví dụ 3 giây (3000 milliseconds)
        new Handler().postDelayed(() -> {
            if (alertDialog.isShowing() && !Addproduct.this.isFinishing()) {
                alertDialog.dismiss();
            }
        }, 5000);

        String name = binding.addnameText.getText().toString();
        String des = binding.adddesText.getText().toString();
        String originalPriceStr = binding.addpriceOrText.getText().toString();
        String discountStr = binding.addDiscountText.getText().toString();
        String quanStr = binding.addSLText.getText().toString();
        Category selectedDanhMuc = (Category) binding.spinnerCate.getSelectedItem();
        String imageURL = binding.addlinkImg.getText().toString();
        String outstandingImageURL = binding.addlinkNB.getText().toString();

        if (name.isEmpty() || des.isEmpty() || originalPriceStr.isEmpty() || quanStr.isEmpty() || imageURL.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int quan = Integer.parseInt(quanStr);
            double originalPrice = Double.parseDouble(originalPriceStr);
            int discount = discountStr.isEmpty() ? 0 : Integer.parseInt(discountStr);
            double finalPrice = originalPrice * (1 - discount / 100.0);

            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String formattedFinalPrice = decimalFormat.format(finalPrice);

            binding.addpriceText.setText(formattedFinalPrice);

            long result = productDao.insertProduct(imageURL, outstandingImageURL, name, des, formattedFinalPrice, originalPriceStr, quan, outstandingStatus, selectedDanhMuc.getName(), discount);
            if (result != -1) {
                Toast.makeText(this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(this, "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Dữ liệu không hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }
}

