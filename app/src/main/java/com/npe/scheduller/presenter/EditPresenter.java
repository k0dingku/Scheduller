package com.npe.scheduller.presenter;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;
import android.widget.EditText;

import com.npe.scheduller.model.JadwalModel;
import com.npe.scheduller.model.dbsqlite.JadwalOperations;
import com.npe.scheduller.view.EditView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class EditPresenter implements EditView.EditPresnterView {
    Context context;
    EditView.EditActivityView view;
    JadwalOperations jadwalOperations;

    public EditPresenter(Context context, EditView.EditActivityView view) {
        this.context = context;
        jadwalOperations = new JadwalOperations(context);
        this.view = view;
    }

    @Override
    public void getDataUser(int id) {
        JadwalModel jadwalModel;
        try{
            jadwalOperations.openDb();
            jadwalModel = jadwalOperations.getUser(id);
            Log.i("BerhasilGetDataCart", "Berhasil");
            jadwalOperations.closeDb();
            view.setDataUser(jadwalModel);
        }catch (SQLException e){
            Log.i("ErrorGetData", e.getMessage());
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
        Log.i("TimePresenter", time);
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
    public void dataMasukkan(int idCart, String judul, String date, String time, int remind, int warna) {
        JadwalModel jadwalModel = new JadwalModel(idCart, judul, remind, date, time, warna);
        //Log.i("JadwalModel", String.valueOf(jadwalModel.getId()));
        updateCart(jadwalModel);
    }

    private void updateCart(JadwalModel jadwalModel) {
        Log.i("IdJadwal", String.valueOf(jadwalModel.getId()));
        Log.i("judul", String.valueOf(jadwalModel.getJudul()));
        Log.i("time", String.valueOf(jadwalModel.getTime()));
        Log.i("date", String.valueOf(jadwalModel.getDate()));
        try {
            jadwalOperations.openDb();
            jadwalOperations.updateJadwal(jadwalModel);
            jadwalOperations.closeDb();
            Log.i("BerhasilUpdate","Berhasil");
            view.updateBerhasil("Berhasil");
        }catch (SQLException e){
            view.failedUpdate("Gagal");
            Log.i("ErrorUpdate",e.getMessage());
        }
    }
}
