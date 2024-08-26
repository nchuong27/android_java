package com.example.java3.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.java3.model.Category;
import com.example.java3.model.Product;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myapp.db";
    private static final int DATABASE_VERSION = 2; // Cập nhật phiên bản cơ sở dữ liệu

    // Table user
    public static final String TABLE_USERS = "users";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String ROLE = "role";

    // Table category
    public static final String TABLE_DM = "category";
    public static final String ID_DM = "id";
    public static final String NAME_DM = "name";
    public static final String ANH_DM = "imgDM";

    // Table product
    public static final String TABLE_SP = "product";
    public static final String ID_SP = "id";
    public static final String ANH = "img";
    public static final String ANH_NB = "IMG_NB";
    public static final String NAME_SP = "name";
    public static final String MO_TA = "description";
    public static final String GIA = "price";
    public static final String ORIGINAL_PRICE = "original_price";
    public static final String GIAM_GIA = "discount";
    public static final String SO_LUONG = "quantity";
    public static final String NOI_BAT = "outstanding";
    public static final String DM_SP = "cate_product";
    public static final String RATE_SP = "rate";
    public static final String REVIEW_COUNT = "riew_count";

    // Table discount
    public static final String TABLE_DISCOUNT = "Discount";
    public static final String ID_DISCOUNT = "id";
    public static final String CODE = "code";
    public static final String DESCRIPTION = "description";
    public static final String DISCOUNT_PERCENT = "discount_percent";
    public static final String MINIMUM_PURCHASE_AMOUNT = "minimum_purchase_amount";

    // Table reviews
    private static final String TABLE_REVIEWS = "Reviews";
    private static final String REVIEW_ID = "review_id";
    private static final String REVIEW_PRODUCT_ID = "product_id";
    private static final String REVIEW_UserID = "user_id";
    private static final String REVIEW_XepHang = "xephang";
    private static final String REVIEW_NoiDung_DanhGia= "noidung";

    // Table cart
    public static final String TABLE_CART = "Cart";
    public static final String CART_ID = "CartID";
    public static final String CART_PRODUCT_ID = "ProductID";
    public static final String CART_USER_ID = "UserID";
    public static final String CART_PRODUCT_NAME = "ProductName";
    public static final String CART_PRODUCT_IMAGE = "ProductImage";
    public static final String CART_SIZE = "Size";
    public static final String CART_PRICE = "Price";
    public static final String CART_QUANTITY = "Quantity";
    //table pt thanh toán
    public static final String TABLE_PAYMENT = "PaymentMethods";
    public static final String PAYMEN_ID = "PaymentID";
    public static final String PAYMENT_IMG = "PaymentImg";
    public static final String PAYMENT_NAME = "PaymentName";
    public static final String PAYMENT_DES = "PaymentDes";
    // table địa chỉ
    public static final String TABLE_ADDRESS = "Address";
    public static final String ADDRESS_ID = "AddressID";
    public static final String ADDRESSUSER_ID = "UserId";
    public static final String ADDRESS_FULLNAME = "FullName";
    public static final String ADDRESS_PHONE = "Phone";
    public static final String ADDRESS_LINE = "Line";
    // table đơn hàng
    public static final String TABLE_ORDERS = "Orders";
    public static final String ORDER_ID = "OrderId";
    public static final String ORDER_USER_ID= "OrderUser";
    public static final String ORDER_DATE = "OrderDate";
    public static final String ORDER_TOTAL_AMOUNT = "OrderTotal";
    public static final String ORDER_STATUS = "OrderStatus";
    public static final String ORDER_ADDRESS_ID = "OrderAddress";
    public static final String PAYMENT_METHOD_ID = "OrderPayment";
    // table chi tiết đơn hàng
    public static final String TABLE_ORDER_DETAILS = "OrderDetails";
    public static final String  ORDER_DETAIL_ID  = "OrderDetailId";
    public static final String ORDERS_ID = "OrdersID";
    public static final String PRODUCT_ID = "ProductId";
    public static final String PRODUCT_NAME = "ProductName";
    public static final String PRODUCT_SIZE = "ProductSize";
    public static final String PRODUCT_QUANTITY = "ProductQuan";
    public static final String PRODUCT_PRICE = "ProductPrice";




    private static final String PREF_NAME = "MySharedPref";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private SharedPreferences sharedPreferences;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableUsers = "CREATE TABLE " + TABLE_USERS + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT NOT NULL, "
                + EMAIL + " TEXT NOT NULL UNIQUE, "  // Thêm chỉ số duy nhất cho email
                + PASSWORD + " TEXT NOT NULL, "
                + ROLE + " INTEGER)";
        sqLiteDatabase.execSQL(createTableUsers);

        String createTableCategory = "CREATE TABLE " + TABLE_DM + " ("
                + ID_DM + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ANH_DM + " TEXT, "
                + NAME_DM + " TEXT NOT NULL)";
        sqLiteDatabase.execSQL(createTableCategory);

        String createTableProduct = "CREATE TABLE " + TABLE_SP + " ("
                + ID_SP + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ANH + " TEXT NOT NULL, "
                + ANH_NB + " TEXT, "
                + NAME_SP + " TEXT NOT NULL, "
                + MO_TA + " TEXT NOT NULL, "
                + GIA + " TEXT NOT NULL, "
                + ORIGINAL_PRICE + " TEXT NOT NULL, "
                + GIAM_GIA + " INTEGER DEFAULT 0, "
                + SO_LUONG + " INTEGER NOT NULL, "
                + DM_SP + " INTEGER NOT NULL, "
                + NOI_BAT + " TEXT, "
                + RATE_SP + " REAL DEFAULT 0, "
                + REVIEW_COUNT + " INTEGER DEFAULT 0, "
                + "FOREIGN KEY(" + DM_SP + ") REFERENCES " + TABLE_DM + "(" + ID_DM + "))";
        sqLiteDatabase.execSQL(createTableProduct);

        String createTableDiscount = "CREATE TABLE " + TABLE_DISCOUNT + " ("
                + ID_DISCOUNT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CODE + " TEXT NOT NULL, "
                + DESCRIPTION + " TEXT, "
                + DISCOUNT_PERCENT + " REAL NOT NULL, "
                + MINIMUM_PURCHASE_AMOUNT + " REAL NOT NULL)";
        sqLiteDatabase.execSQL(createTableDiscount);

        String createTableReview = "CREATE TABLE " + TABLE_REVIEWS + " ("
                + REVIEW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + REVIEW_PRODUCT_ID + " INTEGER NOT NULL, "
                + REVIEW_UserID + " INTEGER NOT NULL, "
                + REVIEW_XepHang + " INTEGER CHECK(" + REVIEW_XepHang + " >= 1 AND " + REVIEW_XepHang + " <= 5), "
                + REVIEW_NoiDung_DanhGia + " TEXT, "
                + "FOREIGN KEY(" + REVIEW_PRODUCT_ID + ") REFERENCES " + TABLE_SP + "(" + ID_SP + "), "
                + "FOREIGN KEY(" + REVIEW_UserID + ") REFERENCES " + TABLE_USERS + "(" + ID + "))";
        sqLiteDatabase.execSQL(createTableReview);

        // Tạo bảng Cart
        String createTableCart = "CREATE TABLE " + TABLE_CART + " ("
                + CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CART_PRODUCT_ID + " INTEGER NOT NULL, "
                + CART_USER_ID + " INTEGER NOT NULL, "
                + CART_PRODUCT_IMAGE + " TEXT, "
                + CART_PRODUCT_NAME + " TEXT NOT NULL, "
                + CART_SIZE + " TEXT NOT NULL, "
                + CART_QUANTITY + " INTEGER NOT NULL, "
                + CART_PRICE + " REAL NOT NULL, "
                + "FOREIGN KEY(" + CART_PRODUCT_ID + ") REFERENCES " + TABLE_SP + "(" + ID_SP + "), "
                + "FOREIGN KEY(" + CART_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + ID + "))";
        sqLiteDatabase.execSQL(createTableCart);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        }
        // Drop các bảng cũ và tạo lại nếu cần thiết
        // sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        // sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_DM);
        // sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SP);
        // sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_DISCOUNT);
        // sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEWS);
        // onCreate(sqLiteDatabase);


    public void setLoggedIn(boolean isLoggedIn) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void logout() {
        setLoggedIn(false);
    }

    public List<Category> getAllDanhMuc() {
        List<Category> danhMucList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, name, imgDM FROM category", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String imgDM = cursor.getString(cursor.getColumnIndex("imgDM"));
                Category category = new Category(id, name,imgDM);
                danhMucList.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return danhMucList;
    }
}
