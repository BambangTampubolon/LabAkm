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

import com.example.android.labakm.Adapter.JurnalNeracaAdapter;
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

public class FragmentNeraca extends Fragment{
    private ListView listAsetTidakLancar, listAsetLancar, listKewajiban, listEkuitas;
    private TextView textJumlahAsetTidakLancar, textJumlahAsetLancar,
            textJumlahAset, textJumlahKewajiban, textJumlahEkuitasModal, textKewajibanEkuitas;
    private List<Jurnal> listJurnalAsetNonLancar, listJurnalAsetLancar, listJurnalKewajiban,
            listJurnalEkuitas;
    private List<JurnalNeraca> neracaAsetNonLancar, neracaAsetLancar, neracaKewajiban, neracaEkuitas;
    private JurnalNeracaAdapter adapterAsetNonLancar, adapterAsetLancar, adapterKewajiban, adapterEkuitas;
    private Corporation corporationIntent;
    private Date startDate, endDate;
    private JurnalDao jurnalDao;

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

        Bundle bundle = getArguments();
        if(null != bundle){
            corporationIntent = (Corporation) bundle.getSerializable("idSelected");
            startDate = new Date(bundle.getLong("awal", 0));
            endDate = new Date(bundle.getLong("akhir", 0));
        }
        jurnalDao = DatabaseSetting.getDatabase(getContext()).jurnalDao();
        try {
            listJurnalAsetNonLancar = new getAllAsyncTask(jurnalDao, startDate, endDate,
                    "12%", corporationIntent.getId()).execute().get();
            listJurnalAsetLancar = new getAllAsyncTask(jurnalDao,startDate,endDate,
                    "11%", corporationIntent.getId()).execute().get();
            listJurnalKewajiban = new getAllAsyncTask(jurnalDao,startDate,endDate,
                    "61%", corporationIntent.getId()).execute().get();
            listJurnalEkuitas = new getAllAsyncTask(jurnalDao,startDate, endDate,
                    "3%", corporationIntent.getId()).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
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


    private static class getAllAsyncTask extends AsyncTask<Void,Void,List<Jurnal>> {
        private JurnalDao jurnalDao;
        private Date startDate, endDate;
        private String kodeAkun;
        private int idCorporation;

        public getAllAsyncTask(JurnalDao jurnalDao, Date startDate, Date endDate,
                               String kodeAkun, int idCorporation){
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
            return jurnalDao.getAllJurnalByKodeAkun(startDate.getTime(), endDate.getTime(),
                    kodeAkun, idCorporation);
        }
    }
}
