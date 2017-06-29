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
 * class to handle showing the detail data screen, also provides features for changing the currency
 * used for the price
 */
public class PlacesToVisitD extends AppCompatActivity{

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

    private TextView placeTitle;
    private TextView description;
    private TextView geolocation;
    private TextView curSymbol;
    private TextView price;
    private RatingBar rank;

    private EditText notes;
    private Switch planToVisit;
    private Switch visited;
    private EditText pDate;
    private EditText pTime;
    private EditText vDate;
    private EditText vTime;

    private Button saveDetails;

    Long id;

    @Override
    protected void onResume()
    {
        super.onResume();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getLong("ID");
        }
        SetupActivity(id.intValue());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placestovisit_d);
        saveDetails = (Button) findViewById(R.id.SaveDetailsB);

        id = 0L;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getLong("ID");
        }

        SetupActivity(id.intValue());
        //Toast.makeText(getApplicationContext(), "ID: " + id , Toast.LENGTH_SHORT).show();


        saveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateActivity();

                Toast.makeText(getApplicationContext(), "Data Updated", Toast.LENGTH_SHORT).show();
            }
        });



        planToVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(planToVisit.isChecked()){
                    visited.setEnabled(true);
                    vDate.setEnabled(true);
                    vTime.setEnabled(true);
                }
                else{
                    visited.setEnabled(false);
                    vDate.setEnabled(false);
                    vTime.setEnabled(false);
                }

            }
        });

        visited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat date = new SimpleDateFormat("dd/MM/yy");
                Date builtDate = new Date();

                SimpleDateFormat time = new SimpleDateFormat("HH:mm");
                Date builtTime = new Date();

                if(visited.isChecked()){
                    vDate.setEnabled(true);
                    vTime.setEnabled(true);
                    vDate.setText(date.format(builtDate).toString());
                    vTime.setText(time.format(builtTime).toString());
                }
                else{
                    vDate.setText(null);
                    vTime.setText(null);
                    vDate.setEnabled(false);
                    vTime.setEnabled(false);
                }
            }
        });

    }
//peforms intial screen setup and data gathering
    private void SetupActivity(int ID){
        Cursor cursor =  getApplicationContext().getContentResolver().query(CONTENT_URI, DBOpenHelper.ALL_COLUMNS, DBOpenHelper.PTV_ID + " = " + ID, null,null);
        detailData = DBOpenHelper.getDetail(ID, cursor);

        placeTitle = (TextView) findViewById(R.id.PlaceNameTV);
        placeTitle.setText(detailData.name);

        description = (TextView) findViewById(R.id.descTV);
        description.setText(detailData.description);

        geolocation = (TextView) findViewById(R.id.geolocTV);
        geolocation.setText(detailData.geolocation);

        rank = (RatingBar) findViewById(R.id.ratingBar);
        rank.setRating(detailData.rank);

        pDate = (EditText) findViewById(R.id.PDateET);
        pTime = (EditText) findViewById(R.id.PTimeET);
        vDate = (EditText) findViewById(R.id.VdateET);
        vTime = (EditText) findViewById(R.id.VTimeET);

        visited = (Switch) findViewById(R.id.VisitedSW);
        planToVisit = (Switch) findViewById(R.id.planToVisitSW);

        if(detailData.datePlanned.equals("null") || detailData.timePlanned.equals("null") ){
            planToVisit.setChecked(false);
            visited.setEnabled(false);
            vDate.setEnabled(false);
            vTime.setEnabled(false);
        }
        else{
            planToVisit.setChecked(true);
        }

        if(detailData.dateVisited.equals("null") || detailData.timeVisited.equals("null") ){
            visited.setChecked(false);
        }
        else{
            visited.setChecked(true);
        }

        notes = (EditText) findViewById(R.id.notesET);
        notes.setText(String.valueOf( detailData.notes));


        String tempPDate = String.valueOf( detailData.datePlanned);
        if(tempPDate.equals("null"))
            pDate.setText(null);
        else
            pDate.setText(tempPDate);

        String tempPTime = String.valueOf( detailData.timePlanned);
        if(tempPTime.equals("null"))
            pTime.setText(null);
        else
            pTime.setText(tempPTime);

        String tempvDate = String.valueOf( detailData.dateVisited);
        if(tempvDate.equals("null"))
            vDate.setText(null);
        else {
            vDate.setText(tempvDate);
        }

        String tempvTime = String.valueOf( detailData.timeVisited);
        if(tempvTime.equals("null"))
            vTime.setText(null);
        else
            vTime.setText(tempvTime);

        setCurrencyGBP();
        cursor.close();
    }
//updates information entered by the user to the database
    private void UpdateActivity(){
            ContentValues values = new ContentValues();

            values.put(DBOpenHelper.PTV_NOTES, notes.getText().toString());
            values.put(DBOpenHelper.PTV_DATEPLANNED, pDate.getText().toString());
            values.put(DBOpenHelper.PTV_TIMEPLANNED, pTime.getText().toString());
            values.put(DBOpenHelper.PTV_DATEVISITED, vDate.getText().toString());
            values.put(DBOpenHelper.PTV_TIMEVISITED, vTime.getText().toString());

            Toast.makeText(this,"Values updated",Toast.LENGTH_SHORT).show();

        getContentResolver().update(CONTENT_URI, values, DBOpenHelper.PTV_ID + " = " + id ,null);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.currency, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.YEN:
                setCurrencyJPY();
                return true;
            case R.id.GBP:
              setCurrencyGBP();
                return true;
            case R.id.EUR:
                setCurrencyEUR();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
/**
 * 3 methods below change the currency and convert it depending on selection
 * */
    void setCurrencyJPY(){

        curSymbol = (TextView) findViewById(R.id.curSymbol);
        price = (TextView) findViewById(R.id.priceTV);
        BigDecimal bd;
        double priceConverted;

        curSymbol.setText("¥");
        priceConverted = (detailData.price * detailData.JPY);
        bd = new BigDecimal(priceConverted);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        priceConverted = bd.doubleValue();
        price.setText(String.valueOf(priceConverted));
    }

    void setCurrencyGBP(){

        curSymbol = (TextView) findViewById(R.id.curSymbol);
        price = (TextView) findViewById(R.id.priceTV);
        BigDecimal bd;
        double priceConverted;

        curSymbol.setText("£");
        priceConverted = (detailData.price * detailData.GBP);
        bd = new BigDecimal(priceConverted);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        priceConverted = bd.doubleValue();
        price.setText(String.valueOf(priceConverted));
    }

    void setCurrencyEUR(){

        curSymbol = (TextView) findViewById(R.id.curSymbol);
        price = (TextView) findViewById(R.id.priceTV);
        BigDecimal bd;
        double priceConverted;

        curSymbol.setText("€");
        priceConverted = detailData.price;
        bd = new BigDecimal(priceConverted);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        priceConverted = bd.doubleValue();
        price.setText(String.valueOf(priceConverted));
    }
}
