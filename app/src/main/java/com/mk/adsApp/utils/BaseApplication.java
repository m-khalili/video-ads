package com.mk.adsApp.utils;

import android.app.Application;

public class BaseApplication extends Application {
    public static BaseApplication baseApp;
    @Override
    public void onCreate() {
        super.onCreate();
        baseApp= this;

    }
}
