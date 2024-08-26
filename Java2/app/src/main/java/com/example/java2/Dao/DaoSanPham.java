package com.example.java2.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.java2.KhoiTao.SanPham;
import com.example.java2.model.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class DaoSanPham {
    private final DatabaseHelper dbHelper;

    public DaoSanPham(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    //thêm danh mục
    public long insertSanPham(String ten, String gia, String mo_ta, String anh, int id_dm) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.SanPham_TEN, ten);
        values.put(DatabaseHelper.SanPham_GIA, gia);
        values.put(DatabaseHelper.SanPham_MOTA, mo_ta);
        values.put(DatabaseHelper.SanPham_Anh, anh);
        values.put(DatabaseHelper.SanPham_DanhMuc_ID, id_dm);

        // Thêm dữ liệu vào bảng danh mục
        long id = db.insert(DatabaseHelper.TABLE_SanPham, null, values);
        db.close();
        return id;
    }

    public void updateSanPham(int id, String ten, String gia, String mo_ta, String anh, int id_dm) {
        // Mở kết nối đến cơ sở dữ liệu
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Tạo đối tượng ContentValues để chứa giá trị cần cập nhật
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.SanPham_TEN, ten);
        values.put(DatabaseHelper.SanPham_GIA, gia);
        values.put(DatabaseHelper.SanPham_MOTA, mo_ta);
        values.put(DatabaseHelper.SanPham_Anh, anh);
        values.put(DatabaseHelper.SanPham_DanhMuc_ID, id_dm);
        // Cập nhật giá trị mới vào bảng DanhMuc dựa trên giá trị ID
        db.update(DatabaseHelper.TABLE_SanPham, values, "Id_sp = ?", new String[]{String.valueOf(id)});
        // Đóng kết nối
        db.close();
    }

    public void deleteSanPham(int id) {
        // Xử lý logic xóa danh mục ởđây. Bạn có thể sử dụng mã đã chỉnh sửa dưới đây:
        // Mở kết nối đến cơ sở dữ liệu
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Xóa danh mục từ bảng DanhMuc dựa trên giá trị ID
        db.delete("SanPham", "Id_sp = ?", new String[]{String.valueOf(id)});
        // Đóng kết nối
        db.close();

    }

    public List<SanPham> getAllSanPham() {
        List<SanPham> spList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " +
                DatabaseHelper.SanPham_TEN + ", " +
                DatabaseHelper.SanPham_GIA + ", " +
                DatabaseHelper.SanPham_MOTA + ", " +
                DatabaseHelper.SanPham_Anh +
                " FROM " + DatabaseHelper.TABLE_SanPham, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String ten = cursor.getString(cursor.getColumnIndex(DatabaseHelper.SanPham_TEN));
                @SuppressLint("Range") String gia = cursor.getString(cursor.getColumnIndex(DatabaseHelper.SanPham_GIA));
                @SuppressLint("Range") String anh = cursor.getString(cursor.getColumnIndex(DatabaseHelper.SanPham_Anh));
                @SuppressLint("Range") String mota = cursor.getString(cursor.getColumnIndex(DatabaseHelper.SanPham_MOTA));
                SanPham sanPham = new SanPham(ten, gia, anh,mota);
                spList.add(sanPham);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return spList;
    }
}

