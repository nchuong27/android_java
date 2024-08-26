package com.example.a21111064571_nguyenvanchuong_lab09.DAO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.a21111064571_nguyenvanchuong_lab09.database.ProductDBHelper;
import com.example.a21111064571_nguyenvanchuong_lab09.model.ProductDetail;

public class ProductDetailDAO {
    private SQLiteDatabase database;
    private ProductDBHelper dbHelper;
    public ProductDetailDAO(Context context) {
        dbHelper = new ProductDBHelper(context);
    }
    public void open() {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }
    // Thêm một chi tiết sản phẩm vào cơ sở dữ liệu
    public long addProductDetail(ProductDetail productDetail) {
        ContentValues values = new ContentValues();
        values.put(ProductDBHelper.COLUMN_PRODUCT_ID, productDetail.getProductId());
        values.put(ProductDBHelper.COLUMN_PRODUCT_DESCRIPTION, productDetail.getDescription());
        values.put(ProductDBHelper.COLUMN_PRODUCT_PRICE, productDetail.getPrice());
        values.put(ProductDBHelper.COLUMN_PRODUCT_COLOR, productDetail.getColor());
        return database.insert(ProductDBHelper.TABLE_PRODUCT_DETAIL, null, values);
    }
    // Cập nhật thông tin chi tiết sản phẩm
    public int updateProductDetail(ProductDetail productDetail) {
        ContentValues values = new ContentValues();
        values.put(ProductDBHelper.COLUMN_PRODUCT_DESCRIPTION, productDetail.getDescription());
        values.put(ProductDBHelper.COLUMN_PRODUCT_PRICE, productDetail.getPrice());
        values.put(ProductDBHelper.COLUMN_PRODUCT_COLOR, productDetail.getColor());
        return database.update(ProductDBHelper.TABLE_PRODUCT_DETAIL, values,

                ProductDBHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(productDetail.getId())});

    }
    // Xóa một chi tiết sản phẩm theo ID
    public void deleteProductDetail(int productDetailId) {
        database.delete(ProductDBHelper.TABLE_PRODUCT_DETAIL,

                ProductDBHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(productDetailId)});
    }
    // Lấy chi tiết sản phẩm dựa trên ID
    public ProductDetail getProductDetailByProductId(int productId) {
        Cursor cursor = database.query(ProductDBHelper.TABLE_PRODUCT_DETAIL, null,

                ProductDBHelper.COLUMN_PRODUCT_ID + " = ?",
                new String[]{String.valueOf(productId)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            ProductDetail productDetail = cursorToProductDetail(cursor);
            cursor.close();
            return productDetail;
        } else {
            return null;
        }
    }
    private ProductDetail cursorToProductDetail(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(ProductDBHelper.COLUMN_ID);
        int productIdIndex = cursor.getColumnIndex(ProductDBHelper.COLUMN_PRODUCT_ID);
        int descriptionIndex = cursor.getColumnIndex(ProductDBHelper.COLUMN_PRODUCT_DESCRIPTION);
        int priceIndex = cursor.getColumnIndex(ProductDBHelper.COLUMN_PRODUCT_PRICE);
        int colorIndex = cursor.getColumnIndex(ProductDBHelper.COLUMN_PRODUCT_COLOR);
        int id = cursor.getInt(idIndex);
        int productId = cursor.getInt(productIdIndex);
        String description = cursor.getString(descriptionIndex);
        double price = cursor.getDouble(priceIndex);
        String color = cursor.getString(colorIndex);
        return new ProductDetail(id, productId, description, price, color);
    }
}
