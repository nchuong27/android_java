package com.example.a21111064571_nguyenvanchuong_lab07.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a21111064571_nguyenvanchuong_lab07.R;
import com.example.a21111064571_nguyenvanchuong_lab07.model.Product;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Product> mProductList;

    public CustomAdapter(Context context, ArrayList<Product> productList) {
        mContext = context;
        mProductList = productList;
    }
    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view==null) {
            LayoutInflater inflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.custom_list_item, null);
        }
        TextView textViewMaSP = view.findViewById(R.id.txtMa);
        TextView textViewTenSP = view.findViewById(R.id.txtTen);
        TextView textViewSoSP = view.findViewById(R.id.txtSoLuong);
        TextView textViewGiaSP = view.findViewById(R.id.txtGia);

        Product product = mProductList.get(position);
        textViewMaSP.setText(String.valueOf(product.getMaSP()));
        textViewTenSP.setText(product.getTensP());
        textViewSoSP.setText(String.valueOf(product.getSoSP()));
        textViewGiaSP.setText(String.valueOf(product.getGia()));
        textViewMaSP.setTextColor(Color.RED);
        textViewTenSP.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        return view;
    }
}
