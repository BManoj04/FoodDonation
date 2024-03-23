package com.example.fooddonation;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Implementation of App Widget functionality.
 */
public class foodDonationCount extends AppWidgetProvider {

    private static DatabaseReference rootDatabseref;
    static long totalNumberOfFood;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        rootDatabseref = FirebaseDatabase.getInstance().getReference().child("totalNumberOfFood");
        rootDatabseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalNumberOfFood = (long) snapshot.getValue();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.food_donation_count);
        views.setTextViewText(R.id.appwidget_text, String.valueOf(totalNumberOfFood));


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
    public static final String ACTION_AUTO_UPDATE = "AUTO_UPDATE";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        super.onReceive(context, intent);

        if(intent.getAction().equals(ACTION_AUTO_UPDATE))
        {
            // DO SOMETHING
            rootDatabseref = FirebaseDatabase.getInstance().getReference().child("totalNumberOfFood");
            rootDatabseref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    totalNumberOfFood = (long) snapshot.getValue();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, foodDonationCount.class));

            for (int id : appWidgetIds) {
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.food_donation_count);
                views.setTextViewText(R.id.appwidget_text, String.valueOf(totalNumberOfFood));
                appWidgetManager.updateAppWidget(id, views);
            }


        }

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        AppWidgetAlarm appWidgetAlarm = new AppWidgetAlarm(context.getApplicationContext());
        appWidgetAlarm.startAlarm();
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidgetComponentName = new ComponentName(context.getPackageName(),getClass().getName());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidgetComponentName);
        if (appWidgetIds.length == 0) {
            // stop alarm
            AppWidgetAlarm appWidgetAlarm = new AppWidgetAlarm(context.getApplicationContext());
            appWidgetAlarm.stopAlarm();
        }
    }
}