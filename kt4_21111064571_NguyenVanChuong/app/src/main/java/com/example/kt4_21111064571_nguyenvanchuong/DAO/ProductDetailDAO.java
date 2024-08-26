package com.example.kt4_21111064571_nguyenvanchuong.DAO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kt4_21111064571_nguyenvanchuong.database.ProductDBHelper;
import com.example.kt4_21111064571_nguyenvanchuong.model.ProductDetail;


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
        values.put(ProductDBHelper.COLUMN_PRODUCT_BIETDANH, productDetail.getBietDanh());
        values.put(ProductDBHelper.COLUMN_PRODUCT_NGAY, productDetail.getNgay());
        values.put(ProductDBHelper.COLUMN_PRODUCT_GIO, productDetail.getGio());
        return database.insert(ProductDBHelper.TABLE_PRODUCT_DETAIL, null, values);
    }
    // Cập nhật thông tin chi tiết sản phẩm
    public int updateProductDetail(ProductDetail productDetail) {
        ContentValues values = new ContentValues();
        values.put(ProductDBHelper.COLUMN_PRODUCT_BIETDANH, productDetail.getBietDanh());
        values.put(ProductDBHelper.COLUMN_PRODUCT_NGAY, productDetail.getNgay());
        values.put(ProductDBHelper.COLUMN_PRODUCT_GIO, productDetail.getGio());
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
        int descriptionIndex = cursor.getColumnIndex(ProductDBHelper.COLUMN_PRODUCT_BIETDANH);
        int priceIndex = cursor.getColumnIndex(ProductDBHelper.COLUMN_PRODUCT_NGAY);
        int colorIndex = cursor.getColumnIndex(ProductDBHelper.COLUMN_PRODUCT_GIO);
        int id = cursor.getInt(idIndex);
        int productId = cursor.getInt(productIdIndex);
        String bietdanh = cursor.getString(descriptionIndex);
        String ngay = cursor.getString(priceIndex);
        String gio = cursor.getString(colorIndex);
        return new ProductDetail(id, productId, bietdanh,ngay , gio);
    }
}
