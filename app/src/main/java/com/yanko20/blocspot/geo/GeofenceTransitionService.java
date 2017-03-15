package com.yanko20.blocspot.geo;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;
import com.yanko20.blocspot.BlocSpotApp;
import com.yanko20.blocspot.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanko on 3/14/2017.
 */

public class GeofenceTransitionService extends IntentService {

    private static final String TAG = GeofenceTransitionService.class.getSimpleName();
    public static final int GEOFENCE_NOTIFICATION_ID = 0;

    public GeofenceTransitionService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Retrieve Geofencing intent
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        // Handling errors
        if(geofencingEvent.hasError()){
            String errorMsg = getErrorString(geofencingEvent.getErrorCode() );
            Log.e(BlocSpotApp.TAG, errorMsg);
            return;
        }

        // Retrieve GeofenceTransition
        int geofenceTransiton = geofencingEvent.getGeofenceTransition();
        // Check transition type
        if(geofenceTransiton == Geofence.GEOFENCE_TRANSITION_ENTER
                || geofenceTransiton == Geofence.GEOFENCE_TRANSITION_EXIT){
            // Get the geofences that were triggered
            List<Geofence> triggerringGeofences = geofencingEvent.getTriggeringGeofences();
            // Create a detailed message with Geofences received
            String geofenceTransitionDetails =
                    getGeofenceTrasitionDetails(geofenceTransiton, triggerringGeofences);
            sendNotification(geofenceTransitionDetails);
        }
    }

    private String getGeofenceTrasitionDetails(int geofenceTransiton,
                                               List<Geofence> triggerringGeofences){
        // Create a detaield message with Geofecnes received
        // Get the id of each geofence triggered
        ArrayList<String> triggeringGeofencesList = new ArrayList<>();
        for (Geofence geofence : triggerringGeofences){
            triggeringGeofencesList.add(geofence.getRequestId());
        }

        String status = null;
        if(geofenceTransiton == Geofence.GEOFENCE_TRANSITION_ENTER){
            status = "Entering";
        }else if(geofenceTransiton == Geofence.GEOFENCE_TRANSITION_EXIT){
            status = "Exiting";
        }
        return status + TextUtils.join(", ", triggeringGeofencesList);
    }

    // Send a nnotification
    private void sendNotification(String msg){
        Log.i(BlocSpotApp.TAG, "sendNotification");

        // Intent to start the main activity
        Intent notificationIntent =
                MainActivity.makeNotificationIntent(getApplicationContext(), msg);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Creating and sending notification
        NotificationManager notificationManager =
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(GEOFENCE_NOTIFICATION_ID,
                createNotification(msg, notificationPendingIntent));
    }

    private Notification createNotification(String msg, PendingIntent notificationPendingIntent){
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this);
        notificationBuilder
                .setSmallIcon(com.google.android.gms.R.drawable.cast_ic_notification_small_icon)
                .setColor(Color.RED)
                .setContentTitle(msg)
                .setContentText("Geofence Notification!")
                .setContentIntent(notificationPendingIntent)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                .setAutoCancel(true);
        return notificationBuilder.build();
    }

    // Handle errors
    private static String getErrorString(int errorCode){
        switch (errorCode) {
            case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                return "GeoFence not available";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                return "Too many GeoFences";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                return "Too many pending intents";
            default:
                return "Unknown error.";
        }
    }
}
