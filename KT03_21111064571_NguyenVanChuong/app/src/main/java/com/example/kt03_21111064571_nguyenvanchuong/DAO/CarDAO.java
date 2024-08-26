package com.example.kt03_21111064571_nguyenvanchuong.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.kt03_21111064571_nguyenvanchuong.database.CarDBHelper;
import com.example.kt03_21111064571_nguyenvanchuong.model.Car;

import java.util.ArrayList;

public class CarDAO {
    private CarDBHelper carDBHelper;
    private SQLiteDatabase sqLiteDatabase;

    public CarDAO(Context context){
        carDBHelper = new CarDBHelper(context);
        sqLiteDatabase = carDBHelper.getWritableDatabase();
    }


    public long addCar(Car car) {
        if (isCarExists(car.getBienSoXe())) {
            return -1;
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("bienSoXe", car.getBienSoXe());
            contentValues.put("tenXe", car.getTenXe());
            contentValues.put("hangXe", car.getHangXe());
            contentValues.put("namSanXuat", car.getNamSanXuat());
            contentValues.put("moTaXe", car.getMoTaXe());
            contentValues.put("tenLaiXe", car.getTenLaiXe());

            long checkDB = sqLiteDatabase.insert("tableCar", null, contentValues);
            return checkDB;
        }
    }

    public long deleteCar(String bienSoXe){
        long checkDB = sqLiteDatabase.delete("tableCar", "bienSoXe=?", new String[]{bienSoXe});
        return checkDB;
    }

    public long updateCar(Car car){
        ContentValues contentValues = new ContentValues();
        contentValues.put("bienSoXe", car.getBienSoXe());
        contentValues.put("tenXe", car.getTenXe());
        contentValues.put("hangXe", car.getHangXe());
        contentValues.put("namSanXuat", car.getNamSanXuat());
        contentValues.put("moTaXe", car.getMoTaXe());
        contentValues.put("tenLaiXe", car.getTenLaiXe());

        long checkDB = sqLiteDatabase.update("tableCar", contentValues, "bienSoXe=?", new String[]{car.getBienSoXe()});
        return checkDB;

    }

    public ArrayList<Car> getListCar(){
        ArrayList<Car> carArrayList = new ArrayList<>();
        sqLiteDatabase = carDBHelper.getReadableDatabase();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM tableCar", null);

            if (cursor.getCount() > 0){
                cursor.moveToFirst();
                do {
                    Car car = new Car(cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getInt(3),
                            cursor.getString(4),
                            cursor.getString(5));
                    carArrayList.add(car);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("CarDAO", e.getMessage());
        }
        return carArrayList;
    }

    public boolean isCarExists(String bienSoXe) {
        sqLiteDatabase = carDBHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query("tableCar", null, "bienSoXe=?", new String[]{bienSoXe}, null, null, null);
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
