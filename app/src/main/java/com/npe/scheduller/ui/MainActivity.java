package com.npe.scheduller.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.npe.scheduller.R;
import com.npe.scheduller.model.JadwalModel;
import com.npe.scheduller.model.dbsqlite.JadwalOperations;
import com.npe.scheduller.presenter.MainPresenter;
import com.npe.scheduller.view.MainView;

import java.util.ArrayList;
import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class MainActivity extends AppCompatActivity implements MainView.MainActivityView, View.OnClickListener {
    private MainPresenter presenter;
    private RecyclerView recyclerView;
    private Button btnDelete;
    public static AdapterJadwal adapter;
    public static ArrayList<JadwalModel> data = new ArrayList<JadwalModel>();
    LinearLayout fullCalendarBottomSheet, calendarLayoutBottomSheet, layoutBottomSheetOnLong;
    BottomSheetBehavior sheetBehaviorCalendar, sheetBehaviorOnLong;
    JadwalOperations jadwalOperations;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //inisialisasi
        presenter = new MainPresenter(getApplicationContext(), this);

        recyclerView = findViewById(R.id.recyclerViewMain);
        btnDelete = findViewById(R.id.btnDelete);
        calendarLayoutBottomSheet = findViewById(R.id.calendarBottomSheet);
        fullCalendarBottomSheet = findViewById(R.id.bottom_sheet_calendar);
        layoutBottomSheetOnLong = findViewById(R.id.bottom_sheet_onhold);
        btnDelete.setOnClickListener(this);

        jadwalOperations = new JadwalOperations(getApplicationContext());


        dbtoarraylist();
        calendar();
        bottomsheet();

    }

    @Override
    public void dbtoarraylist() {
        try {
            jadwalOperations.openDb();
            if (jadwalOperations.checkRecord() == true) {
                presenter.masukkinData();
                listactivity();
                //Toast.makeText(getApplicationContext(),"Data ada",Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(getApplicationContext(),"Kosong",Toast.LENGTH_LONG).show();
            }
            jadwalOperations.closeDb();
        } catch (SQLException e) {
            Log.i("Error", "Error");
        }
    }

    @Override
    public void calendar() {

        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DATE, 0);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.YEAR, 1);

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

        adapter = new AdapterJadwal(data);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new AdapterJadwal.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                setPosition(position);
                if (sheetBehaviorOnLong.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehaviorOnLong.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    sheetBehaviorOnLong.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
    }

    private void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void bottomsheet() {
        sheetBehaviorOnLong = BottomSheetBehavior.from(layoutBottomSheetOnLong);
        sheetBehaviorCalendar = BottomSheetBehavior.from(fullCalendarBottomSheet);
        sheetBehaviorCalendar.setHideable(false);
        sheetBehaviorCalendar.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                switch (i) {
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
        dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Delete");
        dialog.setMessage("Are you sure want to delete?");
        dialog.setCancelable(false);
        dialog.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.deleteCard(position, data);
                onResume();
                sheetBehaviorOnLong.setState(BottomSheetBehavior.STATE_HIDDEN);
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = dialog.create();
        alert.show();
        Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        nbutton.setTextColor(getResources().getColor(R.color.colorBlue));
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setBackgroundColor(getResources().getColor(R.color.colorRed));
        pbutton.setTextColor(getResources().getColor(R.color.colorWhite));

    }

    @Override
    public void deleteSucces(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteFailde(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDelete:
                deleteconfirmation();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.searchForm).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Task");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.add:
                Intent intent = new Intent(getApplicationContext(), CreateNewAcitity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbtoarraylist();
    }
}
