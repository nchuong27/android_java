package com.example.a21111064571_nguyenvanchuong_lab08.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.a21111064571_nguyenvanchuong_lab08.database.ProductDBHelper;
import com.example.a21111064571_nguyenvanchuong_lab08.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private ProductDBHelper productDBHelper;
    private SQLiteDatabase sqLiteDatabase;
    public ProductDao(Context context){
        productDBHelper = new ProductDBHelper(context);
    }
    public void open(){
        sqLiteDatabase=productDBHelper.getWritableDatabase();
    }
    public void close() {
        productDBHelper.close();
    }
    public long addProduct(Product product){
        ContentValues contentValues = new ContentValues();
        contentValues.put("maSP", product.getMaSP());
        contentValues.put("tenSP", product.getTenSP());
        contentValues.put("soSP", product.getSoLuong());
        contentValues.put("giaSP", product.getGiaSP());
        return sqLiteDatabase.insert("tableProduct", null, contentValues);
    }
    public boolean deleteProduct(int maSP) {
        int rowsAffected = sqLiteDatabase.delete("tableProduct", "maSP=?", new

                String[]{String.valueOf(maSP)});
        return rowsAffected > 0;
    }
    public boolean updateProduct(Product product) {
        SQLiteDatabase db = productDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenSP", product.getTenSP());
        values.put("soSP", product.getSoLuong());
        values.put("giaSP", product.getGiaSP());
        int rowsAffected = db.update("tableProduct", values, "maSP = ?", new String[]{String.valueOf(product.getMaSP())});
        db.close();
        return rowsAffected > 0;
    }
    public List<Product> getListProduct() {
        List<Product> productList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM tableProduct", null);
            if (cursor != null && cursor.moveToFirst()) {
                int maSPIndex = cursor.getColumnIndex("maSP");
                int tenSPIndex = cursor.getColumnIndex("tenSP");
                int soSPIndex = cursor.getColumnIndex("soSP");
                int giaSPIndex = cursor.getColumnIndex("giaSP");
                do {
                    if (maSPIndex != -1 && tenSPIndex != -1 && soSPIndex != -1 &&

                            giaSPIndex != -1) {

                        int maSP = cursor.getInt(maSPIndex);
                        String tenSP = cursor.getString(tenSPIndex);
                        int soSP = cursor.getInt(soSPIndex);
                        double giaSP = cursor.getDouble(giaSPIndex);
                        Product product = new Product(maSP, tenSP, soSP, giaSP);
                        productList.add(product);
                    } else {
                        Log.e("ProdutcDAO", "Không tìm thấy chỉ mục cột");
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("ProdutcDAO", "Lỗi khi lấy thông tin sản phẩm từ CSDL", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return productList;
    }
}
