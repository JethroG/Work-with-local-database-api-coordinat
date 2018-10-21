package com.energycompany.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;



public class Prefererenses {

    private static final String PREF = "vod_geo";

    private static final String LOGIN = "login";
    private static final String PASS = "pass";

    public static Pair<String, String> getAuth(Context c){
        SharedPreferences sharedPreferences = c.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        return new Pair<>(sharedPreferences.getString(LOGIN, ""), sharedPreferences.getString(PASS, ""));
    }

    public static void setAuth(Context c, Pair<String, String> auth){
        SharedPreferences sharedPreferences = c.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOGIN, auth.first);
        editor.putString(PASS, auth.second);
        editor.commit();
    }

}
