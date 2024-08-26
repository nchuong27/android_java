package com.example.java2.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java2.R;
import com.example.java2.KhoiTao.TaiKhoan;
import com.example.java2.model.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class QL_taikhoan extends AppCompatActivity {

    private RecyclerView recyclerViewAccounts;
    private DatabaseHelper dbHelper;
    private AccountAdapter accountAdapter;
    private List<TaiKhoan> accountList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ql_taikhoan);

        dbHelper = new DatabaseHelper(this);

        // Liên kết RecyclerView từ giao diện
        recyclerViewAccounts = findViewById(R.id.recycler_accounts);

        // Thiết lập layout manager cho RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewAccounts.setLayoutManager(layoutManager);

        // Thiết lập adapter cho RecyclerView
        accountList = getAccountList();
        accountAdapter = new AccountAdapter(accountList);
        recyclerViewAccounts.setAdapter(accountAdapter);
    }

    private List<TaiKhoan> getAccountList() {
        List<TaiKhoan> accountList = new ArrayList<>();

        // Mở kết nối đến cơ sở dữ liệu
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Truy vấn cơ sở dữ liệu để lấy danh sách tài khoản người dùng từ bảng Users
        Cursor cursor = db.rawQuery("SELECT id,email,password FROM users", null);

        // Duyệt qua các dòng kết quả
        if (cursor.moveToFirst()) {
            do {
                // Lấy giá trị ID và tên danh mục từ cột "id" và "email"
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
                @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));

                // Tạo đối tượng TaiKhoan với ID và email tương ứng
                TaiKhoan taiKhoan = new TaiKhoan(id, email,password);

                // Thêm tài khoản vào danh sách
                accountList.add(taiKhoan);
            } while (cursor.moveToNext());
        }

        // Đóng kết nối và trả về danh sách tài khoản
        cursor.close();
        db.close();
        return accountList;
    }

    private class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {
        private List<TaiKhoan> accountList;

        public AccountAdapter(List<TaiKhoan> accountList) {
            this.accountList = accountList;
        }

        @Override
        public AccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_account, parent, false);
            return new AccountViewHolder(view);
        }

        @Override
        public void onBindViewHolder(AccountViewHolder holder, int position) {
            TaiKhoan taiKhoan = accountList.get(position);
            holder.textAccount.setText(taiKhoan.getEmail());
            holder.textID.setText(String.valueOf(taiKhoan.getID()));
            holder.textmk.setText(String.valueOf(taiKhoan.getPassword()));
            holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Xử lý sự kiện xóa tài khoản ở đây
                    // Ví dụ: Gọi phương thức deleteAccount(taiKhoan) để xóa tài khoản
                    deleteAccount(taiKhoan);
                    Toast.makeText(QL_taikhoan.this, "Đã xóa tài khoản", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return accountList.size();
        }

        public void setAccountList(List<TaiKhoan> accountList) {
            this.accountList = accountList;
            notifyDataSetChanged();
        }

        public class AccountViewHolder extends RecyclerView.ViewHolder {
            public TextView textAccount;
            public Button buttonDelete;
            public TextView textID;
            public TextView textmk;

            public AccountViewHolder(View itemView) {
                super(itemView);
                textID = itemView.findViewById(R.id.text_id);
                textAccount = itemView.findViewById(R.id.text_account);
                textmk = itemView.findViewById(R.id.text_mk);
                buttonDelete = itemView.findViewById(R.id.button_delete);
            }
        }
    }

    public void deleteAccount(TaiKhoan taiKhoan) {
        // Xử lý logic xóa tài khoản ở đây
        // Mở kết nối đến cơ sở dữ liệu
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Xóa tài khoản từ bảng Users với ID tương ứng
        db.delete("users", "id=?", new String[]{String.valueOf(taiKhoan.getID())});
        // Đóng kết nối
        db.close();
        // Cập nhật danh sách tài khoản và thông báo cho adapter
        accountList = getAccountList();
        accountAdapter.setAccountList(accountList);
    }
}