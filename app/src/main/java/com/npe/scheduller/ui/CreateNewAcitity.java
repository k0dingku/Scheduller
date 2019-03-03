package com.npe.scheduller.ui;

import android.content.Context;
import android.content.Intent;
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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.npe.scheduller.R;
import com.npe.scheduller.presenter.JadwalPresenter;
import com.npe.scheduller.view.JadwalView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateNewAcitity extends AppCompatActivity implements JadwalView.JadwalViewMain, View.OnClickListener {
    private InterstitialAd interstitialAdBack, intertitialAdSave;
    private AdView adView;
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
    private Button btnOkDate, btnOkRemind, btnOkColor;
    private int intRemind, intColor;
    private ProgressBar pbInsert;
    private TextView save, cancel;

    public void loadAd(){
        AdRequest adRequest = new AdRequest.Builder().build();
        adView = findViewById(R.id.adViewNew);
        interstitialAdBack = new InterstitialAd(this);
        intertitialAdSave = new InterstitialAd(this);
        interstitialAdBack.setAdUnitId("ca-app-pub-1544132019976371/8172672090");
        interstitialAdBack.loadAd(adRequest);
        intertitialAdSave.setAdUnitId("ca-app-pub-1544132019976371/5163365376");
        intertitialAdSave.loadAd(adRequest);
        adView.loadAd(adRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_acitity);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.app_bar);
        TextView title=findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        title.setText("New Task");
        save = findViewById(getResources().getIdentifier("action_bar_save", "id", getPackageName()));
        cancel = findViewById(getResources().getIdentifier("action_bar_cancel", "id", getPackageName()));

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
        btnOkColor = findViewById(R.id.btnOkPickColor);
        pbInsert = findViewById(R.id.progresInsert);
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
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);

        loadAd();
    }

    private void hiddenKeyboard(View v){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
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

            btnOkColor.setOnClickListener(this);
        } else {
            sheetBehaviorColor.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }


    @Override
    public void insertSuccess(String message) {
        pbInsert.setVisibility(View.GONE);
        save.setVisibility(View.VISIBLE);
        toMain();
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void toMain() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void inserrtFailed(String message) {
        pbInsert.setVisibility(View.GONE);
        save.setVisibility(View.VISIBLE);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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
                hiddenKeyboard(etDate);
                showBottomSheetDate();
                break;
            case R.id.etNewColor:
                invisibleSheetDate();
                hiddenKeyboard(etColor);
                invisibleSheetRemind();
                showBottomSheetColor();
                break;
            case R.id.etNewRemind:
                invisibleSheetDate();
                invisibleSheetRemind();
                hiddenKeyboard(etRemind);
                String date = this.date + " " + this.time;
                presenter.calculateDifferenceDate(date);
                Log.i("Date", date);

                numberPicker.setMinValue(0);
                numberPicker.setMaxValue(diffRemind);

                showBottomSheetRemind();
                break;
           case R.id.action_bar_save:
                judul = etJudul.getText().toString();
                if (presenter.checkJudul(judul, etJudul)) {
                    save.setVisibility(View.GONE);
                    pbInsert.setVisibility(View.VISIBLE);
                    Log.i("WKWKWK",time);
                    presenter.dataMasukkan(judul,this.date, time,intRemind,intColor );
                }
                intertitialAdSave.show();
                break;
            case R.id.btnColorRed:
                int colorRed = getResources().getColor(R.color.colorRed);
                intColor = colorRed;
                disableAnotherColor(layoutColor, R.id.btnColorRed);
                break;
            case R.id.btnColorBlue:
                int colorBlue = getResources().getColor(R.color.colorBlue);
                intColor = colorBlue;
                disableAnotherColor(layoutColor, R.id.btnColorBlue);
                break;
            case R.id.btnColorGreen:
                int colorGreen = getResources().getColor(R.color.colorGreen);
                intColor = colorGreen;
                disableAnotherColor(layoutColor, R.id.btnColorGreen);
                break;
            case R.id.btnColorYellow:
                int colorYellow = getResources().getColor(R.color.colorYellow);
                intColor = colorYellow;
                disableAnotherColor(layoutColor, R.id.btnColorYellow);
                break;
            case R.id.btnColorGrey:
                int colorGrey = getResources().getColor(R.color.colorGrey);
                intColor = colorGrey;
                disableAnotherColor(layoutColor, R.id.btnColorGrey);
                break;
            case R.id.btnColorOrange:
                int colorOrange = getResources().getColor(R.color.colorOrange);
                intColor = colorOrange;
                disableAnotherColor(layoutColor, R.id.btnColorOrange);
                break;
            case R.id.btnOkDate:
                //date
                formatDate();
                //time
                formatTime();
                String strTanggal = this.date + " " + this.time;
                etDate.setText(strTanggal);
                etRemind.setVisibility(View.VISIBLE);
                invisibleSheetRemind();
                invisibleSheetDate();
                invisibleSheetColor();
                break;
            case R.id.btnOkRemind:
                intRemind = numberPicker.getValue();
                etRemind.setText(String.valueOf("D-"+intRemind));
                invisibleSheetRemind();
                invisibleSheetDate();
                invisibleSheetColor();
                break;
            case R.id.btnOkPickColor:
                etColor.setHintTextColor(getResources().getColor(R.color.colorWhite));
                etColor.setBackgroundColor(intColor);
                invisibleSheetColor();
                invisibleSheetRemind();
                invisibleSheetDate();
                break;
            case R.id.action_bar_cancel:
                toMain();
                interstitialAdBack.show();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void formatTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        String hour = String.valueOf(timePicker.getHour());
        String minute = String.valueOf(timePicker.getMinute());
        String waktu = hour+":"+minute;
        try {
            Date time = sdf.parse(waktu);
            String jam = (String) DateFormat.format("hh", time);
            String menit = (String) DateFormat.format("mm", time);
            presenter.setTime(jam, menit);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void formatDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        String day = String.valueOf(datePicker.getDayOfMonth());
        String year = String.valueOf(datePicker.getYear());
        String month = String.valueOf(datePicker.getMonth() + 1);
        String tanggal = day + "/" + month + "/" + year;
        try {
            Date date = sdf.parse(tanggal);
            Log.i("DateFormat", String.valueOf(date));
            String hari =(String) DateFormat.format("dd", date);
            String bulan = (String) DateFormat.format("MM", date);
            String tahun = (String) DateFormat.format("yyyy", date);
            Log.i("HariFormat", hari);
            presenter.setDate(hari, bulan, tahun);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
