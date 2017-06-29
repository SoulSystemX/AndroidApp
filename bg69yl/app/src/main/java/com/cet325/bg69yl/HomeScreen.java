package com.cet325.bg69yl;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cet325.bg69yl.model.Currency;
import com.cet325.bg69yl.model.Weather;
import org.json.JSONException;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener{
    ImageButton imageButton1;
    ImageButton imageButton3;

    private TextView cityText;
    private TextView condDesc;
    private TextView temperature;
    private ImageView imgView;

    private static final String AUTHORITY = "com.cet325.bg69yl";
    private static final String BASE_PATH = "placestovisit";
    private static final String FULL_PATH = "content://" + AUTHORITY + "/" + BASE_PATH;
    public static final Uri CONTENT_URI = Uri.parse(FULL_PATH);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        imageButton1 = (ImageButton) findViewById(R.id.imageButton);
        imageButton1.setOnClickListener(this);

        imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(this);

        String city = "Tokyo, JP";
        cityText = (TextView) findViewById(R.id.cityTextTV);
        condDesc = (TextView) findViewById(R.id.condDescTV);
        temperature = (TextView) findViewById(R.id.tempTV);
        imgView = (ImageView) findViewById(R.id.condIconIV);

        //Get the weather and currency from the internet
        JSONWeatherTask task = new JSONWeatherTask();
        task.execute(city);
        String symbols = "";
        JSONCurrencyTask curTask = new JSONCurrencyTask();
        curTask.execute(symbols);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.imageButton:
                Intent myIntent = new Intent(v.getContext(), PlacesToVisitM.class);
                startActivity(myIntent);
                break;
            case R.id.imageButton3:
                //click button to refresh the weather
                String city = "Tokyo, JP";
                JSONWeatherTask task = new JSONWeatherTask();
                task.execute(city);
                break;
            default:
                throw new RuntimeException("Unknown button ID");
        }
    }
//Get the weather data
    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            Log.d("data", params[0]);
            Weather weather = new Weather();
            String data = ((new WeatherHttpClient()).getWeatherData(params[0]));
            if (data != null) {
                try {
                    weather = JSONWeatherParser.getWeather(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return weather;
            }
            else return null;
        }

// apply the weather to the fields in the layout
        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
            if (weather!=null) {

                if (weather.bmp!=null) imgView.setImageBitmap(weather.bmp);
                cityText.setText("Tokyo, Japan");
                condDesc.setText(weather.currentCondition.getCondition() + " (" + weather.currentCondition.getDesc() + ")");
                temperature.setText(" " + Math.round((weather.temperature.getTemp())) + "°C");
                Toast.makeText(getApplicationContext(),"Weather updated",Toast.LENGTH_LONG).show();

            }
            else
            {
                Toast.makeText(getApplicationContext(),"Unable to retrieve weather data",Toast.LENGTH_LONG).show();
                cityText.setText("Touch get Weather");
            }
        }
    }
    //Get the currency data

    private class JSONCurrencyTask extends AsyncTask<String, Void, Currency> {

        @Override
        protected Currency doInBackground(String... params) {
            Log.d("data", params[0]);
            Currency currency = new Currency();
            //String data = ((new CurrencyConversionHttpClient()).getCurrencyData(params[0]));
            String data = ((new CurrencyConversionHttpClient()).getCurrencyData());
            if (data != null) {
                try {
                    currency = JSONCurrencyParser.getCurrency(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return currency;
            }
            else return null;
        }
        // gather the data and if some data has been found pass it to updatecurrency
        @Override
        protected void onPostExecute(Currency currency) {
            super.onPostExecute(currency);
            if (currency!=null) {
                UpdateCurrency(currency);
                Toast.makeText(getApplicationContext(),"Currency updated",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Unable to retrieve Currency data",Toast.LENGTH_LONG).show();
            }
        }

//Add's currency information to the database
        private void UpdateCurrency(Currency currency){
            ContentValues values = new ContentValues();
            values.put(DBOpenHelper.PTV_GBPCOST, currency.getRateGBP());
            values.put(DBOpenHelper.PTV_YENCOST, currency.getRateJPY());
            getContentResolver().update(CONTENT_URI, values, null ,null);
        }
    }
}
