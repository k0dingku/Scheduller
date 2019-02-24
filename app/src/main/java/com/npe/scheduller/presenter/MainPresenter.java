package com.npe.scheduller.presenter;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;
import android.view.WindowManager;

import com.npe.scheduller.model.JadwalModel;
import com.npe.scheduller.model.dbsqlite.JadwalOperations;
import com.npe.scheduller.ui.MainActivity;
import com.npe.scheduller.view.MainView;

import java.util.ArrayList;

public class MainPresenter implements MainView.MainPresenterView {
    Context context;
    MainView.MainActivityView view;
    JadwalOperations jadwalOperations;
    public MainPresenter(Context context,MainView.MainActivityView view){
        this.context = context;
        jadwalOperations = new JadwalOperations(context);
        this.view = view;
    }



    @Override
    public void listDataJadwal(ArrayList<JadwalModel> data) {
        for (int i = 0; i < data.size(); i++) {
            Log.i("DataJadwal", String.valueOf(data.get(i)));
        }
    }

    @Override
    public void deleteCard(int position, ArrayList<JadwalModel> data) {
        try{
            jadwalOperations.openDb();
            jadwalOperations.deleteRow(String.valueOf(data.get(position).getId()));
            Log.i("IdCart", String.valueOf(data.get(position).getId()));
            Log.i("DeleteCard", "berhasil");
            view.deleteSucces("Berhasil Delete");
            //adapter.notifyItemRemoved(position);
            jadwalOperations.closeDb();
        }catch (SQLException e){
            view.deleteFailde("Gagal Delete");
            Log.i("ErrorDeleteCard", e.getMessage());
        }
    }
}
