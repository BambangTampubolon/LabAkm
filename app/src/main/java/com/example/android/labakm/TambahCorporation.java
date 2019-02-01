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
import com.example.android.labakm.Interface.CorporationManager;
import com.example.android.labakm.Interface.FragmentPauseInterface;
import com.example.android.labakm.dao.CorporationDao;
import com.example.android.labakm.entity.Corporation;
import com.example.android.labakm.manager.CorporationManagerImpl;
import com.example.android.labakm.setting.DatabaseSetting;

public class TambahCorporation extends AppCompatActivity implements FragmentPauseInterface{

    private static CorporationDao corporationDao;
    private EditText editNama, editAddress;
    private Button buttonSave;
    private static Toast toastSucess;
    private static Intent toMainActivityIntent;
    private FragmentManager fragmentManager;
    private FragmentValidasiSave fragmentValidasiSave;
    private CorporationManager corporationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_corporation);
        editNama = findViewById(R.id.edit_nama_korporasi);
        editAddress = findViewById(R.id.edit_alamat_korporasi);
        buttonSave = findViewById(R.id.save_button);
        corporationDao = DatabaseSetting.getDatabase(this).corporationDao();
        corporationManager = new CorporationManagerImpl(corporationDao, this);

        toMainActivityIntent = new Intent(this, MainActivity.class);
        toastSucess = Toast.makeText(this, "Data berhasil Disimpan", Toast.LENGTH_LONG);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validasiSave()){
                    showIntialFragment();
                }
            }
        });
    }

    public void saveKorporasi(final Corporation corporation){
        if(corporationManager.insertCorporation(corporation) > 0){
            toastSucess.show();
            startActivity(toMainActivityIntent);
        }
    }

    @Override
    public void okButton() {
        Corporation corporation = new Corporation();
        corporation.setIsactive(false);
        corporation.setName(editNama.getText().toString());
        corporation.setAddress(editAddress.getText().toString());
        saveKorporasi(corporation);
    }

    @Override
    public void cancelButton() {
        this.fragmentValidasiSave.dismiss();
    }

    private boolean validasiSave(){
        boolean isValid = true;
        Toast toastFailed;
        if(null == editNama.getText()){
            isValid = false;
            toastFailed = Toast.makeText(this, "ISI NAMA KORPORASI TERLEBIH DAHULU", Toast.LENGTH_LONG);
            toastFailed.show();
            return isValid;
        }
        if(null == editAddress.getText()){
            isValid = false;
            toastFailed = Toast.makeText(this, "ISI ALAMAT KORPORASI TERLEBIH DAHULU", Toast.LENGTH_LONG);
            toastFailed.show();
            return isValid;
        }
        return isValid;
    }


    private void showIntialFragment(){
        fragmentManager = getFragmentManager();
        fragmentValidasiSave = (FragmentValidasiSave) fragmentManager.findFragmentByTag("intial");
        if(null == fragmentValidasiSave){
            fragmentValidasiSave = new FragmentValidasiSave();
            Bundle bundle = new Bundle();
            bundle.putString("title_value", "APAKAH ANDA YAKIN AKAN MENYIMPAN DATA KORPORASI");
            fragmentValidasiSave.setArguments(bundle);
        }
        if(!fragmentValidasiSave.isVisible()){
            fragmentValidasiSave.show(fragmentManager, "intial");
        }
    }
}
