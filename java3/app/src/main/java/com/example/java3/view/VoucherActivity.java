package com.example.java3.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.java3.Dao.VoucherDao;
import com.example.java3.R;
import com.example.java3.adapter.VoucherAdapter;
import com.example.java3.database.DatabaseHelper;
import com.example.java3.databinding.ActivityVoucherBinding;
import com.example.java3.model.Voucher;

import java.util.ArrayList;
import java.util.List;

public class VoucherActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private VoucherDao voucherDao;
    private List<Voucher> voucherList;
    private VoucherAdapter voucherAdapter;
    public static final int ADD_Voucher_REQUEST_CODE = 1;
    ActivityVoucherBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVoucherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHelper = new DatabaseHelper(this);
        voucherDao = new VoucherDao(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recycleviewVCher.setLayoutManager(layoutManager);
        voucherList=getVoucherList("");
        voucherAdapter = new VoucherAdapter(voucherList,voucherDao,this);
        binding.recycleviewVCher.setAdapter(voucherAdapter);
        binding.searchVoucher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần sử dụng
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Tìm kiếm tài khoản khi văn bản thay đổi
                voucherList = getVoucherList(s.toString());
                voucherAdapter.setVoucherList(voucherList);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Không cần sử dụng
            }
        });
        binding.addvoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(VoucherActivity.this,Add_Vorcher.class);
                startActivity(i);
            }
        });
        binding.toolbarVC.setTitle("");
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_Voucher_REQUEST_CODE && resultCode == RESULT_OK)
            voucherList = getVoucherList("");
        voucherAdapter.setVoucherList(voucherList);
    }
    private List<Voucher> getVoucherList(String keyword) {
        List<Voucher> voucherList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String query = "SELECT id,code,description,discount_percent,minimum_purchase_amount FROM Discount WHERE code LIKE ?";
        Cursor cursor = db.rawQuery(query, new String[]{"%" + keyword + "%"});
        try {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("code"));
                String des = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                int dis = cursor.getInt(cursor.getColumnIndexOrThrow("discount_percent"));
                long min = cursor.getLong(cursor.getColumnIndexOrThrow("minimum_purchase_amount"));

                Voucher voucher = new Voucher(id,name,des,dis,min);
                voucherList.add(voucher);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            db.close();
        }
        return voucherList;
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