package com.example.android.labakm;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.android.labakm.Fragment.FragmentValidasiSave;
import com.example.android.labakm.Interface.AkunManager;
import com.example.android.labakm.Interface.FragmentPauseInterface;
import com.example.android.labakm.dao.AkunDao;
import com.example.android.labakm.entity.Akun;
import com.example.android.labakm.entity.Barang;
import com.example.android.labakm.entity.Corporation;
import com.example.android.labakm.entity.viewmodel.BarangViewModel;
import com.example.android.labakm.manager.AkunManagerImpl;
import com.example.android.labakm.rest.BarangRest;
import com.example.android.labakm.rest.impl.BarangRestImpl;
import com.example.android.labakm.setting.DatabaseSetting;

public class TambahAkun extends AppCompatActivity implements FragmentPauseInterface{
    private Corporation corporationItent;
    private EditText editKode, editNama, editSaldo;
    private Button saveButton;
    private static AkunDao akunDao;
    private static Toast toastSucess;
    private static Intent toProfileIntent;
    private FragmentManager fragmentManager;
    private FragmentValidasiSave fragmentValidasiSave;
    private AkunManager akunManager;
    private BarangRest barangRest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_akun);
        editKode = findViewById(R.id.edit_kode_akun);
        editNama = findViewById(R.id.edit_nama_akun);
        editSaldo = findViewById(R.id.edit_saldo_awal);
        saveButton = findViewById(R.id.save_button);
        toastSucess = Toast.makeText(this, "Data berhasil Disimpan", Toast.LENGTH_LONG);
        akunDao = DatabaseSetting.getDatabase(this).akunDao();
        akunManager = new AkunManagerImpl(akunDao, this);
        barangRest = new BarangRestImpl();


        Intent intentFromProfile = getIntent();
        corporationItent = (Corporation) intentFromProfile.getSerializableExtra("idSelected");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validasiSave()){
                    showIntialFragment();
                }
            }
        });

        toProfileIntent = new Intent(this,ProfileActivity.class);
        toProfileIntent.putExtra("idSelected", corporationItent);
    }

    public void saveAkun(Akun akun) throws Exception {
        long idReturned = akunManager.insertAkun(akun);
//        barangRest.getAllBarang();
        Barang a = new Barang();
        a.setCreatedby("test");
        a.setHarga(1000);
        a.setNama("testnama");
        a.setTipe_barang_id(5);
        a.setId(0);
        barangRest.saveBarang(a);
        if(idReturned > 0){
           toastSucess.show();
           startActivity(toProfileIntent);
        }
    }

    private void showIntialFragment(){
        fragmentManager = getFragmentManager();
        fragmentValidasiSave = (FragmentValidasiSave) fragmentManager.findFragmentByTag("initial_akun");
        if(null == fragmentValidasiSave){
            fragmentValidasiSave = new FragmentValidasiSave();
            Bundle bundle = new Bundle();
                bundle.putString("title_value", "APAKAH ANDA YAKIN AKAN MENYIMPAN DATA AKUN");
            fragmentValidasiSave.setArguments(bundle);
        }
        if(!fragmentValidasiSave.isVisible()){
            fragmentValidasiSave.show(fragmentManager, "initial_akun");
        }
    }

    @Override
    public void okButton() {
        Akun akun = new Akun();
        akun.setName(editNama.getText().toString());
        akun.setKode(editKode.getText().toString());
        if("".equalsIgnoreCase(editSaldo.getText().toString())){
            akun.setSaldo_awal(0);
        }else {
            akun.setSaldo_awal(Integer.valueOf(editSaldo.getText().toString()));
        }
        akun.setId_corporation(corporationItent.getId());
        try {
            saveAkun(akun);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validasiSave(){
        boolean isValid = true;
        Toast toastFailed;
        if(null == editNama.getText()){
            isValid = false;
            toastFailed = Toast.makeText(this, "ISI NAMA AKUN TERLEBIH DAHULU", Toast.LENGTH_LONG);
            toastFailed.show();
            return isValid;
        }
        if(null == editKode.getText()){
            isValid = false;
            toastFailed = Toast.makeText(this, "ISI KODE AKUN TERLEBIH DAHULU", Toast.LENGTH_LONG);
            toastFailed.show();
            return isValid;
        }
        return isValid;
    }


    @Override
    public void cancelButton() {
        this.fragmentValidasiSave.dismiss();
    }
}
