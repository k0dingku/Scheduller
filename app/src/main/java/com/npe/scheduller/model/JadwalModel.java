package com.npe.scheduller.model;

public class JadwalModel {
    private int id, remind, warna;
    private String date, time, judul;

    public JadwalModel(int id, String judul, String date, String time, int remind, int warna){
        this.id = id;
        this.remind = remind;
        this.warna = warna;
        this.date = date;
        this.time = time;
        this.judul = judul;
    }

    public JadwalModel(String judul, String date, String time, int remind, int warna){
        this.remind = remind;
        this.warna = warna;
        this.date = date;
        this.time = time;
        this.judul = judul;
    }

    public int getId() {
        return id;
    }

    public int getRemind() {
        return remind;
    }

    public int getWarna() {
        return warna;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getJudul() {
        return judul;
    }
}
