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

public class JurnalLabaRugiAdapter extends BaseAdapter {
    private List<JurnalNeraca> jurnalNeracaList;
    private Context context;

    public JurnalLabaRugiAdapter(List<JurnalNeraca> jurnalNeracaList, Context context){
        this.jurnalNeracaList = jurnalNeracaList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return jurnalNeracaList.size();
    }

    @Override
    public JurnalNeraca getItem(int i) {
        return jurnalNeracaList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return jurnalNeracaList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(this.context).inflate(R.layout.list_jurnal_laba_rugi_item, viewGroup, false);
        TextView textNomorUrut = view.findViewById(R.id.nomor_urut);
        TextView textNamaAkun = view.findViewById(R.id.nama_akun);
        TextView textJumlahAkun = view.findViewById(R.id.jumlah_akun);
        JurnalNeraca jurnalNeraca = jurnalNeracaList.get(i);
        textNomorUrut.setText(String.valueOf(i+1));
        textNamaAkun.setText(jurnalNeraca.getNama_akun());
        textJumlahAkun.setText(String.valueOf(jurnalNeraca.getTotal_jumlah()));
        return view;
    }
}
