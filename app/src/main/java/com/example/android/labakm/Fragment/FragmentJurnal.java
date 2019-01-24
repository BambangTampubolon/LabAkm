package com.example.android.labakm.Fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.labakm.Adapter.JurnalAdapter;
import com.example.android.labakm.R;
import com.example.android.labakm.dao.JurnalDao;
import com.example.android.labakm.entity.Corporation;
import com.example.android.labakm.entity.Jurnal;
import com.example.android.labakm.setting.DatabaseSetting;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FragmentJurnal extends Fragment {
    private ListView listViewJurnal;
    private JurnalAdapter jurnalAdapter;
    private List<Jurnal> jurnalList;
    private JurnalDao jurnalDao;
    private Date startDate, endDate;
    private Corporation corporationIntent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jurnal, container, false);
        listViewJurnal = view.findViewById(R.id.list_jurnal);
        Bundle bundle = getArguments();
        if(null != bundle){
            corporationIntent = (Corporation) bundle.getSerializable("idSelected");
            startDate = new Date(bundle.getLong("awal", 0));
            endDate = new Date(bundle.getLong("akhir", 0));
        }

        jurnalDao = DatabaseSetting.getDatabase(getContext()).jurnalDao();
        try {
            jurnalList = new getAllAsyncTask(jurnalDao, startDate, endDate, corporationIntent.getId()).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        jurnalAdapter = new JurnalAdapter(jurnalList, getContext());
        listViewJurnal.setAdapter(jurnalAdapter);
        return view;
    }

    private static class getAllAsyncTask extends AsyncTask<Void,Void,List<Jurnal>> {
        private JurnalDao jurnalDao;
        private Date startDate, endDate;
        private int idCorporation;

        public getAllAsyncTask(JurnalDao jurnalDao, Date startDate, Date endDate, int idCorporation){
            this.jurnalDao = jurnalDao;
            this.startDate = startDate;
            this.endDate = endDate;
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
            return jurnalDao.getAllJurnalForReport(startDate.getTime(), endDate.getTime(), idCorporation);
        }
    }
}
