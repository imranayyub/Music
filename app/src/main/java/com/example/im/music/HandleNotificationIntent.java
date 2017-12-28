package com.example.im.music;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import static com.example.im.music.MyService.isSearch;
import static com.example.im.music.R.layout.big_notification;

/**
 * Created by Im on 15-12-2017.
 */

public class HandleNotificationIntent extends BroadcastReceiver {

    MediaPlayer mediaPlayer = MyService.player;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (MyService.NOTIFY_PLAY.equals(intent.getAction())) {
            MyService.play();
//            Toast.makeText(context, "NOTIFY_PLAY", Toast.LENGTH_LONG).show();
            setplayorpause(0);
        } else if (MyService.NOTIFY_PAUSE.equals(intent.getAction())) {
            if (mediaPlayer.isPlaying()) {
//                pauseMusic();
                MyService.pause();
            } else {
                MyService.play();
            }
            setplayorpause(1);
        } else if (MyService.NOTIFY_DELETE.equals(intent.getAction())) {
            context.stopService(new Intent(context, MyService.class));

        } else if (MyService.NOTIFY_NEXT.equals(intent.getAction())) {
            int position;
            position = MyService.getCurrrentPosition();
            position = position + 1;
            Intent nextintent = new Intent(context, MyService.class);
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            bundle.putInt("search", isSearch);
            nextintent.putExtras(bundle);
            context.startService(nextintent);

        } else if (MyService.NOTIFY_PREVIOUS.equals(intent.getAction())) {
            int position;
            position = MyService.getCurrrentPosition();
            if (position == 0)
                position = -1;
            else
                position = position - 1;
            Intent nextintent = new Intent(context, MyService.class);
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            bundle.putInt("search", isSearch);
            nextintent.putExtras(bundle);
            context.startService(nextintent);


        }

    }

//    public void pauseMusic() {
//        if (mediaPlayer.isPlaying()) {
//            mediaPlayer.pause();
//        }
//    }
//
//    public void resumeMusic() {
//        if (mediaPlayer.isPlaying() == false) {
//            mediaPlayer.start();
//        }
//    }
//
//
//    public void stopMusic() {
//        mediaPlayer.stop();
//        mediaPlayer.release();
//        mediaPlayer = null;
//    }

    static int playorpause;

    public void setplayorpause(int playorpause) {
        this.playorpause = playorpause;
    }

    public static int getplayorpause() {
        return playorpause;
    }
}