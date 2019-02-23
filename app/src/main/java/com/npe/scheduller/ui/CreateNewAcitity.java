package com.npe.scheduller.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.npe.scheduller.R;
import com.npe.scheduller.presenter.JadwalPresenter;
import com.npe.scheduller.view.JadwalView;

public class CreateNewAcitity extends AppCompatActivity implements JadwalView.JadwalViewMain {
    private JadwalPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_acitity);
        presenter = new JadwalPresenter(getApplicationContext(), this);
    }

    @Override
    public void insertSuccess(String message) {

    }

    @Override
    public void inserrtFailed(String message) {

    }
}
