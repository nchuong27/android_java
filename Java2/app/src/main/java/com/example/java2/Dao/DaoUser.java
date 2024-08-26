package com.example.java2.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.java2.model.DatabaseHelper;

public class DaoUser {
    private final DatabaseHelper dbHelper;

    public DaoUser(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Thêm người dùng
    public long insertUser(String email, String password, int role) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_PASSWORD, password);
        values.put(DatabaseHelper.COLUMN_ROLE, role);
        return db.insert(DatabaseHelper.TABLE_USERS, null, values);
    }

    // Kiểm tra người dùng
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = DatabaseHelper.COLUMN_EMAIL + " = ? AND " + DatabaseHelper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, null, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    // Lấy vai trò của người dùng
    @SuppressLint("Range")
    public int getUserRole(String email, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = DatabaseHelper.COLUMN_EMAIL + " = ? AND " + DatabaseHelper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, new String[]{DatabaseHelper.COLUMN_ROLE}, selection, selectionArgs, null, null, null);
        int role = -1; // Giá trị mặc định nếu không tìm thấy người dùng
        if (cursor.moveToFirst()) {
            role = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ROLE));
        }
        cursor.close();
        return role;
    }

    // Kiểm tra xem vai trò có phải admin hay không
}