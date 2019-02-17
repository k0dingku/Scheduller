package com.npe.scheduller.presenter;

import android.util.Log;

import com.npe.scheduller.model.JadwalModel;
import com.npe.scheduller.model.database.JadwalHelper;
import com.npe.scheduller.view.JadwalView;

import java.util.ArrayList;

public class JadwalPresenter implements IJadwalPresenter{
    JadwalView jadwalView;
    String judul, date, time;
    int remind, warna;
    private JadwalHelper jadwalHelper;


    public JadwalPresenter(JadwalView jadwalView){
        this.jadwalView = jadwalView;
    }
    public JadwalPresenter(String judul, String date, String time, int remind, int warna){
        this.judul = judul;
        this.date = date;
        this.time = time;
        this.remind = remind;
        this.warna = warna;
    }

    public void insertData(){
        jadwalHelper.open();
        JadwalModel jadwalModel = new JadwalModel(judul, date, time, remind, warna);
        jadwalHelper.insert(jadwalModel);
        jadwalHelper.close();
    }
    public void getAllData(){
        jadwalHelper.open();
        ArrayList<JadwalModel> jadwalModels = jadwalHelper.getAllData();
        jadwalHelper.close();
        Log.i("Kodingku",String.valueOf(jadwalModels.size()));
    }
    public void deleteitem(int id){
        jadwalHelper.open();
        jadwalHelper.delete(id);
        jadwalHelper.close();
    }

}
