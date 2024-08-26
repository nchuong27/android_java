package com.example.java2.admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.java2.Dao.DaoSanPham;
import com.example.java2.R;
import com.example.java2.model.DatabaseHelper;

import java.text.NumberFormat;
import java.util.List;

public class ThemSP extends AppCompatActivity {
    private EditText tensp, edtgia, edtmota;
    private ImageView edtanh;
    private Button buttonThemsp;
    private Spinner spinner;
    private DatabaseHelper dbHelper;
    private DaoSanPham daoSanPham;
    private static final int PICK_IMAGE_REQUEST = 1;
    private String anh = ""; // Biến lưu đường dẫn ảnh

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sp);
        dbHelper = new DatabaseHelper(this);
        daoSanPham = new DaoSanPham(this);
        tensp = findViewById(R.id.tensp);
        edtgia = findViewById(R.id.editGia);
        edtmota = findViewById(R.id.editMota);
        buttonThemsp = findViewById(R.id.btnThemsp);
        edtanh = findViewById(R.id.edtchonanh);
        spinner = findViewById(R.id.spinner);
        // Lấy danh sách danh mục từ cơ sở dữ liệu
        List<String> danhMucList = dbHelper.getAllDanhMucNames(); // Giả sử phương thức này trả về List<String> chứa tên các danh mục
        // Tạo Adapter cho Spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, danhMucList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        edtanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mở hộp thoại chọn ảnh
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        buttonThemsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenSP = tensp.getText().toString().trim();
                String giaSPString = edtgia.getText().toString().trim();
                String moTa = edtmota.getText().toString().trim();
                int ID_DM = spinner.getSelectedItemPosition() + 1; // Lấy ID của danh mục đã chọn
                // Kiểm tra xem giá sản phẩm có đúng định dạng không
                double giaSP = 0.0;
                try {
                    giaSP = Double.parseDouble(giaSPString);
                } catch (NumberFormatException e) {
                    Toast.makeText(ThemSP.this, "Giá sản phẩm không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Kiểm tra xem người dùng đã chọn ảnh hay chưa
                if (anh.isEmpty()) {
                    Toast.makeText(ThemSP.this, "Vui lòng chọn ảnh sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Định dạng lại giá tiền
                NumberFormat format = NumberFormat.getCurrencyInstance();
                format.setMaximumFractionDigits(0);
                String giaSPFormatted = format.format(giaSP);
                // Tiến hành thêm sản phẩm vào cơ sở dữ liệu
                long result = daoSanPham.insertSanPham(tenSP, giaSPFormatted, moTa, anh, ID_DM);
                if (result != -1) {
                    Toast.makeText(ThemSP.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(ThemSP.this, "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            // Lấy đường dẫn của ảnh đã chọn
            Uri uri = data.getData();
            anh = uri.toString();
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("ImagePath", anh);
            editor.apply();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Đọc đường dẫn ảnh từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        anh = sharedPreferences.getString("ImagePath", "");
    }

}


