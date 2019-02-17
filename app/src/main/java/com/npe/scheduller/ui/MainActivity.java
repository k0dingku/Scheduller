package com.npe.scheduller.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.npe.scheduller.BuildConfig;
import com.npe.scheduller.R;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FrameLayout frameGuide;
    private Button btnGuideAdd, btnGuideSearch, btnGuideDate;
    private LinearLayout layoutGuideAdd, layoutGuideSearch, layoutGuideDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);

        //inisialisasi
        frameGuide = findViewById(R.id.frameGuide);
        btnGuideAdd = findViewById(R.id.btnGuideAdd);
        btnGuideSearch = findViewById(R.id.btnGuideSearch);
        btnGuideDate = findViewById(R.id.btnGuideDate);
        layoutGuideAdd = findViewById(R.id.layoutAdd);
        layoutGuideDate = findViewById(R.id.layoutDate);
        layoutGuideSearch = findViewById(R.id.layoutSearch);

        checkFirstRun();

    }

    private void checkFirstRun() {
        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;

        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {
            // This is just a normal run
            frameGuide.setVisibility(View.GONE);
            return;

        } else if (savedVersionCode == DOESNT_EXIST) {
            frameGuide.setVisibility(View.VISIBLE);
            //add
            btnGuideAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layoutGuideAdd.setVisibility(View.GONE);
                    layoutGuideDate.setVisibility(View.VISIBLE);
                }
            });
            //date
            btnGuideDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  layoutGuideDate.setVisibility(View.GONE);
                  layoutGuideSearch.setVisibility(View.VISIBLE);
                }
            });
            //search
            btnGuideSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layoutGuideSearch.setVisibility(View.GONE);
                    frameGuide.setVisibility(View.GONE);
                }
            });
        } else if (currentVersionCode > savedVersionCode) {

        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_search){
            Toast.makeText(getApplicationContext(), "Search", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.menu_add){
            Toast.makeText(getApplicationContext(), "Add", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
