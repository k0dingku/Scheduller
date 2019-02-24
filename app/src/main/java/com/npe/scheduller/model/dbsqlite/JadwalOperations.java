package com.npe.scheduller.model.dbsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.npe.scheduller.model.JadwalModel;

import java.util.ArrayList;
import java.util.List;

public class JadwalOperations {
    public static final String LOGTAG = "CART_SYS";

    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase sqLiteDatabase;

    private static final String[] allColumns = {
            DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_JUDUL,
            DatabaseHelper.COLUMN_DATE,
            DatabaseHelper.COLUMN_REMIND,
            DatabaseHelper.COLUMN_TIME,
            DatabaseHelper.COLUMN_WARNA
    };

    public JadwalOperations(Context context){
        sqLiteOpenHelper = new DatabaseHelper(context);
    }

    public void openDb() {

         sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public void closeDb() {

        sqLiteOpenHelper.close();
    }

    //insert data
    public JadwalModel insertJadwal (JadwalModel jadwalModel){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_JUDUL, jadwalModel.getJudul());
        values.put(DatabaseHelper.COLUMN_REMIND, jadwalModel.getRemind());
        values.put(DatabaseHelper.COLUMN_DATE, jadwalModel.getDate());
        values.put(DatabaseHelper.COLUMN_TIME, jadwalModel.getTime());
        values.put(DatabaseHelper.COLUMN_WARNA, jadwalModel.getWarna());


        int insertId = (int) sqLiteDatabase.insert(DatabaseHelper.TABLE_JADWAL, null, values);

        jadwalModel.setId(insertId);
        Log.i(LOGTAG,"Test log"+ String.valueOf(jadwalModel.getId()));
        return jadwalModel;
    }

    //check data
    public Boolean checkRecord(){
        String checkRecord = "SELECT * FROM "+ DatabaseHelper.TABLE_JADWAL;
        Cursor cursor = sqLiteDatabase.rawQuery(checkRecord, new String[]{});
        boolean hasRecord = false;
        if (cursor.getCount()!=0){
            hasRecord = true;
        }

        cursor.close();
        return hasRecord;
    }

    //get single data cart
    public JadwalModel getUser(long id) {
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_JADWAL, allColumns, DatabaseHelper.COLUMN_ID
                + "='"+id+"'",null, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        JadwalModel jadwalModel = new JadwalModel(
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_REMIND)),
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_WARNA)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TIME)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_JUDUL))

        );
        // return Cart
        return jadwalModel;
    }

    //get list cart
    public ArrayList<JadwalModel> getAllJadwal() {
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_JADWAL, allColumns,
                null, null, null, null, null);

        ArrayList<JadwalModel> jadwals = new ArrayList<>();
        //cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                JadwalModel jadwal = new JadwalModel();
                jadwal.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)));
                jadwal.setRemind(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_REMIND)));
                jadwal.setWarna(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_WARNA)));
                jadwal.setDate(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE)));
                jadwal.setTime(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TIME)));
                jadwal.setJudul(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_JUDUL)));
                jadwals.add(jadwal);
            }
        }
        // return All schedull
        return jadwals;
    }

    // Updating cart
    public int updateJadwal(JadwalModel jadwal) {

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ID, jadwal.getId());
        values.put(DatabaseHelper.COLUMN_JUDUL, jadwal.getJudul() );
        values.put(DatabaseHelper.COLUMN_REMIND, jadwal.getRemind() );
        values.put(DatabaseHelper.COLUMN_TIME, jadwal.getTime() );
        values.put(DatabaseHelper.COLUMN_WARNA, jadwal.getWarna() );
        values.put(DatabaseHelper.COLUMN_DATE, jadwal.getDate() );

        Log.i("IdUpdateOp", String.valueOf(jadwal.getId()));
        Log.i("JudulUpdateOp", jadwal.getJudul());
        Log.i("RemUpdateOp", String.valueOf(jadwal.getRemind()));
        Log.i("TimUpdateOp", jadwal.getTime());
        Log.i("WarUpdateOp", String.valueOf(jadwal.getWarna()));
        Log.i("DatUpdateOp", jadwal.getDate());
        // updating row
        return sqLiteDatabase.update(DatabaseHelper.TABLE_JADWAL, values,
                DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(jadwal.getId())});
    }

    // Deleting Cart
    public void removeCart(int id) {

        sqLiteDatabase.delete(DatabaseHelper.TABLE_JADWAL, DatabaseHelper.COLUMN_ID + "=" +
                id, null);
    }

    //deltering spesific row
    public void deleteRow(String id){
        sqLiteDatabase.execSQL("DELETE FROM "+DatabaseHelper.TABLE_JADWAL+" WHERE "+ DatabaseHelper.COLUMN_ID
                + "=" + id);
    }
}
