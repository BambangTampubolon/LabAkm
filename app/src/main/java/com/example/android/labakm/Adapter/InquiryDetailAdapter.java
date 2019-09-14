package com.example.android.labakm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.labakm.R;
import com.example.android.labakm.entity.viewmodel.OrderDetailViewModel;

import java.util.List;

public class InquiryDetailAdapter extends BaseAdapter{
    private List<OrderDetailViewModel> listOrderDetail;
    private Context context;

    public InquiryDetailAdapter(List<OrderDetailViewModel> listOrderDetail, Context context){
        this.listOrderDetail = listOrderDetail;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listOrderDetail.size();
    }

    @Override
    public Object getItem(int i) {
        return listOrderDetail.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listOrderDetail.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(this.context).inflate(R.layout.inquiry_detail_item_list, viewGroup, false);
        TextView textNama = view.findViewById(R.id.text_nama);
        TextView textJumlah = view.findViewById(R.id.text_jumlah);
        TextView textHarga = view.findViewById(R.id.text_harga_satuan);
        TextView textJumlahHarga = view.findViewById(R.id.text_jumlah_harga);
        OrderDetailViewModel entity = listOrderDetail.get(i);
        textNama.setText(entity.getNama_barang());
        textJumlah.setText(String.valueOf(entity.getTotal_barang()));
        textHarga.setText(String.valueOf(entity.getHarga()));
        textJumlahHarga.setText(String.valueOf(entity.getTotal_harga()));
        return view;
    }
}
