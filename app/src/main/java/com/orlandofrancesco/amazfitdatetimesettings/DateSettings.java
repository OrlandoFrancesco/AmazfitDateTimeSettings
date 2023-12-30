package com.orlandofrancesco.amazfitdatetimesettings;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateSettings extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    int day = 0;
    int month = 0;
    int year = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_settings);

        Button confirm = findViewById(R.id.confirm);
        Functions functions = new Functions();

        NumberPicker dayPicker = findViewById(R.id.dayPicker);
        NumberPicker monthPicker = findViewById(R.id.monthPicker);
        NumberPicker yearPicker = findViewById(R.id.yearPicker);

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = simpleDateFormat.format(date);
        String[] splittedDate = formattedDate.split("/");

        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(31);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);

        String[] monthNames = {
                getString(R.string.january),
                getString(R.string.february),
                getString(R.string.march),
                getString(R.string.april),
                getString(R.string.may),
                getString(R.string.june),
                getString(R.string.july),
                getString(R.string.august),
                getString(R.string.september),
                getString(R.string.october),
                getString(R.string.november),
                getString(R.string.december)
        };

        monthPicker.setDisplayedValues(monthNames);

        yearPicker.setMinValue(1970);
        yearPicker.setMaxValue(2100);

        dayPicker.setValue(Integer.valueOf(splittedDate[0]));
        monthPicker.setValue(Integer.valueOf(splittedDate[1]));
        yearPicker.setValue(Integer.valueOf(splittedDate[2]));

        day = Integer.valueOf(splittedDate[0]);
        month = Integer.valueOf(splittedDate[1]);
        year = Integer.valueOf(splittedDate[2]);

        dayPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                day = newVal;
            }
        });
        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                month = newVal;
                switch (newVal) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        dayPicker.setMaxValue(31);
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        dayPicker.setMaxValue(30);
                        break;
                    case 2:
                        if (((year % 4 == 0) &&
                                !(year % 100 == 0))
                                || (year % 400 == 0))
                            dayPicker.setMaxValue(29);
                        else
                            dayPicker.setMaxValue(28);
                        break;
                }
            }
        });
        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                year = newVal;
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                functions.setDateTime(
                        getApplicationContext(),
                        day,
                        month - 1,
                        year,
                        null,
                        null);
                // I don't know but the month that is set is the one before the correct one.
                // the minus 1 is a temporary solution.
                onBackPressed();
            }
        });
    }
}