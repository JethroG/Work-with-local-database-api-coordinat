package com.energycompany.app;


import android.app.Application;
import android.content.Context;

public class MyApplicationCustom extends Application{
    private static Context context;

    public static String login = "";
    @Override
    public void onCreate() {
        context=this;
        MyApplicationCustom.context=getApplicationContext();
    }
    public static Context getAppContext() {
        return MyApplicationCustom.context;
    }

}

