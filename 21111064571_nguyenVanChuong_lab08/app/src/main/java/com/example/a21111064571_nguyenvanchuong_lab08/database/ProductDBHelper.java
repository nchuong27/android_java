package com.example.a21111064571_nguyenvanchuong_lab08.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProductDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "ProductDB";
    private static final int DB_VERSION = 1;
    public ProductDBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table_sql="CREATE TABLE tableProduct(\n" +

                "\tmaSP integer PRIMARY KEY,\n" +
                " tenSP text,\n" +
                " soSP integer,\n" +
                " giaSP double\n" +
                ")";
        db.execSQL(create_table_sql);


        if (isTableEmpty(db, "tableProduct")) {
            String insert_table_sql = "INSERT INTO tableProduct VALUES (1,'ten1',12,13),(2,'ten2',22,23)";
            db.execSQL(insert_table_sql);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion!=newVersion){
            db.execSQL("DROP TABLE IF EXISTS tableProduct");
            onCreate(db);
        }
    }
    private boolean isTableEmpty(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("SELECT count(*) FROM " + tableName, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);

            cursor.close();
            return count == 0;
        }
        return true;
    }
}
