package com.cet325.bg69yl.model;

/**
 * Created by Illug on 09/01/2017.
 * Standard currency class to hold currency rate information locally for easy access
 */
public class Currency {

    private String base;
    private String date;
    private float rateEUR;
    private float rateGBP;
    private float rateJPY;

    public float getRateEUR() {
        return rateEUR;
    }
    public void setRateEUR(float rate) {
        this.rateEUR = rate;
    }

    public float getRateGBP() {
        return rateGBP;
    }
    public void setRateGBP(float rate) {
        this.rateGBP = rate;
    }

    public float getRateJPY() {
        return rateJPY;
    }
    public void setRateJPY(float rate) {
        this.rateJPY = rate;
    }
}
