package com.example.android.labakm.manager;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.labakm.Interface.JurnalManager;
import com.example.android.labakm.dao.JurnalDao;
import com.example.android.labakm.entity.Jurnal;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class JurnalManagerImpl implements JurnalManager{
    private JurnalDao jurnalDao;
    private Context context;

    public JurnalManagerImpl(JurnalDao jurnalDao, Context context){
        this.jurnalDao = jurnalDao;
        this.context = context;
    }

    @Override
    public long insertJurnal(Jurnal jurnal) {
        long idSaved = 0;
        try {
            idSaved = new insertJurnalAsyncTask(jurnalDao).execute(jurnal).get();
        }catch(Exception e){
            e.printStackTrace();
        }
        return idSaved;
    }

    @Override
    public List<Jurnal> getAllJurnal(int idCorp) {
        List<Jurnal> jurnalReturned = null;
        try {
            jurnalReturned = new getAllJurnalAsyncTask(jurnalDao).execute(idCorp).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jurnalReturned;
    }

    @Override
    public List<Jurnal> getAllJurnalForReport(Long startDate, Long endDate, int idCorp) {
        List<Jurnal> jurnalReturned = null;
        try {
            jurnalReturned = new getAllJurnalForReportAsyncTask(jurnalDao, startDate, endDate, idCorp).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jurnalReturned;
    }

    @Override
    public List<Jurnal> getAllJurnalForReportLedger(Long startDate, Long endDate, int idCorp) {
        List<Jurnal> jurnalReturned = null;
        try {
            jurnalReturned = new getAllJurnalForReportLedgerAsyncTask(jurnalDao, startDate, endDate, idCorp).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jurnalReturned;
    }

    @Override
    public List<Jurnal> getAllJurnalByKodeAkun(Long startDate, Long endDate, String akun, int idCorp) {
        List<Jurnal> jurnalReturned = null;
        try {
            jurnalReturned = new getAllJurnalByKodeAkunAsyncTask(jurnalDao, startDate, endDate, idCorp, akun).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jurnalReturned;
    }

    @Override
    public int deleteJurnal(int idJurnal) {
        int rowAffected = 0;
        try {
            rowAffected = new deleteJurnalAsyncTask(jurnalDao).execute(idJurnal).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowAffected;
    }

    @Override
    public int updateJurnal(Jurnal jurnal) {
        int rowAffected = 0;
        try {
            rowAffected = new updateJurnalAsyncTask(jurnalDao).execute(jurnal).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowAffected;
    }

    private static class deleteJurnalAsyncTask extends AsyncTask<Integer, Void, Integer>{
        private JurnalDao jurnalDao;

        public deleteJurnalAsyncTask(JurnalDao jurnalDao){
            this.jurnalDao = jurnalDao;
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            return jurnalDao.deleteJurnal(integers[0]);
        }
    }


    private static class updateJurnalAsyncTask extends AsyncTask<Jurnal, Void, Integer>{
        private JurnalDao jurnalDao;

        public updateJurnalAsyncTask(JurnalDao jurnalDao){
            this.jurnalDao = jurnalDao;
        }

        @Override
        protected Integer doInBackground(Jurnal... jurnals) {
            return jurnalDao.updateJurnal(jurnals[0]);
        }
    }

    private static class insertJurnalAsyncTask extends AsyncTask<Jurnal, Void, Long>{
        private JurnalDao jurnalDao;

        public insertJurnalAsyncTask(JurnalDao jurnalDao){
            this.jurnalDao = jurnalDao;
        }

        @Override
        protected Long doInBackground(Jurnal... jurnals) {
            long idSaved = jurnalDao.insertJurnal(jurnals[0]);
            return idSaved;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
        }
    }

    private static class getAllJurnalAsyncTask extends AsyncTask<Integer, Void, List<Jurnal>>{
        private JurnalDao jurnalDao;

        public getAllJurnalAsyncTask(JurnalDao jurnalDao){
            this.jurnalDao = jurnalDao;
        }

        @Override
        protected List<Jurnal> doInBackground(Integer... integers) {
            List<Jurnal> jurnalList = jurnalDao.getAllJurnal(integers[0]);
            return jurnalList;
        }

        @Override
        protected void onPostExecute(List<Jurnal> jurnalList) {
            super.onPostExecute(jurnalList);
        }
    }

    private static class getAllJurnalForReportAsyncTask extends AsyncTask<Void, Void, List<Jurnal>>{
        private JurnalDao jurnalDao;
        private long startDate, endDate;
        private int idCorp;

        public getAllJurnalForReportAsyncTask(JurnalDao jurnalDao, long startDate, long endDate,
                                     int idCorp){
            this.jurnalDao = jurnalDao;
            this.startDate = startDate;
            this.endDate = endDate;
            this.idCorp = idCorp;
        }

        @Override
        protected List<Jurnal> doInBackground(Void... voids) {
            List<Jurnal> jurnalList = jurnalDao.getAllJurnalForReport(startDate, endDate, idCorp);
            return jurnalList;
        }

        @Override
        protected void onPostExecute(List<Jurnal> jurnalList) {
            super.onPostExecute(jurnalList);
        }
    }

    private static class getAllJurnalForReportLedgerAsyncTask extends AsyncTask<Void, Void, List<Jurnal>>{
        private JurnalDao jurnalDao;
        private long startDate, endDate;
        private int idCorp;

        public getAllJurnalForReportLedgerAsyncTask(JurnalDao jurnalDao, long startDate, long endDate,
                                              int idCorp){
            this.jurnalDao = jurnalDao;
            this.startDate = startDate;
            this.endDate = endDate;
            this.idCorp = idCorp;
        }

        @Override
        protected List<Jurnal> doInBackground(Void... voids) {
            List<Jurnal> jurnalList = jurnalDao.getAllJurnalForReportLedger(startDate, endDate, idCorp);
            return jurnalList;
        }

        @Override
        protected void onPostExecute(List<Jurnal> jurnalList) {
            super.onPostExecute(jurnalList);
        }
    }

    private static class getAllJurnalByKodeAkunAsyncTask extends AsyncTask<Void, Void, List<Jurnal>>{
        private JurnalDao jurnalDao;
        private long startDate, endDate;
        private int idCorp;
        private String akun;

        public getAllJurnalByKodeAkunAsyncTask(JurnalDao jurnalDao, long startDate, long endDate,
                                                    int idCorp, String akun){
            this.jurnalDao = jurnalDao;
            this.startDate = startDate;
            this.endDate = endDate;
            this.idCorp = idCorp;
            this.akun = akun;
        }

        @Override
        protected List<Jurnal> doInBackground(Void... voids) {
            List<Jurnal> jurnalList = jurnalDao.getAllJurnalByKodeAkun(startDate, endDate, akun, idCorp);
            return jurnalList;
        }

        @Override
        protected void onPostExecute(List<Jurnal> jurnalList) {
            super.onPostExecute(jurnalList);
        }
    }
}
