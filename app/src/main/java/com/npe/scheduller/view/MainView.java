package com.npe.scheduller.view;

import com.npe.scheduller.model.JadwalModel;

import java.util.ArrayList;

public interface MainView {
    public interface MainActivityView{
        public void calendar();
        public void listactivity(ArrayList<JadwalModel> data);
        public void bottomsheet();
        public void deleteconfirmation();
    }
    public interface MainPresenterView{

    }
}
