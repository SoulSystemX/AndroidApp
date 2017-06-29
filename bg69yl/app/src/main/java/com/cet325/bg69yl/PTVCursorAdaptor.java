package com.cet325.bg69yl;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Illug on 19/12/2016.
 */
public class PTVCursorAdaptor extends CursorAdapter{

    public static final String PTV_NAME = "ptvName";
    public static final String PTV_LOCATION = "ptvLocation";
    public static final String PTV_DESCRIPTION = "ptvDescription";
    public static final String PTV_IMAGE = "ptvImage";
    public static final String PTV_GEOLOCATION = "ptvGeoLocation";
    public static final String PTV_PRICE = "ptvPrice";
    public static final String PTV_RANK = "ptvRank";
/**
 * handle setup of placestovisitM from cursor with database information
 * */


    public PTVCursorAdaptor(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        return LayoutInflater.from(context).inflate(
                R.layout.ptv_list_item,viewGroup,false );
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String ptvName = cursor.getString(cursor.getColumnIndex(PTV_NAME));
        String ptvLocation = cursor.getString(cursor.getColumnIndex(PTV_LOCATION));
        String ptvDescription = cursor.getString(cursor.getColumnIndex(PTV_DESCRIPTION));
        String ptvImage = cursor.getString(cursor.getColumnIndex(PTV_IMAGE));
        String ptvGeoLocation = cursor.getString(cursor.getColumnIndex(PTV_GEOLOCATION));
        String ptvPrice = cursor.getString(cursor.getColumnIndex(PTV_PRICE));
        String ptvRank = cursor.getString(cursor.getColumnIndex(PTV_RANK));

        TextView ptvNameTV = (TextView) view.findViewById(R.id.nameTV);
        TextView ptvLocationTV = (TextView) view.findViewById(R.id.locationTV);
        TextView ptvDescriptionTV = (TextView) view.findViewById(R.id.descriptionTV);
        TextView ptvGeoLocationTV = (TextView) view.findViewById(R.id.geolocationTV);
        TextView ptvPriceTV = (TextView) view.findViewById(R.id.priceTV);
        TextView ptvRankTV = (TextView) view.findViewById(R.id.rankTV);


        int id = context.getResources().getIdentifier("drawable/"+ptvImage, null, context.getPackageName());
        ((ImageView)view.findViewById(R.id.imageTV)).setImageResource(id);

        ptvNameTV.setText(ptvName);
        ptvLocationTV.setText(ptvLocation);
        ptvDescriptionTV.setText(ptvDescription);
        ptvGeoLocationTV.setText(ptvGeoLocation);
        ptvPriceTV.setText(ptvPrice);
        ptvRankTV.setText(ptvRank);

    }


}