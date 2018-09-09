package com.jullae.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jullae.R;
import com.jullae.data.db.model.PushNotificationModel;
import com.jullae.ui.home.HomeActivity;
import com.jullae.utils.AppUtils;

import java.util.Calendar;
import java.util.Map;
import java.util.Random;

import static com.jullae.ui.notification.NotificationAdapter.NOTI_MESSAGE;
import static com.jullae.ui.notification.NotificationAdapter.NOTI_TYPE_FOLLOW;
import static com.jullae.ui.notification.NotificationAdapter.NOTI_TYPE_NEW_COMMENT;
import static com.jullae.ui.notification.NotificationAdapter.NOTI_TYPE_NEW_STORY;
import static com.jullae.ui.notification.NotificationAdapter.NOTI_TYPE_PICTURE_LIKE;
import static com.jullae.ui.notification.NotificationAdapter.NOTI_TYPE_STORY_LIKE;

public class MyFirebaseMessageService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessageService.class.getName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            parseDataPayload(remoteMessage.getData());
            //showNotification(remoteMessage.getData().toString(),"PixStory");


        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    private void parseDataPayload(Map<String, String> data) {
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(data);
        PushNotificationModel pushNotificationModel = gson.fromJson(jsonElement, PushNotificationModel.class);
        pushNotificationModel.setSpannable_text(this);
        showNotification(pushNotificationModel);
    }

    private void showNotification(PushNotificationModel pushNotificationModel) {
        Random random = new Random();

        int m = random.nextInt(9999 - 1000) + 1000;

        Intent i;
        switch (pushNotificationModel.getNotification_type_id()) {
            case NOTI_TYPE_FOLLOW:
                i = AppUtils.buildVisitorProfileActivityIntent(this, pushNotificationModel.getActor_penname());
                break;
            case NOTI_TYPE_NEW_STORY:
                i = AppUtils.buildStoryDetailActivityIntent(this, pushNotificationModel.getStory_id());
                break;
            case NOTI_TYPE_NEW_COMMENT:
                i = AppUtils.buildStoryDetailActivityIntent(this, pushNotificationModel.getStory_id());
                break;
            case NOTI_TYPE_STORY_LIKE:
                i = AppUtils.buildStoryDetailActivityIntent(this, pushNotificationModel.getStory_id());
                break;
            case NOTI_TYPE_PICTURE_LIKE:
                i = AppUtils.buildPictureDetailActivityIntent(this, pushNotificationModel.getPicture_id());
                break;
            case NOTI_MESSAGE:
                i = AppUtils.buildMessageActivityIntent(this, pushNotificationModel);
                break;
            default:
                i = new Intent(this, HomeActivity.class);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), m, i, PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.bigText(pushNotificationModel.getSpannable_text());
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setStyle(bigTextStyle)
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),
                        R.mipmap.ic_launcher))
                .setContentText(pushNotificationModel.getSpannable_text());

        if (pushNotificationModel.getTitle() != null && !pushNotificationModel.getTitle().isEmpty())
            mBuilder.setContentTitle(pushNotificationModel.getTitle());

        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour < 7 || hour > 22) {
            mBuilder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        } else {
            mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);

        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(m, mBuilder.build());

    }
}
