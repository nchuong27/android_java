package com.example.a85_21111064571_nguyenvanchuong.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.a85_21111064571_nguyenvanchuong.database.GVDBHelper;
import com.example.a85_21111064571_nguyenvanchuong.model.GV;


import java.util.ArrayList;

public class GVDAO {
    private static GVDBHelper gvdbHelper;
    private static SQLiteDatabase sqLiteDatabase;

    public GVDAO(Context context){
        gvdbHelper = new GVDBHelper(context);
        sqLiteDatabase = gvdbHelper.getWritableDatabase();
    }


    public long addGv(GV gv ) {
        if (isCarExists(gv.getMa())) {
            return -1;
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("ma", gv.getMa());
            contentValues.put("ten", gv.getTen());
            contentValues.put("ngaysinh", gv.getNgaysinh());
            contentValues.put("sdt", gv.getSdt());
            contentValues.put("khoa", gv.getKhoa());
            contentValues.put("mon", gv.getMon());
            contentValues.put("trinhdo", gv.getTrinhdo());

            long checkDB = sqLiteDatabase.insert("tableGv", null, contentValues);
            return checkDB;
        }
    }

    public long deleteGv(String ma){
        long checkDB = sqLiteDatabase.delete("tableGv", "ma=?", new String[]{ma});
        return checkDB;
    }

    public long updateGv(GV gv){
        ContentValues contentValues = new ContentValues();
        contentValues.put("ma", gv.getMa());
        contentValues.put("ten", gv.getTen());
        contentValues.put("ngaysinh", gv.getNgaysinh());
        contentValues.put("sdt", gv.getSdt());
        contentValues.put("khoa", gv.getKhoa());
        contentValues.put("mon", gv.getMon());
        contentValues.put("trinhdo", gv.getTrinhdo());

        long checkDB = sqLiteDatabase.update("tableGv", contentValues, "ma=?", new String[]{gv.getMa()});
        return checkDB;

    }

    public static ArrayList<GV> getListGV(){
        ArrayList<GV> gvArrayList = new ArrayList<>();
        sqLiteDatabase = gvdbHelper.getReadableDatabase();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM tableGv", null);

            if (cursor.getCount() > 0){
                cursor.moveToFirst();
                do {
                   GV gv = new GV(cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6));
                    gvArrayList.add(gv);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("GVDAO", e.getMessage());
        }
        return gvArrayList;
    }

    public boolean isCarExists(String ma) {
        sqLiteDatabase = gvdbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query("tableGv", null, "ma=?", new String[]{ma}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                return true;
            }
        } catch (Exception e) {
            Log.e("CarDAO", e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }

}
