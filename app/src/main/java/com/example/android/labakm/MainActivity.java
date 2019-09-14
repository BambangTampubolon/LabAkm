package com.example.android.labakm;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.android.labakm.Fragment.FragmentExitApplication;
import com.example.android.labakm.Interface.FragmentExitInterface;
import com.example.android.labakm.dao.CorporationDao;
import com.example.android.labakm.entity.Corporation;
import com.example.android.labakm.entity.MySingleton;
import com.example.android.labakm.setting.DatabaseSetting;
import com.example.android.labakm.util.DatePickerFragment;
import com.example.android.labakm.util.StaticVariable;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, FragmentExitInterface{

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
    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAsDHkEeQ:APA91bH697nLvUSRrTXlC95hT1RP70OU2kJz9Kr2xPc6v_8Awcj7jfA8kynSPaBZtxEZey8jFqdjqvbQQF1DHvDh5Dxul7ne8rQJ_NhwVKc6EDlEwOih4zsfpToOo4aulyrIp2MkjNhW";
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";
    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;
    private FragmentManager fragmentManager;
    private SharedPreferences sharedPreferences;
    private FragmentExitApplication fragmentExitApplication;
    private Intent toSignInIntent;

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

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        String deviceToken = instanceIdResult.getToken();
                        Log.i("onsucesstoken", "onSuccess: " + deviceToken);
                    }
                });

//        FirebaseMessaging.getInstance().subscribeToTopic("basar")
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        String msg = "SUCESSS";
//                        if(!task.isSuccessful()){
//                            msg = "FAILED";
//                        }
//                        Log.i("cek", "onComplete: " + msg);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });

        corporationDao = DatabaseSetting.getDatabase(this).corporationDao();
        toProfileIntent = new Intent(this, ProfileActivity.class);
        toSignInIntent = new Intent(this, SignInActivity.class);
        try {
            listCorporationDb = new getAllAsyncTask(corporationDao).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        String[] corpDB = populateSpinner(listCorporationDb).toArray(new String[0]);
        spinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, corpDB);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinnerCorp.setAdapter(spinnerAdapter);
        sharedPreferences = getSharedPreferences(StaticVariable.USER_PREFERENCES, Context.MODE_PRIVATE);


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

//                TOPIC = "/topics/basar";
                TOPIC = "/cR6rkMefLO4:APA91bGWT16WZDD_aKTV6fPYxucdwsXQXjL0VXytq6rL8r2REJVVKIOZe71vcgvKElsVxh4cZOrQW-e8tJDyhIrvGf2sNJ8VqRhCcTXXTw2D8z6QoTwDUgY5Slm3DtJkLESHCGY4yN0g";
                NOTIFICATION_TITLE = "amrunn sempakkkk";
                NOTIFICATION_MESSAGE = "KOEEEEEEE";

                JSONObject notification = new JSONObject();
                JSONObject notifBody = new JSONObject();
                try {
                    notifBody.put("title", NOTIFICATION_TITLE);
                    notifBody.put("message", NOTIFICATION_MESSAGE);

                    notification.put("to", TOPIC);
                    notification.put("data", notifBody);
                    JSONArray registrationId = new JSONArray();
                    registrationId.put("cR6rkMefLO4:APA91bGWT16WZDD_aKTV6fPYxucdwsXQXjL0VXytq6rL8r2REJVVKIOZe71vcgvKElsVxh4cZOrQW-e8tJDyhIrvGf2sNJ8VqRhCcTXXTw2D8z6QoTwDUgY5Slm3DtJkLESHCGY4yN0g");
                    notification.put("token", registrationId);
                    Log.i("cekjson", "onClick: " + notification.toString());
                } catch (JSONException e) {
                    Log.i("CEKERROR", "onClick: ");
                }

                sendNotification(notification);
            }
        });
    }

    private void sendNotification(JSONObject notification){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("cekresponse", "onResponse: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Request error", Toast.LENGTH_LONG).show();
                        Log.i("cekerrorcall", "onErrorResponse: " + "notaworka");
                    }
                })
                {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
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

    @Override
    public void okExitButton() {
        if(fragmentExitApplication.isVisible()){
            fragmentExitApplication.dismiss();
        }
        sharedPreferences.edit().clear().apply();
        toSignInIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        toSignInIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toSignInIntent);
    }

    @Override
    public void cancelExitButton() {
        if(fragmentExitApplication.isVisible()){
            fragmentExitApplication.dismiss();
        }
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

    @Override
    public void onBackPressed() {
        fragmentManager = getFragmentManager();
        fragmentExitApplication = (FragmentExitApplication) fragmentManager.findFragmentByTag("exit_app");
        if(null == fragmentExitApplication){
            fragmentExitApplication = new FragmentExitApplication();
            Bundle bundle = new Bundle();
            bundle.putString("title_value", "APAKAH ANDA YAKIN AKAN KELUAR");
            fragmentExitApplication.setArguments(bundle);
        }
        if(!fragmentExitApplication.isVisible()){
            fragmentExitApplication.show(fragmentManager, "initial_jurnal");
        }
    }
}
