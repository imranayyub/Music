package com.example.im.music.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.im.music.R;
import com.example.im.music.activities.HomeActivity;
import com.example.im.music.adapters.CustomAdapterforList;
import com.example.im.music.interfaces.ApiInterface;
import com.example.im.music.models.Songinfo;
import com.example.im.music.services.MyService;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.im.music.activities.HomeActivity.Activated;
import static com.example.im.music.activities.HomeActivity.play;

/**
 * Created by Im on 26-12-2017.
 */

public class OnlinePLaylistFragment extends Fragment {
    ListView onlinePlayList;
    String name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.onlineplaylist, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onlinePlayList = (ListView) getActivity().findViewById(R.id.onlinePlaylist);
        displayOnlinePlaylist();

    }

    @Override
    public void onCreateContextMenu(final ContextMenu menu, final View v, final ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.clear();
        menu.setHeaderTitle(name);
        menu.add(0, v.getId(), 0, "Remove from Playlist");//groupId, itemId, order, title
        menu.add(0, v.getId(), 0, "Cancel");

    }

    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        if (item.getTitle() == "Remove from Playlist") {

            Toast.makeText(getActivity(), "Removing from Playlist..", Toast.LENGTH_LONG).show();
            removeFromPlaylist();


        } else if (item.getTitle() == "Cancel") {

        }
        return true;
    }

    void displayOnlinePlaylist() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())     //Using GSON to Convert JSON into POJO.
                .build();
        ApiInterface apiService = retrofit.create(ApiInterface.class);
        try {
            Call<List<Songinfo>> call = apiService.getDetails();   //Making Api call using Call Retrofit method that sends a request to a webserver and returns a response.
            call.enqueue(new Callback<List<Songinfo>>() {  //enqueue  send request asynchronously and notify it response or any error occurs while talking to server.
                //In case of Success and server responds.
                @Override
                public void onResponse(Call<List<Songinfo>> call, Response<List<Songinfo>> response) {
                    List<Songinfo> songinfo = response.body(); //storing response body in songinfo.
                    final ArrayList<String> songs = new ArrayList<>();
                    final ArrayList<String> songArt = new ArrayList<>();
                    int i = 0;
                    for (Songinfo m : songinfo) {
                        Log.d("SongName : ", m.getName());
                        Log.d("Album: ", m.getAlbum());
                        Log.d("Artist : ", m.getArtist());
                        songs.add(i, m.getName());
                        songArt.add(i, m.getAlbumArt());
                        i++;

                    }
                    CustomAdapterforList adapter = new CustomAdapterforList(getActivity(), songArt, songs);

                    // Assign adapter to ListView
                    onlinePlayList.setAdapter(adapter);
                    registerForContextMenu(onlinePlayList);
//   Checks If item on ListView is Clicked And performs Required Function.

                    onlinePlayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            name = (String) adapterView.getItemAtPosition(position);
//                            Toast.makeText(getActivity(), "Playing : " + name, Toast.LENGTH_SHORT).show();
                            String name = (String) adapterView.getItemAtPosition(position);
                            //Intent to Start Service(Service to play Music in Background).


                            Bundle bundle = new Bundle();
                            Intent intent = new Intent(getActivity(), MyService.class);
                            bundle.putInt("position", position);
                            bundle.putString("songname", name);
                            bundle.putInt("search", 1);
                            intent.putExtras(bundle);
                            getActivity().startService(intent);
                        }
                    });


                    onlinePlayList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                            name = (String) adapterView.getItemAtPosition(position);
                            return false;
                        }
                    });


                    Toast.makeText(getActivity(), "Online Songs", Toast.LENGTH_SHORT).show();
                }

                //In case of Failure i.e., couldnot connect to server because of some error.
                @Override
                public void onFailure(Call<List<Songinfo>> call, Throwable t) {
                    // Log error here since request failed
                    Log.e("OnlinePlaylistFragmet", t.toString());
//                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void removeFromPlaylist() {
        //posting to the server.
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())     //Using GSON to Convert JSON into POJO.
                .build();
        //Passing Interface to create an implementation.
        ApiInterface apiService = retrofit.create(ApiInterface.class);
        try {
            Songinfo songinfo = new Songinfo(name, "", "", "", "", "", "", "", "");
            apiService.delete(songinfo).enqueue(new Callback<JsonObject>() {
                //        apiService.savePost(userName, password, phone).enqueue(new Callback<playList>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
//                            showResponse(
                        Toast.makeText(getActivity(), response.body().toString(), Toast.LENGTH_SHORT).show();
                        displayOnlinePlaylist();

                    } else if (response.code() == 500) {
                        Toast.makeText(getActivity(), "Some Error Occured ", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {
                        Log.d("Error code : ", "" + response.code());
                    } else {
                        Log.d("Error code :", "" + response.code());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    t.printStackTrace();
                    Log.e("here", "Unable to submit post to API.");
                    Toast.makeText(getActivity(), "Failed ", Toast.LENGTH_SHORT).show();

                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

