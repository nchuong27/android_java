package com.example.java2.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.java2.Dao.DaoSanPham;
import com.example.java2.KhoiTao.SanPham;
import com.example.java2.R;
import com.example.java2.admin.Ql_sanpham;
import com.example.java2.admin.suaSP;
import com.bumptech.glide.Glide;

import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.SanPhamViewHolder> {

    private List<SanPham> spList;
    private Context context;
    private DaoSanPham daoSanPham;

    public SanPhamAdapter(List<SanPham> spList, Context context) {
        this.spList = spList;
        this.context = context;
        this.daoSanPham = new DaoSanPham(context);
    }

    @NonNull
    @Override
    public SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_sp, parent, false);
        return new SanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SanPham sanPham = spList.get(position);
        holder.txtTenSP.setText(sanPham.getTen());
        holder.txtGia.setText(sanPham.getGia());
        holder.txtMota.setText(sanPham.getMo_ta());

        // Load ảnh bằng Glide
        Glide.with(context)
                .load(sanPham.getAnh())
                .into(holder.imgSanPham);

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daoSanPham.deleteSanPham(sanPham.getId());
                spList.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });

        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, suaSP.class);
                i.putExtra("Id_sp", sanPham.getId());
                i.putExtra("Ten_sp", sanPham.getTen());
                i.putExtra("Gia", sanPham.getGia());
                i.putExtra("Anh_sp", sanPham.getAnh());
                i.putExtra("Mo_ta", sanPham.getMo_ta());
                i.putExtra("Id_dm", sanPham.getId_dm());
                ((Ql_sanpham) context).startActivityForResult(i, Ql_sanpham.REQUEST_CODE_SUA_SP);
            }
        });
    }

    @Override
    public int getItemCount() {
        return spList.size();
    }

    public void setSPList(List<SanPham> spList) {
        this.spList = spList;
        notifyDataSetChanged();
    }

    public class SanPhamViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTenSP, txtGia, txtMota;
        public ImageView imgSanPham;
        public Button buttonDelete, buttonEdit;

        public SanPhamViewHolder(View itemView) {
            super(itemView);
            imgSanPham = itemView.findViewById(R.id.imgSanPham);
            txtTenSP = itemView.findViewById(R.id.txtTenSP);
            txtGia = itemView.findViewById(R.id.txtGia);
            txtMota = itemView.findViewById(R.id.txtMota);
            buttonDelete = itemView.findViewById(R.id.buttonDeleteSP);
            buttonEdit = itemView.findViewById(R.id.buttonEditSP);
        }
    }
}
