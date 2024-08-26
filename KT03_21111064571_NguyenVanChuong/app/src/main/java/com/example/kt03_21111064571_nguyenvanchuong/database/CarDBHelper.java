package com.example.kt03_21111064571_nguyenvanchuong.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CarDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "CarDB";
    private static final int DB_VERSION = 1;

    public CarDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableSQL = "CREATE TABLE tableCar (\n" +
                "\tbienSoXe TEXT PRIMARY KEY,\n" +
                "\ttenXe TEXT,\n" +
                "\thangXe TEXT,\n" +
                "\tnamSanXuat INTEGER,\n" +
                "\tmoTaXe TEXT,\n" +
                "\ttenLaiXe TEXT\n" +
                ")";

        sqLiteDatabase.execSQL(createTableSQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tableCar");
            onCreate(sqLiteDatabase);
        }
    }
}
