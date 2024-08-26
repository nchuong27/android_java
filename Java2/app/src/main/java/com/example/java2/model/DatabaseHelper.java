package com.example.java2.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.java2.KhoiTao.DanhMuc;
import com.example.java2.admin.themDm;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myapp.db";
    private static final int DATABASE_VERSION = 4;
    //cột user
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ROLE = "role";


    // Tên bảng và cột danh mục
    public static final String TABLE_DANHMUC = "DanhMuc";
    private static final String ID_DM = "id";
    public static final String TEN_DM = "ten";

    //cột sản phẩm
    public static final String TABLE_SanPham = "SanPham";
    public static final String SanPham_ID = "Id_sp";
    public static final String SanPham_TEN = "Ten_sp";
    public static final String SanPham_Anh = "Anh_sp";
    public static final String SanPham_GIA = "Gia";
    public static final String SanPham_MOTA  = "Mo_ta";
    public static final String SanPham_DanhMuc_ID = "Id_dm";
    //cột đơn hàng
    private static final String TABLE_DonHang = "DonHang";
    private static final String COLUMN_DonHang_ID = "id_dh";
    private static final String COLUMN_DonHang_SanPham_ID = "id_sp";
    private static final String COLUMN_DonHang_USERS_ID = "id_user";

    public DatabaseHelper(themDm context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //bảng user
        String createTableUsers = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_ROLE + " INTEGER"
                + ")";
        db.execSQL(createTableUsers);
        //bảng sản phẩm
        String createProductTable = "CREATE TABLE " + TABLE_SanPham + "("
                + SanPham_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + SanPham_TEN + " TEXT NOT NULL,"
                + SanPham_Anh + " TEXT NOT NULL,"
                + SanPham_GIA + " TEXT NOT NULL,"
                + SanPham_MOTA + " TEXT NOT NULL,"
                + SanPham_DanhMuc_ID + " INTEGER NOT NULL,"
                + "FOREIGN KEY(" + SanPham_DanhMuc_ID + ") REFERENCES " + TABLE_DANHMUC + "(" + ID_DM + ")"
                + ")";
        db.execSQL(createProductTable);

        //bảng DanhMuc
        String createDanhMucTable = "CREATE TABLE " + TABLE_DANHMUC + "(" +
                ID_DM + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                TEN_DM + " TEXT NOT NULL"
                + ")";
        db.execSQL(createDanhMucTable);

        //bảng đơn hàng
        String createOrderTable = "CREATE TABLE " + TABLE_DonHang + "("
                + COLUMN_DonHang_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DonHang_SanPham_ID + " INTEGER NOT NULL,"
                + COLUMN_DonHang_USERS_ID + " INTEGER NOT NULL,"
                + "FOREIGN KEY(" + COLUMN_DonHang_SanPham_ID + ") REFERENCES " + TABLE_SanPham + "(" + SanPham_ID + "),"
                + "FOREIGN KEY(" + COLUMN_DonHang_USERS_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + ")"
                + ")";
        db.execSQL(createOrderTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ nếu tồn tại
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DANHMUC);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SanPham);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DonHang);

        // Tạo lại bảng
        onCreate(db);
    }

    private boolean isLoggedIn = false; // Biến để theo dõi trạng thái đăng nhập
    public void logout() {
        isLoggedIn = false;
    }
    public boolean isLoggedIn() {
        return isLoggedIn;
    }
    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }


    public List<String> getAllDanhMucNames() {
        List<String> danhMucNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, ten FROM DanhMuc", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String ten = cursor.getString(cursor.getColumnIndex("ten"));
                DanhMuc danhMuc = new DanhMuc(id, ten);
                danhMucNames.add(String.valueOf(danhMuc));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return danhMucNames;
    }
}
