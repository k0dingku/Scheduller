package com.npe.scheduller.presenter;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;
import android.widget.EditText;

import com.npe.scheduller.model.JadwalModel;
import com.npe.scheduller.model.dbsqlite.JadwalOperations;
import com.npe.scheduller.view.JadwalView;

import java.util.Calendar;

public class JadwalPresenter implements JadwalView.JadwalViewPresenter {
    Context context;
    JadwalView.JadwalViewMain view;
    JadwalOperations jadwalOperations;
    public JadwalPresenter(Context context, JadwalView.JadwalViewMain view) {
        this.context = context;
        jadwalOperations = new JadwalOperations(context);
        this.view = view;
    }



    @Override
    public void insertData(JadwalModel jadwalModel) {
        try{
            jadwalOperations.openDb();
            jadwalOperations.insertJadwal(jadwalModel);
            Log.i("InsertJadwal", "Success");
            jadwalOperations.closeDb();
            view.insertSuccess("Berhasil Insert Jadwal");
        } catch (SQLException e){
            Log.i("InsertJadwalError", "Error");
            view.inserrtFailed("Gagal Insert Jadwal");
        }
    }

    @Override
    public void setDate(String day, String month, String year) {
        String date = day+"/"+month+"/"+year;
        view.getDate(date);
    }

    @Override
    public void setTime(String hour, String minute) {
        String time = hour+"/"+minute;
        view.getTime(time);
    }

    @Override
    public Boolean checkJudul(String judul, EditText etJudul) {
        boolean valid = true;
        if(judul.isEmpty()){
            valid = false;
            etJudul.setError("Judul Masih Kosong");
            etJudul.requestFocus();
        }
        return valid;
    }

    @Override
    public void dataMasukkan(String judul, String date, String time, int remind, int warna) {
        JadwalModel jadwalModel = new JadwalModel(remind, warna, date, time, judul);
        insertData(jadwalModel);
    }
}
