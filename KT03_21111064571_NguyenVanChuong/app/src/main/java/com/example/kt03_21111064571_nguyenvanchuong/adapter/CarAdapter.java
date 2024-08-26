package com.example.kt03_21111064571_nguyenvanchuong.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kt03_21111064571_nguyenvanchuong.R;
import com.example.kt03_21111064571_nguyenvanchuong.model.Car;

import java.util.ArrayList;

public class CarAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Car> mCarList;

    public CarAdapter(Context context, ArrayList<Car> carList) {
        mContext = context;
        mCarList = carList;
    }

    @Override
    public int getCount() {
        return mCarList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCarList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_car_item, null);
        }

        // Ánh xạ các thành phần trong layout của mỗi item trong ListView
        TextView textViewBienSoXe = view.findViewById(R.id.textViewBienSoXe);
        TextView textViewTenXe = view.findViewById(R.id.textViewTenXe);
        TextView textViewHangXe = view.findViewById(R.id.textViewHangXe);
        TextView textViewNamSanXuat = view.findViewById(R.id.textViewNamSanXuat);
        TextView textViewMoTaXe = view.findViewById(R.id.textViewMoTaXe);
        TextView textViewTenLaiXe = view.findViewById(R.id.textViewTenLaiXe);

        // Lấy đối tượng Car tương ứng với vị trí position
        Car car = mCarList.get(position);

        textViewBienSoXe.setText(car.getBienSoXe());
        textViewTenXe.setText(car.getTenXe());
        textViewHangXe.setText(car.getHangXe());
        textViewNamSanXuat.setText(String.valueOf(car.getNamSanXuat()));
        textViewMoTaXe.setText(car.getMoTaXe());
        textViewTenLaiXe.setText(car.getTenLaiXe());

        textViewBienSoXe.setTextColor(Color.BLUE);
        textViewTenXe.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        return view;
    }
}
