package com.example.android.labakm;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.android.labakm.Adapter.JurnalFragmentAdapter;

import com.example.android.labakm.Fragment.FragmentValidasiDelete;
import com.example.android.labakm.Fragment.FragmentValidasiSave;
import com.example.android.labakm.Fragment.StateSelectionFragment;
import com.example.android.labakm.Interface.AkunManager;
import com.example.android.labakm.Interface.CorporationManager;
import com.example.android.labakm.Interface.FragmentDeleteInterface;
import com.example.android.labakm.Interface.FragmentPauseInterface;
import com.example.android.labakm.Interface.JurnalManager;
import com.example.android.labakm.Interface.ListJurnalInterface;
import com.example.android.labakm.dao.AkunDao;
import com.example.android.labakm.dao.CorporationDao;
import com.example.android.labakm.dao.JurnalDao;
import com.example.android.labakm.entity.Akun;
import com.example.android.labakm.entity.Corporation;
import com.example.android.labakm.entity.Jurnal;
import com.example.android.labakm.manager.AkunManagerImpl;
import com.example.android.labakm.manager.CorporationManagerImpl;
import com.example.android.labakm.manager.JurnalManagerImpl;
import com.example.android.labakm.setting.DatabaseSetting;
import com.example.android.labakm.util.DatePickerFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class TambahJurnal extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, FragmentPauseInterface,
        FragmentDeleteInterface, ListJurnalInterface{

    private CorporationDao corporationDao;
    StateSelectionFragment stateSelectionFragment;
    private Corporation corporationIntent;
    private Spinner spinnerAkun;
    private List<Akun> akunsDB;
    private static AkunDao akunDao;
    private static JurnalDao jurnalDao;
    private ArrayAdapter<String> spinnerAdapter;
    private TextView textTanggal;
    private EditText editDebit, editKredit, editKeterangan;
    private CheckBox checkKredit, checkDebit;
    private Button saveButton, addButton, editButton, cancelButton;
    private Date tanggalJurnal;
    private Akun akunSelected;
    private static Toast toastSucessSave, toasSucessUpdate, toastSucessDelete;
    private static Intent toProfileIntent;
    private FragmentManager fragmentManager;
    private FragmentValidasiSave fragmentValidasiSave;
    private ListView listViewJurnal;
    private JurnalFragmentAdapter jurnalFragmentAdapter;
    private List<Jurnal> listAllJurnal;
    private FragmentValidasiDelete fragmentValidasiDelete;
    private Jurnal jurnalSelected;
    private boolean isEditMode;
    private CorporationManager corporationManager;
    private AkunManager akunManager;
    private JurnalManager jurnalManager;

    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
    String[] akunDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_jurnal);
        spinnerAkun = findViewById(R.id.spinner_akun);
        textTanggal = findViewById(R.id.tanggal_jurnal);
        editDebit = findViewById(R.id.edit_debit);
        editKredit = findViewById(R.id.edit_kredit);
        checkDebit = findViewById(R.id.check_isdebit);
        checkKredit = findViewById(R.id.check_iskredit);
        editKeterangan = findViewById(R.id.edit_keterangan);
        saveButton = findViewById(R.id.save_button);
        listViewJurnal = findViewById(R.id.list_jurnal_all);
        addButton = findViewById(R.id.add_button);
        editButton = findViewById(R.id.edit_button);
        cancelButton = findViewById(R.id.cancel_button);

        isEditMode = false;
        corporationDao = DatabaseSetting.getDatabase(this).corporationDao();
        akunDao = DatabaseSetting.getDatabase(this).akunDao();
        jurnalDao = DatabaseSetting.getDatabase(this).jurnalDao();

        corporationManager = new CorporationManagerImpl(corporationDao, this);
        jurnalManager = new JurnalManagerImpl(jurnalDao, this);
        akunManager = new AkunManagerImpl(akunDao, this);

        toProfileIntent = new Intent(this,ProfileActivity.class);
        try {
            corporationIntent = corporationManager.getCorporationActive(true);
            akunsDB = akunManager.getAllAkun(corporationIntent.getId());
            listAllJurnal = jurnalManager.getAllJurnal(corporationIntent.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        toastSucessSave = Toast.makeText(this, "Data Jurnal berhasil Disimpan", Toast.LENGTH_LONG);
        toasSucessUpdate = Toast.makeText(this, "Data Jurnal berhasil Diubah", Toast.LENGTH_LONG);
        toastSucessDelete = Toast.makeText(this, "Data Jurnal berhasil Dihapus", Toast.LENGTH_LONG);
        akunDB = populateSpinner(akunsDB).toArray(new String[0]);
        spinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, akunDB);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerAkun.setAdapter(spinnerAdapter);

        jurnalFragmentAdapter = new JurnalFragmentAdapter(listAllJurnal, this, this);
        listViewJurnal.setAdapter(jurnalFragmentAdapter);

        spinnerAkun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!= 0){
                    akunSelected = akunsDB.get(i-1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        textTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker(view);
            }
        });
        editKredit.setEnabled(false);
        editDebit.setEnabled(false);
        checkKredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkKredit.isChecked()) {
                    editKredit.setEnabled(true);
                }else {
                    editKredit.setEnabled(false);
                }
            }
        });

        checkDebit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkDebit.isChecked()){
                    editDebit.setEnabled(true);
                }else {
                    editDebit.setEnabled(false);
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validasiSave()){
                    showIntialFragment();
                }
            }
        });
        conditionInitiateUI(isEditMode);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isEditMode = true;
                conditionInitiateUI(isEditMode);
                editSelectionMode();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isEditMode = true;
                conditionInitiateUI(isEditMode);
                clearConditionUI();
                jurnalSelected = null;
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isEditMode = false;
                conditionInitiateUI(isEditMode);
            }
        });



    }

    private void editSelectionMode(){
        if(checkDebit.isChecked()){
            editDebit.setEnabled(true);
        }
        if(checkKredit.isChecked()){
            editKredit.setEnabled(true);
        }
    }

    @Override
    public void setSelectedItem(Jurnal jurnal) {
        this.jurnalSelected = jurnal;
        clearConditionUI();
        if(jurnal.getTotal_kredit() != 0){
            checkKredit.setChecked(true);
            editKredit.setText(String.valueOf(jurnal.getTotal_kredit()));
        }
        if(jurnal.getTotal_debit() != 0){
            checkDebit.setChecked(true);
            editDebit.setText(String.valueOf(jurnal.getTotal_debit()));
        }
        if(null != jurnal.getCreated_date()){
            tanggalJurnal = jurnal.getCreated_date();
            textTanggal.setText(dateFormat.format(jurnal.getCreated_date()));
        }
        if(jurnal.getKeterangan() != null){
            editKeterangan.setText(jurnal.getKeterangan());
        }
        int selectedAkun = 0;
        for(int i=0; i < akunsDB.size(); i++){
            if(akunsDB.get(i).getKode().equalsIgnoreCase(jurnal.getId_akun())){
                selectedAkun = i;
            }
        }
        spinnerAkun.setSelection(selectedAkun+1);
    }


    private void conditionInitiateUI(boolean isEditMode){
        spinnerAkun.setEnabled(isEditMode);
        textTanggal.setEnabled(isEditMode);
        checkDebit.setEnabled(isEditMode);
        checkKredit.setEnabled(isEditMode);
        editKeterangan.setEnabled(isEditMode);
        saveButton.setEnabled(isEditMode);
        cancelButton.setEnabled(isEditMode);
        addButton.setEnabled(!isEditMode);
        editButton.setEnabled(!isEditMode);
    }

    private void clearConditionUI(){
        spinnerAkun.setSelection(0);
        textTanggal.setText("");
        checkKredit.setChecked(false);
        checkDebit.setChecked(false);
        editDebit.setText("");
        editKredit.setText("");
        editKeterangan.setText("");
        tanggalJurnal = null;
    }

    private void datePicker(View view){
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getFragmentManager(), "dialog");
    }

    public List<String> populateSpinner(List<Akun> akuns){
        List<String> listCorporations = new ArrayList<>();
        listCorporations.add("Pilih AKUN");
        if(null != akuns){
            for(Akun akun: akuns){
                StringBuilder sb = new StringBuilder();
                sb.append(akun.getKode()).append(" - ").append(akun.getName());
                listCorporations.add(sb.toString());
            }
        }
        return listCorporations;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar = new GregorianCalendar(i, i1, i2);
        setDateAwal(calendar);
    }

    private void setDateAwal(final Calendar calendar){
        tanggalJurnal = calendar.getTime();
        textTanggal.setText(dateFormat.format(calendar.getTime()));
    }

    @Override
    public void okButton() {
        if(null == jurnalSelected){
            Jurnal jurnal = new Jurnal();
            jurnal.setId_corporation(corporationIntent.getId());
            jurnal.setCreated_date(tanggalJurnal);
            jurnal.setId_akun(akunSelected.getKode());
            jurnal.setNama_akun(akunSelected.getName());
            jurnal.setTotal_kredit(0);
            jurnal.setTotal_debit(0);
            if(checkKredit.isChecked()){
                jurnal.setTotal_kredit(Integer.valueOf(editKredit.getText().toString()));
            }
            if(checkDebit.isChecked()){
                jurnal.setTotal_debit(Integer.valueOf(editDebit.getText().toString()));
            }
            jurnal.setKeterangan(editKeterangan.getText().toString());
            jurnal.setSaldo_awal(akunSelected.getSaldo_awal());
            saveJurnal(jurnal);
        }else {
            Jurnal jurnal = new Jurnal();
            jurnal.setId(jurnalSelected.getId());
            jurnal.setId_corporation(corporationIntent.getId());
            jurnal.setCreated_date(tanggalJurnal);
            jurnal.setId_akun(akunSelected.getKode());
            jurnal.setNama_akun(akunSelected.getName());
            jurnal.setTotal_kredit(0);
            jurnal.setTotal_debit(0);
            if(checkKredit.isChecked()){
                jurnal.setTotal_kredit(Integer.valueOf(editKredit.getText().toString()));
            }
            if(checkDebit.isChecked()){
                jurnal.setTotal_debit(Integer.valueOf(editDebit.getText().toString()));
            }
            jurnal.setKeterangan(editKeterangan.getText().toString());
            jurnal.setSaldo_awal(akunSelected.getSaldo_awal());
            editJurnal(jurnal);
        }

    }

    private boolean validasiSave(){
        boolean isValid = true;
        Toast toastFailed;
        if(null == akunSelected){
            isValid = false;
            toastFailed = Toast.makeText(this, "PILIH AKUN TERLEBIH DAHULU", Toast.LENGTH_LONG);
            toastFailed.show();
            return isValid;
        }
        if(checkKredit.isChecked()){
            if(null == editKredit.getText()){
                isValid = false;
                toastFailed = Toast.makeText(this, "ISI TOTAL KREDIT TERLEBIH DAHULU", Toast.LENGTH_LONG);
                toastFailed.show();
                return isValid;
            }
        }
        if(checkDebit.isChecked()){
            if(null == editDebit.getText()){
                isValid = false;
                toastFailed = Toast.makeText(this, "ISI TOTAL DEBIT TERLEBIH DAHULU", Toast.LENGTH_LONG);
                toastFailed.show();
                return isValid;
            }
        }
        if(null ==tanggalJurnal){
            if(null == editDebit.getText()){
                isValid = false;
                toastFailed = Toast.makeText(this, "ISI TANGGAL JURNAL TERLEBIH DAHULU", Toast.LENGTH_LONG);
                toastFailed.show();
                return isValid;
            }
        }
        return isValid;
    }

    @Override
    public void cancelButton() {
        this.fragmentValidasiSave.dismiss();
    }

    @Override
    public void deleteButton(int i) {
        jurnalSelected = listAllJurnal.get(i);
        showIntialDeleteFragment();
    }

    @Override
    public void okDeleteButton() {
        deleteJurnal(jurnalSelected);
        this.fragmentValidasiDelete.dismiss();
        startActivity(toProfileIntent);
    }

    @Override
    public void cancelDeleteButton() {
        this.fragmentValidasiDelete.dismiss();
    }

    public void editJurnal(Jurnal jurnal){
        if(jurnalManager.updateJurnal(jurnal) > 0){
            toasSucessUpdate.show();
            startActivity(toProfileIntent);
        }
    }


    public void saveJurnal(Jurnal jurnal){
        if(jurnalManager.insertJurnal(jurnal) > 0){
            toastSucessSave.show();
            startActivity(toProfileIntent);
        }
    }

    public void deleteJurnal(Jurnal jurnal){
        if(jurnalManager.deleteJurnal(jurnal.getId()) > 0){
            toastSucessDelete.show();
        }
    }

    private void showIntialFragment(){
        fragmentManager = getFragmentManager();
        fragmentValidasiSave = (FragmentValidasiSave) fragmentManager.findFragmentByTag("initial_jurnal");
        if(null == fragmentValidasiSave){
            fragmentValidasiSave = new FragmentValidasiSave();
            Bundle bundle = new Bundle();
            if(null == jurnalSelected){
                bundle.putString("title_value", "APAKAH ANDA YAKIN AKAN MENYIMPAN DATA Jurnal");
            }else {
                bundle.putString("title_value", "APAKAH ANDA YAKIN AKAN MENGUBAH DATA Jurnal");
            }
            fragmentValidasiSave.setArguments(bundle);
        }
        if(!fragmentValidasiSave.isVisible()){
            fragmentValidasiSave.show(fragmentManager, "initial_jurnal");
        }
    }

    private void showIntialDeleteFragment(){
        fragmentManager = getFragmentManager();
        fragmentValidasiDelete = (FragmentValidasiDelete) fragmentManager.findFragmentByTag("initial_delete_jurnal");
        if(null == fragmentValidasiDelete){
            fragmentValidasiDelete = new FragmentValidasiDelete();
            Bundle bundle = new Bundle();
            bundle.putString("title_value", "APAKAH ANDA YAKIN AKAN MENGHAPUS DATA Jurnal");
            fragmentValidasiDelete.setArguments(bundle);
        }
        if(!fragmentValidasiDelete.isVisible()){
            fragmentValidasiDelete.show(fragmentManager, "initial_delete_jurnal");
        }
    }



}
