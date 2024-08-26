package com.example.java3.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.java3.database.DatabaseHelper;
import com.example.java3.model.CartItem;

import java.util.ArrayList;
import java.util.List;

import static com.example.java3.database.DatabaseHelper.*;

public class CartDao {
    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;
    private Context context; // Add a Context member variable

    public CartDao(Context context) {
        dbHelper = new DatabaseHelper(context); // Initialize DatabaseHelper
        this.context = context; // Store the context
    }

    private void open() {
        if (database == null || !database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }
    }

    private void close() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    // Thêm sản phẩm vào giỏ hàng
    public void addCartItem(CartItem item) {
        open();

        // Truy vấn để kiểm tra nếu sản phẩm với cùng ID, ID người dùng và kích thước đã tồn tại trong giỏ
        String query = "SELECT * FROM " + TABLE_CART +
                " WHERE " + CART_PRODUCT_ID + " = ?" +
                " AND " + CART_USER_ID + " = ?" +
                " AND " + CART_SIZE + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(item.getProductId()), String.valueOf(item.getUserId()), item.getSize()});

        if (cursor.moveToFirst()) {
            // Sản phẩm với cùng kích thước đã tồn tại, cập nhật số lượng
            @SuppressLint("Range") int currentQuantity = cursor.getInt(cursor.getColumnIndex(CART_QUANTITY));
            ContentValues values = new ContentValues();
            values.put(CART_QUANTITY, currentQuantity + item.getQuantity()); // Tăng số lượng lên theo giá trị đã chọn
            database.update(TABLE_CART, values,
                    CART_PRODUCT_ID + " = ? AND " + CART_USER_ID + " = ? AND " + CART_SIZE + " = ?",
                    new String[]{String.valueOf(item.getProductId()), String.valueOf(item.getUserId()), item.getSize()});
            Toast.makeText(context, "Số lượng sản phẩm đã được cập nhật trong giỏ hàng", Toast.LENGTH_SHORT).show();
        } else {
            // Sản phẩm với kích thước khác hoặc sản phẩm mới, thêm vào giỏ hàng
            ContentValues values = new ContentValues();
            values.put(CART_PRODUCT_ID, item.getProductId());
            values.put(CART_USER_ID, item.getUserId());
            values.put(CART_QUANTITY, item.getQuantity());
            values.put(CART_PRICE, item.getPrice());
            values.put(CART_SIZE, item.getSize());
            values.put(CART_PRODUCT_NAME, item.getProductName());
            values.put(CART_PRODUCT_IMAGE, item.getProductImage());

            long result = database.insert(TABLE_CART, null, values);
            if (result != -1) {
                Toast.makeText(context, "Sản phẩm đã được thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Lỗi khi thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            }
        }
        cursor.close();
        close();
    }
    public void removeCartItem(int id) {
        open();
        database.delete(TABLE_CART, CART_ID + " = ?", new String[]{String.valueOf(id)});
        close();
    }

    // Cập nhật sản phẩm trong giỏ hàng
    public void updateCartItem(CartItem item) {
        open();
        ContentValues values = new ContentValues();
        values.put(CART_PRODUCT_ID, item.getProductId());
        values.put(CART_USER_ID, item.getUserId());
        values.put(CART_QUANTITY, item.getQuantity());
        values.put(CART_PRICE, item.getPrice());
        values.put(CART_SIZE, item.getSize());
        values.put(CART_PRODUCT_NAME, item.getProductName());
        values.put(CART_PRODUCT_IMAGE, item.getProductImage());

        database.update(TABLE_CART, values, CART_ID + " = ?", new String[]{String.valueOf(item.getId())});
        close();
    }

    // Lấy tất cả sản phẩm trong giỏ hàng
    public List<CartItem> getCartItemsForUser(int userId) {
        open();
        List<CartItem> cartItems = new ArrayList<>();

        String selection = CART_USER_ID + " = ?";
        String[] selectionArgs = { String.valueOf(userId) };

        Cursor cursor = database.query(TABLE_CART, null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(CART_ID));
                @SuppressLint("Range") int productId = cursor.getInt(cursor.getColumnIndex(CART_PRODUCT_ID));
                @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex(CART_QUANTITY));
                @SuppressLint("Range") String price = cursor.getString(cursor.getColumnIndex(CART_PRICE));
                @SuppressLint("Range") String size = cursor.getString(cursor.getColumnIndex(CART_SIZE));
                @SuppressLint("Range") String productName = cursor.getString(cursor.getColumnIndex(CART_PRODUCT_NAME));
                @SuppressLint("Range") String productImage = cursor.getString(cursor.getColumnIndex(CART_PRODUCT_IMAGE));

                CartItem item = new CartItem(id, productId, userId, quantity, price, size, productName, productImage);
                cartItems.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return cartItems;
    }
}
