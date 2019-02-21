package com.npe.scheduller.model;

public class JadwalModel {
    int id;
    int remind;
    int warna;
    String date;
    String time;
    String judul;

    public JadwalModel() {
    }

    public JadwalModel(int remind, int warna, String date, String time, String judul) {
        this.remind = remind;
        this.warna = warna;
        this.date = date;
        this.time = time;
        this.judul = judul;
    }

    public JadwalModel(int id, int remind, int warna, String date, String time, String judul) {
        this.id = id;
        this.remind = remind;
        this.warna = warna;
        this.date = date;
        this.time = time;
        this.judul = judul;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRemind() {
        return remind;
    }

    public void setRemind(int remind) {
        this.remind = remind;
    }

    public int getWarna() {
        return warna;
    }

    public void setWarna(int warna) {
        this.warna = warna;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }
}
