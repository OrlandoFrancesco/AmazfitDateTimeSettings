package com.orlandofrancesco.amazfitdatetimesettings;

import android.app.AlarmManager;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Functions {
    Calendar calendar = Calendar.getInstance();
    AlarmManager alarmManager;
    String dateTime;

    public void setDateTime(Context context, Integer day, Integer month, Integer year, Integer hours, Integer minutes) {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        try {
            // date
            if (day != null && month != null && year != null && hours == null && minutes == null ) {
                calendar.set(year, month, day);
            } else if (hours != null && minutes != null && day == null && month == null && year == null) {
                String today = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                String[] splittedDay = today.split("/");
                calendar.set(
                        Integer.valueOf(splittedDay[2]),
                        Integer.valueOf(splittedDay[1]),
                        Integer.valueOf(splittedDay[0]),
                        hours, minutes);
            } else {
                calendar.set(year, month, day, hours, minutes);
            }

            alarmManager.setTime(calendar.getTimeInMillis());

            if (MainActivity.status.getVisibility() == View.VISIBLE){
                MainActivity.status.setText(context.getString(R.string.done));
            }
        } catch (Exception e) {
            Toast.makeText(context, context.getString(R.string.root), Toast.LENGTH_SHORT).show();
            if (MainActivity.status.getVisibility() == View.VISIBLE){
                MainActivity.status.setText(R.string.root);
            }
        }
    }

    public void autoDateTime(Context context) {
        String url = "http://worldtimeapi.org/api/ip";

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            dateTime = jsonObject.getString("datetime");

                            String date = dateTime.split("T")[0];
                            String time = dateTime.split("T")[1]
                                    .replace(".","/")
                                    .split("/")[0];

                            setDateTime( context,
                                    Integer.valueOf(date.split("-")[2]), // day
                                    Integer.valueOf(date.split("-")[1]) - 1, // month
                                    Integer.valueOf(date.split("-")[0]), // year
                                    Integer.valueOf(time.split(":")[0]), // hours
                                    Integer.valueOf(time.split(":")[1])); // minutes
                        } catch (Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MainActivity.status.setText(context.getString(R.string.network_error));
            }
        });

        queue.add(stringRequest);
    }
}
