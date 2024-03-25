package com.example.fooddonation;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link foodDonationCount2ConfigureActivity foodDonationCount2ConfigureActivity}
 */
public class foodDonationCount2 extends AppWidgetProvider {

    private static DatabaseReference rootDatabseref;
    static CharSequence widgetText;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        widgetText = foodDonationCount2ConfigureActivity.loadTitlePref(context, appWidgetId);
        rootDatabseref = FirebaseDatabase.getInstance().getReference().child("donationNumberList");
        rootDatabseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final int[] count = {0};
                String city = "";

                if(snapshot.exists()){
                    if(snapshot.getValue().getClass().getSimpleName().equals("ArrayList")){
                        ArrayList listOfValues = (ArrayList) snapshot.getValue();
                        for (Object x:listOfValues) {
                            if(!String.valueOf(x).equals("null")){
                                city = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 3) + 1, IndexOfOccurence(String.valueOf(x), ",", 3));
                                Log.d("ssssssssssssssssss",city +" "+widgetText+ " "+city.equals(widgetText));
                                if(city.equals(widgetText)){
                                    count[0]++;
                                }
                            }

                        }
                    }else if (snapshot.getValue().getClass().getSimpleName().equals("HashMap")){
                        HashMap map  = (HashMap) snapshot.getValue();
                        Collection<Integer> values = map.values();

                        // Creating an ArrayList of values
                        ArrayList<Object> listOfValues = new ArrayList<>(values);
                        for (Object x:listOfValues) {

                            if(!String.valueOf(x).equals("null")) {
                                city = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 3) + 1, IndexOfOccurence(String.valueOf(x), ",", 3));
                                Log.d("ssssssssssssssssss",city +" "+widgetText+ " "+city.equals(widgetText));
                                if(city.equals(widgetText)){
                                    count[0]++;
                                }

                            }
                        }
                    }
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, foodDonationCount2.class));

                    for (int id : appWidgetIds) {
                        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.food_donation_count2);
                        Log.d("ssssssssssssssssss", String.valueOf(count[0]));
                        String msg = widgetText + " : " + count[0];
                        views.setTextViewText(R.id.appwidget_text2,msg);
                        appWidgetManager.updateAppWidget(id, views);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
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
            rootDatabseref = FirebaseDatabase.getInstance().getReference().child("donationNumberList");

            rootDatabseref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    final int[] count = {0};
                    String city = "";

                    if(snapshot.exists()){
                        if(snapshot.getValue().getClass().getSimpleName().equals("ArrayList")){
                            ArrayList listOfValues = (ArrayList) snapshot.getValue();
                            for (Object x:listOfValues) {
                                if(!String.valueOf(x).equals("null")){
                                    city = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 3) + 1, IndexOfOccurence(String.valueOf(x), ",", 3));
                                    Log.d("ssssssssssssssssss",city +" "+widgetText+ " "+city.equals(widgetText));
                                    if(city.equals(widgetText)){
                                        count[0]++;
                                    }
                                }

                            }
                        }else if (snapshot.getValue().getClass().getSimpleName().equals("HashMap")){
                            HashMap map  = (HashMap) snapshot.getValue();
                            Collection<Integer> values = map.values();

                            // Creating an ArrayList of values
                            ArrayList<Object> listOfValues = new ArrayList<>(values);
                            for (Object x:listOfValues) {

                                if(!String.valueOf(x).equals("null")) {
                                    city = String.valueOf(x).substring(IndexOfOccurence(String.valueOf(x), "=", 3) + 1, IndexOfOccurence(String.valueOf(x), ",", 3));
                                    Log.d("ssssssssssssssssss",city +" "+widgetText+ " "+city.equals(widgetText));
                                    if(city.equals(widgetText)){
                                        count[0]++;
                                    }
                                }
                            }
                        }
                    }
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, foodDonationCount2.class));

                    for (int id : appWidgetIds) {
                        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.food_donation_count2);
                        Log.d("ssssssssssssssssss", String.valueOf(count[0]));
                        String msg = widgetText + " : " + count[0];
                        views.setTextViewText(R.id.appwidget_text2,msg);
                        appWidgetManager.updateAppWidget(id, views);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });



        }

    }

    private static int IndexOfOccurence(String s, String match, int occurence)
    {
        int i = 1;
        int index = 0;
        while (i <= occurence && (index = s.indexOf(match, index + 1)) != -1)
        {
            if (i == occurence)
                return index;
            i++;
        }
        return -1;
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            foodDonationCount2ConfigureActivity.deleteTitlePref(context, appWidgetId);
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