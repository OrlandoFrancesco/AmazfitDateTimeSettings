package com.orlandofrancesco.amazfitdatetimesettings;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    WifiManager wifiManager;
    public static TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout date = findViewById(R.id.date);
        LinearLayout time = findViewById(R.id.time);
        LinearLayout timeZone = findViewById(R.id.timeZone);
        status = findViewById(R.id.status);

        Intent DateSettingsNavIntent = new Intent(getApplicationContext(), DateSettings.class);
        Intent TimeSettingsNavIntent = new Intent(getApplicationContext(), TimeSettings.class);

        Functions functions = new Functions();

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);

        status.setSelected(true);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(DateSettingsNavIntent);
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TimeSettingsNavIntent);
            }
        });

        timeZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                functions.autoDateTime(getApplicationContext());
                status.setVisibility(View.VISIBLE);
                status.setText(R.string.setting_up);
            }
        });
    }
}