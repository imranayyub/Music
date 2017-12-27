package com.example.im.music;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Im on 25-12-2017.
 */

public class Songinfo {
    @SerializedName("song_name")
    @Expose
    private String name;

    @SerializedName("artist_name")
    @Expose
    private String artist;

    @SerializedName("duration")
    @Expose
    private String duration;

    @SerializedName("bitrate")
    @Expose
    private String bitrate;

    @SerializedName("song_title")
    @Expose
    private String title;

    @SerializedName("path")
    @Expose
    private String path;

    @SerializedName("album_art")
    @Expose
    private String albumArt;

    @SerializedName("album")
    @Expose
    private String album;

    @SerializedName("genre")
    @Expose
    private String genre;


    public Songinfo(String name, String artist, String duration, String bitrate, String title, String path, String albumArt, String album, String genre) {
        this.name = name;
        this.artist = artist;
        this.duration = duration;
        this.bitrate = bitrate;
        this.title = title;
        this.path = path;
        this.albumArt = albumArt;
        this.album = album;
        this.genre = genre;
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

    public String getBitrate() {
        return bitrate;
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

    public String getAlbumArt() {
        return albumArt;
    }

    public String getGenre() {
        return genre;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setAlbumArt(String albumArt) {
        this.albumArt = albumArt;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}
