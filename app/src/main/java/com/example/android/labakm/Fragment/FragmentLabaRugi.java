package com.example.android.labakm.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.labakm.Adapter.JurnalLabaRugiAdapter;
import com.example.android.labakm.Interface.JurnalManager;
import com.example.android.labakm.Interface.LaporanTotal;
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


public class FragmentLabaRugi extends Fragment{
    private TextView jumlahPendapatan, jumlahPendapatanJasa, jumlahBeban
            , jumlahBebanOperasional, jumlahBebanadministrasi,jumlahPendapatanOperasional;
    private ListView listPendapatanJasa, listBebanAdministrasi, listBebanOperasional;
    private JurnalLabaRugiAdapter pendapatanJasaAdapter, bebanOperasionalAdapter
            , bebanAdministrasiAdapter;
    private JurnalDao jurnalDao;
    List<JurnalNeraca> pendapatanNeraca, bebanOperasionalNeraca, bebanAdministrasiNeraca;
    List<Jurnal> pendapatanJurnals, bebanOperasionalJurnals, bebanAdministrasiJurnals;
    private Corporation corporationIntent;
    private Date startDate,endDate;
    private LaporanTotal laporanTotal;
    private JurnalManager jurnalManager;
    private LinearLayout layoutPendapatanJasa, layoutOperasional, layoutAdministrasi;
    private boolean isHidePendapatanJasa, isHideOperasional, isHideAdministrasi = true;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_laba_rugi, container, false);
        jumlahPendapatan = view.findViewById(R.id.jumlah_pendapatan);
        jumlahPendapatanJasa = view.findViewById(R.id.jumlah_pendapatan_jasa);
        jumlahBeban = view.findViewById(R.id.jumlah_beban);
        jumlahBebanOperasional = view.findViewById(R.id.jumlah_beban_operasional);
        jumlahBebanadministrasi = view.findViewById(R.id.jumlah_beban_administrasi);
        jumlahPendapatanOperasional = view.findViewById(R.id.jumlah_pendapatan_operasional);
        listPendapatanJasa = view.findViewById(R.id.list_pendapatan_jasa);
        listBebanOperasional = view.findViewById(R.id.list_beban_operasional);
        listBebanAdministrasi = view.findViewById(R.id.list_beban_administrasi);
        layoutPendapatanJasa = view.findViewById(R.id.pendapatan_jasa);
        layoutOperasional = view.findViewById(R.id.beban_operasional);
        layoutAdministrasi = view.findViewById(R.id.beban_adminstrasi);

        jurnalDao = DatabaseSetting.getDatabase(getContext()).jurnalDao();
        jurnalManager = new JurnalManagerImpl(jurnalDao, getContext());
        Bundle bundle = getArguments();
        if(null != bundle){
            corporationIntent = (Corporation) bundle.getSerializable("idSelected");
            startDate = new Date(bundle.getLong("awal", 0));
            endDate = new Date(bundle.getLong("akhir", 0));
        }

        try {
            pendapatanJurnals = jurnalManager.getAllJurnalByKodeAkun(startDate.getTime(),endDate.getTime(),
                    "42%", corporationIntent.getId());
            bebanOperasionalJurnals = jurnalManager.getAllJurnalByKodeAkun(startDate.getTime(), endDate.getTime(),
                    "51%", corporationIntent.getId());
            bebanAdministrasiJurnals = jurnalManager.getAllJurnalByKodeAkun(startDate.getTime(), endDate.getTime(),
                    "52%", corporationIntent.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        pendapatanNeraca = populateJurnalNeracaFromList(pendapatanJurnals);
        bebanOperasionalNeraca = populateJurnalNeracaFromList(bebanOperasionalJurnals);
        bebanAdministrasiNeraca = populateJurnalNeracaFromList(bebanAdministrasiJurnals);
        pendapatanJasaAdapter = new JurnalLabaRugiAdapter(pendapatanNeraca, getContext());
        bebanOperasionalAdapter = new JurnalLabaRugiAdapter(bebanOperasionalNeraca, getContext());
        bebanAdministrasiAdapter = new JurnalLabaRugiAdapter(bebanAdministrasiNeraca, getContext());

        listPendapatanJasa.setAdapter(pendapatanJasaAdapter);
        listBebanOperasional.setAdapter(bebanOperasionalAdapter);
        listBebanAdministrasi.setAdapter(bebanAdministrasiAdapter);
        populateLastText();

        layoutPendapatanJasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isHidePendapatanJasa){
                    listPendapatanJasa.setVisibility(View.GONE);
                    isHidePendapatanJasa = !isHidePendapatanJasa;
                }else {
                    listPendapatanJasa.setVisibility(View.VISIBLE);
                    isHidePendapatanJasa = !isHidePendapatanJasa;
                }
            }
        });

        layoutOperasional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isHideOperasional){
                    listBebanOperasional.setVisibility(View.GONE);
                    isHideOperasional = !isHideOperasional;
                }else {
                    listBebanOperasional.setVisibility(View.VISIBLE);
                    isHideOperasional = !isHideOperasional;
                }
            }
        });

        layoutAdministrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isHideAdministrasi){
                    listBebanAdministrasi.setVisibility(View.GONE);
                    isHideAdministrasi = !isHideAdministrasi;
                }else {
                    listBebanAdministrasi.setVisibility(View.VISIBLE);
                    isHideAdministrasi = !isHideAdministrasi;
                }
            }
        });

        return view;
    }

    private void populateLastText(){
        int totalPendapatan = 0;
        int totalBebanOperasional = 0;
        int totalBebanAdministrasi = 0;
        for (JurnalNeraca jurnalNeraca: pendapatanNeraca){
            totalPendapatan += jurnalNeraca.getTotal_jumlah();
        }
        for (JurnalNeraca jurnalNeraca: bebanOperasionalNeraca){
            totalBebanOperasional += jurnalNeraca.getTotal_jumlah();
        }
        for(JurnalNeraca jurnalNeraca: bebanAdministrasiNeraca){
            totalBebanAdministrasi += jurnalNeraca.getTotal_jumlah();
        }
        int totalBeban = totalBebanAdministrasi + totalBebanOperasional;
        int totalPendapatanOperasional = totalPendapatan - totalBeban;
        jumlahPendapatan.setText(String.valueOf(totalPendapatan));
        jumlahPendapatanJasa.setText(String.valueOf(totalPendapatan));
        jumlahBeban.setText(String.valueOf(totalBeban));
        jumlahBebanOperasional.setText(String.valueOf(totalBebanOperasional));
        jumlahBebanadministrasi.setText(String.valueOf(totalBebanAdministrasi));
        jumlahPendapatanOperasional.setText(String.valueOf(totalPendapatanOperasional));
        laporanTotal.kirimTotalHitung(totalPendapatanOperasional);
    }

    public List<JurnalNeraca> populateJurnalNeracaFromList(List<Jurnal> jurnalList){
        List<JurnalNeraca> jurnalNeracaList = new ArrayList<>();
        if(null != jurnalList){
            for(Jurnal jurnal: jurnalList){
                Log.i("cekjurnal ", "populateJurnalNeracaFromList: " + jurnal.toString());
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.laporanTotal = (LaporanTotal) context;
    }
}
