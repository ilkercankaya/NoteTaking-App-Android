package com.example.NotesApp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;

@Entity(tableName = "Reminders")
public class Reminder {

    @PrimaryKey(autoGenerate = true)
    private int pid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "calendar")
    private Calendar calendar;

    @ColumnInfo(name = "priority")
    private int priority;

    @ColumnInfo(name = "enabled")
    private boolean enabled;


    @Ignore
    public Reminder(String name, String note, String date, int priority, int enabled) {


        this.name = name;
        this.note = note;
        this.priority = priority;

        String [] dateSplit = date.split("/");

        int day = Integer.parseInt(dateSplit[0]);
        int month = Integer.parseInt(dateSplit[1]);
        int year = Integer.parseInt(dateSplit[2]);

        calendar = new GregorianCalendar(year, month-1,day);

        this.enabled = enabled == 1;
    }

    public Reminder(String name, String note, Calendar calendar, int priority, boolean enabled) {
        this.name = name;
        this.note = note;
        this.priority = priority;
        this.calendar = calendar;
        this.enabled = enabled;
    }


    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public String getNote() {
        return note;
    }


    public int getPriority() {
        return priority;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Long getTime()
    {
        return calendar.getTimeInMillis();
    }

    public Calendar getCalendar()
    {
        return calendar;
    }

    public void setTime(Long time)
    {
        calendar.setTimeInMillis(time);
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public static class SortByDate implements Comparator<Reminder>
    {

        @Override
        public int compare(Reminder o1, Reminder o2) {

            return o1.calendar.compareTo(o2.calendar);
        }
    }


    public static class SortByPriority implements Comparator<Reminder>
    {

        @Override
        public int compare(Reminder o1, Reminder o2)
        {
            Integer p1 = o1.getPriority();
            Integer p2 = o2.getPriority();

            // We reverse the comparison because we want higher priority to come first
            return p2.compareTo(p1);
        }
    }
}
