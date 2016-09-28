package tk.gengwai.waiapp.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import tk.gengwai.waiapp.R;

/**
 *
 * Created by danielchan303 on 19/9/2016.
 */
public class UpdateService extends IntentService {
    // Constructor
    public UpdateService() {
        super("UpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("service", "service works");
        Notification mNotification = (new NotificationCompat.Builder(this))
                .setSmallIcon(R.drawable.poo)
                .setContentTitle("Testing")
                .setContentText("Testing Notification")
                .build();
//        Intent chatIntent = new Intent(this, ChatActivity.class);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mNotification);
    }
}
