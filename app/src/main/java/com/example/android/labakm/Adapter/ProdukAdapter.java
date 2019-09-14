package com.example.android.labakm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.labakm.Interface.ProdukInquiryInterface;
import com.example.android.labakm.R;
import com.example.android.labakm.entity.viewmodel.BarangViewModel;

import java.util.List;

public class ProdukAdapter extends BaseAdapter{
    private List<BarangViewModel> listProduk;
    private Context context;
    private ProdukInquiryInterface produkInquiryInterface;

    public ProdukAdapter(List<BarangViewModel> listProduk, Context context, ProdukInquiryInterface produkInquiryInterface) {
        this.listProduk = listProduk;
        this.context = context;
        this.produkInquiryInterface = produkInquiryInterface;
    }

    @Override
    public int getCount() {
        return listProduk.size();
    }

    @Override
    public Object getItem(int i) {
        return listProduk.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listProduk.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.barang_list_item, viewGroup, false);
        TextView textName = view.findViewById(R.id.item_barang_name);
        EditText textJumlah = view.findViewById(R.id.item_barang_jumlah);
        Button saveButton = view.findViewById(R.id.save_button);
        BarangViewModel entity = listProduk.get(i);
        textName.setText(entity.getNama());
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entity.setJumlah(0);
//                if(!"".equals(textJumlah.getText().toString()) && textJumlah.getText().toString().length() > 0){
//                    entity.setJumlah(Integer.valueOf(textJumlah.getText().toString()));
//                }
                produkInquiryInterface.saveBarangtoProduk(entity);
            }
        });
        return view;
    }
}
