package com.npe.scheduller.model.database;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABEL_JADWAL = "tabel_jadwal";

    static final class JadwalColumns implements BaseColumns{
        static String JUDUL = "judul";
        static String WARNA = "warna";
        static String REMIND = "remind";
        static String DATE = "date";
        static String TIME = "time";
    }
}
