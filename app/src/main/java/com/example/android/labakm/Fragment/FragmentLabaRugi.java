package com.example.android.labakm.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.labakm.Adapter.JurnalLabaRugiAdapter;
import com.example.android.labakm.Interface.LaporanTotal;
import com.example.android.labakm.R;
import com.example.android.labakm.ViewModel.JurnalNeraca;
import com.example.android.labakm.dao.JurnalDao;
import com.example.android.labakm.entity.Corporation;
import com.example.android.labakm.entity.Jurnal;
import com.example.android.labakm.setting.DatabaseSetting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;


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

        Bundle bundle = getArguments();
        if(null != bundle){
            corporationIntent = (Corporation) bundle.getSerializable("idSelected");
            startDate = new Date(bundle.getLong("awal", 0));
            endDate = new Date(bundle.getLong("akhir", 0));
        }

        jurnalDao = DatabaseSetting.getDatabase(getContext()).jurnalDao();

        try {
            pendapatanJurnals = new getAllAsyncTask(jurnalDao, startDate, endDate,
                    "42%",corporationIntent.getId()).execute().get();
            bebanOperasionalJurnals = new getAllAsyncTask(jurnalDao, startDate, endDate,
                    "51%",corporationIntent.getId()).execute().get();
            bebanAdministrasiJurnals = new getAllAsyncTask(jurnalDao, startDate, endDate,
                    "52%",corporationIntent.getId()).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.i("cekpendapatanjurnal", "onCreateView: " + pendapatanJurnals.size() + " :" + pendapatanJurnals);
        Log.i("OperasionalJurnals", "onCreateView: " + bebanOperasionalJurnals.size() + " :" + bebanOperasionalJurnals);
        Log.i("AdministrasiJurnals", "onCreateView: " + bebanAdministrasiJurnals.size() + " :" + bebanAdministrasiJurnals);
        pendapatanNeraca = populateJurnalNeracaFromList(pendapatanJurnals);
        bebanOperasionalNeraca = populateJurnalNeracaFromList(bebanOperasionalJurnals);
        bebanAdministrasiNeraca = populateJurnalNeracaFromList(bebanAdministrasiJurnals);
        Log.i("cekpendapatanNeraca", "onCreateView: " + pendapatanNeraca.size() + " :" + pendapatanNeraca);
        Log.i("bebanOperasionalNeraca", "onCreateView: " + bebanOperasionalNeraca.size() + " :" + bebanOperasionalNeraca);
        Log.i("bebanAdministrasiNeraca", "onCreateView: " + bebanAdministrasiNeraca.size() + " :" + bebanAdministrasiNeraca);
        pendapatanJasaAdapter = new JurnalLabaRugiAdapter(pendapatanNeraca, getContext());
        bebanOperasionalAdapter = new JurnalLabaRugiAdapter(bebanOperasionalNeraca, getContext());
        bebanAdministrasiAdapter = new JurnalLabaRugiAdapter(bebanAdministrasiNeraca, getContext());

        listPendapatanJasa.setAdapter(pendapatanJasaAdapter);
        listBebanOperasional.setAdapter(bebanOperasionalAdapter);
        listBebanAdministrasi.setAdapter(bebanAdministrasiAdapter);
        populateLastText();

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

    private static class getAllAsyncTask extends AsyncTask<Void,Void,List<Jurnal>> {
        private JurnalDao jurnalDao;
        private Date startDate, endDate;
        private String kodeAkun;
        private int idCorporation;

        public getAllAsyncTask(JurnalDao jurnalDao, Date startDate, Date endDate, String kodeAkun,
                               int idCorporation){
            this.jurnalDao = jurnalDao;
            this.startDate = startDate;
            this.endDate = endDate;
            this.kodeAkun = kodeAkun;
            this.idCorporation = idCorporation;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Jurnal> jurnalList) {
            super.onPostExecute(jurnalList);
        }

        @Override
        protected List<Jurnal> doInBackground(Void... voids) {
            return jurnalDao.getAllJurnalByKodeAkun(startDate.getTime(), endDate.getTime()
                    ,kodeAkun,idCorporation);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.laporanTotal = (LaporanTotal) context;
    }
}
