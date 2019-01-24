package com.example.android.labakm.Fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.labakm.Adapter.AkunAdapter;
import com.example.android.labakm.R;
import com.example.android.labakm.dao.AkunDao;
import com.example.android.labakm.entity.Akun;
import com.example.android.labakm.entity.Corporation;
import com.example.android.labakm.setting.DatabaseSetting;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FragmentBas extends Fragment {
    private ListView listViewAkun;
    private AkunAdapter akunAdapter;
    private List<Akun> akunList;
    private AkunDao akunDao;
    private Date startDate, endDate;
    private Corporation corporationIntent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bas, container, false);
        listViewAkun = view.findViewById(R.id.list_akun);
        akunDao = DatabaseSetting.getDatabase(getContext()).akunDao();

        Bundle bundle = getArguments();
        if(null != bundle){
            corporationIntent = (Corporation) bundle.getSerializable("idSelected");
            startDate = new Date(bundle.getLong("awal", 0));
            endDate = new Date(bundle.getLong("akhir", 0));
        }
        try {
            akunList = new getAllAsyncTask(akunDao,corporationIntent.getId()).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        akunAdapter = new AkunAdapter(akunList, getContext());
        listViewAkun.setAdapter(akunAdapter);
        return view;
    }

    private static class getAllAsyncTask extends AsyncTask<Void,Void,List<Akun>> {
        private AkunDao akunDao;
        private int idCorporation;

        public getAllAsyncTask(AkunDao akunDao, int idCorporation){
            this.akunDao = akunDao;
            this.idCorporation = idCorporation;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Akun> akuns) {
            super.onPostExecute(akuns);
        }

        @Override
        protected List<Akun> doInBackground(Void... voids) {
            return akunDao.getAllAkun(idCorporation);
        }
    }
}
