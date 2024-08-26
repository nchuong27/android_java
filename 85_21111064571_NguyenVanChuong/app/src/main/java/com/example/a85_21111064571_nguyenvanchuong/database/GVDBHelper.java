package com.example.a85_21111064571_nguyenvanchuong.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GVDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "GVDB";
    private static final int DB_VERSION = 1;

    public GVDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableSQL = "CREATE TABLE tableGv (\n" +
                "\tma TEXT PRIMARY KEY,\n" +
                "\tten TEXT,\n" +
                "\tngaysinh TEXT,\n" +
                "\tsdt TEXT,\n" +
                "\tkhoa TEXT,\n" +
                "\tmon TEXT,\n" +
                "\ttrinhdo TEXT\n" +
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
