package com.example.android.labakm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StaticVariable {
    public static String FRAGMENT_IDENTITAS = "IDENTITAS";
    public static String FRAGMENT_BAS = "BAS";
    public static String FRAGMENT_JURNAL = "JURNAL";
    public static String FRAGMENT_LEDGER = "LEDGER";
    public static String FRAGMENT_NERACA = "NERACA";
    public static String FRAGMENT_LABA_RUGI = "LABARUGI";
    public static String FRAGMENT_EKUITAS = "EKUITAS";
    public static String FRAGMENT_TAMBAH_JURNAL = "TAMBAH_JURNAL";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String IPSERVER = "192.168.1.2:8081";
//    public static final String IPSERVER = "192.168.8.105:8081";
    public static final String GET_ALL_TIPE_BARANG = "http://" + IPSERVER + "/basar.api/v1/TipeBarangs";
    public static final String GET_ALL_BARANG = "http://" + IPSERVER + "/basar.api/v1/Barangs";
    public static final String UPDATE_BARANG = "http://" + IPSERVER + "/basar.api/v1/Barang";
    public static final String SAVE_BARANG = "http://" + IPSERVER + "/basar.api/v1/Barang";
    public static final String SAVE_USER = "http://" + IPSERVER + "/basar.api/v1/UserToken";
    public static final String SAVE_INQUIRY = "http://" + IPSERVER + "/basar.api/v1/Inquiry";
    public static final String GET_INQUIRY_BY_IDTOKO = "http://" + IPSERVER + "/basar.api/v2/Inquiry/";
    public static final String USER_PREFERENCES = "USER_TOKEN";
//    public static final String IS_EXIST_USERNAME = "http://192.168.1.4:8081/basar.api/v2/UserToken";
//    public static final String IS_VALID_LOGIN = "http://192.168.1.4:8081/basar.api/v1/UserToken";
    public static final String IS_EXIST_USERNAME = "http://" + IPSERVER + "/basar.api/v2/UserToken";
    public static final String IS_VALID_LOGIN = "http://" + IPSERVER  + "/basar.api/v1/UserToken";

    public static String date2String(Date date, String pattern) {
        if (null == date) {
            return null;
        } else {
            if (null == pattern || pattern.trim().isEmpty())
                pattern = StaticVariable.DATE_PATTERN;
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        }
    }

    public static Date string2Date(String date, String pattern) throws ParseException {
        if (null == date || date.trim().isEmpty()) {
            return null;
        } else {
            if (null == pattern || pattern.trim().isEmpty())
                pattern = StaticVariable.DATE_PATTERN;
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(date);
        }
    }
}
