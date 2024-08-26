package com.example.java3.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.java3.Dao.AccontDao;
import com.example.java3.adapter.AcountAdapter;
import com.example.java3.database.DatabaseHelper;
import com.example.java3.databinding.ActivityAcountBinding;
import com.example.java3.model.Account;

import java.util.ArrayList;
import java.util.List;

public class AcountActivity extends AppCompatActivity {
    ActivityAcountBinding binding;
    private List<Account> accountList;
    private DatabaseHelper databaseHelper;
    private AcountAdapter acountAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAcountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHelper = new DatabaseHelper(this);
        // Thiết lập layout manager cho RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recycleviewAC.setLayoutManager(layoutManager);

        // Thiết lập adapter cho RecyclerView
        accountList = getAccountList("");
        acountAdapter = new AcountAdapter(accountList, new AccontDao(databaseHelper));
        binding.recycleviewAC.setAdapter(acountAdapter);

        // Lắng nghe thay đổi văn bản trong EditText
        binding.searchAcount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần sử dụng
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Tìm kiếm tài khoản khi văn bản thay đổi
                accountList = getAccountList(s.toString());
                acountAdapter.setAccounts(accountList);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Không cần sử dụng
            }
        });
        binding.toolbarAC.setTitle("");
        // Thiết lập Toolbar làm ActionBar
        setSupportActionBar(binding.toolbarAC);

        // Hiển thị mũi tên quay lại
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Xử lý sự kiện khi nhấn vào mũi tên quay lại
        binding.toolbarAC.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private List<Account> getAccountList(String keyword) {
        List<Account> accountList = new ArrayList<>();
        // Open a connection to the database
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        // Query the database to get the list of user accounts from the Users table
        String query = "SELECT id, name, email, password FROM users WHERE name LIKE ?";
        Cursor cursor = db.rawQuery(query, new String[]{"%" + keyword + "%"});
        // Iterate through the result set and add each account to the list
        if (cursor.moveToNext()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
                @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));
                // Create an Account object and add it to the list
                Account account = new Account(id, name, email, password);
                accountList.add(account);
            } while (cursor.moveToNext());
        }
        // Close the database connection and return the list of accounts
        cursor.close();
        db.close();
        return accountList;
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