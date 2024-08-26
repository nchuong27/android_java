package com.example.java3.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.java3.Dao.AccontDao;
import com.example.java3.R;
import com.example.java3.model.Account;

import java.util.List;

public class AcountAdapter extends RecyclerView.Adapter<AcountAdapter.AccountViewHolder> {
    private List<Account> accountList;
    private AccontDao accontDao;

    public AcountAdapter(List<Account> accountList, AccontDao accontDao) {
        this.accountList = accountList;
        this.accontDao = accontDao;
    }


    @NonNull
    @Override
    public AcountAdapter.AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.acount_item, parent, false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AcountAdapter.AccountViewHolder holder, int position) {
        Account account = accountList.get(position);
        holder.nameTextview.setText(account.getUsername());
        holder.emailTextview.setText(account.getEmail());
        holder.deleAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accontDao.deleteUser(account.getId());
                accountList.remove(account);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    public void setAccounts(List<Account> accountList) {
        this.accountList = accountList;
        notifyDataSetChanged();
    }

    public static class AccountViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextview,emailTextview;
        public ImageView editAcount,deleAcount;

        public AccountViewHolder(View itemView) {
            super(itemView);
            nameTextview = itemView.findViewById(R.id.nameTextview);
            emailTextview = itemView.findViewById(R.id.emailtextView);
            editAcount = itemView.findViewById(R.id.editAcount);
            deleAcount = itemView.findViewById(R.id.deleAcount);
        }
    }
}