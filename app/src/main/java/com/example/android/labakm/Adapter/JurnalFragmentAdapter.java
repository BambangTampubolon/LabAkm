package com.example.android.labakm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.labakm.Interface.FragmentPauseInterface;
import com.example.android.labakm.Interface.ListJurnalInterface;
import com.example.android.labakm.R;
import com.example.android.labakm.entity.Jurnal;

import java.text.DateFormat;
import java.util.List;

public class JurnalFragmentAdapter extends BaseAdapter {
    private List<Jurnal> jurnalList;
    private Context context;
    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
    private ListJurnalInterface listJurnalInterface;

    public JurnalFragmentAdapter(List<Jurnal> jurnalList, Context context,
                                 ListJurnalInterface listJurnalInterface){
        this.jurnalList = jurnalList;
        this.context = context;
        this.listJurnalInterface = listJurnalInterface;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(this.context).inflate(R.layout.fragment_jurnal_list_item, viewGroup, false);
        TextView tanggalJurnal = view.findViewById(R.id.tanggal_jurnal);
        TextView akunJurnal = view.findViewById(R.id.akun_jurnal);
        TextView totalDebit = view.findViewById(R.id.total_debit);
        TextView totalKredit = view.findViewById(R.id.total_kredit);
        TextView keteranganJurnal = view.findViewById(R.id.keterangan_jurnal);
        LinearLayout layoutToClick = view.findViewById(R.id.layout_click_item);
        Button buttonDelete = view.findViewById(R.id.delete_jurnal);
        Jurnal jurnal = jurnalList.get(i);
        if(null != jurnal.getCreated_date()){
            tanggalJurnal.setText(dateFormat.format(jurnal.getCreated_date()));
        }
        akunJurnal.setText(jurnal.getId_akun());
        totalDebit.setText(String.valueOf(jurnal.getTotal_debit()));
        totalKredit.setText(String.valueOf(jurnal.getTotal_kredit()));
        keteranganJurnal.setText(String.valueOf(jurnal.getKeterangan()));

        layoutToClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listJurnalInterface.setSelectedItem(jurnalList.get(i));
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listJurnalInterface.deleteButton(i);
            }
        });
        return view;
    }
}
