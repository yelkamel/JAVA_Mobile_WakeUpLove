package com.youceferie.wakeuplove;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class AlarmList {
    private int count;

    private static List<Alarm> alarms;



    public AlarmList()
    {
        alarms = new ArrayList<>();
    }

    public void loadAlarms(ArrayList getliftdb)
    {
        alarms = getliftdb;

    }

    public String DateToString(Date date, String type)
    {

        SimpleDateFormat form;


        if (type.equals("time"))
        form = new SimpleDateFormat("HH:mm");
        else
        form = new SimpleDateFormat("MM/dd/yyyy HH:mm");

        return form.format(date);
    }

    public void addAlarm(Date date, String title, boolean snooze, String songs)
    {
        alarms.add(new Alarm(date,title,snooze,songs));
    }

    public List<Alarm> getAlarms() {

        return alarms;
    }



    public void cancelAlarm(int i, boolean test) {

         alarms.get(i).setCancel(test);
    }


    public void deleteAlarmById(int id) {
        for (int i=0; i<alarms.size(); i++){
            if (id == alarms.get(i).getId())
                alarms.remove(i);
        }
    }


    public int rowAlarmById(int id) {
        for (int i=0; i<alarms.size(); i++){
            if (id == alarms.get(i).getId())
                return i;
        }
        return 0;
    }

    public void deleteAlarmByRow(int row) {

        alarms.remove(row);
    }

    public int getSize(){
        return alarms.size();
    }


    public String getStrAlarmByRow(int i){
        return DateToString(alarms.get(i).getDate(),"time");
    }

    public Alarm getAlarmByRow(int row){
        return alarms.get(row);
    }

    public Alarm getAlarmById(int id){
        for (int i=0; i<alarms.size(); i++){
            if (id == alarms.get(i).getId())
            return alarms.get(i);
        }

        return null;
    }
}
