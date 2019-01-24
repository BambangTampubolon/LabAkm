package com.example.android.labakm;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.labakm.Fragment.FragmentBas;
import com.example.android.labakm.Fragment.FragmentEkuitas;
import com.example.android.labakm.Fragment.FragmentExitApplication;
import com.example.android.labakm.Fragment.FragmentIdentitas;
import com.example.android.labakm.Fragment.FragmentJurnal;
import com.example.android.labakm.Fragment.FragmentLabaRugi;
import com.example.android.labakm.Fragment.FragmentLedger;
import com.example.android.labakm.Fragment.FragmentNeraca;
import com.example.android.labakm.Fragment.FragmentValidasiSave;
import com.example.android.labakm.Fragment.StateSelectionFragment;
import com.example.android.labakm.Interface.FragmentExitInterface;
import com.example.android.labakm.Interface.LaporanTotal;
import com.example.android.labakm.dao.CorporationDao;
import com.example.android.labakm.entity.Corporation;
import com.example.android.labakm.setting.DatabaseSetting;
import com.example.android.labakm.util.StaticVariable;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LaporanTotal, FragmentExitInterface{

    private TextView userNameView, userAddressView;
    private LinearLayout linearLayout1,linearLayout2;
    private boolean isHide1, isHide2;
    private Intent intentToTambahJurnal, intentToTambahAkun;
    private Corporation corporationIntent;
    StateSelectionFragment stateSelectionFragment;
    private Date awal, akhir;
    private int totalUntung;
    private CorporationDao corporationDao;
    private FragmentManager fragmentManager;
    private FragmentExitApplication fragmentExitApplication;
    private Intent toMainActivityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intentFromMain = getIntent();

        stateSelectionFragment = (StateSelectionFragment) getFragmentManager().findFragmentByTag("headless");
        if(null == stateSelectionFragment){
            stateSelectionFragment = new StateSelectionFragment();
            getFragmentManager().beginTransaction().add(stateSelectionFragment, "headless").commit();
        }
        corporationDao = DatabaseSetting.getDatabase(this).corporationDao();

//        corporationIntent = (Corporation) intentFromMain.getSerializableExtra("idSelected");
        try {
            corporationIntent = new getAllAsyncTask(corporationDao).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        awal = corporationIntent.getStart_date();
        akhir = corporationIntent.getEnd_date();
//        awal = new Date(intentFromMain.getLongExtra("awal", 0));
//        akhir = new Date(intentFromMain.getLongExtra("akhir", 0));
        intentToTambahJurnal = new Intent(this, TambahJurnal.class);
        intentToTambahJurnal.putExtra("idSelected", corporationIntent);
        intentToTambahAkun = new Intent(this, TambahAkun.class);
        intentToTambahAkun.putExtra("idSelected", corporationIntent);
        toMainActivityIntent = new Intent(this, MainActivity.class);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View content = findViewById(R.id.app_bar_layout);
        View layoutContent = content.findViewById(R.id.layout_content);
        linearLayout1 = layoutContent.findViewById(R.id.layout_hide);
        linearLayout2 = layoutContent.findViewById(R.id.layout_hide2);

        isHide1 = false;
        isHide2 = false;

        View headerView = navigationView.getHeaderView(0);
        userNameView = headerView.findViewById(R.id.user_name_header);
        userAddressView = headerView.findViewById(R.id.user_address_header);
        userNameView.setText(corporationIntent.getName());
        userAddressView.setText(corporationIntent.getAddress());

        showFragmentActive("identitas");

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showFragmentActive(String value){
        stateSelectionFragment.lastSelection = value;
        FragmentTransaction transaction;
        if(value.equalsIgnoreCase("identitas")){
            FragmentIdentitas fragmentIdentitas = (FragmentIdentitas) getFragmentManager().findFragmentByTag(StaticVariable.FRAGMENT_IDENTITAS);
            if(null == fragmentIdentitas){
                Bundle args = new Bundle();
                args.putSerializable("idSelected", corporationIntent);
                args.putLong("awal", awal.getTime());
                args.putLong("akhir", akhir.getTime());
                fragmentIdentitas = new FragmentIdentitas();
                fragmentIdentitas.setArguments(args);
            }
            transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_menu_container, fragmentIdentitas, StaticVariable.FRAGMENT_IDENTITAS);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(value.equalsIgnoreCase("bas")){
            FragmentBas fragmentBas = (FragmentBas) getFragmentManager().findFragmentByTag(StaticVariable.FRAGMENT_BAS);
            if(null == fragmentBas){
                Bundle args = new Bundle();
                args.putSerializable("idSelected", corporationIntent);
                args.putLong("awal", awal.getTime());
                args.putLong("akhir", akhir.getTime());
                fragmentBas = new FragmentBas();
                fragmentBas.setArguments(args);
            }
            transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_menu_container, fragmentBas, StaticVariable.FRAGMENT_BAS);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(value.equalsIgnoreCase("jurnal")){
            FragmentJurnal fragmentJurnal = (FragmentJurnal) getFragmentManager().findFragmentByTag(StaticVariable.FRAGMENT_JURNAL);
            if(null == fragmentJurnal){
                Bundle args = new Bundle();
                args.putSerializable("idSelected", corporationIntent);
                args.putLong("awal", awal.getTime());
                args.putLong("akhir", akhir.getTime());
                fragmentJurnal = new FragmentJurnal();
                fragmentJurnal.setArguments(args);
            }
            transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_menu_container, fragmentJurnal, StaticVariable.FRAGMENT_JURNAL);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(value.equalsIgnoreCase("ledger")){
            FragmentLedger fragmentLedger = (FragmentLedger) getFragmentManager().findFragmentByTag(StaticVariable.FRAGMENT_LEDGER);
            if(null == fragmentLedger){
                Bundle args = new Bundle();
                args.putSerializable("idSelected", corporationIntent);
                args.putLong("awal", awal.getTime());
                args.putLong("akhir", akhir.getTime());
                fragmentLedger = new FragmentLedger();
                fragmentLedger.setArguments(args);
            }
            transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_menu_container, fragmentLedger, StaticVariable.FRAGMENT_LEDGER);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(value.equalsIgnoreCase("neraca")){
            FragmentNeraca fragmentNeraca = (FragmentNeraca) getFragmentManager().findFragmentByTag(StaticVariable.FRAGMENT_NERACA);
            if(null == fragmentNeraca){
                Bundle args = new Bundle();
                args.putSerializable("idSelected", corporationIntent);
                args.putLong("awal", awal.getTime());
                args.putLong("akhir", akhir.getTime());
                fragmentNeraca = new FragmentNeraca();
                fragmentNeraca.setArguments(args);
            }
            transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_menu_container, fragmentNeraca, StaticVariable.FRAGMENT_NERACA);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(value.equalsIgnoreCase("labarugi")){
            FragmentLabaRugi fragmentLabaRugi = (FragmentLabaRugi) getFragmentManager().findFragmentByTag(StaticVariable.FRAGMENT_LABA_RUGI);
            if(null == fragmentLabaRugi){
                Bundle args = new Bundle();
                args.putSerializable("idSelected", corporationIntent);
                args.putLong("awal", awal.getTime());
                args.putLong("akhir", akhir.getTime());
                fragmentLabaRugi = new FragmentLabaRugi();
                fragmentLabaRugi.setArguments(args);
            }
            transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_menu_container, fragmentLabaRugi, StaticVariable.FRAGMENT_LABA_RUGI);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(value.equalsIgnoreCase("ekuitas")){
            FragmentEkuitas fragmentEkuitas = (FragmentEkuitas) getFragmentManager().findFragmentByTag(StaticVariable.FRAGMENT_EKUITAS);
            if(null == fragmentEkuitas){
                Bundle args = new Bundle();
                args.putSerializable("idSelected", corporationIntent);
                args.putLong("awal", awal.getTime());
                args.putLong("akhir", akhir.getTime());
                args.putInt("jumlah_untung", this.totalUntung);
                fragmentEkuitas = new FragmentEkuitas();
                fragmentEkuitas.setArguments(args);
            }
            transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_menu_container, fragmentEkuitas, StaticVariable.FRAGMENT_EKUITAS);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.add_transaction) {
            startActivity(intentToTambahJurnal);
        } else if (id == R.id.add_akun) {
            startActivity(intentToTambahAkun);
        } else if (id == R.id.laporan_jurnal) {
            showFragmentActive("jurnal");
        } else if (id == R.id.laporan_ledger) {
            showFragmentActive("ledger");
        } else if (id == R.id.laporan_identitas) {
            showFragmentActive("identitas");
        } else if (id == R.id.laporan_bas) {
            showFragmentActive("bas");
        } else if(id == R.id.laporan_neraca){
            showFragmentActive("neraca");
        }else if(id == R.id.laporan_laba_rugi){
            showFragmentActive("labarugi");
        }else if(id == R.id.laporan_perubahan_ekuitas){
            showFragmentActive("ekuitas");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void kirimTotalHitung(int totalHitung) {
        this.totalUntung = totalHitung;
    }

    @Override
    public void okExitButton() {
        this.fragmentExitApplication.dismiss();
        toMainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        toMainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toMainActivityIntent);
    }

    @Override
    public void cancelExitButton() {
        this.fragmentExitApplication.dismiss();
    }

    private static class getAllAsyncTask extends AsyncTask<Void,Void,Corporation> {
        private CorporationDao corporationDao;

        public getAllAsyncTask(CorporationDao corporationDao){
            this.corporationDao = corporationDao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Corporation corporations) {
            super.onPostExecute(corporations);
        }

        @Override
        protected Corporation doInBackground(Void... voids) {
            return corporationDao.getCorporationActive(true);
        }
    }
}
