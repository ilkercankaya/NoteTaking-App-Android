package com.example.NotesApp;

import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Calendar;

public class DetailsViewModel extends ViewModel {

    public final static String PRIORITY_KEY = "com.example.cs310_hw4.bundle.priority";
    public final static String TIME_KEY = "com.example.cs310_hw4.bundle.time";
    public final static String NAME_KEY = "com.example.cs310_hw4.bundle.name";
    public final static String NOTE_KEY = "com.example.cs310_hw4.bundle.note";


    private MutableLiveData<Integer> livePriority;
    private MutableLiveData<String> liveName, liveNote;
    private MutableLiveData<Long> liveTime;

    public DetailsViewModel() {
        livePriority = new MutableLiveData<>();
        liveName = new MutableLiveData<>();
        liveNote = new MutableLiveData<>();
        liveTime = new MutableLiveData<>();

        livePriority.setValue(1);
        liveName.setValue("");
        liveNote.setValue("");
        liveTime.setValue(Calendar.getInstance().getTimeInMillis());

    }

    public MutableLiveData<Integer> getLivePriority() {
        return livePriority;
    }

    public MutableLiveData<String> getLiveName() {
        return liveName;
    }

    public MutableLiveData<String> getLiveNote() {
        return liveNote;
    }

    public MutableLiveData<Long> getLiveTime() {
        return liveTime;
    }

    public void setTime(Long time)
    {
        liveTime.setValue(time);
    }

    public void setName(String name)
    {
        liveName.setValue(name);
    }

    public void setNote(String note)
    {
        liveNote.setValue(note);
    }

    public void setPriority(int priority)
    {
        if(priority <= 4 && priority >= 0)
        {
            livePriority.setValue(priority);
        }
    }


    public void writeToBundle(Bundle bundle) {


        bundle.putInt(PRIORITY_KEY, livePriority.getValue());
        bundle.putString(NAME_KEY, liveName.getValue());
        bundle.putString(NOTE_KEY, liveNote.getValue());
        bundle.putLong(TIME_KEY, liveTime.getValue());
    }

    public void readFromBundle(Bundle bundle) {
        setName(bundle.getString(NAME_KEY));
        setNote(bundle.getString(NOTE_KEY));
        setPriority(bundle.getInt(PRIORITY_KEY));
        setTime(bundle.getLong(TIME_KEY));
    }

}

