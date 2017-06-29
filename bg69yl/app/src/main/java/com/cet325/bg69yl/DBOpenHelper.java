package com.cet325.bg69yl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.cet325.bg69yl.model.DetailData;

/**
 * Helper class that creates database and manages its version
 *
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    //Constants for db name and version
    private static final String DATABASE_NAME = "placestovisit.db";
    private static final int DATABASE_VERSION = 1;

    //Constants for table and columns
    public static final String TABLE_PTV = "placestovisit";
    public static final String PTV_ID = "_id";
    public static final String PTV_NAME = "ptvName";
    public static final String PTV_LOCATION = "ptvLocation";
    public static final String PTV_DESCRIPTION = "ptvDescription";
    public static final String PTV_IMAGE = "ptvImage";
    public static final String PTV_GEOLOCATION = "ptvGeoLocation";
    public static final String PTV_PRICE = "ptvPrice";
    public static final String PTV_RANK = "ptvRank";

    public static final String PTV_NOTES = "ptvNotes";
    public static final String PTV_DATEPLANNED = "ptvDatePlanned";
    public static final String PTV_TIMEPLANNED = "ptvTimePlanned";
    public static final String PTV_DATEVISITED = "ptvDateVisited";
    public static final String PTV_TIMEVISITED = "ptvTimeVisited";
    public static final String PTV_GBPCOST = "ptvGBPCost";
    public static final String PTV_EURCOST = "ptvEURCost";
    public static final String PTV_YENCOST = "ptvYENCost";
    public static final String PTV_FEE = "ptvFee";

    public static final String PTV_PRELOAD = "ptvPreload";

    public static final String PTV_CREATED_ON = "ptvCreationTimeStamp";

    public static final String[] ALL_COLUMNS =
            {       PTV_ID,
                    PTV_NAME,
                    PTV_LOCATION,
                    PTV_DESCRIPTION,
                    PTV_IMAGE,
                    PTV_GEOLOCATION,
                    PTV_PRICE,
                    PTV_RANK,
                    PTV_NOTES,
                    PTV_DATEPLANNED,
                    PTV_TIMEPLANNED,
                    PTV_DATEVISITED,
                    PTV_TIMEVISITED,
                    PTV_GBPCOST,
                    PTV_EURCOST,
                    PTV_YENCOST,
                    PTV_FEE,
                    PTV_PRELOAD,
                    PTV_CREATED_ON};

    //Create Table
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_PTV + " (" +
                    PTV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PTV_NAME + " TEXT, " +
                    PTV_LOCATION + " TEXT, " +
                    PTV_DESCRIPTION + " TEXT, " +
                    PTV_IMAGE + " TEXT, " +
                    PTV_GEOLOCATION + " TEXT, " +
                    PTV_PRICE + " DOUBLE, " +
                    PTV_RANK + " INTEGER, " +

                    PTV_NOTES + " TEXT, " +
                    PTV_DATEPLANNED + " TEXT, " +
                    PTV_TIMEPLANNED + " TEXT, " +
                    PTV_DATEVISITED + " TEXT, " +
                    PTV_TIMEVISITED + " TEXT, " +
                    PTV_GBPCOST + " INTEGER, " +
                    PTV_EURCOST + " INTEGER, " +
                    PTV_YENCOST + " INTEGER, " +
                    PTV_FEE + " INTEGER, " +
                    PTV_PRELOAD + " INTEGER, " +

                    PTV_CREATED_ON + " TEXT default CURRENT_TIMESTAMP" +
                    ")";



    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_PTV);
        onCreate(sqLiteDatabase);
    }

    /**
     * Another helper method that simply make a detaildata object and populates it with data from the database
     * placed here for global scope and because its a helper method.
     *
     */

    public static DetailData getDetail(int id,Cursor cursor){

        DetailData dData = null;
        if (cursor != null && cursor.moveToFirst()) {

            // build object
            dData = new DetailData();
            dData.id = Integer.parseInt(cursor.getString(0));
            dData.name = cursor.getString(1);
            dData.location = cursor.getString(2);

            dData.description = cursor.getString(3);
            dData.geolocation = cursor.getString(5);
            dData.price = cursor.getDouble(6);
            dData.rank = cursor.getInt(7);

            dData.notes = cursor.getString(8);
            dData.datePlanned = cursor.getString(9);
            dData.timePlanned = cursor.getString(10);
            dData.dateVisited = cursor.getString(11);
            dData.timeVisited = cursor.getString(12);

            dData.GBP = cursor.getDouble(13);
            dData.JPY = cursor.getDouble(15);

            Log.d("Get Detail(" + id + ")", dData.toString());

        }
        cursor.close();
        return dData;
    }
}


