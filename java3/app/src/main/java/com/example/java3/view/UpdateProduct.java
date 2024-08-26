package com.example.java3.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.java3.R;
import com.example.java3.databinding.ActivityUpdateProductBinding;
import com.example.java3.Dao.Product_Dao;
import com.example.java3.database.DatabaseHelper;
import com.example.java3.model.Category;

import java.text.DecimalFormat;
import java.util.List;

public class UpdateProduct extends AppCompatActivity {
    ActivityUpdateProductBinding binding;
    private String outstandingStatus;
    private DatabaseHelper databaseHelper;
    private Product_Dao productDao;
    private List<Category> danhMucList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);
        productDao = new Product_Dao(this);
        // Lấy danh sách danh mục từ cơ sở dữ liệu
        danhMucList = databaseHelper.getAllDanhMuc();

        // Kiểm tra danh sách danh mục có trống không
        if (danhMucList == null || danhMucList.isEmpty()) {
            Toast.makeText(this, "Không có danh mục nào được tìm thấy", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo adapter cho spinner danh mục
        ArrayAdapter<Category> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, danhMucList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.UpspinnerCate.setAdapter(spinnerAdapter);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        String price = intent.getStringExtra("price");
        String original_price = intent.getStringExtra("original_price");
        String img = intent.getStringExtra("img");
        String IMG = intent.getStringExtra("IMG");
        int quantity = intent.getIntExtra("quantity", 0);
        String outstanding = intent.getStringExtra("outstanding");
        int cate_product = intent.getIntExtra("cate_product", 1);
        int discount = intent.getIntExtra("giam_gia", -1); // Đã thay đổi thành giam_gia

        // Set the current values to the input fields
        binding.updatenameText.setText(name);
        binding.updatedesText.setText(description);
        binding.uppriceText.setText(price);
        binding.uppriceOrText.setText(original_price);
        binding.uplinkImg.setText(img);
        binding.uplinkNB.setText(IMG);
        binding.upSLText.setText(String.valueOf(quantity));
        binding.upDiscountText.setText(String.valueOf(discount));
        binding.checkBox.setChecked("Có".equals(outstanding));

        binding.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            outstandingStatus = isChecked ? "Có" : "Không";
        });
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

        binding.upproDone.setOnClickListener(view -> updateProduct(id));

    }
    @Override
    public void onBackPressed() {
        // Xử lý logic trước khi quay lại
        // Ví dụ: hiển thị một thông báo trước khi thoát
        // Toast.makeText(this, "Quay lại!", Toast.LENGTH_SHORT).show();

        // Gọi phương thức onBackPressed mặc định để quay lại
        super.onBackPressed();
    }

    private void updateProduct(int productId) {
        String name = binding.updatenameText.getText().toString();
        String des = binding.updatedesText.getText().toString();
        String originalPriceStr = binding.uppriceText.getText().toString();
        String discountStr = binding.upDiscountText.getText().toString();
        String quanStr = binding.upSLText.getText().toString();
        String imageURL = binding.uplinkImg.getText().toString();
        String outstandingImageURL = binding.uplinkNB.getText().toString();

        if (name.isEmpty() || des.isEmpty() || originalPriceStr.isEmpty() || quanStr.isEmpty() || imageURL.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double originalPrice = Double.parseDouble(originalPriceStr);
            int quan = Integer.parseInt(quanStr);
            int discount = discountStr.isEmpty() ? 0 : Integer.parseInt(discountStr);
            double finalPrice = originalPrice * (1 - discount / 100.0);

            // Sử dụng DecimalFormat để định dạng giá tiền
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String formattedFinalPrice = decimalFormat.format(finalPrice);

            Category selectedDanhMuc = (Category) binding.UpspinnerCate.getSelectedItem();

            long result = productDao.updateProduct(productId, name, des, formattedFinalPrice, originalPriceStr , quan, imageURL, outstandingImageURL, outstandingStatus, selectedDanhMuc.getName(), discount);
            if (result != -1) {
                Toast.makeText(this, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(this, "Cập nhật sản phẩm thất bại", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Giá hoặc số lượng không hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }
}
