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
    public static final String GET_ALL_TIPE_BARANG = "http://192.168.43.14:8080/basar.api/v1/TipeBarangs";
    public static final String GET_ALL_BARANG = "http://192.168.43.14:8080/basar.api/v1/Barangs";
    public static final String UPDATE_BARANG = "http://192.168.43.14:8080/basar.api/v1/Barang";
    public static final String SAVE_BARANG = "http://192.168.43.14:8080/basar.api/v1/Barang";
    public static final String SAVE_INQUIRY = "http://192.168.43.14:8080/basar.api/v1/Inquiry";

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
