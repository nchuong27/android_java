package com.example.a21111064571_nguyenvanchuong_lab09.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.a21111064571_nguyenvanchuong_lab09.database.ProductDBHelper;
import com.example.a21111064571_nguyenvanchuong_lab09.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private SQLiteDatabase database;
    private ProductDBHelper dbHelper;
    public ProductDAO(Context context) {
        dbHelper = new ProductDBHelper(context);
    }
    public void open() {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }
    // Tạo bảng mới
    public long addProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put(ProductDBHelper.COLUMN_PRODUCT_NAME, product.getName());
        values.put(ProductDBHelper.COLUMN_PRODUCT_QUANTITY, product.getQuantity());
        return database.insert(ProductDBHelper.TABLE_PRODUCT, null, values);
    }
    // Lấy toàn bộ thông tin bảng product
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        Cursor cursor = database.query(ProductDBHelper.TABLE_PRODUCT, null,

                null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Product product = cursorToProduct(cursor);
                products.add(product);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return products;
    }
    // Cập nhật bảng product
    public int updateProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put(ProductDBHelper.COLUMN_PRODUCT_NAME, product.getName());
        values.put(ProductDBHelper.COLUMN_PRODUCT_QUANTITY, product.getQuantity());
        return database.update(ProductDBHelper.TABLE_PRODUCT, values,

                ProductDBHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(product.getId())});

    }
    // Xóa trong bảng product theo id
    public void deleteProduct(int productId) {
        database.delete(ProductDBHelper.TABLE_PRODUCT,
                ProductDBHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(productId)});

    }
    // Chuyển đổi con trỏ thành đối tượng Sản phẩm
    private Product cursorToProduct(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(ProductDBHelper.COLUMN_ID);
        int nameIndex = cursor.getColumnIndex(ProductDBHelper.COLUMN_PRODUCT_NAME);
        int quantityIndex = cursor.getColumnIndex(ProductDBHelper.COLUMN_PRODUCT_QUANTITY);
        int id = cursor.getInt(idIndex);
        String name = cursor.getString(nameIndex);
        int quantity = cursor.getInt(quantityIndex);
        return new Product(id, name, quantity);
    }
}
