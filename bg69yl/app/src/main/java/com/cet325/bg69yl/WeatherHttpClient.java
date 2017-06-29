package com.cet325.bg69yl;

/**
 * Created by Illug on 03/01/2017.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/*
* Class that connects to a given url, reads and stores the given data from the connected url to a stringbuilder
* this finished buffer is then returned to the caller with the data from the JSON feed
* */

public class WeatherHttpClient {

    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String IMG_URL = "http://openweathermap.org/img/w/";
    private static String API_KEY = "1c60573cf36b73c22ef932ca14a4c0bd";

    public String getWeatherData(String location) {
        HttpURLConnection con = null ;
        InputStream is = null;
        String urlString = "";

        try {
            // create URL for specified city and metric units (Celsius)
            urlString = BASE_URL + URLEncoder.encode(location, "UTF-8") + "&units=metric&APPID=" + API_KEY;
            Log.d("urlString",urlString);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            con = (HttpURLConnection) (new URL(urlString)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            int response = con.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                // Let's read the response
                StringBuilder buffer = new StringBuilder();
                is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = br.readLine()) != null) {
                    Log.d("JSON-line",line);
                    buffer.append(line + "\r\n");
                }
                is.close();
                con.disconnect();
                Log.d("JSON",buffer.toString());
                return buffer.toString();
            }
            else {
                Log.d("HttpURLConnection","Unable to connect");
                return null;
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Exception e) {}
            try { con.disconnect(); } catch(Exception e) {}
        }

        return null;
    }

    /*
    * The get image class connects to the given url and gets a image,
    * this is then returned to the calling class
    * */

    public Bitmap getImage(String code) {

        URL url = null;
        Bitmap bmp = null;
        try {
            url = new URL(IMG_URL + code + ".png");
            Log.d("urlImage",IMG_URL + code + ".png");
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return bmp;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

