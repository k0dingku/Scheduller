package com.npe.scheduller.view;

import android.view.ViewGroup;
import android.widget.EditText;

import com.npe.scheduller.model.JadwalModel;

public interface JadwalView {
    public interface JadwalViewMain{
        public void alertDialogDate(int day, int month, int year);
        public void showBottomSheetColor();
        public void showBottomSheetDate();
        public void showBottomSheetRemind();

        public void invisibleSheetColor();
        public void invisibleSheetRemind();
        public void invisibleSheetDate();

        public void insertSuccess(String message);
        public void inserrtFailed(String message);
        public void disableAnotherColor(ViewGroup layout, int idBtn);

        public void minDate();
    }

    public interface JadwalViewPresenter{
        void insertData(JadwalModel jadwalModel);
        public Boolean checkJudul(String judul, EditText etJudul);
        public void setAlertDate();
        public void dataMasukkan(String judul, String date, String time, int remind, int warna);
    }
}
