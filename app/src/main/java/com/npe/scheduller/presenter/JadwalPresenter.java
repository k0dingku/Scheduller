package com.npe.scheduller.presenter;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;

import com.npe.scheduller.model.JadwalModel;
import com.npe.scheduller.model.database.JadwalHelper;
import com.npe.scheduller.view.JadwalView;

import java.util.ArrayList;

public class JadwalPresenter implements IJadwalPresenter {
    String judul, date, time;
    int remind, warna;
    Context context;
    private JadwalHelper jadwalHelper;


    public JadwalPresenter( Context context) {
        this.context = context;
        jadwalHelper = new JadwalHelper(context);
    }

    public JadwalPresenter(String judul, String date, String time, int remind, int warna, Context context) {
        this.judul = judul;
        this.date = date;
        this.time = time;
        this.remind = remind;
        this.warna = warna;
        this.context = context;
        jadwalHelper = new JadwalHelper(context);
    }

    public void insertData() {
        try {
            jadwalHelper.open();
            JadwalModel jadwalModel = new JadwalModel(judul, date, time, remind, warna);
            jadwalHelper.insert(jadwalModel);
            jadwalHelper.close();
            Log.i("SQLinsert", "masuk");
        } catch (SQLException e) {
            Log.i("SQLinsertError", e.getMessage());
        }
    }

    public void getAllData() {
        try {
            jadwalHelper.open();
            ArrayList<JadwalModel> jadwalModels = jadwalHelper.getAllData();
            jadwalHelper.close();
            Log.i("Kodingku", String.valueOf(jadwalModels.size()));
        } catch (SQLException e) {
            Log.i("SQLgetError", e.getMessage());
        }
    }

    public void deleteitem(int id) {
        jadwalHelper.open();
        jadwalHelper.delete(id);
        jadwalHelper.close();
    }

}
