package com.example.im.music;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.width;

/**
 * Created by Im on 12-12-2017.
 */

public class CustomAdapterforList extends ArrayAdapter<String> {
    private final Activity context;
    private final ArrayList<String> song;
    private final ArrayList<String> imageId;

    public CustomAdapterforList(Activity context, ArrayList<String> imageId, ArrayList<String> song) {
        super(context, R.layout.listview, song);
        this.context = context;
        this.imageId = imageId;
        this.song = song;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview, null, true);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);

        if (imageId.get(position) == null) {
            imageView.setImageResource(R.drawable.music1);
        } else {
            int width = 120, height = 120;
            byte[] imag = Base64.decode(String.valueOf(imageId.get(position)), Base64.DEFAULT);
            try {
                Bitmap bmp = BitmapFactory.decodeByteArray(imag, 0, imag.length);
//
                imageView.setImageBitmap(Bitmap.createScaledBitmap(bmp, width,
                        height, false));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception ", e.toString());
            }

//            imageView.setImageBitmap(imageId.get(position));
            //imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageId,0,imageId.length));
        }
        TextView textView = (TextView) rowView.findViewById(R.id.name);
        textView.setText(song.get(position));
        return rowView;
    }
}
