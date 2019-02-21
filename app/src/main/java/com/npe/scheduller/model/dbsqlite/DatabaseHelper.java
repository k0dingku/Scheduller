package com.npe.scheduller.model.dbsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "jadwal.db";
    public static final int DATABASE_VERSION = 1;

    //jadwal
    public static final String TABLE_JADWAL = "jadwals";
    public static final String COLUMN_ID = "idColumn";
    public static final String COLUMN_REMIND = "remindColumn";
    public static final String COLUMN_WARNA = "warnaColumn";
    public static final String COLUMN_DATE = "dateColumn";
    public static final String COLUMN_TIME = "timeColumn";
    public static final String COLUMN_JUDUL = "judulColumn";

    private static final String CREATE_TABLE =
            "CREATE TABLE "+TABLE_JADWAL + " ( "+
                    COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    COLUMN_JUDUL + " TEXT NOT NULL, "+
                    COLUMN_DATE + " TEXT NOT NULL, "+
                    COLUMN_TIME + " TEXT NOT NULL,"+
                    COLUMN_WARNA + " INTEGER NOT NULL, "+
                    COLUMN_REMIND + " INTEGER NOT NULL)";



    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_JADWAL);
        onCreate(db);
    }
}
