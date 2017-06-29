package com.cet325.bg69yl;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by Illug on 01/01/2017.
 */
public class AddPlace extends AppCompatActivity {

// Assign all edit texts to variables for sending to database.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_place);

        Button tempInsertRecords = (Button) findViewById(R.id.addPlaceButton);

        tempInsertRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ((EditText)findViewById(R.id.NameET)).getText().toString();
                String location = ((EditText)findViewById(R.id.LocationET)).getText().toString();

                String description = "No description provided";
                description = ((EditText)findViewById(R.id.DescriptionET)).getText().toString();

                String geolocation = "No geolocation data";
                geolocation = ((EditText)findViewById(R.id.GeolocationET)).getText().toString();
                double price = 0.0;
                price = Double.parseDouble(((EditText)findViewById(R.id.PriceET)).getText().toString());

                if(name != null && location != null)
                insertPTV(name,location,description, "image", geolocation, price, 0);
                else
                    Toast.makeText(getApplicationContext(), "Information missing" ,Toast.LENGTH_SHORT).show();
                //restartLoader();

                finish();

            }
        });

    }
//Standard function for building a new database entry containing all required information and standard currency conversion rates. once complete will save data straight to database.
    public void insertPTV(String ptv_Name,String ptv_Location,String ptv_Description,String ptv_Image,String ptv_GeoLocation,double ptv_Price,int ptv_Rank) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.PTV_NAME,ptv_Name);
        values.put(DBOpenHelper.PTV_LOCATION,ptv_Location);
        values.put(DBOpenHelper.PTV_DESCRIPTION,ptv_Description);
        values.put(DBOpenHelper.PTV_IMAGE,ptv_Image);
        values.put(DBOpenHelper.PTV_GEOLOCATION,ptv_GeoLocation);
        values.put(DBOpenHelper.PTV_PRICE,ptv_Price);
        values.put(DBOpenHelper.PTV_RANK, ptv_Rank);
        values.put(DBOpenHelper.PTV_IMAGE,"noimage");
        values.put(DBOpenHelper.PTV_NOTES,"");
        values.put(DBOpenHelper.PTV_DATEPLANNED,"null");
        values.put(DBOpenHelper.PTV_TIMEPLANNED,"null");
        values.put(DBOpenHelper.PTV_DATEVISITED,"null");
        values.put(DBOpenHelper.PTV_TIMEVISITED,"null");
        values.put(DBOpenHelper.PTV_GBPCOST,"0.87");
        values.put(DBOpenHelper.PTV_EURCOST,"1");
        values.put(DBOpenHelper.PTV_YENCOST,"122.19");
        values.put(DBOpenHelper.PTV_FEE,"");
        values.put(DBOpenHelper.PTV_PRELOAD, 0);

        Uri contactUri  = getContentResolver().insert(DataProvider.CONTENT_URI,values);
        Toast.makeText(this,"Place added " + ptv_Name,Toast.LENGTH_LONG).show();
    }




    }
