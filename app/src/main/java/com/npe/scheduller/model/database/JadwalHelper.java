package com.npe.scheduller.model.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.npe.scheduller.model.JadwalModel;

import java.util.ArrayList;

public class JadwalHelper {
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public JadwalHelper(Context context){
        this.context = context;
    }

    public JadwalHelper open() throws SQLException{
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public ArrayList<JadwalModel> getAllData(){
        ArrayList<JadwalModel> jadwals = new ArrayList<>();


        return jadwals;
    }

    public long insert(JadwalModel jadwalModel){
        return 0;
    }
    public int update(JadwalModel jadwalModel){
        return 0;
    }
    public int deleter(int id){
        return 0;
    }
}
