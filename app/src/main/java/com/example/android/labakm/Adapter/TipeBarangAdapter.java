package com.example.android.labakm.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.labakm.Interface.TipeBarangHolderInterface;
import com.example.android.labakm.R;
import com.example.android.labakm.entity.viewmodel.TipeBarangViewModel;
import java.util.List;

public class TipeBarangAdapter extends RecyclerView.Adapter<TipeBarangAdapter.TipeBarangViewHolder> {
    private List<TipeBarangViewModel> listData;
    private Context context;
    private TipeBarangHolderInterface tipeBarangHolderInterface;

    public TipeBarangAdapter(List<TipeBarangViewModel> listData, Context context
            ,TipeBarangHolderInterface tipeBarangHolderInterface){
        this.listData = listData;
        this.context = context;
        this.tipeBarangHolderInterface = tipeBarangHolderInterface;
    }

    @Override
    public TipeBarangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tipe_barang_recycler_item_layout, parent, false);
        return new TipeBarangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TipeBarangViewHolder holder, int position) {
        TipeBarangViewModel tipeBarang = listData.get(position);
        holder.textTipeBarang.setText(tipeBarang.getNama());
        holder.textTipeBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipeBarangHolderInterface.searchTipeBarangById(tipeBarang);
                holder.textTipeBarang.setPaintFlags(holder.textTipeBarang.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                holder.textTipeBarang.setBackgroundResource(R.color.colorAccent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class TipeBarangViewHolder extends RecyclerView.ViewHolder {
        public TextView textTipeBarang;

        public TipeBarangViewHolder(View view){
            super(view);
            this.textTipeBarang = view.findViewById(R.id.text_tipe_barang);
        }
    }
}
