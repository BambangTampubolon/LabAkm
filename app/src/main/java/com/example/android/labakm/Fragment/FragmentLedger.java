package com.example.android.labakm.Fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.labakm.Adapter.JurnalLedgerAdapter;
import com.example.android.labakm.Interface.JurnalManager;
import com.example.android.labakm.R;
import com.example.android.labakm.ViewModel.JurnalLedger;
import com.example.android.labakm.dao.JurnalDao;
import com.example.android.labakm.entity.Corporation;
import com.example.android.labakm.entity.Jurnal;
import com.example.android.labakm.manager.JurnalManagerImpl;
import com.example.android.labakm.setting.DatabaseSetting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FragmentLedger extends Fragment{
    private ListView listViewJurnalAset, listViewJurnalNonAset;
    private JurnalLedgerAdapter jurnalAdapterAset, jurnalAdapterNonAset;
    private List<Jurnal> jurnalList;
    private JurnalDao jurnalDao;
    private Date startDate, endDate;
    private Corporation corporationIntent;
    private List<JurnalLedger> ledgerAset, ledgerNonAset;
    private TextView textJumlahSaldoAwal, textJumlahModal, textJumlahDebit,
            textJumlahKredit, textJumlahSaldoAkhir;
    private JurnalManager jurnalManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ledger, container, false);
        listViewJurnalAset = view.findViewById(R.id.list_jurnal_aset);
        listViewJurnalNonAset = view.findViewById(R.id.list_jurnal_nonaset);
        textJumlahSaldoAwal = view.findViewById(R.id.text_jumlah_saldo_awal);
        textJumlahModal = view.findViewById(R.id.text_jumlah_modal);
        textJumlahDebit = view.findViewById(R.id.text_jumlah_debit);
        textJumlahKredit = view.findViewById(R.id.text_jumlah_kredit);
        textJumlahSaldoAkhir = view.findViewById(R.id.text_jumlah_saldo_akhir);
        jurnalDao = DatabaseSetting.getDatabase(getContext()).jurnalDao();
        jurnalManager = new JurnalManagerImpl(jurnalDao, getContext());

        Bundle bundle = getArguments();
        if(null != bundle){
            corporationIntent = (Corporation) bundle.getSerializable("idSelected");
            startDate = new Date(bundle.getLong("awal", 0));
            endDate = new Date(bundle.getLong("akhir", 0));
        }

        try {
            jurnalList = jurnalManager.getAllJurnalForReportLedger(startDate.getTime(), endDate.getTime(), corporationIntent.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ledgerAset = new ArrayList<>();
        ledgerNonAset = new ArrayList<>();
        populatelistAkun();
        jurnalAdapterAset = new JurnalLedgerAdapter(ledgerAset, getContext());
        jurnalAdapterNonAset = new JurnalLedgerAdapter(ledgerNonAset, getContext());
        listViewJurnalAset.setAdapter(jurnalAdapterAset);
        listViewJurnalNonAset.setAdapter(jurnalAdapterNonAset);
        populateLastText();
        return view;
    }

    private void populateLastText(){
        int totalSaldoAwal = 0;
        int totalModal = 0;
        int totalDebit = 0;
        int totalKredit = 0;
        int totalSaldoAkhir = 0;
        for(JurnalLedger jurnalLedger : ledgerAset){
            totalSaldoAwal += jurnalLedger.getSaldo_awal();
            totalModal += jurnalLedger.getTotal_modal();
            totalDebit += jurnalLedger.getTotal_debit();
            totalKredit += jurnalLedger.getTotal_kredit();
            totalSaldoAkhir += jurnalLedger.getSaldo_awal() + jurnalLedger.getTotal_debit() - jurnalLedger.getTotal_kredit();
        }
        for(JurnalLedger jurnalLedger : ledgerNonAset){
            totalSaldoAwal += jurnalLedger.getSaldo_awal();
            totalModal += jurnalLedger.getTotal_modal();
            totalDebit += jurnalLedger.getTotal_debit();
            totalKredit += jurnalLedger.getTotal_kredit();
            totalSaldoAkhir += jurnalLedger.getSaldo_awal() + jurnalLedger.getTotal_debit() - jurnalLedger.getTotal_kredit();
        }
        textJumlahSaldoAwal.setText(String.valueOf(totalSaldoAwal));
        textJumlahModal.setText(String.valueOf(totalModal));
        textJumlahDebit.setText(String.valueOf(totalDebit));
        textJumlahKredit.setText(String.valueOf(totalKredit));
        textJumlahSaldoAkhir.setText(String.valueOf(totalSaldoAkhir));

    }

    private void populatelistAkun(){
        ledgerAset.clear();
        String akunLedger = "0";
        int indexAset = 0;
        String akunNonLedger = "0";
        int indexNonAset = 0;
        for(Jurnal jurnal : jurnalList){
            if(jurnal.getId_akun().startsWith("1")){
                JurnalLedger jurnalLedger = new JurnalLedger();
                jurnalLedger.setSaldo_awal(jurnal.getSaldo_awal());
                jurnalLedger.setId_akun(jurnal.getId_akun());
                jurnalLedger.setNama_akun(jurnal.getNama_akun());
                jurnalLedger.setTotal_debit(jurnal.getTotal_debit());
                jurnalLedger.setTotal_kredit(jurnal.getTotal_kredit());
                jurnalLedger.setTotal_modal(0);
                if(!akunLedger.equalsIgnoreCase(jurnalLedger.getId_akun())){
                    ledgerAset.add(indexAset, jurnalLedger);
                    indexAset += 1;
                    akunLedger = jurnalLedger.getId_akun();
                }else {
                    JurnalLedger existingJurnal = ledgerAset.get(indexAset-1);
                    JurnalLedger newJurnal = add2Jurnal(existingJurnal, jurnalLedger);
                    ledgerAset.remove(indexAset-1);
                    ledgerAset.add(indexAset-1, newJurnal);
                }
            }else {
                JurnalLedger jurnalLedger = new JurnalLedger();
                jurnalLedger.setSaldo_awal(jurnal.getSaldo_awal());
                jurnalLedger.setId_akun(jurnal.getId_akun());
                jurnalLedger.setNama_akun(jurnal.getNama_akun());
                jurnalLedger.setTotal_debit(jurnal.getTotal_debit());
                jurnalLedger.setTotal_kredit(jurnal.getTotal_kredit());
                jurnalLedger.setTotal_modal(0);
                if(jurnal.getId_akun().startsWith("3")){
                    jurnalLedger.setTotal_debit(0);
                    jurnalLedger.setTotal_kredit(0);
                    jurnalLedger.setTotal_modal(jurnal.getTotal_debit());
                }
                if(!akunNonLedger.equalsIgnoreCase(jurnalLedger.getId_akun())){
                    ledgerNonAset.add(indexNonAset, jurnalLedger);
                    indexNonAset += 1;
                    akunNonLedger = jurnalLedger.getId_akun();
                }else {
                    JurnalLedger existingJurnal = ledgerNonAset.get(indexNonAset-1);
                    JurnalLedger newJurnal = add2Jurnal(existingJurnal, jurnalLedger);
                    ledgerAset.remove(indexNonAset-1);
                    ledgerAset.add(indexNonAset-1, newJurnal);
                }
            }
        }
    }

    private JurnalLedger add2Jurnal(JurnalLedger existing, JurnalLedger newJurnal){
        JurnalLedger resultJurnal = existing;
        resultJurnal.setTotal_kredit(existing.getTotal_kredit() + newJurnal.getTotal_kredit());
        resultJurnal.setTotal_debit(existing.getTotal_debit() + newJurnal.getTotal_debit());
        resultJurnal.setTotal_modal(existing.getTotal_modal() + newJurnal.getTotal_modal());
        return  resultJurnal;
    }

}
