package com.npe.scheduller.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.npe.scheduller.R;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button btnCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //inisialisasi
        recyclerView = findViewById(R.id.recyclerViewMain);
        btnCreate = findViewById(R.id.btnCreate);

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


}
