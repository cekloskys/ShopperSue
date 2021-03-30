package com.example.shoppersue;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

/**
 * This class creates a Notification Channel for Shopper.  Notification Channels became
 * necessary starting with Android Oreo (API 26) to be able to show notifications.  The
 * Notification Channel for Shopper will be created one time as soon as the application
 * starts and will be available for all other Activity classes to use.
 */
public class App extends Application {

    // declare and initialize a Channel Id
    public static final String CHANNEL_SHOPPER_ID = "channelshopper";

    // override the onCreate method
    @Override
    public void onCreate() {
        super.onCreate();

        // call method that creates Notification Channel for Shopper
        createNotificationChannel();
    }

    /**
     * This method creates the Notification Channel for Shopper.
     */
    private void createNotificationChannel() {
        // check if Android Oreo (API 26) or higher because Notification Channels weren't
        // available on lower versions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // initialize Notification Channel - must give it an Id, name, and importance
            // level
            NotificationChannel channelshopper = new NotificationChannel(
                    CHANNEL_SHOPPER_ID,
                    "Channel Shopper",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            // customize the Notification Channel - set its description
            channelshopper.setDescription("This is the Shopper Channel.");

            // initialize a Notification Manager
            NotificationManager manager = getSystemService(NotificationManager.class);

            // create Shopper Notification Channel
            manager.createNotificationChannel(channelshopper);
        }
    }
}
