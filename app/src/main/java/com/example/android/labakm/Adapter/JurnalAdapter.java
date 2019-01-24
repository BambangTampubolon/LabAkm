package com.example.android.labakm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.labakm.R;
import com.example.android.labakm.entity.Jurnal;

import java.text.DateFormat;
import java.util.List;

public class JurnalAdapter extends BaseAdapter{
    private List<Jurnal> jurnalList;
    private Context context;
    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);

    public JurnalAdapter(List<Jurnal> jurnalList, Context context){
        this.jurnalList = jurnalList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return jurnalList.size();
    }

    @Override
    public Jurnal getItem(int i) {
        return jurnalList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return jurnalList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(this.context).inflate(R.layout.list_jurnal_item, viewGroup, false);
        TextView textTanggal = view.findViewById(R.id.text_jurnal_tanggal);
        TextView textAkun = view.findViewById(R.id.text_jurnal_akun);
        TextView textDebit = view.findViewById(R.id.text_jurnal_debit);
        TextView textKredit = view.findViewById(R.id.text_jurnal_kredit);
        TextView textKeterangan = view.findViewById(R.id.text_jurnal_keterangan);
        Jurnal jurnal = jurnalList.get(i);
        textTanggal.setText(dateFormat.format(jurnal.getCreated_date()));
        String akunJurnal = jurnal.getId_akun() + " - " + jurnal.getNama_akun();
        textAkun.setText(akunJurnal);
        textKredit.setText(String.valueOf(jurnal.getTotal_kredit()));
        textDebit.setText(String.valueOf(jurnal.getTotal_debit()));
        textKeterangan.setText(jurnal.getKeterangan());
        return view;
    }
}
