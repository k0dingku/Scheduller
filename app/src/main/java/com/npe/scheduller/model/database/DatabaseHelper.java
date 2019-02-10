package com.npe.scheduller.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static android.provider.BaseColumns._ID;
import static com.npe.scheduller.model.database.DatabaseContract.*;



public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "jadwal.db";
    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABEL_JADWAL = "CREATE TABLE "+TABEL_JADWAL+"("+
            _ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            JadwalColumns.JUDUL+" VARCHAR(100),"+
            JadwalColumns.DATE+" VARCHAR(20), "+
            JadwalColumns.TIME+" VARCHAR(10), "+
            JadwalColumns.REMIND+" INTEGER, "+
            JadwalColumns.WARNA+" INTEGER )";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABEL_JADWAL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABEL_JADWAL);
        onCreate(db);
    }
}
