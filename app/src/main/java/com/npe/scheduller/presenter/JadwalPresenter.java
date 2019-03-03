package com.npe.scheduller.presenter;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;
import android.widget.EditText;

import com.npe.scheduller.model.JadwalModel;
import com.npe.scheduller.model.dbsqlite.JadwalOperations;
import com.npe.scheduller.view.JadwalView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
        String time = hour+":"+minute;
        view.getTime(time);
    }

    @Override
    public void calculateDifferenceDate(String date) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        //get current date and time
        String strCurrentDate = sdf.format(calendar.getTime());
        try {
            Date date1 = sdf.parse(date);
            Date date2 = sdf.parse(strCurrentDate);
            setDifference(date1, date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setDifference(Date date1, Date date2) {
        long diff = date1.getTime() - date2.getTime();
        long diffInDays = TimeUnit.MILLISECONDS.toDays(diff);
        long diffInHours = TimeUnit.MILLISECONDS.toHours(diff);
        Log.i("Difference", String.valueOf(diff));
        Log.i("DifferenceDays", String.valueOf(diffInDays));
        Log.i("DifferenceHours", String.valueOf(diffInHours));
        view.getDifference(diffInDays);
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
        //Log.i("JadwalModel", String.valueOf(jadwalModel.getId()));
        insertData(jadwalModel);
    }
}
