package com.example.a21111064571nguyenvanchuonglab06.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.a21111064571nguyenvanchuonglab06.R;
import com.example.a21111064571nguyenvanchuonglab06.model.PerSon;

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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_person, parent, false);
        }
        ImageView imageViewList = convertView.findViewById(R.id.imgPerson);
        TextView textViewList = convertView.findViewById(R.id.txtPersonName);

        PerSon perSon = getItem(position);
        if (perSon != null) {
            imageViewList.setImageResource(perSon.getImgPerSon());
            textViewList.setText(perSon.getNamePerSon());
        }
        return convertView;
    }
}
