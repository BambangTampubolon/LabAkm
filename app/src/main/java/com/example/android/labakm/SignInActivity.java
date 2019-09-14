package com.example.android.labakm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.labakm.entity.UserToken;
import com.example.android.labakm.rest.UserTokenRest;
import com.example.android.labakm.rest.impl.UserTokenRestImpl;
import com.example.android.labakm.util.StaticVariable;

import java.util.List;

public class SignInActivity extends Activity {
//    ProgressBar progressBar;
    TextInputEditText editUserName, editPassword;
    boolean isExistUsername = false;
    UserTokenRest userTokenRest;
    Button buttonSignIn, buttonCancel;
    List<UserToken> userToken;
    SharedPreferences sharedPreferences;
    Intent toMainActivityIntent;
    TextView textSignUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        editUserName = findViewById(R.id.edit_username);
        editPassword = findViewById(R.id.edit_password);
//        progressBar = findViewById(R.id.progress_exist_username);
        textSignUp = findViewById(R.id.text_tambah_korporasi);
        buttonSignIn = findViewById(R.id.button_signin);
        buttonCancel = findViewById(R.id.button_cancel);
        userTokenRest = new UserTokenRestImpl();
//        editPassword.setEnabled(false);
        sharedPreferences = getSharedPreferences(StaticVariable.USER_PREFERENCES, Context.MODE_PRIVATE);

        final Intent toTambahCorporationIntent = new Intent(this, TambahCorporation.class);
        textSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(toTambahCorporationIntent);
            }
        });

        Toast invalidPasswordToast = Toast.makeText(this, "Password yang anda masukkan salah", Toast.LENGTH_SHORT);
        Toast invalidUsernameToast = Toast.makeText(this, "Username yang anda masukkan tidak terdaftar", Toast.LENGTH_SHORT);
        toMainActivityIntent = new Intent(this, MainActivity.class);

        editUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    String username = editUserName.getText().toString();
                    Log.i("checkusername", "onFocusChange: " + username);
                    isExistUsername = userTokenRest.isExistUsername(username);
//                    setActiveEditPassword(isExistUsername);
                    if(!isExistUsername){
                        invalidUsernameToast.show();
                    }
                }
            }
        });

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userToken = userTokenRest.isValidLogin(editUserName.getText().toString(), editPassword.getText().toString());
                if(userToken.size() > 0){
                    UserToken entity = userToken.get(0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("idsaved", entity.getId());
                    editor.apply();
                    startActivity(toMainActivityIntent);
                }else {
                    invalidPasswordToast.show();
                }
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        int idsaved = sharedPreferences.getInt("idsaved", 0);
        if(idsaved > 0){
            startActivity(toMainActivityIntent);
        }
    }

    private void setActiveEditPassword(boolean isExistUsername){
        editPassword.setEnabled(isExistUsername);
    }

}
