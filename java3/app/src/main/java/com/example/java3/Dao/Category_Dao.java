package com.example.java3.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.java3.database.DatabaseHelper;
import com.example.java3.model.Category;

import java.util.ArrayList;
import java.util.List;

public class Category_Dao {
    private static DatabaseHelper helper;
    public Category_Dao(Context context) {helper = new DatabaseHelper(context);}

    public long insertCatagory(String name,String img) {
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME_DM,name);
        contentValues.put(DatabaseHelper.ANH_DM,img);
        return database.insert(DatabaseHelper.TABLE_DM, null, contentValues);
    }
    public void deleteCategory(int Id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_DM,
                DatabaseHelper.ID_DM + " = ?",
                new String[]{String.valueOf(Id)});

    }
    public int updateDanhMuc(int id, String name, String img) {
        // Mở kết nối đến cơ sở dữ liệu
        SQLiteDatabase db = helper.getWritableDatabase();
        // Tạo đối tượng ContentValues để chứa giá trị cần cập nhật
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.NAME_DM, name);
        values.put(DatabaseHelper.ANH_DM, img);
        // Cập nhật giá trị mới vào bảng DanhMuc dựa trên giá trị ID
        db.update(DatabaseHelper.TABLE_DM, values, "id = ?", new String[]{String.valueOf(id)});
        // Đóng kết nối
        db.close();
        return id;
    }
    public List<Category> getAllDanhMuc() {
        List<Category> categoryList = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name,imgDM FROM category", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String img = cursor.getString(cursor.getColumnIndex("imgDM"));
                Category category = new Category(name,img);
                categoryList.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categoryList;
    }
    }

