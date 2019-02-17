package com.npe.scheduller.presenter;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;

import com.npe.scheduller.model.JadwalModel;
import com.npe.scheduller.model.database.JadwalHelper;
import com.npe.scheduller.view.JadwalView;

public class PresenterJadwal implements JadwalView.JadwalPresenter {
    JadwalView.JadwalViewMain view;
    Context context;
    JadwalHelper helper;
    JadwalModel model;

    public PresenterJadwal(JadwalView.JadwalViewMain view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void insertDataSql(JadwalModel model) {
        //inisialissi
        helper = new JadwalHelper(context);
        try{
            helper.open();
            helper.insert(model);
            helper.close();

            view.insertSuccess("Insert Success");
            Log.i("SQLinsertPresenter", "masuk");
        }catch (SQLException e){
            Log.i("SqlErrror", e.getMessage());
        }
    }

    @Override
    public void dataMasukkan(String judul, String date, String time, String remind, String warna) {
        this.model = new JadwalModel(judul, date, time, Integer.parseInt(remind),Integer.parseInt(warna) );
        insertDataSql(model);
    }

}
