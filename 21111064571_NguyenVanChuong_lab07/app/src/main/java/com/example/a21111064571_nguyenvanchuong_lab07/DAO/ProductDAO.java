package com.example.a21111064571_nguyenvanchuong_lab07.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.a21111064571_nguyenvanchuong_lab07.DataBase.ProductDBHelper;
import com.example.a21111064571_nguyenvanchuong_lab07.model.Product;

import java.util.ArrayList;

public class ProductDAO {
    private ProductDBHelper productDBHelper;
    private SQLiteDatabase sqLiteDatabase;

    public ProductDAO(Context context) {
        productDBHelper = new ProductDBHelper(context);
        sqLiteDatabase = productDBHelper.getWritableDatabase();
    }

    public long addProduct(Product product) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("maSP", product.getMaSP());
        contentValues.put("tenSP", product.getTensP());
        contentValues.put("soSP", product.getSoSP());
        contentValues.put("giaSP", product.getGia());
        long checkDB = sqLiteDatabase.insert("tableProduct", null, contentValues);
        return checkDB;
    }

    public long deleteProduct(int maSP) {
        long checkDB = sqLiteDatabase.delete("tableProduct", "maSP=?", new String[]{String.valueOf(maSP)});
        return checkDB;
    }

    public long updateProduct(Product product) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("maSP", product.getMaSP());
        contentValues.put("tenSP", product.getTensP());
        contentValues.put("soSP", product.getSoSP());
        contentValues.put("giaSP", product.getGia());
        long checkDB = sqLiteDatabase.update("tableProduct", contentValues, "maSP=?", new String[]{String.valueOf(product.getMaSP())});
        return checkDB;
    }

    public ArrayList<Product> getListProduct() {
        ArrayList<Product> productArrayList = new ArrayList<>();
        sqLiteDatabase = productDBHelper.getReadableDatabase();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * from tableProduct", null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    productArrayList.add(new Product(cursor.getInt(0),
                            cursor.getString(1), // Tên sản phẩm kiểu String
                            cursor.getInt(2), // Số lượng sản phẩm kiểu int
                            cursor.getLong(3))); // Giá sản phẩm kiểu long

                } while (cursor.moveToNext());
            }
            cursor.close(); // Đóng con trỏ sau khi hoàn thành việc sử dụng
        } catch (Exception e) {
            Log.e("TAG", e.getMessage());
        }
        return productArrayList;
    }
}