package com.example.a21111064571_nguyenvanchuong_lab09.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProductDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "product_database";
    private static final int DATABASE_VERSION = 1;
    // Table names
    public static final String TABLE_PRODUCT = "products";
    public static final String TABLE_PRODUCT_DETAIL = "product_details";
    // Common column names
    public static final String COLUMN_ID = "id";
    // Product table column names
    public static final String COLUMN_PRODUCT_NAME = "name";
    public static final String COLUMN_PRODUCT_QUANTITY = "quantity";
    // Product detail table column names
    public static final String COLUMN_PRODUCT_ID = "product_id";
    public static final String COLUMN_PRODUCT_DESCRIPTION = "description";
    public static final String COLUMN_PRODUCT_PRICE = "price";
    public static final String COLUMN_PRODUCT_COLOR = "color";
    // Create table statements
    private static final String CREATE_TABLE_PRODUCT = "CREATE TABLE " + TABLE_PRODUCT + "(" +

            COLUMN_ID + " INTEGER PRIMARY KEY," +
            COLUMN_PRODUCT_NAME + " TEXT," +
            COLUMN_PRODUCT_QUANTITY + " INTEGER" + ")";

    private static final String CREATE_TABLE_PRODUCT_DETAIL = "CREATE TABLE " + TABLE_PRODUCT_DETAIL + "(" +

            COLUMN_ID + " INTEGER PRIMARY KEY," +
            COLUMN_PRODUCT_ID + " INTEGER," +
            COLUMN_PRODUCT_DESCRIPTION + " TEXT," +
            COLUMN_PRODUCT_PRICE + " REAL," +
            COLUMN_PRODUCT_COLOR + " TEXT" + "," +
            "FOREIGN KEY(" + COLUMN_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCT + "(" + COLUMN_ID + ") ON DELETE CASCADE)";
    public ProductDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PRODUCT);
        db.execSQL(CREATE_TABLE_PRODUCT_DETAIL);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_DETAIL);
// Create tables again
        onCreate(db);
    }
}
