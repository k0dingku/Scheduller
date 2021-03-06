package com.npe.scheduller.ui;

import android.app.SearchManager;
import android.content.Context;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
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

    private AdView adView;
    private MainPresenter presenter;
    private RecyclerView recyclerView;
    private SearchView searchView;
    public ArrayList<JadwalModel> data = new ArrayList<>();
    private Button btnDelete, btnEdit;
    public static AdapterJadwal adapter;
    LinearLayout fullCalendarBottomSheet, calendarLayoutBottomSheet, layoutBottomSheetOnLong;
    BottomSheetBehavior sheetBehaviorCalendar, sheetBehaviorOnLong;
    JadwalOperations jadwalOperations;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    int position;

    public void loadAd(){
        AdRequest adRequest = new AdRequest.Builder().build();
        adView = findViewById(R.id.adViewMain);

        adView.loadAd(adRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //inisialisasi
        presenter = new MainPresenter(getApplicationContext(), this);
        btnEdit = findViewById(R.id.btnEdit);

        recyclerView = findViewById(R.id.recyclerViewMain);
        btnDelete = findViewById(R.id.btnDelete);
        calendarLayoutBottomSheet = findViewById(R.id.calendarBottomSheet);
        fullCalendarBottomSheet = findViewById(R.id.bottom_sheet_calendar);
        layoutBottomSheetOnLong = findViewById(R.id.bottom_sheet_onhold);


        jadwalOperations = new JadwalOperations(getApplicationContext());

        btnDelete.setOnClickListener(this);
        btnEdit.setOnClickListener(this);

        dbtoarraylist();
        calendar();
        bottomsheet();

        btnDelete.setOnClickListener(this);
        loadAd();
    }



    @Override
    public void dbtoarraylist() {
        try {
            jadwalOperations.openDb();
            data = jadwalOperations.getAllJadwal();
            jadwalOperations.closeDb();
            listactivity();
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

        adapter = new AdapterJadwal(this,data);
        Log.i("Banyaknya","adalah "+data.size());
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
                break;
            case R.id.btnEdit:
                toEdit(position);
        }

    }

    private void toEdit(int position) {
        int idCart = data.get(position).getId();
        Intent intent = new Intent(getApplicationContext(), EditActivity.class);
        intent.putExtra("id", idCart);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.searchForm)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Task");

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });
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
    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}
