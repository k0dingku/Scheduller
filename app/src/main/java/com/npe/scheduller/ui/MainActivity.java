package com.npe.scheduller.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import com.npe.scheduller.R;
import com.npe.scheduller.model.JadwalModel;
import com.npe.scheduller.view.MainView;

import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class MainActivity extends AppCompatActivity implements MainView.MainActivityView, View.OnClickListener {
    private RecyclerView recyclerView;
    private Button btnCreate, btnDelete;
    private static RecyclerView.Adapter adapter;
    private static ArrayList<JadwalModel> data;
    LinearLayout fullCalendarBottomSheet,calendarLayoutBottomSheet, layoutBottomSheetOnLong;
    BottomSheetBehavior sheetBehaviorCalendar, sheetBehaviorOnLong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerViewMain);
        btnCreate = findViewById(R.id.btnCreate);
        btnDelete = findViewById(R.id.btnDelete);
        calendarLayoutBottomSheet = findViewById(R.id.calendarBottomSheet);
        fullCalendarBottomSheet = findViewById(R.id.bottom_sheet_calendar);
        layoutBottomSheetOnLong = findViewById(R.id.bottom_sheet_onhold);
        btnDelete.setOnClickListener(this);

        listactivity();
        calendar();
        bottomsheet();


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toCreate();
            }
        });
    }

    private void toCreate() {
        Intent intent = new Intent(getApplicationContext(), CreateNewAcitity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }



    @Override
    public void calendar() {

        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        final HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {

            }
        });
    }

    @Override
    public void listactivity() {

        data = new ArrayList<>();
        data.add(new JadwalModel(1,1,"Saturday","07.00","Test"));

        AdapterJadwal adapter = new AdapterJadwal(data);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new AdapterJadwal.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                if(sheetBehaviorOnLong.getState()!=BottomSheetBehavior.STATE_EXPANDED){
                    sheetBehaviorOnLong.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else{
                    sheetBehaviorOnLong.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
    }

    @Override
    public void bottomsheet() {
        sheetBehaviorOnLong = BottomSheetBehavior.from(layoutBottomSheetOnLong);
        sheetBehaviorCalendar = BottomSheetBehavior.from(fullCalendarBottomSheet);
        sheetBehaviorCalendar.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                switch (i){
                    case BottomSheetBehavior.STATE_HIDDEN:
                        calendarLayoutBottomSheet.setVisibility(View.VISIBLE);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        calendarLayoutBottomSheet.setVisibility(View.GONE);
                        break;
                    }
                    case BottomSheetBehavior.STATE_DRAGGING:
                        calendarLayoutBottomSheet.setVisibility(View.VISIBLE);
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        calendarLayoutBottomSheet.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

    }

    @Override
    public void deleteconfirmation() {
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.delete_confirm_dialog, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnDelete:
                deleteconfirmation();
        }
    }
}
