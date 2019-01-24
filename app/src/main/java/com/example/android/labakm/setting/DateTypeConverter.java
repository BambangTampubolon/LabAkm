package com.example.android.labakm.setting;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateTypeConverter {
    @TypeConverter
    public Long convertDateToLong(Date date){
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public Date convertLongToDate(Long value){
        return value == null ? null : new Date(value);
    }
}
