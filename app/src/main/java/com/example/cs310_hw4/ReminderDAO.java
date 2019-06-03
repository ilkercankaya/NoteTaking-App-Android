package com.example.NotesApp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ReminderDAO {
    @Query("SELECT * FROM Reminders")
    LiveData<List<Reminder>> getAllReminders();

    @Query("SELECT *  FROM Reminders ORDER BY priority DESC")
    List<Reminder> sortByPriority();

    @Query("SELECT *  FROM Reminders ORDER BY calendar")
    List<Reminder> sortByDate();

    @Query("DELETE FROM Reminders")
    void deleteAllEntries();

    @Query("DELETE FROM Reminders WHERE pid = :pid")
    void deleteById(int pid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRemindersDatabase(Reminder... reminders);

    @Update
    void updateRemindersDatabase(Reminder... reminders);

    @Update
    void updateOnlyOneRemindersDatabase(Reminder reminders);
}
