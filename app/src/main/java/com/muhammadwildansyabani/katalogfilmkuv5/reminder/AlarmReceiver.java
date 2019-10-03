package com.muhammadwildansyabani.katalogfilmkuv5.reminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.muhammadwildansyabani.katalogfilmkuv5.R;
import com.muhammadwildansyabani.katalogfilmkuv5.main.movie.model.Movies;
import com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String TYPE_DAILY_REMINDER = "Daily Reminder";
    public static final String TYPE_RELEASE_TODAY = "New Release";
    private final String EXTRA_TYPE = "type";
    private final int ID_REMINDER = 101;
    private final int ID_RELEASE = 100;

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        if(type.equalsIgnoreCase(TYPE_DAILY_REMINDER))
            showReminderNotification(context);
        else
            showNewFilmNotification(context);
    }

    private void showReminderNotification(Context context){
        String message =
                context.getResources().getString(R.string.notification_reminder_message);
        showAlarmNotification(context, TYPE_DAILY_REMINDER, message, ID_REMINDER);
    }

    private void showNewFilmNotification(Context context){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = dateFormat.format(Calendar.getInstance().getTime());
        String url = Constant.getUrlOf(
                Constant.URL_TYPE_NEW_RELEASE,
                Constant.URL_MOVIES,
                date,
                context
        );
        getMovies(context, url);
    }

    private void getMovies(final Context context, String url){
        AndroidNetworking.get(url)
                .setTag("movies")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results =  response.getJSONArray("results");
                            int totalItem = results.length();
                            if(totalItem == 0)
                                showAlarmNotification(
                                        context,
                                        context.getResources().getString(R.string.notification_empty_title),
                                        context.getResources().getString(R.string.notification_empty),
                                        ID_RELEASE
                                );
                            else
                                for(int i=0; i< totalItem;i++) {
                                    JSONObject movieObject = results.getJSONObject(i);
                                    final Movies moviesItem = new Movies(movieObject);
                                    String message = String.format(
                                            context.getResources().getString(R.string.notification_format_message),
                                            moviesItem.getTitle()
                                    );
                                    showAlarmNotification(
                                            context,
                                            moviesItem.getTitle(),
                                            message,
                                            (int) moviesItem.getId()
                                    );
                                }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        String title = context.getResources().getString(R.string.notification_error_title);
                        String message = context.getResources().getString(R.string.notification_error_message);
                        showAlarmNotification(
                                context,
                                title,
                                message,
                                ID_RELEASE
                        );
                    }
                });
    }

    private void showAlarmNotification(Context context, String title, String message, int notifyId) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "AlarmManager channel";

        NotificationManager notificationManagerCompat =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.movie_creation)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifyId, notification);
        }

    }

    public void setRepeatingAlarm(Context context, String type, String time){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_TYPE, type);

        String[] timeArray = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        int requestCode = type.equalsIgnoreCase(TYPE_DAILY_REMINDER) ? ID_REMINDER : ID_RELEASE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
            );
        }
    }

    public void cancelAlarm(Context context, String type){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode = type.equalsIgnoreCase(TYPE_DAILY_REMINDER) ? ID_REMINDER : ID_RELEASE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
