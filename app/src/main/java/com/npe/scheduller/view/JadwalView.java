package com.npe.scheduller.view;

import com.npe.scheduller.model.JadwalModel;

public interface JadwalView {
    public interface JadwalViewMain{
        public void insertSuccess(String message);
    }

    public interface JadwalPresenter{
        void insertDataSql(JadwalModel model);
        public void dataMasukkan(String judul, String date, String time,String remind, String warna);
    }
}
