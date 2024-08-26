package com.example.java2.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.java2.Dao.DaoSanPham;
import com.example.java2.KhoiTao.SanPham;
import com.example.java2.R;
import com.example.java2.model.DatabaseHelper;

import java.text.NumberFormat;
import java.util.List;

public class suaSP extends AppCompatActivity {
    private EditText tensp, edtgia, edtmota,edtsuaanh;
    private Button buttonSua;
    private Spinner spinner;
    private DatabaseHelper dbHelper;
    private DaoSanPham daoSanPham;
    private static final int PICK_IMAGE_REQUEST = 1;
    private String anh = ""; // Biến lưu đường dẫn ảnh
    private int maSP;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_sp);
        dbHelper = new DatabaseHelper(this);
        daoSanPham = new DaoSanPham(this);
        tensp = findViewById(R.id.suaten);
        edtgia = findViewById(R.id.editsuaGia);
        edtmota = findViewById(R.id.editsuaMota);
        buttonSua = findViewById(R.id.btnSuasp);
        edtsuaanh = findViewById(R.id.edtsuaanh);
        spinner = findViewById(R.id.suaspinner);
        // Lấy danh sách danh mục từ cơ sở dữ liệu
        List<String> danhMucList = dbHelper.getAllDanhMucNames(); // Giả sử phương thức này trả về List<String> chứa tên các danh mục
        // Tạo Adapter cho Spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, danhMucList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        // Nhận dữ liệu từ màn hình danh mục chính
        Intent intent = getIntent();
        maSP = intent.getIntExtra("Id_sp", 0);
        String tenSP = intent.getStringExtra("Ten_sp");
        tensp.setText(tenSP);
        String moTa = intent.getStringExtra("Mo_ta");
        edtmota.setText(moTa);
        double giaSP = intent.getDoubleExtra("Gia", 0);
        edtgia.setText(String.valueOf(giaSP));
        String anhSP = intent.getStringExtra("Anh_sp");
        anh = anhSP; // Cập nhật đường dẫn ảnh
        edtsuaanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mở hộp thoại chọn ảnh
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        buttonSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenSPMoi = tensp.getText().toString().trim();
                String moTaMoi = edtmota.getText().toString().trim();
                String giaSPMoiString = edtgia.getText().toString().trim();
                int ID_DMMoi = spinner.getSelectedItemPosition() + 1; // Lấy ID của danh mục đã chọn
                // Kiểm tra xem giá sản phẩm có đúng định dạng không
                double giaSPMoi = 0.0;
                try {
                    giaSPMoi = Double.parseDouble(giaSPMoiString);
                } catch (NumberFormatException e) {
                    Toast.makeText(suaSP.this, "Giá sản phẩm không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Kiểm tra xem người dùng đã chọn ảnh hay chưa
                if (anh.isEmpty()) {
                    Toast.makeText(suaSP.this, "Vui lòng chọn ảnh sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Định dạng lại giá tiền
                NumberFormat format = NumberFormat.getCurrencyInstance();
                format.setMaximumFractionDigits(0);
                String giaSPFormatted = format.format(giaSPMoi);
                // Tiến hành cập nhật thông tin sản phẩm vào cơ sở dữ liệu
                daoSanPham.updateSanPham(maSP, tenSPMoi, giaSPFormatted, moTaMoi, anh, ID_DMMoi);
                // Trả kết quả về cho màn hình danh mục chính
                Intent resultIntent = new Intent();
                resultIntent.putExtra("Id_sp", maSP);
                resultIntent.putExtra("Ten_sp", tenSPMoi);
                resultIntent.putExtra("Mo_ta", moTaMoi);
                resultIntent.putExtra("Gia", giaSPFormatted);
                resultIntent.putExtra("Anh_sp", anh);
                resultIntent.putExtra("Id_dm", ID_DMMoi);
                setResult(RESULT_OK, resultIntent);

                finish(); // Đóng màn hình sửa sản phẩm
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
        }
    }
}
