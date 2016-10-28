package com.sid.demoapp.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.sid.demoapp.R;

public class PlayMusicService extends Service {
    private static final int NOTIFICATION_ID = 134;
    private int startId;
    private MediaPlayer player;

    public PlayMusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        buildMusicPlayer();
        buildNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.startId = startId;
        if (player != null) {
            if (player.isPlaying()) {
                player.seekTo(0);
            } else {
                player.start();
            }
        }
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        if (player != null) {
            player.stop();
            player.release();
        }
        super.onDestroy();
    }

    private void buildMusicPlayer() {
        player = MediaPlayer.create(this, R.raw.badnews);
        if (player != null) {
            player.setLooping(false);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopSelf(startId);
                }
            });
        }
    }

    private void buildNotification() {
        final Intent notificationIntent = new Intent(this, PlayMusicService.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        final Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setOngoing(true)
                .setContentTitle("Music Playing")
                .setContentText("Click to access Music Player")
                .setContentIntent(pendingIntent).build();

        startForeground(NOTIFICATION_ID, notification);
    }
}
