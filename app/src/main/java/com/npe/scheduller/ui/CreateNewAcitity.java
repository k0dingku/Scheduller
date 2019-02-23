package com.npe.scheduller.ui;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.npe.scheduller.R;
import com.npe.scheduller.presenter.JadwalPresenter;
import com.npe.scheduller.view.JadwalView;

public class CreateNewAcitity extends AppCompatActivity implements JadwalView.JadwalViewMain {
    private JadwalPresenter presenter;
    Button btnDate, btnTime, btnColor, btnRemind, btnInsert;
    EditText etJudul;
    String judul;

    BottomSheetBehavior bottomSheetBehavior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_acitity);
        //inisialisasi
        presenter = new JadwalPresenter(getApplicationContext(), this);
        btnDate = findViewById(R.id.btnDate);
        btnColor = findViewById(R.id.btnColor);
        btnTime = findViewById(R.id.btnTime);
        btnRemind = findViewById(R.id.btnRemind);
        btnInsert = findViewById(R.id.btnInsert);
        etJudul = findViewById(R.id.etCreateJudul);

        LinearLayout linearLayout = findViewById(R.id.bottom_sheet_set_date);

        bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);


        //check judul

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judul = etJudul.getText().toString();
                if(presenter.checkJudul(judul, etJudul)){
                    Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toogleBottomSheet();
            }
        });
    }


    private void toogleBottomSheet() {
        if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

    @Override
    public void insertSuccess(String message) {

    }

    @Override
    public void inserrtFailed(String message) {

    }
}
