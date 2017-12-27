package com.example.im.music;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

import timber.log.Timber;


/**
 * Created by Im on 07-12-2017.
 */

public class DbflowApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

//        Timber.plant(new Timber.DebugTree());
        FlowManager.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        FlowManager.destroy();
    }
}
