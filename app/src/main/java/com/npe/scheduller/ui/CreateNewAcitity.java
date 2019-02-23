package com.npe.scheduller.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.npe.scheduller.R;
import com.npe.scheduller.presenter.JadwalPresenter;
import com.npe.scheduller.view.JadwalView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateNewAcitity extends AppCompatActivity implements JadwalView.JadwalViewMain, View.OnClickListener {
    private JadwalPresenter presenter;
    private EditText etDate, etColor, etRemind;
    private EditText etJudul;
    private LinearLayout layoutBottomColor,
            layoutBottomDate,
            layoutBottomRemind, layoutColor;
    private BottomSheetBehavior sheetBehaviorColor, sheetBehaviorDate, sheetBehaviorRemind;
    private String judul, date, time, currentDate;
    private int diffRemind;

    //btn color
    private Button btnPickColorRed, btnPickColorBlue, btnPickColorGreen, btnPickColorYellow, btnPickColorGrey, btnPickColorOrange;
    //date picker
    private DatePicker datePicker;
    private TimePicker timePicker;
    private NumberPicker numberPicker;
    private Button btnOkDate, btnOkRemind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_acitity);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.app_bar);
        TextView title=findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        title.setText("New Task");

        //inisialisasi
        presenter = new JadwalPresenter(getApplicationContext(), this);
        etDate = findViewById(R.id.etNewDate);
        etColor = findViewById(R.id.etNewColor);
        etRemind = findViewById(R.id.etNewRemind);
        etJudul = findViewById(R.id.etCreateJudul);
        layoutColor = findViewById(R.id.linearColor);
        btnPickColorRed = findViewById(R.id.btnColorRed);
        btnPickColorBlue = findViewById(R.id.btnColorBlue);
        btnPickColorGreen = findViewById(R.id.btnColorGreen);
        btnPickColorYellow = findViewById(R.id.btnColorYellow);
        btnPickColorGrey = findViewById(R.id.btnColorGrey);
        btnPickColorOrange = findViewById(R.id.btnColorOrange);
        btnOkDate = findViewById(R.id.btnOkDate);
        btnOkRemind = findViewById(R.id.btnOkRemind);
        //datepicker
        datePicker = findViewById(R.id.datePicker);
        minDate();
        //time picker
        timePicker = findViewById(R.id.timePicker);
        //number picker
        numberPicker = findViewById(R.id.numberPicker);
        //color
        layoutBottomColor = findViewById(R.id.layoutBottomSheetColor);
        sheetBehaviorColor = BottomSheetBehavior.from(layoutBottomColor);
        //set sheet color hiden
        sheetBehaviorColor.setState(BottomSheetBehavior.STATE_HIDDEN);


        //date
        layoutBottomDate = findViewById(R.id.bottom_sheet_set_date);
        sheetBehaviorDate = BottomSheetBehavior.from(layoutBottomDate);
        //set sheet date hidden
        sheetBehaviorDate.setState(BottomSheetBehavior.STATE_HIDDEN);

        //remind
        layoutBottomRemind = findViewById(R.id.bottom_sheet_remind);
        sheetBehaviorRemind = BottomSheetBehavior.from(layoutBottomRemind);
        //set sheet remind hidden
        sheetBehaviorRemind.setState(BottomSheetBehavior.STATE_HIDDEN);

        etDate.setOnClickListener(this);
        etColor.setOnClickListener(this);
        etRemind.setOnClickListener(this);
    }

    @Override
    public void minDate() {
        datePicker.setMinDate(System.currentTimeMillis());
    }

    @Override
    public void showBottomSheetDate() {
        if (sheetBehaviorDate.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehaviorDate.setState(BottomSheetBehavior.STATE_EXPANDED);
            btnOkDate.setOnClickListener(this);
        } else {
            sheetBehaviorDate.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

    @Override
    public void showBottomSheetRemind() {
        if (sheetBehaviorRemind.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehaviorRemind.setState(BottomSheetBehavior.STATE_EXPANDED);
            btnOkRemind.setOnClickListener(this);
        } else {
            sheetBehaviorRemind.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }


    @Override
    public void showBottomSheetColor() {

        if (sheetBehaviorColor.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehaviorColor.setState(BottomSheetBehavior.STATE_EXPANDED);
            //color click
            btnPickColorRed.setOnClickListener(this);
            btnPickColorBlue.setOnClickListener(this);
            btnPickColorGreen.setOnClickListener(this);
            btnPickColorYellow.setOnClickListener(this);
            btnPickColorGrey.setOnClickListener(this);
            btnPickColorOrange.setOnClickListener(this);
        } else {
            sheetBehaviorColor.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }


    @Override
    public void insertSuccess(String message) {

    }

    @Override
    public void inserrtFailed(String message) {

    }

    @Override
    public void getDate(String date) {
        this.date = date;
    }

    @Override
    public void getTime(String time) {
        this.time = time;
    }


    @Override
    public void getDifference(long diff) {
        this.diffRemind = (int) diff;
    }

    @Override
    public void invisibleSheetColor() {
        sheetBehaviorColor.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void invisibleSheetRemind() {
        sheetBehaviorRemind.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void invisibleSheetDate() {
        sheetBehaviorDate.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void disableAnotherColor(ViewGroup layout, int idBtn) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            int childid = layout.getChildAt(i).getId();
            View child = layout.getChildAt(i);
            if (childid != idBtn) {
                child.setVisibility(View.GONE);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.etNewDate:
                invisibleSheetColor();
                invisibleSheetRemind();
                showBottomSheetDate();
                break;
            case R.id.etNewColor:
                invisibleSheetDate();
                invisibleSheetRemind();
                showBottomSheetColor();
                break;
            case R.id.etNewRemind:
                invisibleSheetDate();
                invisibleSheetRemind();
                showBottomSheetRemind();
                break;
//            case R.id.btnInsert:
//                judul = etJudul.getText().toString();
//                if (presenter.checkJudul(judul, etJudul)) {
//                    //presenter.dataMasukkan(judul,date, );
//                }
//                break;
            case R.id.btnColorRed:
                int colorRed = getResources().getColor(R.color.colorRed);
                disableAnotherColor(layoutColor, R.id.btnColorRed);
                Toast.makeText(getApplicationContext(), "Color : " + String.valueOf(colorRed), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnColorBlue:
                int colorBlue = getResources().getColor(R.color.colorBlue);
                disableAnotherColor(layoutColor, R.id.btnColorBlue);
                Toast.makeText(getApplicationContext(), "Color : " + String.valueOf(colorBlue), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnColorGreen:
                int colorGreen = getResources().getColor(R.color.colorGreen);
                disableAnotherColor(layoutColor, R.id.btnColorGreen);
                Toast.makeText(getApplicationContext(), "Color : " + String.valueOf(colorGreen), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnColorYellow:
                int colorYellow = getResources().getColor(R.color.colorYellow);
                disableAnotherColor(layoutColor, R.id.btnColorYellow);
                Toast.makeText(getApplicationContext(), "Color : " + String.valueOf(colorYellow), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnColorGrey:
                int colorGrey = getResources().getColor(R.color.colorGrey);
                disableAnotherColor(layoutColor, R.id.btnColorGrey);
                Toast.makeText(getApplicationContext(), "Color : " + String.valueOf(colorGrey), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnColorOrange:
                int colorOrange = getResources().getColor(R.color.colorOrange);
                disableAnotherColor(layoutColor, R.id.btnColorOrange);
                Toast.makeText(getApplicationContext(), "Color : " + String.valueOf(colorOrange), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnOkDate:
                //date
                formatDate();
                //presenter.setDate(day, bulan, tahun);
                //time
                String hour = String.valueOf(timePicker.getHour());
                String minute = String.valueOf(timePicker.getMinute());
                presenter.setTime(hour, minute);
                break;
            case R.id.btnOkRemind:
                String date = this.date + " " + this.time;
                presenter.calculateDifferenceDate(date);
                numberPicker.setMinValue(0);
                numberPicker.setMaxValue(diffRemind);
                Log.i("Date", date);
                break;
        }
    }

    private void formatDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        String day = String.valueOf(datePicker.getDayOfMonth());
        String tahun = String.valueOf(datePicker.getYear());
        String bulan = String.valueOf(datePicker.getMonth() + 1);
        String tanggal = day + "/" + bulan + "/" + tahun;
        try {
            Date date = sdf.parse(tanggal);
            Log.i("DateFormat", String.valueOf(date));
            String hari =(String) DateFormat.format("dd", date);
            Log.i("HariFormat", hari);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
