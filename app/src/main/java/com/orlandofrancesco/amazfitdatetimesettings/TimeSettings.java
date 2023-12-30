package com.orlandofrancesco.amazfitdatetimesettings;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class TimeSettings extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    int hours = 0;
    int minutes = 0;
    int amPm = 0;
    boolean isAm = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_settings);

        NumberPicker amPmPicker = findViewById(R.id.amPmPicker);
        NumberPicker hoursPicker = findViewById(R.id.hoursPicker);
        NumberPicker minutesPicker = findViewById(R.id.minutesPicker);

        Button confirm = findViewById(R.id.confirm);
        Functions functions = new Functions();

        Date date = new Date();

        amPmPicker.setMinValue(0);
        amPmPicker.setMaxValue(1);
        amPmPicker.setDisplayedValues(new String[]{"AM", "PM"});

        hoursPicker.setMinValue(1);
        hoursPicker.setMaxValue(12);

        minutesPicker.setMinValue(0);
        minutesPicker.setMaxValue(59);

        if (date.getHours() > 12){
            isAm = false;
            amPmPicker.setValue(1);
            hoursPicker.setValue(date.getHours() - 12);
            hours = date.getHours() - 12;
        } else {
            hoursPicker.setValue(date.getHours());
            hours = date.getHours();
        }

        minutesPicker.setValue(date.getMinutes());
        minutes = date.getMinutes();

        amPmPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                amPm = newVal;
            }
        });
        hoursPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                hours = newVal;
            }
        });
        minutesPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                minutes = newVal;
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isAm == false){
                    hours += 12;
                }

                functions.setDateTime(
                        getApplicationContext(),
                        null,
                        null,
                        null,
                        hours,
                        minutes);

                onBackPressed();
            }
        });
    }
}