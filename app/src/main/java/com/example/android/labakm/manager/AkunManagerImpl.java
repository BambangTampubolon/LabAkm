package com.example.android.labakm.manager;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.labakm.Interface.AkunManager;
import com.example.android.labakm.dao.AkunDao;
import com.example.android.labakm.entity.Akun;

import java.util.List;

public class AkunManagerImpl implements AkunManager {
    private AkunDao akunDao;
    private Context context;

    public AkunManagerImpl(AkunDao akunDao, Context context){
        this.akunDao = akunDao;
        this.context = context;
    }

    @Override
    public long insertAkun(Akun akun) throws Exception{
        return new insertAkunAsyncTask(akunDao).execute(akun).get();
    }

    @Override
    public List<Akun> getAllAkun(int idCorporation) throws Exception{
        return new getAllAkunAsyncTask(akunDao).execute(idCorporation).get();
    }

    private static class getAllAkunAsyncTask extends AsyncTask<Integer,Void,List<Akun>> {
        private AkunDao akunDao;

        public getAllAkunAsyncTask(AkunDao akunDao){
            this.akunDao = akunDao;
        }

        @Override
        protected List<Akun> doInBackground(Integer... integers) {
            return akunDao.getAllAkun(integers[0]);
        }

        @Override
        protected void onPostExecute(List<Akun> akunList) {
            super.onPostExecute(akunList);
        }
    }

    private static class insertAkunAsyncTask extends AsyncTask<Akun, Void, Long>{
        private AkunDao akunDao;

        public insertAkunAsyncTask(AkunDao akunDao){
            this.akunDao = akunDao;
        }


        @Override
        protected Long doInBackground(Akun... akuns) {
            return akunDao.insert(akuns[0]);
        }
    }
}
