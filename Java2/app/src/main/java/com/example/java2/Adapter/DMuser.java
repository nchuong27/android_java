package com.example.java2.Adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.java2.KhoiTao.DanhMuc;
import com.example.java2.R;
import com.example.java2.admin.QLDanhMuc;

import java.util.List;

public class DMuser extends RecyclerView.Adapter<DMuser.DanhMucViewHolder> {
    private Context context;
    private List<DanhMuc> dmList;
    public DMuser(Context context, List<DanhMuc> dmList) {
        this.context = context;
        this.dmList = dmList;
    }

    @NonNull
    @Override
    public DMuser.DanhMucViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rc_listdm_item,parent,false);
        return new DanhMucViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DMuser.DanhMucViewHolder holder, int position) {
        DanhMuc danhMuc  = dmList.get(position);
        holder.bind(danhMuc);
    }

    @Override
    public int getItemCount() {
        return dmList.size();
    }
    public class DanhMucViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDM;


        public DanhMucViewHolder(View itemView) {
            super(itemView);
            txtDM = itemView.findViewById(R.id.txtDM);

        }

        public void bind(DanhMuc danhMuc) {
            txtDM.setText(danhMuc.getTen());
        }
    }
}
