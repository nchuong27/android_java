package com.example.java2.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.java2.KhoiTao.DanhMuc;
import com.example.java2.admin.suaDM;
import com.example.java2.admin.QLDanhMuc;
import com.example.java2.model.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class DaoDanhMuc {
    private final DatabaseHelper dbHelper;

    public DaoDanhMuc(Context context) {

        dbHelper = new DatabaseHelper(context);
    }
    public DaoDanhMuc(suaDM context) {

        dbHelper = new DatabaseHelper(context);
    }
    public DaoDanhMuc(QLDanhMuc context) {

        dbHelper = new DatabaseHelper(context);
    }
    //thêm danh mục
    public long insertDanhMuc(String ten) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.TEN_DM, ten);
        // Thêm dữ liệu vào bảng danh mục
        long id = db.insert(DatabaseHelper.TABLE_DANHMUC, null, values);
        db.close();
        return id;
    }
    public void updateDanhMuc(int id, String ten) {
        // Mở kết nối đến cơ sở dữ liệu
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Tạo đối tượng ContentValues để chứa giá trị cần cập nhật
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.TEN_DM, ten);
        // Cập nhật giá trị mới vào bảng DanhMuc dựa trên giá trị ID
        db.update(DatabaseHelper.TABLE_DANHMUC, values, "id = ?", new String[]{String.valueOf(id)});
        // Đóng kết nối
        db.close();
    }
    public void deleteDanhMuc(int id) {
        // Xử lý logic xóa danh mục ởđây. Bạn có thể sử dụng mã đã chỉnh sửa dưới đây:
        // Mở kết nối đến cơ sở dữ liệu
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Xóa danh mục từ bảng DanhMuc dựa trên giá trị ID
        db.delete("DanhMuc", "id = ?", new String[]{String.valueOf(id)});
        // Đóng kết nối
        db.close();

    }

    public List<DanhMuc> getAllDanhMuc() {
        List<DanhMuc> dmList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ten FROM DanhMuc", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String ten = cursor.getString(cursor.getColumnIndex("ten"));
                DanhMuc danhMuc = new DanhMuc(ten);
                dmList.add(danhMuc);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return dmList;
    }

}

