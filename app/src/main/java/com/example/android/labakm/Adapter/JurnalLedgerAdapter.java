package com.example.android.labakm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.labakm.R;
import com.example.android.labakm.ViewModel.JurnalLedger;

import java.util.List;

public class JurnalLedgerAdapter extends BaseAdapter{
    private List<JurnalLedger> jurnalList;
    private Context context;

    public JurnalLedgerAdapter(List<JurnalLedger> jurnalList, Context context){
        this.jurnalList = jurnalList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return jurnalList.size();
    }

    @Override
    public JurnalLedger getItem(int i) {
        return jurnalList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return jurnalList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(this.context).inflate(R.layout.list_jurnal_ledger_item, viewGroup, false);
        TextView textAkun = view.findViewById(R.id.text_jurnal_ledger_akun);
        TextView textSaldoAwal = view.findViewById(R.id.text_jurnal_ledger_saldo_awal);
        TextView textModal = view.findViewById(R.id.text_jurnal_ledger_modal);
        TextView textDebit = view.findViewById(R.id.text_jurnal_ledger_debit);
        TextView textKredit = view.findViewById(R.id.text_jurnal_ledger_kredit);
        TextView textSaldoAkhir = view.findViewById(R.id.text_jurnal_ledger_saldo_akhir);
        JurnalLedger jurnal = jurnalList.get(i);
        String akunJurnal = jurnal.getId_akun() + " - " + jurnal.getNama_akun();
        textAkun.setText(akunJurnal);
        if(jurnal.getId_akun().startsWith("3")){
            textModal.setText(String.valueOf(jurnal.getTotal_modal()));
        }
        textSaldoAwal.setText(String.valueOf(jurnal.getSaldo_awal()));
        textKredit.setText(String.valueOf(jurnal.getTotal_kredit()));
        textDebit.setText(String.valueOf(jurnal.getTotal_debit()));
        int saldoAkhir = Math.abs(jurnal.getSaldo_awal() + jurnal.getTotal_debit() - jurnal.getTotal_kredit());
        textSaldoAkhir.setText(String.valueOf(saldoAkhir));
        return view;
    }
}
