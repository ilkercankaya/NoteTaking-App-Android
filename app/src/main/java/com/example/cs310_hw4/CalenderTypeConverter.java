package com.example.NotesApp;

import androidx.room.TypeConverter;

import java.util.Calendar;

public class CalenderTypeConverter {
    @TypeConverter
    public static Calendar toCalender(long timestamp) {

        Calendar calender = Calendar.getInstance();
        calender.setTimeInMillis(timestamp);

        return calender;
    }

    @TypeConverter
    public static long toLong(Calendar calender) {
        return calender.getTimeInMillis();
    }
}
