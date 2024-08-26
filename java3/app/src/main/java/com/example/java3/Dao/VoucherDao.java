package com.example.java3.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.java3.database.DatabaseHelper;

public class VoucherDao {
    private static DatabaseHelper databaseHelper;

    public VoucherDao(Context context) {databaseHelper = new DatabaseHelper(context);}
    public long insertVoucher(String name, String des,int giam_gia,long toi_thieu) {
        SQLiteDatabase database = null;
        long result = -1; // Giá trị mặc định nếu không chèn thành công

        try {
            database = databaseHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.CODE,name);
            contentValues.put(DatabaseHelper.DESCRIPTION, des);
            contentValues.put(DatabaseHelper.DISCOUNT_PERCENT, giam_gia);
            contentValues.put(DatabaseHelper.MINIMUM_PURCHASE_AMOUNT, toi_thieu);
            result = database.insert(DatabaseHelper.TABLE_DISCOUNT, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return result;
    }
    public void deleteVoucher(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_DISCOUNT,
                DatabaseHelper.ID_DISCOUNT + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public long updateVoucher(int id, String name, String des,int giam_gia , long toi_thieu) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.CODE,name);
        contentValues.put(DatabaseHelper.DESCRIPTION, des);
        contentValues.put(DatabaseHelper.DISCOUNT_PERCENT, giam_gia);
        contentValues.put(DatabaseHelper.MINIMUM_PURCHASE_AMOUNT, toi_thieu);
        int rowsAffected = db.update(DatabaseHelper.TABLE_DISCOUNT, contentValues, "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;
    }
}
