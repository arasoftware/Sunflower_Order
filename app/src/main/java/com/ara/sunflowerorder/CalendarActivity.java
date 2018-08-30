package com.ara.sunflowerorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_DATE_RESULT;

public class CalendarActivity extends AppCompatActivity implements CalendarView.OnDateChangeListener {

    @BindView(R.id.calendarView)
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        ButterKnife.bind(this);
        calendarView.setOnDateChangeListener(this);
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        Intent intent = new Intent();
        String date = String.format("%d-%d-%d", dayOfMonth, month + 1, year);
        intent.putExtra(EXTRA_DATE_RESULT, date);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
