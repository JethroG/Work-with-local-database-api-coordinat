package com.energycompany.network;

import android.content.Context;
import android.util.Log;

import java.math.BigInteger;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;




    public class Network {

        private static final String KEY = "h477tHBGYYFGWyg83778";
        private static final String AUTH_URL = "------------------";
        private static final String SEND_LOCATION_URL = "-----------------------";


        private OkHttpClient client;


        public Network() {
            System.out.println("CLIENT INITIALIZATION");
            client = new OkHttpClient.Builder()
                    .connectTimeout(500, TimeUnit.SECONDS)
                    .writeTimeout(500, TimeUnit.SECONDS)
                    .readTimeout(500, TimeUnit.SECONDS)
                    .build();
        }

        //Метод перевірки наявності інтернет з'єднання
        public static boolean isOnline(Context c){
            try {
                InetAddress ipAddr = InetAddress.getByName("google.com");
                return !ipAddr.equals("");
            } catch (Exception e) {
                return false;
            }
        }
        public String sendData(String login, String pass){
            pass=md5(pass);
            String resp = "INIT";
            String summ = md5(login+pass+KEY);
            FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("login", login)
                    .add("password",pass)
                    .add("summ", summ);
            RequestBody formBody = formBuilder.build();
            Request request = new Request.Builder().url(AUTH_URL)
                    .post(formBody)
                    .build();
            Response response;
            try {
                response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                }
                resp = response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return  resp;
        }
        public String sendLocation(String login, double lat, double lng){
            String resp = "INIT";
            long date = System.currentTimeMillis()/1000;
            String summ = md5(login+lat+lng+date+KEY);
            FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("login", login)
                    .add("lat", String.valueOf(lat))
                    .add("lng", String.valueOf(lng))
                    .add("date", date+"")
                    .add("summ", summ);

            Log.w("lat", String.valueOf(lat));
            Log.w("lng", String.valueOf(lng));
            Log.w("dat", date+ "");

            RequestBody formBody = formBuilder.build();
            Request request = new Request.Builder().url(SEND_LOCATION_URL)
                    .post(formBody)
                    .build();
            Response response;
            try {
                response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                }
                resp = response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return  resp;
        }

        //Метод MD5 хешування строки
        public static String md5(String encTarget){
            MessageDigest mdEnc = null;
            try {
                mdEnc = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                System.out.println("Exception while encrypting to md5");
                e.printStackTrace();
            } // Encryption algorithm
            mdEnc.update(encTarget.getBytes(), 0, encTarget.length());
            String md5 = new BigInteger(1, mdEnc.digest()).toString(16);
            while ( md5.length() < 32 ) {
                md5 = "0"+md5;
            }
            return md5;
        }

    }

