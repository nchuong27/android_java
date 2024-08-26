package com.example.java3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.java3.Dao.VoucherDao;
import com.example.java3.R;
import com.example.java3.model.Voucher;
import com.example.java3.view.VoucherActivity;

import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {
    private List<Voucher> voucherList;
    private VoucherDao voucherDao;
    private VoucherActivity activity;

    public VoucherAdapter(List<Voucher> voucherList, VoucherDao voucherDao, VoucherActivity activity) {
        this.voucherList = voucherList;
        this.voucherDao = voucherDao;
        this.activity = activity;
    }

    public void setVoucherList(List<Voucher> voucherList) {
        this.voucherList = voucherList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.voucher_item, parent, false);
        return new VoucherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherViewHolder holder, int position) {
        Voucher voucher = voucherList.get(position);
        holder.nameVoucher.setText(voucher.getName_vc());
        holder.desVoucher.setText(voucher.getDes_vc());
        holder.deleVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voucherDao.deleteVoucher(voucher.getId_vc());
                voucherList.remove(voucher);
                notifyDataSetChanged();
            }
        });
        holder.editVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return voucherList.size();
    }
    public static class VoucherViewHolder extends RecyclerView.ViewHolder{
        private ImageView editVoucher,deleVoucher;
        private TextView nameVoucher,desVoucher;
        public VoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            nameVoucher = itemView.findViewById(R.id.nameVoucher);
            desVoucher = itemView.findViewById(R.id.desVoucher);
            editVoucher = itemView.findViewById(R.id.editVoucher);
            deleVoucher = itemView.findViewById(R.id.deleVoucher);
        }
    }
}
