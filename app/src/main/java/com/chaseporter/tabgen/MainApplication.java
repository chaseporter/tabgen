package com.chaseporter.tabgen;

import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {
    private static Context context;
    private AppComponent appComponent;

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(getExternalFilesDir(null).getAbsolutePath())).build();
    }

    public static Context getAppContext() {
        return context;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
