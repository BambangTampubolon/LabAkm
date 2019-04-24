package com.example.android.labakm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.labakm.Interface.FragmentBarangInterface;
import com.example.android.labakm.R;
import com.example.android.labakm.entity.Barang;
import com.example.android.labakm.entity.viewmodel.BarangViewModel;

import java.util.List;

public class BarangAdapter extends BaseAdapter {
    private List<BarangViewModel> listBarang;
    private Context context;
    private FragmentBarangInterface fragmentBarangInterface;

    public BarangAdapter(List<BarangViewModel> listBarang,Context context, FragmentBarangInterface fragmentBarangInterface){
        this.listBarang = listBarang;
        this.context = context;
        this.fragmentBarangInterface = fragmentBarangInterface;
    }

    @Override
    public int getCount() {
        return listBarang.size();
    }

    @Override
    public Object getItem(int i) {
        return listBarang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listBarang.get(i).getId();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.list_barang_item, viewGroup, false);
        TextView textTipeBarang = view.findViewById(R.id.view_tipe_barang);
        TextView textNamaBarang = view.findViewById(R.id.view_nama_barang);
        TextView textCreatedDate = view.findViewById(R.id.view_created_date);
        TextView textHarga = view.findViewById(R.id.view_harga_barang);
        BarangViewModel barang = listBarang.get(i);
        textTipeBarang.setText(String.valueOf(barang.getTipe_barang_id()));
        textNamaBarang.setText(barang.getNama());
        textCreatedDate.setText(barang.getCreateddate().toString());
        textHarga.setText(String.valueOf(barang.getHarga()));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentBarangInterface.selectItem(barang);
            }
        });
        return  view;
    }
}
