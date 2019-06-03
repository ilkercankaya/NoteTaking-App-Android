package com.example.NotesApp;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReminderViewModel extends AndroidViewModel {

    private ExecutorService executor;
    private LiveData<List<Reminder>> liveReminderList;
    private RemindersDataBase database;

    public ReminderViewModel(Application application) {
        super(application);

        executor = Executors.newSingleThreadExecutor();
        database = RemindersDataBase.getInstance(getApplication().getApplicationContext());
        liveReminderList = database.getPhoneDao().getAllReminders();
    }


    private ArrayList<Reminder> parseRemindersJSONtoList(JSONArray reminders) {
        ArrayList<Reminder> result = new ArrayList<Reminder>();
        for (int i = 0; i < reminders.length(); i++) {
            try {
                JSONObject reminder = reminders.getJSONObject(i);

                String date = reminder.getString(ServerInfo.DATE_KEY);
                String[] dateSplit = date.split("/");

                int day = Integer.parseInt(dateSplit[0]);
                int month = Integer.parseInt(dateSplit[1]);
                int year = Integer.parseInt(dateSplit[2]);

                Calendar calendar = new GregorianCalendar(year, month - 1, day);


                result.add(
                        new Reminder(
                                reminder.getString(ServerInfo.TITLE_SERVER_KEY),
                                reminder.getString(ServerInfo.NOTE_SERVER_KEY),
                                calendar,
                                reminder.getInt(ServerInfo.PRIORITY_SERVER_KEY),
                                reminder.getInt(ServerInfo.SWITCH_KEY) == 1
                        )
                );
            } catch (JSONException j) {
                j.printStackTrace();
            }
        }

        return result;
    }

    public void insertRemindersDatabase() {
        RequestQueue queue = Volley.newRequestQueue(getApplication().getApplicationContext());

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerInfo.SERVER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray reminders = jsonObj.getJSONArray("Reminders");

                            final ArrayList<Reminder> list = parseRemindersJSONtoList(reminders);

                            executor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    database.runInTransaction(new Runnable() {
                                        @Override
                                        public void run() {
                                            database.getPhoneDao().insertRemindersDatabase(
                                                    list.toArray(new Reminder[list.size()]));
                                        }
                                    });
                                }
                            });
                        } catch (JSONException j) {
                            j.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void deleteAllEntries() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.getPhoneDao().deleteAllEntries();
            }
        });
    }

    public LiveData<List<Reminder>> getLiveReminderList() {
        return liveReminderList;
    }

    public List<Reminder> getReminderList() {
        return liveReminderList.getValue();
    }


    public void sortByDate() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<Reminder> list = database.getPhoneDao().sortByDate();

                for (Reminder reminder : list) {
                    reminder.setPid(0);
                }

                database.getPhoneDao().deleteAllEntries();

                database.getPhoneDao().insertRemindersDatabase(list.toArray(new Reminder[list.size()]));
            }
        });
    }

    public void sortByPriority() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<Reminder> list = database.getPhoneDao().sortByPriority();

                for (Reminder reminder : list) {
                    // reset PID's so removing works
                    reminder.setPid(0);

                }

                database.getPhoneDao().deleteAllEntries();

                database.getPhoneDao().insertRemindersDatabase(list.toArray(new Reminder[list.size()]));
            }
        });
    }

    public void remove(int index) {
        List<Reminder> list = getReminderList();
        final int id = list.get(index).getPid();
        list.remove(index);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.getPhoneDao()
                        .deleteById(id);
            }
        });

    }

    public void setPriority(int priority, int index) {
        List<Reminder> list = getReminderList();
        final Reminder reminder = list.get(index);
        reminder.setPriority(priority);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.getPhoneDao()
                        .updateOnlyOneRemindersDatabase(reminder);
            }
        });
    }


    public void switchEnabled(int index) {
        List<Reminder> list = getReminderList();
        final Reminder reminder = list.get(index);
        reminder.setEnabled(!reminder.isEnabled());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.getPhoneDao()
                        .updateOnlyOneRemindersDatabase(reminder);
            }
        });
    }

    public void updateReminder(int index, String name, String note, int priority, Long time) {
        final List<Reminder> list = getReminderList();
        final Reminder reminder = list.get(index);
        reminder.setName(name);
        reminder.setNote(note);
        reminder.setTime(time);
        reminder.setPriority(priority);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.getPhoneDao()
                        .updateOnlyOneRemindersDatabase(reminder);
            }
        });

    }
}
