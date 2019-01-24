package com.example.android.labakm.setting;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.android.labakm.dao.AkunDao;
import com.example.android.labakm.dao.CorporationDao;
import com.example.android.labakm.dao.JurnalDao;
import com.example.android.labakm.entity.Akun;
import com.example.android.labakm.entity.Corporation;
import com.example.android.labakm.entity.Jurnal;

@Database(entities = {Akun.class, Corporation.class, Jurnal.class}, version = 1, exportSchema = false)
@TypeConverters({DateTypeConverter.class})
public abstract class DatabaseSetting extends RoomDatabase{
    public abstract AkunDao akunDao();
    public abstract CorporationDao corporationDao();
    public abstract JurnalDao jurnalDao();
    private static DatabaseSetting INSTANCE;

    public static DatabaseSetting getDatabase(final Context context){
        if(null == INSTANCE){
            synchronized (DatabaseSetting.class){
                if(null == INSTANCE){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseSetting.class,
                            "labakm.db").fallbackToDestructiveMigration().addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDBAsync extends AsyncTask<Void, Void, Void>{
        private AkunDao akunDao;
        private CorporationDao corporationDao;

        public PopulateDBAsync(DatabaseSetting databaseSetting){
            akunDao = databaseSetting.akunDao();
            corporationDao = databaseSetting.corporationDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Akun akun = new Akun();
            akun.setKode("11102");
            akun.setName("Kas");
            akun.setSaldo_awal(100000);
            akunDao.insert(akun);

            akun = new Akun();
            akun.setKode("12101");
            akun.setName("Equipment - Kursi Tunggu");
            akun.setSaldo_awal(200000);
            akunDao.insert(akun);

            akun = new Akun();
            akun.setKode("12102");
            akun.setName("Akumulasi Penyusutan Equipment - Kursi Tunggu");
            akun.setSaldo_awal(300000);
            akunDao.insert(akun);

            akun = new Akun();
            akun.setKode("12103");
            akun.setName("Equipment - Kursi Cukur");
            akun.setSaldo_awal(50000);
            akunDao.insert(akun);

            akun = new Akun();
            akun.setKode("12104");
            akun.setName("Akumulasi Penyusutan Equipment - Kursi Cukur");
            akun.setSaldo_awal(70000);
            akunDao.insert(akun);

            Corporation corporation = new Corporation();
            corporation.setName("PANGKAS RAMBUT NURZANI ARIF");
            corporation.setAddress("Jl. Ceger Raya, Jurang Mangu Timur, Pondok Aren, Tangerang Selatan, Banten 15222");
            corporation.setIsactive(false);
            corporationDao.insertCorporation(corporation);

            return null;
        }
    }
}
