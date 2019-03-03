package com.npe.scheduller.view;

import com.npe.scheduller.model.JadwalModel;

import java.util.ArrayList;

public interface MainView {
    public interface MainActivityView {
        public void calendar();

        public void listactivity();

        public void bottomsheet();

        public void deleteconfirmation();

        public void dbtoarraylist();

        public void deleteSucces(String message);

        public void deleteFailde(String message);
    }

    public interface MainPresenterView {
        public void deleteCard(int position, ArrayList<JadwalModel> data);



        public void listDataJadwal(ArrayList<JadwalModel> data);
    }
}
