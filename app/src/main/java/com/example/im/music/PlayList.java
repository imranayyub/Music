package com.example.im.music;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Im on 14-12-2017.
 */
@Table(database = Appdatabase.class)
public class PlayList extends BaseModel {

    @PrimaryKey // at least one primary key required
            int id;

    @Column
    @SerializedName("song_name")
    @Expose
    private String name;

    @Column
    @SerializedName("artist_name")
    @Expose
    private String artist;

    @Column
    @SerializedName("duration")
    @Expose
    private String duration;

    @Column
    @SerializedName("bitrate")
    @Expose
    private String bitrate;

    @Column
    @SerializedName("song_title")
    @Expose
    private String title;

    @Column
    @SerializedName("path")
    @Expose
    private String path;

    @Column
    @SerializedName("album_art")
    @Expose
    private String albumArt;

    @Column
    @SerializedName("album")
    @Expose
    private String album;

    @Column
    @SerializedName("genre")
    @Expose
    private String genre;

//    public PlayList(int id, String name, String artist, String duration, String bitrate, String title, String path, String albumArt, String album, String genre) {
//        this.id = id;
//        this.name = name;
//        this.artist = artist;
//        this.duration = duration;
//        this.bitrate = bitrate;
//        this.title = title;
//        this.path = path;
//        this.albumArt = albumArt;
//        this.album = album;
//        this.genre = genre;
//    }

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