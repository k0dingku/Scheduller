package com.npe.scheduller.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
    LinearLayout layoutBottomColor;
    BottomSheetBehavior sheetBehaviorColor;
    String judul;
    //btn color
    Button btnColorRed;
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
        btnColorRed = findViewById(R.id.btnColorRed);
        //color
        layoutBottomColor = findViewById(R.id.layoutBottomSheetColor);
        sheetBehaviorColor = BottomSheetBehavior.from(layoutBottomColor);

        //set sheet color hiden
        sheetBehaviorColor.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetColorBehavior();

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.setAlertDate();
            }
        });

        btnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetColor();
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check judul
                judul = etJudul.getText().toString();
                if (presenter.checkJudul(judul, etJudul)) {
                    Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void bottomSheetColorBehavior() {
        sheetBehaviorColor.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                switch (i){
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {

                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {

                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
    }

    @Override
    public void showBottomSheetColor() {
        if (sheetBehaviorColor.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehaviorColor.setState(BottomSheetBehavior.STATE_EXPANDED);
            
        } else {
            sheetBehaviorColor.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

    @Override
    public void alertDialogDate(int day, int month, int year) {

    }

    @Override
    public void insertSuccess(String message) {

    }

    @Override
    public void inserrtFailed(String message) {

    }
}
