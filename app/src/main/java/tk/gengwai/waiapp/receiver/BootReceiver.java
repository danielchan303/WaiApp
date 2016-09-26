package tk.gengwai.waiapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import tk.gengwai.waiapp.service.UpdateService;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            // Testing Service
            Intent intentToast = new Intent(context, UpdateService.class);
            context.startService(intentToast);
        }
    }
}
