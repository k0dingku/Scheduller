package com.npe.scheduller.ui;

import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.npe.scheduller.R;
import com.npe.scheduller.model.JadwalModel;
import com.npe.scheduller.model.database.JadwalHelper;
import com.npe.scheduller.presenter.IJadwalPresenter;
import com.npe.scheduller.presenter.JadwalPresenter;
import com.npe.scheduller.view.JadwalView;

import java.util.ArrayList;

public class AddEventActivity extends AppCompatActivity implements JadwalView {

    private JadwalHelper jadwalHelper;
    private EditText judul, date, time, remind, warna;
    private Button tambah, hapus;

    IJadwalPresenter jadwalPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        //init view
        judul = findViewById(R.id.etJudul);
        date = findViewById(R.id.etDate);
        time = findViewById(R.id.etTime);
        remind = findViewById(R.id.etRemind);
        warna = findViewById(R.id.etWarna);

        tambah = findViewById(R.id.btnAddEvent);
        hapus = findViewById(R.id.btnDeleteEvent);

        //init presenter
        //jadwalPresenter = new JadwalPresenter(getApplicationContext());
        //init helper
        jadwalHelper = new JadwalHelper(getApplicationContext());
        jadwalHelper.open();
        final ArrayList<JadwalModel> jadwalModels = jadwalHelper.getAllData();
        jadwalHelper.close();
        //event
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tambah.getText().equals("edit")) {

                } else {
                    /*JadwalPresenter jadwalPresenter = new JadwalPresenter(judul.getText().toString(),
                            date.getText().toString(), time.getText().toString(),
                            Integer.parseInt(remind.getText().toString()),
                            Integer.parseInt(warna.getText().toString()),
                            getApplicationContext());
                    //JadwalModel model = new JadwalModel(judul.getText().toString(), date.getText().toString(), time.getText().toString(), Integer.parseInt(remind.getText().toString()), Integer.parseInt(warna.getText().toString()));
                    jadwalPresenter.insertData();
                    jadwalPresenter.getAllData();*/

//                    try {
//                        jadwalHelper.open();
//                        jadwalHelper.insert(model);
//                        jadwalHelper.close();
//                        Log.i("InsertSucces", "masuk");
//                    } catch (SQLException e) {
//                        Log.i("SQLInsertError", e.getMessage());
//                    }
                }
            }
        });
        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jadwalPresenter.deleteitem((0));
                jadwalModels.remove(0);

            }
        });
    }

//    @Override
//    public void datePickerDialog() {
//
//    }
}
