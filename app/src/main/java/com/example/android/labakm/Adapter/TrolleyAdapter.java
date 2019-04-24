package com.example.android.labakm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.labakm.Interface.TrolleyItemInterface;
import com.example.android.labakm.R;
import com.example.android.labakm.entity.viewmodel.BarangViewModel;

import java.util.List;

public class TrolleyAdapter extends BaseAdapter{
    private List<BarangViewModel> listBarang;
    private Context context;
    private TrolleyItemInterface trolleyItemInterface;

    public TrolleyAdapter(List<BarangViewModel> listBarang, Context context, TrolleyItemInterface trolleyItemInterface) {
        this.listBarang = listBarang;
        this.context = context;
        this.trolleyItemInterface = trolleyItemInterface;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.trolley_barang_item, viewGroup, false);
        TextView viewNama = view.findViewById(R.id.nama_item);
        TextView viewJumlah = view.findViewById(R.id.jumlah_item);
        Button deleteButton  = view.findViewById(R.id.hapus_button);
        BarangViewModel entity = listBarang.get(i);
        viewNama.setText(entity.getNama());
        viewJumlah.setText(String.valueOf(entity.getJumlah()));
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trolleyItemInterface.deleteItem(entity);
            }
        });

        return view;
    }
}
