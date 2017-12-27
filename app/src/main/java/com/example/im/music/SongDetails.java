package com.example.im.music;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Im on 06-12-2017.
 */

//Model Class to Get and Set data for DetailDialogFragment.
@Table(database = Appdatabase.class)
public class SongDetails extends BaseModel {

    @PrimaryKey // at least one primary key required
            int id;

    @Column
    String name;
    @Column
    String artist;
    @Column
    String duration;
    @Column
    String album;
    @Column
    String genre;
    @Column
    String bitrate;
    @Column
    String path;
    @Column
    String albumArt;
    @Column
    String title;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getDuration() {
        return duration;
    }

    public String getAlbum() {
        return album;
    }

    public String getGenre() {
        return genre;
    }

    public String getBitrate() {
        return bitrate;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(String albumArt) {
        this.albumArt = albumArt;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

