package com.cet325.bg69yl;

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/*
* Class that connects to a given url, reads and stores the given data from the connected url to a stringbuilder
* this finished buffer is then returned to the caller with the data from the JSON feed
* */

public class CurrencyConversionHttpClient {

    private static String BASE_URL = "http://api.fixer.io/latest?symbols=GBP,JPY";

    public String getCurrencyData() {
        HttpURLConnection con = null ;
        InputStream is = null;
        String urlString = "";

        try {
            urlString = BASE_URL;
            Log.d("urlString",urlString);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            con = (HttpURLConnection) (new URL(urlString)).openConnection();
            con.setRequestMethod("GET");
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
}

