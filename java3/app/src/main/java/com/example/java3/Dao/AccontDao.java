package com.example.java3.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.java3.MainActivity;
import com.example.java3.database.DatabaseHelper;
import com.example.java3.model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccontDao {
    private static  DatabaseHelper helper;
    public AccontDao(Context context) {
        helper = new DatabaseHelper(context);
    }

    public AccontDao(DatabaseHelper databaseHelper) {
    }

    public long insertUser(String name, String email, String password, int role) {
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME,name);
        contentValues.put(DatabaseHelper.EMAIL, email);
        contentValues.put(DatabaseHelper.PASSWORD, password);
        contentValues.put(DatabaseHelper.ROLE, role);
        return database.insert(DatabaseHelper.TABLE_USERS, null, contentValues);
    }
    public void deleteUser(int Id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_USERS,
                DatabaseHelper.ID + " = ?",
                new String[]{String.valueOf(Id)});

    }

    public boolean checkUser(String name, String password) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String selection = DatabaseHelper.NAME + " = ? AND " + DatabaseHelper.PASSWORD + " = ?";
        String[] selectionArgs = {name, password};
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, null, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }
    public List<Account> getUsersByPartialUsername(String partialUsername) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USERS,
                null, // lấy tất cả các cột
                DatabaseHelper.NAME + " LIKE ?",
                new String[]{"%" + partialUsername + "%"},
                null,
                null,
                null
        );

        List<Account> accounts = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.NAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.EMAIL));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PASSWORD));
                Account account = new Account(id, name, email, password);
                accounts.add(account);
            }
            cursor.close();
        }
        return accounts;
    }

    @SuppressLint("Range")
    public int getUserRole(String name, String password) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String selection = DatabaseHelper.NAME + " = ? AND " + DatabaseHelper.PASSWORD + " = ?";
        String[] selectionArgs = {name, password};
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, new String[]{DatabaseHelper.ROLE}, selection, selectionArgs, null, null, null);
        int role = -1; // Giá trị mặc định nếu không tìm thấy người dùng
        if (cursor.moveToFirst()) {
            role = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ROLE));
        }
        cursor.close();
        return role;
    }

    @SuppressLint("Range")
    public int getUserId(String name, String password) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String selection = DatabaseHelper.NAME + " = ? AND " + DatabaseHelper.PASSWORD + " = ?";
        String[] selectionArgs = {name, password};
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, new String[]{DatabaseHelper.ID}, selection, selectionArgs, null, null, null);
        int userId = -1; // Giá trị mặc định nếu không tìm thấy người dùng
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ID));
        }
        cursor.close();
        return userId;
    }
}

