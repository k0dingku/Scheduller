package com.npe.scheduller.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.npe.scheduller.R;
import com.npe.scheduller.view.EditView;

public class EditActivity extends AppCompatActivity implements View.OnClickListener, EditView.EditActivityView {

    private EditText judul, date, remind, color;
    LinearLayout layoutBottomColor, layoutBottomDate, layoutBottomRemind;
    BottomSheetBehavior sheetBehaviorColor, sheetBehaviorDate, sheetBehaviorRemind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.app_bar);
        TextView title=findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        title.setText("Edit Task");

        date = findViewById(R.id.etEditDate);
        remind = findViewById(R.id.etEditRemind);
        color = findViewById(R.id.etEditColor);

        //color
        layoutBottomColor = findViewById(R.id.layoutBottomSheetColor);
        sheetBehaviorColor = BottomSheetBehavior.from(layoutBottomColor);

        //set sheet color hiden
        sheetBehaviorColor.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetColorBehavior();

        layoutBottomDate = findViewById(R.id.bottom_sheet_set_date);

        sheetBehaviorDate = BottomSheetBehavior.from(layoutBottomDate);


        layoutBottomRemind = findViewById(R.id.bottom_sheet_remind);

        sheetBehaviorRemind = BottomSheetBehavior.from(layoutBottomRemind);
        bottomSheetColorBehavior();

        date.setOnClickListener(this);
        remind.setOnClickListener(this);
        color.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.etEditDate:
                showBottomSheetDate();
                break;
            case R.id.etEditRemind:
                showBottomSheetRemind();
                break;
            case R.id.etEditColor:
                showBottomSheetColor();
                break;
        }
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
    public void showBottomSheetDate() {
        if (sheetBehaviorDate.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehaviorDate.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            sheetBehaviorDate.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

    @Override
    public void bottomSheetDateBehavior() {
        sheetBehaviorDate.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void showBottomSheetRemind() {
        if (sheetBehaviorRemind.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehaviorRemind.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            sheetBehaviorRemind.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

    @Override
    public void bottomSheetRemindBehavior() {
        sheetBehaviorRemind.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void showBottomSheetColor() {

        if (sheetBehaviorColor.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehaviorColor.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            sheetBehaviorColor.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}
