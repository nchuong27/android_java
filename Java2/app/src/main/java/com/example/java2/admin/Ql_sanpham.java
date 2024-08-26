package com.example.java2.admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.java2.Adapter.DMAdapter;
import com.example.java2.Adapter.SanPhamAdapter;
import com.example.java2.Dao.DaoDanhMuc;
import com.example.java2.KhoiTao.DanhMuc;
import com.example.java2.KhoiTao.SanPham;
import com.example.java2.R;
import com.example.java2.Dao.DaoSanPham;
import com.example.java2.model.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class Ql_sanpham extends AppCompatActivity {

    public static final int REQUEST_CODE_SUA_SP = 2;
    private static final int REQUEST_CODE_THEM_SP = 1;

    private RecyclerView recyclerViewSP;
    private DatabaseHelper dbHelper;
    private SanPhamAdapter spAdapter;
    private List<SanPham> spList;
    private DaoSanPham daoSanPham;
    private Button buttonaddsp;
    private String anh = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ql_sanpham);

        dbHelper = new DatabaseHelper(this);
        daoSanPham = new DaoSanPham(this);

        recyclerViewSP = findViewById(R.id.recycler_sp);
        buttonaddsp = findViewById(R.id.btnaddSp);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewSP.setLayoutManager(layoutManager);

        spList = getSPList();
        spAdapter = new SanPhamAdapter(spList, this);
        recyclerViewSP.setAdapter(spAdapter);
        spAdapter.notifyDataSetChanged();

        buttonaddsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Ql_sanpham.this, ThemSP.class);
                startActivityForResult(i, REQUEST_CODE_THEM_SP);
            }
        });
    }

    private List<SanPham> getSPList() {
        List<SanPham> spList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " +
                DatabaseHelper.SanPham_ID + ", " +
                DatabaseHelper.SanPham_TEN + ", " +
                DatabaseHelper.SanPham_GIA + ", " +
                DatabaseHelper.SanPham_Anh + ", " +
                DatabaseHelper.SanPham_MOTA + ", " +
                DatabaseHelper.SanPham_DanhMuc_ID +
                " FROM " + DatabaseHelper.TABLE_SanPham, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.SanPham_ID));
                @SuppressLint("Range") String ten = cursor.getString(cursor.getColumnIndex(DatabaseHelper.SanPham_TEN));
                @SuppressLint("Range") String gia = cursor.getString(cursor.getColumnIndex(DatabaseHelper.SanPham_GIA));
                @SuppressLint("Range") String anh = cursor.getString(cursor.getColumnIndex(DatabaseHelper.SanPham_Anh));
                @SuppressLint("Range") String moTa = cursor.getString(cursor.getColumnIndex(DatabaseHelper.SanPham_MOTA));
                @SuppressLint("Range") int idDanhMuc = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.SanPham_DanhMuc_ID));
                SanPham sanPham = new SanPham(id, ten, gia, moTa, anh, idDanhMuc);
                spList.add(sanPham);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return spList;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_THEM_SP && resultCode == RESULT_OK) {
            spList = getSPList();
            spAdapter.setSPList(spList);
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("ImagePath", anh); // Lấy đường dẫn ảnh từ biến anh trong ThemSP
            editor.apply();
        } else if (requestCode == REQUEST_CODE_SUA_SP && resultCode == RESULT_OK) {
            String tensp = data.getStringExtra("Ten_sp");
            String giasp = data.getStringExtra("Gia");
            String mota_sp = data.getStringExtra("Mo_ta");
            String anh_sp = data.getStringExtra("Anh_sp");
            int id_dm = data.getIntExtra("Id_dm",-1);
            int idsp = data.getIntExtra("Id_sp", -1);
            if (idsp != -1 && tensp != null && giasp!=null && mota_sp!=null && anh_sp!=null && id_dm != -1) {
                daoSanPham.updateSanPham(idsp, tensp,giasp,mota_sp,anh_sp,id_dm);
                spList = getSPList();
                spAdapter.setSPList(spList);
            }
        }
    }
}
