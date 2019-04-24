package com.example.android.labakm.Fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.labakm.Adapter.JurnalNeracaAdapter;
import com.example.android.labakm.Interface.JurnalManager;
import com.example.android.labakm.R;
import com.example.android.labakm.ViewModel.JurnalNeraca;
import com.example.android.labakm.dao.JurnalDao;
import com.example.android.labakm.entity.Corporation;
import com.example.android.labakm.entity.Jurnal;
import com.example.android.labakm.manager.JurnalManagerImpl;
import com.example.android.labakm.setting.DatabaseSetting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FragmentNeraca extends Fragment{
    private ListView listAsetTidakLancar, listAsetLancar, listKewajiban, listEkuitas;
    private TextView textJumlahAsetTidakLancar, textJumlahAsetLancar,
            textJumlahAset, textJumlahKewajiban, textJumlahEkuitasModal, textKewajibanEkuitas;
    private List<Jurnal> listJurnalAsetNonLancar, listJurnalAsetLancar, listJurnalKewajiban,
            listJurnalEkuitas;
    private LinearLayout layoutAsetTidakLancar, layoutAsetLancar, layoutKewajiban, layoutEkuitasModal;
    private List<JurnalNeraca> neracaAsetNonLancar, neracaAsetLancar, neracaKewajiban, neracaEkuitas;
    private JurnalNeracaAdapter adapterAsetNonLancar, adapterAsetLancar, adapterKewajiban, adapterEkuitas;
    private Corporation corporationIntent;
    private Date startDate, endDate;
    private JurnalDao jurnalDao;
    private JurnalManager jurnalManager;
    private TextView asetTidakLancar, asetLancar, kewajiban, ekuitasModal;
    private boolean isHideAsetTidakLancar, isHideAsetLancar, isHideKewajiban, isHideEkuitasModal = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neraca, container, false);
        listAsetTidakLancar = view.findViewById(R.id.list_jurnal_aset_tidak_lancar);
        listAsetLancar = view.findViewById(R.id.list_jurnal_aset_lancar);
        listKewajiban = view.findViewById(R.id.list_kewajiban);
        listEkuitas = view.findViewById(R.id.list_ekuitas_modal);
        textJumlahAsetTidakLancar = view.findViewById(R.id.jumlah_aset_tidak_lancar);
        textJumlahAsetLancar = view.findViewById(R.id.jumlah_aset_lancar);
        textJumlahAset = view.findViewById(R.id.jumlah_aset);
        textJumlahKewajiban = view.findViewById(R.id.jumlah_kewajiban);
        textJumlahEkuitasModal = view.findViewById(R.id.jumlah_ekuitas_modal);
        textKewajibanEkuitas = view.findViewById(R.id.jumlah_kewajiban_ekuitas_modal);
        asetTidakLancar = view.findViewById(R.id.aset_tidak_lancar);
        asetLancar = view.findViewById(R.id.aset_lancar);
        kewajiban = view.findViewById(R.id.kewajiban);
        ekuitasModal = view.findViewById(R.id.ekuitas_modal);
        layoutAsetTidakLancar = view.findViewById(R.id.layout_aset_tidak_lancar);
        layoutAsetLancar = view.findViewById(R.id.layout_aset_lancar);
        layoutKewajiban = view.findViewById(R.id.layout_kewajiban);
        layoutEkuitasModal = view.findViewById(R.id.layout_ekuitas_modal);

        jurnalDao = DatabaseSetting.getDatabase(getContext()).jurnalDao();
        jurnalManager = new JurnalManagerImpl(jurnalDao, getContext());

        Bundle bundle = getArguments();
        if(null != bundle){
            corporationIntent = (Corporation) bundle.getSerializable("idSelected");
            startDate = new Date(bundle.getLong("awal", 0));
            endDate = new Date(bundle.getLong("akhir", 0));
        }

        try {
            listJurnalAsetNonLancar = jurnalManager.getAllJurnalByKodeAkun(startDate.getTime(), endDate.getTime(),
                    "12%", corporationIntent.getId());
            listJurnalAsetLancar = jurnalManager.getAllJurnalByKodeAkun(startDate.getTime(), endDate.getTime(),
                    "11%", corporationIntent.getId());
            listJurnalKewajiban = jurnalManager.getAllJurnalByKodeAkun(startDate.getTime(), endDate.getTime(),
                    "61%", corporationIntent.getId());
            listJurnalEkuitas = jurnalManager.getAllJurnalByKodeAkun(startDate.getTime(), endDate.getTime(),
                    "3%", corporationIntent.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        neracaAsetNonLancar = populateJurnalNeracaFromListJurnal(listJurnalAsetNonLancar);
        neracaAsetLancar = populateJurnalNeracaFromListJurnal(listJurnalAsetLancar);
        neracaKewajiban = populateJurnalNeracaFromListJurnal(listJurnalKewajiban);
        neracaEkuitas = populateJurnalNeracaFromListJurnal(listJurnalEkuitas);
        adapterAsetNonLancar = new JurnalNeracaAdapter(neracaAsetNonLancar, getContext());
        adapterAsetLancar = new JurnalNeracaAdapter(neracaAsetLancar, getContext());
        adapterKewajiban = new JurnalNeracaAdapter(neracaKewajiban, getContext());
        adapterEkuitas = new JurnalNeracaAdapter(neracaEkuitas, getContext());
        listAsetTidakLancar.setAdapter(adapterAsetNonLancar);
        listAsetLancar.setAdapter(adapterAsetLancar);
        listKewajiban.setAdapter(adapterKewajiban);
        listEkuitas.setAdapter(adapterEkuitas);
        populateLastText();

        asetTidakLancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isHideAsetTidakLancar){
                    layoutAsetTidakLancar.setVisibility(View.GONE);
                    isHideAsetTidakLancar = !isHideAsetTidakLancar;
                }else {
                    layoutAsetTidakLancar.setVisibility(View.VISIBLE);
                    isHideAsetTidakLancar = !isHideAsetTidakLancar;
                }

            }
        });

        asetLancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isHideAsetLancar){
                    layoutAsetLancar.setVisibility(View.GONE);
                    isHideAsetLancar = !isHideAsetLancar;
                }else {
                    layoutAsetLancar.setVisibility(View.GONE);
                    isHideAsetLancar = !isHideAsetLancar;
                }
            }
        });

        kewajiban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isHideKewajiban){
                    layoutKewajiban.setVisibility(View.GONE);
                    isHideKewajiban = !isHideKewajiban;
                }else {
                    layoutKewajiban.setVisibility(View.VISIBLE);
                    isHideKewajiban = !isHideKewajiban;
                }
            }
        });

        ekuitasModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isHideEkuitasModal){
                    layoutEkuitasModal.setVisibility(View.GONE);
                    isHideEkuitasModal = !isHideEkuitasModal;
                }else {
                    layoutEkuitasModal.setVisibility(View.VISIBLE);
                    isHideEkuitasModal = !isHideEkuitasModal;
                }
            }
        });
        return view;
    }


    private void populateLastText(){
        int totalAsetTidakLancar= 0;
        int totalAsetLancar = 0;
        int totalAset = 0;
        int totalKewajiban = 0;
        int totalEkuitasModal = 0;
        int totalKewajibanEkuitas = 0;
        for(JurnalNeraca jurnalNeraca: neracaAsetNonLancar){
            totalAsetTidakLancar += jurnalNeraca.getTotal_jumlah();
        }
        for(JurnalNeraca jurnalNeraca: neracaAsetLancar){
            totalAsetLancar += jurnalNeraca.getTotal_jumlah();
        }
        for(JurnalNeraca jurnalNeraca: neracaKewajiban){
            totalKewajiban += jurnalNeraca.getTotal_jumlah();
        }
        for(JurnalNeraca jurnalNeraca: neracaEkuitas){
            totalEkuitasModal += jurnalNeraca.getTotal_jumlah();
        }
        totalAset = totalAsetLancar + totalAsetTidakLancar;
        totalKewajibanEkuitas = totalKewajiban + totalEkuitasModal;

        textJumlahAsetTidakLancar.setText(String.valueOf(totalAsetTidakLancar));
        textJumlahAsetLancar.setText(String.valueOf(totalAsetLancar));
        textJumlahAset.setText(String.valueOf(totalAset));
        textJumlahKewajiban.setText(String.valueOf(totalKewajiban));
        textJumlahEkuitasModal.setText(String.valueOf(totalEkuitasModal));
        textKewajibanEkuitas.setText(String.valueOf(totalKewajibanEkuitas));
    }


    public List<JurnalNeraca> populateJurnalNeracaFromListJurnal(List<Jurnal> jurnalList){
        List<JurnalNeraca> jurnalNeracaList = new ArrayList<>();
        if(null != jurnalList){
            for(Jurnal jurnal: jurnalList){
                JurnalNeraca jurnalNeraca = new JurnalNeraca();
                jurnalNeraca.setId(jurnal.getId());
                jurnalNeraca.setId_akun(jurnal.getId_akun());
                jurnalNeraca.setTotal_jumlah(Math.abs(jurnal.getSaldo_awal()+jurnal.getTotal_debit()-jurnal.getTotal_kredit()));
                jurnalNeraca.setNama_akun(jurnal.getNama_akun());
                jurnalNeracaList.add(jurnalNeraca);
            }
        }
        return  jurnalNeracaList;
    }
}
