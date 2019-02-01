package com.example.android.labakm;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.labakm.dao.CorporationDao;
import com.example.android.labakm.entity.Corporation;
import com.example.android.labakm.setting.DatabaseSetting;
import com.example.android.labakm.util.DatePickerFragment;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private Spinner spinnerCorp;
    private CorporationDao corporationDao;
    private ArrayAdapter<String> spinnerAdapter;
    private List<Corporation> listCorporationDb;
    private Button loginButton;
    private Corporation corporationSelected;
    private TextView tanggalAwal, tanggalAkhir, textTambah;
    private boolean isAwal;
    private Date dateAwal, dateAkhir;
    Intent toProfileIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinnerCorp = findViewById(R.id.spinner_corp);
        loginButton = findViewById(R.id.login_button);
        tanggalAkhir = findViewById(R.id.tanggal_akhir);
        tanggalAwal = findViewById(R.id.tanggal_awal);
        textTambah = findViewById(R.id.text_tambah_korporasi);
        isAwal = false;


        corporationDao = DatabaseSetting.getDatabase(this).corporationDao();
        toProfileIntent = new Intent(this, ProfileActivity.class);
        try {
            listCorporationDb = new getAllAsyncTask(corporationDao).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        String[] corpDB = populateSpinner(listCorporationDb).toArray(new String[0]);
        spinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, corpDB);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerCorp.setAdapter(spinnerAdapter);

        tanggalAwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAwal = true;
                datePicker(view);
            }
        });

        tanggalAkhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAwal = false;
                datePicker(view);
            }
        });

        spinnerCorp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 0){
                    corporationSelected = listCorporationDb.get(i-1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Intent toTambahCorporationIntent = new Intent(this, TambahCorporation.class);
        textTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(toTambahCorporationIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validasiForm()){
                    toProfileIntent.putExtra("idSelected", corporationSelected);
                    toProfileIntent.putExtra("awal", dateAwal.getTime());
                    toProfileIntent.putExtra("akhir", dateAkhir.getTime());
                    new changeIsActive(corporationDao, corporationSelected.getId()
                    , dateAwal.getTime(), dateAkhir.getTime(), toProfileIntent).execute();

                }
            }
        });
    }

    public List<String> populateSpinner(List<Corporation> corporations){
        List<String> listCorporations = new ArrayList<>();
        listCorporations.add("Pilih Korporasi");
        if(null != corporations){
            for(Corporation corporation: corporations){
                listCorporations.add(corporation.getName());
            }
        }
        return listCorporations;
    }

    private void datePicker(View view){
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getFragmentManager(), "dialog");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar = new GregorianCalendar(i, i1, i2);
        if(isAwal){
            setDateAwal(calendar);
        }else {
            setDateAkhir(calendar);
        }
    }

    private void setDateAwal(final Calendar calendar){
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        dateAwal = calendar.getTime();
        tanggalAwal.setText(dateFormat.format(calendar.getTime()));
    }

    private void setDateAkhir(final Calendar calendar){
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        dateAkhir = calendar.getTime();
        tanggalAkhir.setText(dateFormat.format(calendar.getTime()));
    }

    private static class getAllAsyncTask extends AsyncTask<Void,Void,List<Corporation>>{
        private CorporationDao corporationDao;

        public getAllAsyncTask(CorporationDao corporationDao){
            this.corporationDao = corporationDao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Corporation> corporations) {
            super.onPostExecute(corporations);
        }

        @Override
        protected List<Corporation> doInBackground(Void... voids) {
            return corporationDao.getAllCorporation();
        }
    }

    private class changeIsActive extends AsyncTask<Void,Void,Void>{
        private CorporationDao corporationDao;
        private int id;
        private Long startDate;
        private Long endDate;
        private Intent toProfileIntent;

        public changeIsActive(CorporationDao corporationDao, int id, Long startDate, Long endDate, Intent toProfileIntent){
            this.corporationDao = corporationDao;
            this.id = id;
            this.startDate = startDate;
            this.endDate = endDate;
            this.toProfileIntent = toProfileIntent;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            corporationDao.changeAllIsActive(false);
            corporationDao.changeIsActive(id, true, startDate, endDate);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            startActivity(toProfileIntent);
        }
    }

    private boolean validasiForm(){
        Toast toastFailed;
        if(null == corporationSelected){
            toastFailed = Toast.makeText(this, "Pilih Korporasi Terlebih Dahulu", Toast.LENGTH_LONG);
            toastFailed.show();
            return false;
        } else if(null == dateAkhir){
            toastFailed = Toast.makeText(this, "Pilih Tanggal Akhir Terlebih Dahulu", Toast.LENGTH_LONG);
            toastFailed.show();
            return false;
        } else if(null == dateAwal){
            toastFailed = Toast.makeText(this, "Pilih Tanggal Awal Terlebih Dahulu", Toast.LENGTH_LONG);
            toastFailed.show();
            return false;
        }
        return true;
    }

}
