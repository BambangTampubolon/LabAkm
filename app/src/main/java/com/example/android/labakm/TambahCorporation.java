package com.example.android.labakm;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.labakm.Fragment.FragmentValidasiSave;
import com.example.android.labakm.Interface.CorporationManager;
import com.example.android.labakm.Interface.FragmentPauseInterface;
import com.example.android.labakm.dao.CorporationDao;
import com.example.android.labakm.entity.Barang;
import com.example.android.labakm.entity.Corporation;
import com.example.android.labakm.entity.UserToken;
import com.example.android.labakm.entity.viewmodel.BarangViewModel;
import com.example.android.labakm.manager.CorporationManagerImpl;
import com.example.android.labakm.rest.UserTokenRest;
import com.example.android.labakm.rest.impl.UserTokenRestImpl;
import com.example.android.labakm.setting.DatabaseSetting;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class TambahCorporation extends AppCompatActivity implements FragmentPauseInterface{
    private static CorporationDao corporationDao;
    private TextInputEditText editUsername, editUserPassword, editUserAddress, editUserEmail;
    private Button buttonSave;
    private static Toast toastSucess, toastFailed;
    private static Intent toSignInActivity;
    private FragmentManager fragmentManager;
    private FragmentValidasiSave fragmentValidasiSave;
    private UserTokenRest userTokenRest;
    private String deviceToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_corporation);
        editUsername = findViewById(R.id.edit_username);
        editUserPassword = findViewById(R.id.edit_password);
        editUserAddress = findViewById(R.id.edit_address);
        editUserEmail = findViewById(R.id.edit_email);
        buttonSave = findViewById(R.id.save_button);
        userTokenRest = new UserTokenRestImpl();
        corporationDao = DatabaseSetting.getDatabase(this).corporationDao();

        toastSucess = Toast.makeText(this, "Data berhasil Disimpan", Toast.LENGTH_LONG);
        toastFailed = Toast.makeText(this, "Data gagal Disimpan", Toast.LENGTH_LONG);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        deviceToken = instanceIdResult.getToken();
                        toastSucess.show();
                    }
                });

        toSignInActivity = new Intent(this, SignInActivity.class);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validasiSave()){
                    showIntialFragment();
                }
            }
        });
    }

    public void saveUserToken(UserToken userToken){
        if(userTokenRest.saveNewUser(userToken)){
            toastSucess.show();
            startActivity(toSignInActivity);
        }else {
            toastSucess.show();
        }
    }

    @Override
    public void okButton() {
        UserToken userToken = new UserToken();
        userToken.setCreatedbyname("application");
        userToken.setCreatedterminal("application");
        userToken.setCreatedby("application");
        userToken.setUser_name(editUsername.getText().toString());
        userToken.setPassword(Integer.valueOf(editUserPassword.getText().toString()));
        userToken.setAlamat(editUserAddress.getText().toString());
        userToken.setEmail(editUserEmail.getText().toString());
        userToken.setFirebase_token(deviceToken);
        saveUserToken(userToken);
    }

    @Override
    public void cancelButton() {
        this.fragmentValidasiSave.dismiss();
    }

    private boolean validasiSave(){
        boolean isValid = true;
        Toast toastFailed;
        if(null == editUsername.getText()){
            isValid = false;
            toastFailed = Toast.makeText(this, "ISI NAMA KORPORASI TERLEBIH DAHULU", Toast.LENGTH_LONG);
            toastFailed.show();
            return isValid;
        }
        if(null == editUserPassword.getText()){
            isValid = false;
            toastFailed = Toast.makeText(this, "ISI PASSWORD KORPORASI TERLEBIH DAHULU", Toast.LENGTH_LONG);
            toastFailed.show();
            return isValid;
        }
        if(null == editUserEmail.getText()){
            isValid = false;
            toastFailed = Toast.makeText(this, "ISI EMAIL KORPORASI TERLEBIH DAHULU", Toast.LENGTH_LONG);
            toastFailed.show();
            return isValid;
        }
        if(null == editUserAddress.getText()){
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
