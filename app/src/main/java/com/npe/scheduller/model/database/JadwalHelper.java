package com.npe.scheduller.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.npe.scheduller.model.JadwalModel;

import static com.npe.scheduller.model.database.DatabaseContract.*;
import static android.provider.BaseColumns._ID;

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
        Cursor cursor = database.query(TABEL_JADWAL,null,null,null,null,null,null,null);
        cursor.moveToFirst();
        ArrayList<JadwalModel> jadwals = new ArrayList<>();
        JadwalModel jadwalModel;
        if(cursor.getCount() > 0){
            do{
                jadwalModel = new JadwalModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(JadwalColumns.JUDUL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(JadwalColumns.DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(JadwalColumns.TIME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(JadwalColumns.REMIND)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(JadwalColumns.WARNA))
                );
                jadwals.add(jadwalModel);
                cursor.moveToNext();
            }while(!cursor.isAfterLast());
        }
        cursor.close();

        return jadwals;
    }

    public long insert(JadwalModel jadwalModel){
        ContentValues initialValues = new ContentValues();

        initialValues.put(DatabaseContract.JadwalColumns.JUDUL, jadwalModel.getJudul());
        initialValues.put(DatabaseContract.JadwalColumns.REMIND, jadwalModel.getRemind());
        initialValues.put(DatabaseContract.JadwalColumns.WARNA, jadwalModel.getWarna());
        initialValues.put(DatabaseContract.JadwalColumns.TIME, jadwalModel.getTime());
        initialValues.put(DatabaseContract.JadwalColumns.DATE, jadwalModel.getDate());

        return database.insert(DatabaseContract.TABEL_JADWAL,null, initialValues);
    }
    public int update(JadwalModel jadwalModel){
        return 0;
    }
    public int delete(int id){
        return database.delete(DatabaseContract.TABEL_JADWAL, id + " = '" + id + "'", null);
    }
}
