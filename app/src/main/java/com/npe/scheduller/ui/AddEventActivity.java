package com.npe.scheduller.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.npe.scheduller.R;
import java.util.ArrayList;

import com.npe.scheduller.model.JadwalModel;
import com.npe.scheduller.model.database.JadwalHelper;
public class AddEventActivity extends AppCompatActivity {

    private JadwalHelper jadwalHelper;
    private EditText judul, date, time, remind, warna;
    private Button tambah, hapus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);


        judul = findViewById(R.id.etJudul);
        date = findViewById(R.id.etDate);
        time = findViewById(R.id.etTime);
        remind = findViewById(R.id.etRemind);
        warna = findViewById(R.id.etWarna);

        tambah = findViewById(R.id.btnAddEvent);
        hapus = findViewById(R.id.btnDeleteEvent);
        jadwalHelper = new JadwalHelper(this);
        jadwalHelper.open();
        final ArrayList<JadwalModel>jadwalModels = jadwalHelper.getAllData();
        jadwalHelper.close();

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tambah.getText().equals("edit")){

                }
                else{
                    insertData();
                    getAllData();
                }
            }
        });
        /*hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteitem(jadwalModels.get(0));
                jadwalModels.remove(0);

            }
        });*/
    }
    private void insertData(){
        jadwalHelper.open();
        JadwalModel jadwalModel = new JadwalModel(judul.getText().toString(), date.getText().toString(), time.getText().toString(), Integer.parseInt(remind.getText().toString()), Integer.parseInt(warna.getText().toString()));
        jadwalHelper.insert(jadwalModel);
        jadwalHelper.close();
    }
    private void getAllData(){
        jadwalHelper.open();
        ArrayList<JadwalModel>jadwalModels = jadwalHelper.getAllData();
        jadwalHelper.close();
        Log.i("Kodingku",String.valueOf(jadwalModels.size()));
    }
    private void deleteitem(int id){
        jadwalHelper.open();
        jadwalHelper.delete(id);
        jadwalHelper.close();
    }
}
