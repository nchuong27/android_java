package com.example.java3.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.java3.database.DatabaseHelper;
import com.example.java3.model.Product;

import java.util.ArrayList;
import java.util.List;

public class Product_Dao {
    private static DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    public Product_Dao(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public long insertProduct(String img, String IMG, String name, String des, String price, String original_price, int quantity, String noi_bat, String cate_product, int discount) {
        SQLiteDatabase database = null;
        long result = -1; // Giá trị mặc định nếu không chèn thành công

        try {
            database = databaseHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.ANH, img);
            contentValues.put(DatabaseHelper.ANH_NB, IMG);
            contentValues.put(DatabaseHelper.NAME_SP, name);
            contentValues.put(DatabaseHelper.MO_TA, des);
            contentValues.put(DatabaseHelper.GIA, price);
            contentValues.put(DatabaseHelper.ORIGINAL_PRICE, original_price);
            contentValues.put(DatabaseHelper.SO_LUONG, quantity);
            contentValues.put(DatabaseHelper.NOI_BAT, noi_bat);
            contentValues.put(DatabaseHelper.DM_SP, cate_product);
            contentValues.put(DatabaseHelper.GIAM_GIA, discount);

            result = database.insert(DatabaseHelper.TABLE_SP, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }

        return result;
    }

    public void deleteProduct(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_SP,
                DatabaseHelper.ID_SP + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public long updateProduct(int id, String name, String des, String price, String original_price, int quantity, String img, String IMG, String noi_bat, String cateProduct, int discount) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.ANH, img);
        contentValues.put(DatabaseHelper.ANH_NB, IMG);
        contentValues.put(DatabaseHelper.NAME_SP, name);
        contentValues.put(DatabaseHelper.MO_TA, des);
        contentValues.put(DatabaseHelper.GIA, price);
        contentValues.put(DatabaseHelper.ORIGINAL_PRICE, original_price);
        contentValues.put(DatabaseHelper.SO_LUONG, quantity);
        contentValues.put(DatabaseHelper.NOI_BAT, noi_bat);
        contentValues.put(DatabaseHelper.DM_SP, cateProduct);
        contentValues.put(DatabaseHelper.GIAM_GIA, discount);

        int rowsAffected = db.update(DatabaseHelper.TABLE_SP, contentValues, "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;
    }

    public List<Product> getAllSanPham() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        // Thêm câu lệnh ORDER BY để sắp xếp sản phẩm theo id giảm dần
        String query = "SELECT " +
                DatabaseHelper.ID_SP + ", " +
                DatabaseHelper.NAME_SP + ", " +
                DatabaseHelper.GIA + ", " +
                DatabaseHelper.GIAM_GIA + ", " +
                DatabaseHelper.MO_TA + ", " +
                DatabaseHelper.RATE_SP + ", " +
                DatabaseHelper.SO_LUONG + ", " +
                DatabaseHelper.REVIEW_COUNT + ", " +
                DatabaseHelper.ANH +
                " FROM " + DatabaseHelper.TABLE_SP +
                " ORDER BY " + DatabaseHelper.ID_SP + " DESC"; // Sắp xếp theo ID giảm dần

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String anh = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ANH));
                @SuppressLint("Range") String ten = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME_SP));
                @SuppressLint("Range") String gia = cursor.getString(cursor.getColumnIndex(DatabaseHelper.GIA));
                @SuppressLint("Range") String des = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MO_TA));
                @SuppressLint("Range") int giam = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.GIAM_GIA));
                @SuppressLint("Range") int sl = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.SO_LUONG));
                @SuppressLint("Range") float rate = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.RATE_SP));
                @SuppressLint("Range") int review = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.REVIEW_COUNT));
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ID_SP));

                Product product = new Product(anh, ten, gia, giam, rate, review, des, sl, id);
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productList;
    }


    public List<Product> getProductsByCategory(String categoryId) {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM product WHERE cate_product = ?", new String[]{String.valueOf(categoryId)});
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String anh = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ANH));
                @SuppressLint("Range") String ten = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME_SP));
                @SuppressLint("Range") String gia = cursor.getString(cursor.getColumnIndex(DatabaseHelper.GIA));
                @SuppressLint("Range") String des = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MO_TA));
                @SuppressLint("Range") int giam = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.GIAM_GIA));
                @SuppressLint("Range") int sl = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.SO_LUONG));
                @SuppressLint("Range") float rate = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.RATE_SP));
                @SuppressLint("Range") int review = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.REVIEW_COUNT));
                @SuppressLint("Range") int id  = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ID_SP));
                Product product = new Product(anh, ten, gia, giam, rate, review,des,sl,id);
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productList;
    }
}
