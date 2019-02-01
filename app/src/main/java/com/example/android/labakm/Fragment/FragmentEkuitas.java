package com.example.android.labakm.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.labakm.Interface.JurnalManager;
import com.example.android.labakm.R;
import com.example.android.labakm.dao.JurnalDao;
import com.example.android.labakm.entity.Corporation;
import com.example.android.labakm.entity.Jurnal;
import com.example.android.labakm.manager.JurnalManagerImpl;
import com.example.android.labakm.setting.DatabaseSetting;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FragmentEkuitas extends Fragment {

    private TextView textPeriodeAwal, textJumlahPeriodeAwal, textJumlahPendapatan
            ,textPeriodeAkhir, textJumlahPeriodeAkhir;
    private JurnalDao jurnalDao;
    private List<Jurnal> jurnalPendapatan;
    private Date startDate, endDate;
    private Corporation corporationIntent;
    final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
    private JurnalManager jurnalManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perubahan_ekuitas, container, false);
        textPeriodeAwal = view.findViewById(R.id.text_awal_periode);
        textJumlahPeriodeAwal = view.findViewById(R.id.text_jumlah_awal_periode);
        textJumlahPendapatan = view.findViewById(R.id.text_jumlah_laba_periode);
        textPeriodeAkhir = view.findViewById(R.id.text_akhir_periode);
        textJumlahPeriodeAkhir = view.findViewById(R.id.text_jumlah_akhir_periode);
        jurnalDao = DatabaseSetting.getDatabase(getContext()).jurnalDao();
        jurnalManager = new JurnalManagerImpl(jurnalDao, getContext());
        int awalModal = 0;
        int jumlahPendapatan = 0;
        Bundle bundle = getArguments();
        if(null != bundle){
            corporationIntent = (Corporation) bundle.getSerializable("idSelected");
            startDate = new Date(bundle.getLong("awal", 0));
            endDate = new Date(bundle.getLong("akhir", 0));
            jumlahPendapatan = bundle.getInt("jumlah_untung");
        }
        try {
            jurnalPendapatan = jurnalManager.getAllJurnalByKodeAkun(startDate.getTime(), endDate.getTime(), "3%", corporationIntent.getId());
//            jurnalPendapatan = new getAllAsyncTask(jurnalDao,startDate, endDate, "3%", corporationIntent.getId()).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(Jurnal jurnal: jurnalPendapatan){
            awalModal += Math.abs(jurnal.getSaldo_awal()+jurnal.getTotal_debit()-jurnal.getTotal_kredit());
        }
        String periodeAwal = "Modal Awal," + dateFormat.format(startDate);
        String periodeAkhir = "Modal Akhir," + dateFormat.format(endDate);
        textPeriodeAwal.setText(periodeAwal);
        textPeriodeAkhir.setText(periodeAkhir);
        textJumlahPendapatan.setText(String.valueOf(jumlahPendapatan));
        textJumlahPeriodeAkhir.setText(String.valueOf(jumlahPendapatan + awalModal));
        textJumlahPeriodeAwal.setText(String.valueOf(awalModal));
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
