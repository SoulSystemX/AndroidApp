package com.cet325.bg69yl;

import com.cet325.bg69yl.model.Currency;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class gets a json object from fixer.io and takes the rates data out of it and saves it locally
 */
public class JSONCurrencyParser {

    public static Currency getCurrency(String data) throws JSONException {
        Currency currency = new Currency();

        // We create out JSONObject from the data
        JSONObject jObj = new JSONObject(data);

        //get the rates portion of the json data and store it in currency
        JSONObject ratesObj = getObject("rates", jObj);
        currency.setRateGBP(getFloat("GBP", ratesObj));
        currency.setRateJPY(getFloat("JPY", ratesObj));


        return currency;
    }


    private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
        return jObj.getJSONObject(tagName);
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }

}

