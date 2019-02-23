package com.npe.scheduller.view;

import com.npe.scheduller.model.JadwalModel;

public interface JadwalView {
    public interface JadwalViewMain{
        public void insertSuccess(String message);
        public void inserrtFailed(String message);
    }

    public interface JadwalViewPresenter{
        void insertData(JadwalModel jadwalModel);
        public void dataMasukkan(String judul, String date, String time, int remind, int warna);
    }
}
