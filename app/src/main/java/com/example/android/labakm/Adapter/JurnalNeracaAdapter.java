package com.example.android.labakm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.labakm.R;
import com.example.android.labakm.ViewModel.JurnalNeraca;

import java.util.List;

public class JurnalNeracaAdapter extends BaseAdapter {
    private List<JurnalNeraca> jurnalList;
    private Context context;

    public JurnalNeracaAdapter(List<JurnalNeraca> jurnalList, Context context){
        this.jurnalList = jurnalList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return jurnalList.size();
    }

    @Override
    public JurnalNeraca getItem(int i) {
        return jurnalList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return jurnalList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(this.context).inflate(R.layout.list_neraca_item, viewGroup, false);
        TextView textNama = view.findViewById(R.id.nama_akun);
        TextView textJumlah = view.findViewById(R.id.jumlah_akun);
        JurnalNeraca jurnal = jurnalList.get(i);
        textJumlah.setText(String.valueOf(jurnal.getTotal_jumlah()));
        textNama.setText(jurnal.getNama_akun());
        return view;
    }
}
