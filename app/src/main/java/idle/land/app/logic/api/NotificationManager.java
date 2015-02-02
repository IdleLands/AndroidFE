package idle.land.app.logic.api;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import idle.land.app.IdleLandApplication;
import idle.land.app.R;
import idle.land.app.logic.Model.Event;
import idle.land.app.logic.Preferences;
import idle.land.app.ui.MainActivity;

import java.util.Date;
import java.util.List;

public class NotificationManager {

    public static final int EVENT_NOTIFICATION_ID = 31;
    private static NotificationManager singelton;
    Preferences mPreferences;

    /**
     * Time user opened the app the last time.
     * Well be read out when new notifications are build to give a number of unseen events
     * Must be updated when app opens
     */
    Date lastTimeEventsSeen = new Date();
    int lastNotificationCount = 0;
    boolean isActive = true;

    public static NotificationManager getInstance()
    {
        if(singelton == null)
            singelton = new NotificationManager();
        return singelton;
    }

    private NotificationManager() {
        mPreferences = new Preferences();
    }


    /**
     * returns an updated service status notification
     * @return Notification to post
     */
    public Notification buildServiceStatusNotification(Context context)
    {
        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent launchIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        Intent stopIntent = new Intent(context, HeartbeatService.class);
        stopIntent.putExtra(HeartbeatService.EXTRA_STOP, true);
        PendingIntent stopPendingIntent = PendingIntent.getService(context, 1, stopIntent, 0);

        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.notification_no_news));

        // Buttons only on sdk level >= 16
        if(Build.VERSION.SDK_INT >= 16) {
            builder.addAction(R.drawable.notification_close, context.getString(R.string.notification_action_stop), stopPendingIntent);
            builder.addAction(R.drawable.notification_more, context.getString(R.string.notification_action_more), launchIntent);
        }

        builder.setContentIntent(launchIntent);
        return builder.getNotification();
    }

    public void postEventNotification(Context context, @Nullable List<Event> events)
    {
        // get last unseen event if there are any
        int totalUnseenEvents = 0;
        Event lastUnseenEvent = null;
        if(events != null)
        {
            for(Event e : events)
            {
                if(e.getCreatedAt().after(lastTimeEventsSeen)) {
                    totalUnseenEvents += 1;
                    if(lastUnseenEvent == null || e.getCreatedAt().after(lastUnseenEvent.getCreatedAt()))
                        lastUnseenEvent = e;
                }
            }
        }

        if(totalUnseenEvents > 0 && !isActive && lastNotificationCount < totalUnseenEvents)
        {
            Intent notificationIntent = new Intent(context, MainActivity.class);
            PendingIntent launchPI = PendingIntent.getActivity(context, 2, notificationIntent, 0);

            Intent dismissIntent = new Intent(context, HeartbeatService.class);
            dismissIntent.putExtra(HeartbeatService.EXTRA_DISMISS_NOTIFICATION, true);
            PendingIntent dismissPI = PendingIntent.getService(context, 3, dismissIntent, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(lastUnseenEvent.getMessage()))
                    .setSmallIcon(lastUnseenEvent.getType().getIconResId())
                    .setContentTitle(String.format("New Events! (%1$s)", totalUnseenEvents))
                    .setContentText(lastUnseenEvent.getMessage())
                    .setContentIntent(launchPI)
                    .addAction(R.drawable.notification_close, context.getString(R.string.notification_action_dismiss), dismissPI)
                    .addAction(R.drawable.notification_more, context.getString(R.string.notification_action_show), launchPI);
            if(mPreferences.getBoolean(Preferences.Property.NOTIFICATION_PLAY_SOUND))
                builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));


            Notification n = builder.build();
            NotificationManagerCompat.from(context).notify(EVENT_NOTIFICATION_ID, n);
            lastNotificationCount = totalUnseenEvents;
        }
    }

    /**
     * resets notifications
     */
    public void resetNotifications(Context c)
    {
        NotificationManagerCompat.from(c).cancel(EVENT_NOTIFICATION_ID);
        lastTimeEventsSeen = new Date();
        lastNotificationCount = 0;
    }

    /**
     * enables/disables showing of new events and updates last event time
     * @param isActive
     */
    public void setActive(boolean isActive)
    {
        this.isActive = isActive;
        if(!isActive)
            lastTimeEventsSeen = new Date();
        else
            ((android.app.NotificationManager) IdleLandApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE)).cancel(EVENT_NOTIFICATION_ID);
    }

}
