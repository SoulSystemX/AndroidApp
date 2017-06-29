package com.cet325.bg69yl;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.cet325.bg69yl.model.DetailData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Illug on 18/12/2016.
 */
public class UpdateRecord extends AppCompatActivity{

    private CursorAdapter cursorAdapter;
    private static final String AUTHORITY = "com.cet325.bg69yl";
    private static final String BASE_PATH = "placestovisit";
    private static final String FULL_PATH = "content://" + AUTHORITY + "/" + BASE_PATH;
    public static final Uri CONTENT_URI = Uri.parse(FULL_PATH);

    // Constant to identify the requested operation
    private static final int PLACETOVISIT = 1;
    private static final int PTV_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY,BASE_PATH, PLACETOVISIT);
        uriMatcher.addURI(AUTHORITY,BASE_PATH + "/#",PTV_ID);
    }

    DetailData detailData;

    private EditText NameET;
    private EditText LocationET;
    private EditText PriceET;
    private EditText DescriptionET;
    private EditText GeolocationET;

    private Button updatePlaceButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_record);

        updatePlaceButton = (Button) findViewById(R.id.updatePlaceButton);

        Long id = 0L;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getLong("ID");
        }

        SetupActivity(id.intValue());
        Toast.makeText(getApplicationContext(), "ID: " + id , Toast.LENGTH_SHORT).show();


        updatePlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long value = 0L;
                int id = 0;
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    value = extras.getLong("ID");
                    id = value.intValue();
                }

                UpdateActivity(id);

                Toast.makeText(getApplicationContext(), "Data Updated", Toast.LENGTH_SHORT).show();
            }
        });

    }
//setup fields with preexisting data
    private void SetupActivity(int ID){
        Cursor cursor =  getApplicationContext().getContentResolver().query(CONTENT_URI, DBOpenHelper.ALL_COLUMNS, DBOpenHelper.PTV_ID + " = " + ID, null,null);

        int preloaded = 0;

        if (cursor.moveToFirst()){
            do
            {
                int path = cursor.getInt(17);
                preloaded = path;
                //Log.e("QUERY Data", path);
            } while (cursor.moveToNext());
        }
        detailData = DBOpenHelper.getDetail(ID, cursor);
        cursor.close();

        NameET = (EditText) findViewById(R.id.NameET);
        NameET.setText(detailData.name);

        LocationET = (EditText) findViewById(R.id.LocationET);
        LocationET.setText(detailData.location);

        PriceET = (EditText) findViewById(R.id.PriceET);
        PriceET.setText(String.valueOf(detailData.price));

        DescriptionET = (EditText) findViewById(R.id.DescriptionET);
        DescriptionET.setText(detailData.description);

        GeolocationET = (EditText) findViewById(R.id.GeolocationET);
        GeolocationET.setText(detailData.geolocation);

        if(preloaded != 0)
        {
            NameET.setEnabled(false);
            LocationET.setEnabled(false);
            PriceET.setEnabled(false);
            DescriptionET.setEnabled(false);
            GeolocationET.setEnabled(false);
            updatePlaceButton.setEnabled(false);
            Toast.makeText(this,"You cannot edit this option",Toast.LENGTH_LONG).show();
        }
    }
//updates data on the database.
    private void UpdateActivity(int ID){
            ContentValues values = new ContentValues();
            values.put(DBOpenHelper.PTV_NAME, NameET.getText().toString());
            values.put(DBOpenHelper.PTV_LOCATION,NameET.getText().toString());
            values.put(DBOpenHelper.PTV_DESCRIPTION,NameET.getText().toString());
            values.put(DBOpenHelper.PTV_GEOLOCATION,NameET.getText().toString());
            values.put(DBOpenHelper.PTV_PRICE,Double.valueOf(PriceET.getText().toString()));
            Toast.makeText(this,"Values updated",Toast.LENGTH_SHORT).show();

        getContentResolver().update(CONTENT_URI, values, DBOpenHelper.PTV_ID + " = " + ID ,null);

    }

}
