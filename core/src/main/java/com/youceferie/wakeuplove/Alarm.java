package com.youceferie.wakeuplove;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by YouCef on 24/02/2016.
 */
public class Alarm {



    private Date date;
    private String title;
    private boolean snooze;
    private  String songs;


    private int id;

    private boolean cancel;

    public Alarm(Date date, String title, boolean snooze, String songs)
    {
        this.date = date ;
        this.title = title;
        this.snooze = snooze;
        this.songs =songs;
        this.id = (int) ((Math.random()*100) +(Math.random()*100000));
    }

    public Alarm()
    {
        this.id = (int) (Math.random()*100);
    }

    public void setSnoozeFromStr(String str)
    {
        snooze = Boolean.valueOf(str);
    }


    public void setDateFromStr(String str)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
//        String dateInString = "7-Jun-2013 20:27:34";

        try {
             date = formatter.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setCancel(boolean b)
    {
        cancel= b;
    }

    public boolean isCancel()
    {
        return cancel;
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public boolean isSnooze() {
        return snooze;
    }

    public String getSongs() {
        return songs;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSnooze(boolean snooze) {
        this.snooze = snooze;
    }

    public void setSongs(String songs) {
        this.songs = songs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdFromStr(String id) {
        this.id = Integer.parseInt(id);
    }


}
