package com.cet325.bg69yl;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.support.design.widget.FloatingActionButton;

/**
 * this class is the main entry to viewing all places in the database, it enables sorting and filtering
 * as well as adding new placed, deleting existing and editing entries you have made yourself
 */
public class PlacesToVisitM extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placestovisit_m);
        cursorAdapter = new PTVCursorAdaptor(this,null,0);
        final ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(cursorAdapter);
        registerForContextMenu(list);

        getLoaderManager().initLoader(0, null, this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), AddPlace.class);
                startActivity(myIntent);
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(view.getContext(), PlacesToVisitD.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });
        // Insert preload data if database is empty
        Cursor query  = getContentResolver().query(CONTENT_URI, DBOpenHelper.ALL_COLUMNS,null, null,null,null);
        if(query != null && query.getCount() == 0){
            //1
            insertPTV("Tsukiji Fish Market",
                    "Tokyo",
                    "The worlds largest Fish market, offering early auctions on many fish, early morning all the auctions start late afternoon they start filleting the fish",
                    "img1",
                    "35.662005, 139.770009",
                    0.00,
                    5,
                    1);
            //2
            insertPTV("Sumo Wrestling",
                    "Tokyo",
                    "If you happen to be in Tokyo for one of the grand tournaments you can catch some action at Ryogoku Kokugigan, the number one Sumo hall in Japan",
                    "img2",
                    "35.697040, 139.793166",
                    18.94,
                    5,
                    1);
            //3
            insertPTV("Meiji Shrine",
                    "Tokyo",
                    "Dedicated to the 19th century emperor, this is Tokyo's more famous shrine. It is serene and not as flashy as typical places of worship.",
                    "img3",
                    "35.674789, 139.699526",
                    0.00,
                    5,
                    1);
            //4
            insertPTV("Yoyogi Park",
                    "Tokyo",
                    "As living space in the city is tight, Yoyogi park draws all sorts of club meetings from horn players to rockabiliy hip-hop dancers.",
                    "img4",
                    "35.671692, 139.695610",
                    0.00,
                    5,
                    1);
            //5
            insertPTV("City Views",
                    "Tokyo",
                    "There's a lot going on around the Roppongi hills complex, loads of shops and cafe's but you should start at the giant spider sculpture.",
                    "img5",
                    "35.659982, 139.729029",
                    14.20,
                    5,
                    1);
            //6
            insertPTV("Shibuya Crossing",
                    "Tokyo",
                    "It would be a shame to visit Tokyo and not cross the Shibuya Crossing, when the all lights turn red you'll witness a surge of pedestrians like no other.",
                    "img6",
                    "35.659566, 139.700500",
                    0.00,
                    5,
                    1);
            //7
            insertPTV("Ebisu",
                    "Tokyo",
                    "It's very easy to run up a bill in this city through buying food, and for some of the best food you can get you should really visit Ebisu.",
                    "img7",
                    "35.647959, 139.708828",
                    37.89,
                    5,
                    1);
            //8
            insertPTV("Smash Hits",
                    "Tokyo",
                    "In Japan karaoke normally happens in a private room with room service however at smash hits you will perform on stage in front of a rowdy audience.",
                    "img8",
                    "35.650224, 139.720028",
                    23.68,
                    5,
                    1);
            //9
            insertPTV("Daimaru's Kimono",
                    "Tokyo",
                    "Before departing check the Daimaru department store, this kimono shop is not gears for tourists but you need to see the breathtaking clothing.",
                    "img9",
                    "35.650224, 139.720028",
                    56.85,
                    5,
                    1);
            //10
            insertPTV("J-World Tokyo",
                    "Tokyo",
                    "J-World Tokyo invites visitors to play in the world of Jump magazines. Enjoy attractions, shop for limited-edition merchandise, and taste dishes that appear in works of manga..",
                    "img10",
                    "35.729323, 139.720627",
                    6.5472,
                    5,
                    1);

        }
        query.close();
        cursorAdapter.notifyDataSetChanged();
    }

//Delete and edit context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String[] menuItems = {"Delete", "Edit"};
        String menuItemName = menuItems[menuItemIndex];
        String listItemName = "Options";

        if(menuItemIndex == 0) // delete
        {
            deletePTV(info.id);
            cursorAdapter.notifyDataSetChanged();
        }
        else if (menuItemIndex == 1) // edit
        {
            Intent intent = new Intent(getApplicationContext(), UpdateRecord.class);
            intent.putExtra("ID", info.id);
            startActivity(intent);
        }

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==android.R.id.list) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle("Options");
            String[] menuItems = {"Delete", "Edit"};
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }
//Insert method for preload data
    private void insertPTV(String ptv_Name,String ptv_Location,String ptv_Description,String ptv_Image,String ptv_GeoLocation,double ptv_Price,int ptv_Rank,int ptv_Preload) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.PTV_NAME,ptv_Name);
        values.put(DBOpenHelper.PTV_LOCATION,ptv_Location);
        values.put(DBOpenHelper.PTV_DESCRIPTION,ptv_Description);
        values.put(DBOpenHelper.PTV_IMAGE,ptv_Image);
        values.put(DBOpenHelper.PTV_GEOLOCATION,ptv_GeoLocation);
        values.put(DBOpenHelper.PTV_PRICE,ptv_Price);
        values.put(DBOpenHelper.PTV_RANK,ptv_Rank);
        values.put(DBOpenHelper.PTV_PRELOAD, ptv_Preload);

        values.put(DBOpenHelper.PTV_NOTES,"");
        values.put(DBOpenHelper.PTV_DATEPLANNED,"null");
        values.put(DBOpenHelper.PTV_TIMEPLANNED,"null");
        values.put(DBOpenHelper.PTV_DATEVISITED,"null");
        values.put(DBOpenHelper.PTV_TIMEVISITED,"null");
        values.put(DBOpenHelper.PTV_GBPCOST,"0.87");
        values.put(DBOpenHelper.PTV_EURCOST,"1");
        values.put(DBOpenHelper.PTV_YENCOST,"122.19");
        values.put(DBOpenHelper.PTV_FEE,"");

        Uri contactUri  = getContentResolver().insert(DataProvider.CONTENT_URI,values);
        Toast.makeText(this,"Place added " + ptv_Name,Toast.LENGTH_SHORT).show();
    }
//Delete method for use with long click context menu
    public boolean deletePTV(Long ID){

        Log.e("QUERY ID",String.valueOf( ID));

        Cursor query  = getContentResolver().query(CONTENT_URI, DBOpenHelper.ALL_COLUMNS, DBOpenHelper.PTV_ID + " = " + ID,null,null,null);

        int preloaded = 0;

        if (query.moveToFirst()){
            do
                {
                    int path = query.getInt(17);
                    preloaded = path;
                    //Log.e("QUERY Data", path);
                } while (query.moveToNext());
            }
        query.close();

        //Log.e("PRELOADED Data", preloaded);

        if(preloaded != 0)
        {
            Toast.makeText(this,"You cannot delete this option",Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            getContentResolver().delete(DataProvider.CONTENT_URI,DBOpenHelper.PTV_ID + "=?", new String[] {ID.toString()});
            Toast.makeText(this,"Deleted",Toast.LENGTH_SHORT).show();
            return true;
        }

    }

    private void restartLoader() {
        getLoaderManager().restartLoader(0,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,CONTENT_URI,null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        cursorAdapter.swapCursor(cursor);
        cursorAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        cursorAdapter.swapCursor(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.filter_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.All:
                SortAll();
                return true;
            case R.id.Favourite:
                SortFavourite();
                return true;
            case R.id.Planned:
                SortPlanned();
                return true;
            case R.id.Visited:
                SortVisited();
                return true;
            case R.id.DispAll:
                DispAll();
                return true;
            case R.id.DispFavourite:
                DispFavourite();
                return true;
            case R.id.DispPlanned:
                DispPlanned();
                return true;
            case R.id.DispVisited:
                DispVisited();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
//Sorting and filter functions below
    private void SortAll(){
        Cursor cursor = getApplicationContext().getContentResolver().query(CONTENT_URI, DBOpenHelper.ALL_COLUMNS,null, null, DBOpenHelper.PTV_RANK + " ASC");
        cursorAdapter.swapCursor(cursor);
        cursorAdapter.notifyDataSetChanged();
    }

    private void SortFavourite(){
        Cursor cursor = getApplicationContext().getContentResolver().query(CONTENT_URI, DBOpenHelper.ALL_COLUMNS,null, null, DBOpenHelper.PTV_DATEPLANNED + " OR " + DBOpenHelper.PTV_DATEVISITED + " ASC");
        cursorAdapter.swapCursor(cursor);
        cursorAdapter.notifyDataSetChanged();
    }

    private void SortPlanned(){
        Cursor cursor = getApplicationContext().getContentResolver().query(CONTENT_URI, DBOpenHelper.ALL_COLUMNS,null, null, DBOpenHelper.PTV_DATEPLANNED + " ASC");
        cursorAdapter.swapCursor(cursor);
        cursorAdapter.notifyDataSetChanged();
    }

    private void SortVisited(){
        Cursor cursor = getApplicationContext().getContentResolver().query(CONTENT_URI, DBOpenHelper.ALL_COLUMNS,null, null, DBOpenHelper.PTV_DATEVISITED + " ASC");
        cursorAdapter.swapCursor(cursor);
        cursorAdapter.notifyDataSetChanged();

    }

    private void DispAll(){
        Cursor cursor = getApplicationContext().getContentResolver().query(CONTENT_URI, DBOpenHelper.ALL_COLUMNS,null, null, null);
        cursorAdapter.swapCursor(cursor);
        cursorAdapter.notifyDataSetChanged();
    }

    private void DispFavourite(){
        Cursor cursor = getApplicationContext().getContentResolver().query(CONTENT_URI, DBOpenHelper.ALL_COLUMNS,
                                                                    DBOpenHelper.PTV_DATEPLANNED + " !="+0+" OR "
                                                                    + DBOpenHelper.PTV_DATEVISITED + "!="+0,
                                                                    null,
                                                                    null);
        cursorAdapter.swapCursor(cursor);
        cursorAdapter.notifyDataSetChanged();
    }

    private void DispPlanned(){
        Cursor cursor = getApplicationContext().getContentResolver().query(CONTENT_URI, DBOpenHelper.ALL_COLUMNS,
                DBOpenHelper.PTV_DATEPLANNED + " != " + 0,
                null,
                null);
        cursorAdapter.swapCursor(cursor);
        cursorAdapter.notifyDataSetChanged();
    }

    private void DispVisited(){
        Cursor cursor = getApplicationContext().getContentResolver().query(CONTENT_URI, DBOpenHelper.ALL_COLUMNS,
                DBOpenHelper.PTV_DATEVISITED + " != " + 0,
                null,
                null);
        cursorAdapter.swapCursor(cursor);
        cursorAdapter.notifyDataSetChanged();

    }


}
