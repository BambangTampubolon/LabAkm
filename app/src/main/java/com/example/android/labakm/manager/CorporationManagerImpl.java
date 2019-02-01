package com.example.android.labakm.manager;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.labakm.Interface.CorporationManager;
import com.example.android.labakm.dao.CorporationDao;
import com.example.android.labakm.entity.Corporation;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CorporationManagerImpl implements CorporationManager {
    private CorporationDao corporationDao;
    private Context context;

    public CorporationManagerImpl(CorporationDao corporationDao, Context context){
        this.corporationDao = corporationDao;
        this.context = context;
    }

    @Override
    public long insertCorporation(Corporation corporation) {
        long idSaved = 0;
        try {
            idSaved = new insertCorporationAsyncTask(corporationDao).execute(corporation).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idSaved;
    }

    @Override
    public List<Corporation> getAllCorporation() {
        List<Corporation> corpDB = null;
        try {
            corpDB = new getAllCorporationAsyncTask(corporationDao).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return corpDB;
    }

    @Override
    public void changeIsActive(int id, boolean isactive, Long startDate, Long endDate) {
        try {
            new changeIsActiveAsyncTask(corporationDao, id, isactive, startDate, endDate).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeAllIsActive(boolean isactive) {
        new changeAllIsActiveAsyncTask(corporationDao).execute();
    }

    @Override
    public Corporation getCorporationActive(boolean isactive) {
        Corporation corporation = null;
        try {
            corporation = new getCorporationActiveAsyncTask(corporationDao).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return corporation;
    }

    private static class insertCorporationAsyncTask extends AsyncTask<Corporation, Void, Long>{
        private  CorporationDao corporationDao;

        public insertCorporationAsyncTask(CorporationDao corporationDao){
            this.corporationDao = corporationDao;
        }

        @Override
        protected Long doInBackground(Corporation... corporations) {
            long idSaved = corporationDao.insertCorporation(corporations[0]);
            return idSaved;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
        }
    }

    private static class getAllCorporationAsyncTask extends AsyncTask<Void,Void, List<Corporation>>{
        private CorporationDao corporationDao;

        public getAllCorporationAsyncTask(CorporationDao corporationDao){
            this.corporationDao = corporationDao;
        }

        @Override
        protected List<Corporation> doInBackground(Void... voids) {
            return corporationDao.getAllCorporation();
        }

        @Override
        protected void onPostExecute(List<Corporation> corporations) {
            super.onPostExecute(corporations);
        }
    }

    private static class changeIsActiveAsyncTask extends AsyncTask<Void, Void, Void>{
        private CorporationDao corporationDao;
        private int id;
        private boolean isActive;
        private Long startDate;
        private Long endDate;

        public changeIsActiveAsyncTask(CorporationDao corporationDao, int id
                , boolean isActive, long startDate, long endDate){
            this.corporationDao = corporationDao;
            this.id = id;
            this.startDate = startDate;
            this.isActive = isActive;
            this.endDate = endDate;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            corporationDao.changeIsActive(id, isActive, startDate, endDate);
            return null;
        }
    }

    private static class changeAllIsActiveAsyncTask extends AsyncTask<Void, Void, Void>{
        private CorporationDao corporationDao;

        public changeAllIsActiveAsyncTask(CorporationDao corporationDao){
            this.corporationDao = corporationDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            corporationDao.changeAllIsActive(false);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private static class getCorporationActiveAsyncTask extends AsyncTask<Void,Void,Corporation>{
        private CorporationDao corporationDao;

        public getCorporationActiveAsyncTask(CorporationDao corporationDao){
            this.corporationDao = corporationDao;
        }

        @Override
        protected Corporation doInBackground(Void... voids) {
            return corporationDao.getCorporationActive(true);
        }

        @Override
        protected void onPostExecute(Corporation corporation) {
            super.onPostExecute(corporation);
        }
    }
}
