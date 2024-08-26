package com.example.a21111064571_nguyenvanchuong_lab05.Adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.a21111064571_nguyenvanchuong_lab05.PerSon;
import com.example.a21111064571_nguyenvanchuong_lab05.R;

import java.util.List;

public class PerSonAdapter extends ArrayAdapter<PerSon> {
    public PerSonAdapter(@NonNull Context context, int resource, @NonNull List<PerSon> perSonList) {
        super(context, resource, perSonList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    @NonNull
    private View createView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }
        ImageView imageViewSpriner = convertView.findViewById(R.id.imageViewSpriner);
        TextView textViewSpriner = convertView.findViewById(R.id.textViewSpriner);

        PerSon perSon = getItem(position);
        if (perSon != null) {
            imageViewSpriner.setImageResource(perSon.getImgPerSon());
            textViewSpriner.setText(perSon.getNamePerSon());
        }
        return convertView;
    }
}
