package com.example.im.music.fragments;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im.music.R;
import com.example.im.music.models.SongDetails;
import com.example.im.music.activities.HomeActivity;
import com.example.im.music.models.SongDetails_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;


/**
 * Created by Im on 06-12-2017.
 */

public class SongDetailDialogFragment extends DialogFragment implements View.OnClickListener {
    TextView name1;
    Button back;
    long hours = 0;
    String time, artist, album, genre, name, albumart;
    ImageView image;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflating the dialogfragment layout
        View rootView = inflater.inflate(R.layout.songdetail_dialog, container, false);
        name1 = (TextView) rootView.findViewById(R.id.name1);
        image = (ImageView) rootView.findViewById(R.id.image1);
        String songname = HomeActivity.getSongDetailName();
        List<SongDetails> songDetailses = SQLite.select().
                from(SongDetails.class).where(SongDetails_Table.name.like(songname)).
                queryList();
//        int size=songDetailses.size();
        SongDetails song = songDetailses.get(0);
        back = (Button) rootView.findViewById(R.id.back);
        back.setOnClickListener(this); //setting OnClickListener on Back Button.

        getDialog().setTitle("Song Details :"); //Sets title of Dialog Fragment.
        getDialog().setCancelable(false);
//        getDialog().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,R.drawable.album_art);
        //Converting Duration in Proper time Format.
        long duration = Long.parseLong(song.getDuration());
        hours = (duration / 3600000);
        long remaining_minutes = (duration - (hours * 3600000)) / 60000;
        String minutes = String.valueOf(remaining_minutes);
        long remaining_seconds = (duration - (hours * 3600000) - (remaining_minutes * 60000));
        String seconds = String.valueOf(remaining_seconds);
        seconds = seconds.substring(0, 2);
        if (Integer.parseInt(seconds) > 60)
            seconds = "0" + seconds.substring(0, 1);
        if (hours > 0) {
            time = hours + ":" + minutes + ":" + seconds;
        } else {
            time = minutes + ":" + seconds;
        }
        //Converting Bitrate into proper Format
        String bitrate = song.getBitrate();
        String[] separated = bitrate.split("0");
        bitrate = separated[0];
        if (bitrate.equals("32"))
            bitrate = "320";

        artist = song.getArtist();
        if (artist == null)
            artist = "--";
        album = song.getAlbum();
        if (album == null)
            album = "--";
        genre = song.getGenre();
        if (genre == null)
            genre = "--";
        name = song.getTitle();
        if (name == null)
            name = song.getName();
        //Displaying data in TextView of DialogFragment.
        name1.setText("Name : " + name
                + "\n\nPath : " + song.getPath()
                + "\n\nArtist : " + artist
                + "\n\nAlbum : " + album
                + "\n\nDuration : " + time
                + "\n\nGenre : " + genre
                + "\n\nBitrate : " + bitrate + " kbps");

        //Displays embedded Byte image into Image View.
        albumart = song.getAlbumArt();
        int width = 120, height = 120;
        if (albumart != "" && albumart != null) {
            byte[] imag = Base64.decode(albumart, Base64.DEFAULT);
            try {
                Bitmap bmp = BitmapFactory.decodeByteArray(imag, 0, imag.length);
//
                image.setImageBitmap(Bitmap.createScaledBitmap(bmp, width,
                        height, false));
            } catch (Exception e) {
                image.getLayoutParams().height = 120;
                image.getLayoutParams().width = 120;
                image.setImageResource(R.drawable.album_art);
                e.printStackTrace();
                Log.e("Exception ", e.toString());
            }
        }

        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.back:
                dismiss(); //Dismisses the Dialog Fragment.
                break;
        }
    }
}
