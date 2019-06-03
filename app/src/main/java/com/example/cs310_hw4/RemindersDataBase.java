package com.example.NotesApp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Reminder.class}, version = 2, exportSchema = false)
@TypeConverters({CalenderTypeConverter.class})
abstract class RemindersDataBase extends RoomDatabase {
    public abstract ReminderDAO  getPhoneDao();

    private static RemindersDataBase database;

    public static RemindersDataBase getInstance(Context context){
        if(database == null){
            database = Room.databaseBuilder(context.getApplicationContext(),
                    RemindersDataBase.class, "REMINDER_DATABASE")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
}
