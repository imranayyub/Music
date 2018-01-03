package com.example.im.music.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.im.music.R;
import com.example.im.music.activities.HomeActivity;
import com.example.im.music.models.PlayList;
import com.example.im.music.models.SongDetails;
import com.example.im.music.models.SongDetails_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import static com.example.im.music.R.layout.big_notification;

/**
 * Created by Im on 04-12-2017.
 */

public class MyService extends Service {
    public static MediaPlayer player;
    static int position;
    public static int isSearch;
    public static final String NOTIFY_PLAY = "com.example.im.music.play";
    public static final String NOTIFY_PREVIOUS = "com.example.im.music.previous";
    public static final String NOTIFY_DELETE = "com.example.im.music.delete";
    public static final String NOTIFY_PAUSE = "com.example.im.music.pause";
    public static final String NOTIFY_NEXT = "com.example.im.music.next";
    String songName;

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    //Starts Service in Background.
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onTaskRemoved(intent);  //checks if App is closed and let the service still running.
        if (intent != null && intent.getExtras() != null) {
            if (player != null)
                player.stop();
            final List<SongDetails> songDetailses;
            final List<PlayList> playLists;
            //Extracting data from Intent.
            Bundle bundle = intent.getExtras();
            isSearch = bundle.getInt("search");
            if (isSearch == 1) {
                if (bundle.getString("songname") != null) {
                    songName = bundle.getString("songname");
                    setSongname(songName);
                } else {
                    songName = getSongname();
                }

                songDetailses = SQLite.select().
                        from(SongDetails.class).
                        where(SongDetails_Table.name.like("%" + songName + "%")).
                        queryList();
                if(songDetailses.size()!=0) {
                    position = bundle.getInt("position");
                    if (position >= songDetailses.size())  //checks if it's last Song in the list.
                        position = 0;
                    if (position == -1)
                        position = songDetailses.size() - 1;
                    setCurrentPosition(position);
                    SongDetails song = songDetailses.get(position);
                    Uri sing = Uri.parse((String) song.getPath()); //Converting String path into Uri.
                    player = MediaPlayer.create(this, sing);
                    player.start();  //playing Song Using MediaPlayer.
                    createNotification(getApplicationContext(), (String) song.getName(), song.getAlbumArt());  //shows Notification each time new song is played.
                    //checks if the Songs is over(Here we play next Song if previous is over).
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer play) {
                            position++;

                            if (position >= songDetailses.size())  //checks if it's last Song in the list.
                                position = 0;
                            if (player != null)
                                player.stop();
                            setCurrentPosition(position);
                            SongDetails song = songDetailses.get(position);
                            Uri sing = Uri.parse((String) song.getPath());
                            player = MediaPlayer.create(MyService.this, sing);
                            createNotification(getApplicationContext(), (String) song.getName(), song.getAlbumArt());  //shows Notification each time new song is played.
                            player.setOnCompletionListener(this);
                            player.start();
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Can't find song in phone..",Toast.LENGTH_SHORT).show();
                }

            } else if (isSearch == 2) {

                playLists = SQLite.select().
                        from(PlayList.class).
                        queryList();
                position = bundle.getInt("position");
                if (position >= playLists.size())  //checks if it's last Song in the list.
                    position = 0;
                if (position == -1)
                    position = playLists.size() - 1;
                setCurrentPosition(position);
                PlayList song = playLists.get(position);
                Uri sing = Uri.parse((String) song.getPath()); //Converting String path into Uri.
                player = MediaPlayer.create(this, sing);
                player.start();  //playing Song Using MediaPlayer.
                createNotification(getApplicationContext(), (String) song.getName(), song.getAlbumArt());  //shows Notification each time new song is played.
                //checks if the Songs is over(Here we play next Song if previous is over).
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer play) {
                        position++;

                        if (position >= playLists.size())  //checks if it's last Song in the list.
                            position = 0;
                        if (player != null)
                            player.stop();
                        setCurrentPosition(position);
                        PlayList song = playLists.get(position);
                        Uri sing = Uri.parse((String) song.getPath());
                        player = MediaPlayer.create(MyService.this, sing);
                        createNotification(getApplicationContext(), (String) song.getName(), song.getAlbumArt());  //shows Notification each time new song is played.
                        player.setOnCompletionListener(this);
                        player.start();
                    }
                });
            } else {
                songDetailses = SQLite.select().
                        from(SongDetails.class).
                        queryList();
                position = bundle.getInt("position");
                if (position >= songDetailses.size())  //checks if it's last Song in the list.
                    position = 0;
                if (position == -1)
                    position = songDetailses.size() - 1;
                setCurrentPosition(position);
                SongDetails song = songDetailses.get(position);
                Uri sing = Uri.parse((String) song.getPath()); //Converting String path into Uri.
                player = MediaPlayer.create(this, sing);
                player.start();  //playing Song Using MediaPlayer.
                createNotification(getApplicationContext(), (String) song.getName(), song.getAlbumArt());  //shows Notification each time new song is played.
                //checks if the Songs is over(Here we play next Song if previous is over).
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer play) {
                        position++;

                        if (position >= songDetailses.size())  //checks if it's last Song in the list.
                            position = 0;
                        if (player != null)
                            player.stop();
                        setCurrentPosition(position);
                        SongDetails song = songDetailses.get(position);
                        Uri sing = Uri.parse((String) song.getPath());
                        player = MediaPlayer.create(MyService.this, sing);
                        createNotification(getApplicationContext(), (String) song.getName(), song.getAlbumArt());  //shows Notification each time new song is played.
                        player.setOnCompletionListener(this);
                        player.start();
                    }
                });
            }
        }
        return START_STICKY;
    }

    //In case if App is closed then also service should keep running in background.
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent intent = new Intent(getApplicationContext(), this.getClass());
        intent.setPackage(getPackageName());
        startService(intent);
        super.onTaskRemoved(rootIntent);
    }

    //Stops Service(In this case stops playing soogs).
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null)
            player.stop();
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();

    }

    //Pauses Current Song.
    public static void pause() {
        if (player.isPlaying()) {
            player.pause();

        }
    }

    //Stops Current Song.
    public static void play() {
        if (!player.isPlaying()) {
            player.start();
        }
    }


    public static final int NOTIFICATION_ID_CUSTOM_BIG = 9;

    public void createNotification(Context context, String name, String art) {
        RemoteViews expandedView = new RemoteViews(context.getPackageName(), big_notification);

        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(context);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notifyIntent = new Intent(context, HomeActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationCompat.setContentIntent(pendingIntent);
        notificationCompat.setSmallIcon(R.drawable.ic_action_play);
        notificationCompat.setAutoCancel(false);
        notificationCompat.setCustomBigContentView(expandedView);
        notificationCompat.setContentTitle("Music Player");
        notificationCompat.setContentText("Control Audio");
        notificationCompat.getBigContentView().setTextViewText(R.id.textSongName, name);
        notificationCompat.setOngoing(true);
        notificationCompat.setOnlyAlertOnce(true);
        setListeners(expandedView, context);
//         int playorpause=HandleNotificationIntent.getplayorpause();
//        if(playorpause==0)
//        {
//            expandedView.setViewVisibility(R.id.btnPause, View.VISIBLE);
//            expandedView.setViewVisibility(R.id.btnPlay, View.INVISIBLE);
//
//        }
//        if(playorpause==1)
//        {
//            expandedView.setViewVisibility(R.id.btnPause, View.INVISIBLE);
//            expandedView.setViewVisibility(R.id.btnPlay, View.VISIBLE);
//
//        }

        if (art != null && !art.equals("")) {
            byte[] imag = Base64.decode(art, Base64.DEFAULT);
            try {
                Bitmap bmp = BitmapFactory.decodeByteArray(imag, 0, imag.length);
                notificationCompat.getBigContentView().setImageViewBitmap(R.id.albumart, bmp);
            } catch (Exception e) {
                e.printStackTrace();
                notificationCompat.getBigContentView().setImageViewResource(R.id.albumart, R.drawable.album_art);
                Log.e("Exception ", e.toString());
            }
        } else {
            notificationCompat.getBigContentView().setImageViewResource(R.id.albumart, R.drawable.album_art);
        }
        notificationManager.notify(NOTIFICATION_ID_CUSTOM_BIG, notificationCompat.build());


    }

    //    int isPause=0;
    public void setListeners(RemoteViews view, Context context) {

//        Intent notificationIntent = new Intent(this, HandleNotificationIntent.class);
//        notificationIntent.putExtra("id","0");

        Intent previous = new Intent(NOTIFY_PREVIOUS);
        Intent delete = new Intent(NOTIFY_DELETE);
        Intent pause = new Intent(NOTIFY_PAUSE);
        Intent next = new Intent(NOTIFY_NEXT);
        Intent play = new Intent(NOTIFY_PLAY);

        PendingIntent pPause = PendingIntent.getBroadcast(context, 0, pause, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnPause, pPause);

        PendingIntent pPlay = PendingIntent.getBroadcast(this, 0, play, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnPlay, pPlay);

        PendingIntent pNext = PendingIntent.getBroadcast(context, 0, next, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnNext, pNext);

        PendingIntent pPrevious = PendingIntent.getBroadcast(context, 0, previous, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnPrevious, pPrevious);

        PendingIntent pDelete = PendingIntent.getBroadcast(context, 0, delete, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnDelete, pDelete);
    }

    public void setCurrentPosition(int currentPosition) {
        this.position = currentPosition;
    }

    public static int getCurrrentPosition() {
        return position;
    }

    public void setSongname(String songname) {
        this.songName = songname;

    }

    String getSongname() {
        return songName;
    }
}
