package com.example.a85_21111064571_nguyenvanchuong.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a85_21111064571_nguyenvanchuong.R;
import com.example.a85_21111064571_nguyenvanchuong.model.GV;

import java.util.ArrayList;

public class GVAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<GV> mGVList;

    public GVAdapter(Context context, ArrayList<GV> carList) {
        mContext = context;
        mGVList = carList;
    }

    @Override
    public int getCount() {
        return mGVList.size();
    }

    @Override
    public Object getItem(int position) {
        return mGVList.get(position);
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
            view = inflater.inflate(R.layout.gv_item, null);
        }

        // Ánh xạ các thành phần trong layout của mỗi item trong ListView
        TextView textViewMa = view.findViewById(R.id.textViewMa);
        TextView textViewTen = view.findViewById(R.id.textViewTen);
        TextView textViewNgay = view.findViewById(R.id.textViewNgay);
        TextView textViewsdt = view.findViewById(R.id.textViewNamsdt);
        TextView textViewkhoa = view.findViewById(R.id.textViewKhoa);
        TextView textViewMon = view.findViewById(R.id.textViewMon);
        TextView textViewTrinh = view.findViewById(R.id.textViewTrinhdo);

        // Lấy đối tượng Car tương ứng với vị trí position
        GV gv = mGVList.get(position);

        textViewMa.setText(gv.getMa());
        textViewTen.setText(gv.getTen());
        textViewNgay.setText(gv.getNgaysinh());
        textViewsdt.setText(gv.getSdt());
        textViewkhoa.setText(gv.getKhoa());
        textViewMon.setText(gv.getMon());
        textViewTrinh.setText(gv.getTrinhdo());

        textViewMa.setTextColor(Color.RED);
        textViewTen.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        return view;
    }
}
