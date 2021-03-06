package com.cet325.bg69yl.model;

/**
 * Created by Illug on 03/01/2017.
 * Further advanced class for storing more information about the weather in a specific location
 */

import android.graphics.Bitmap;

public class Weather {

    public Location location;
    public CurrentCondition currentCondition = new CurrentCondition();
    public Temperature temperature = new Temperature();
    public Wind wind = new Wind();
    public Rain rain = new Rain();
    public Snow snow = new Snow()	;
    public Clouds clouds = new Clouds();
    public Bitmap bmp = null;

    public  class CurrentCondition {
        private int weatherId;
        private String condition;
        private String desc;
        private String icon;

        private float pressure;
        private float humidity;

        public int getWeatherId() {
            return weatherId;
        }
        public void setWeatherId(int weatherId) {
            this.weatherId = weatherId;
        }
        public String getCondition() {
            return condition;
        }
        public void setCondition(String condition) {
            this.condition = condition;
        }
        public String getDesc() { return desc; }
        public void setDesc(String desc) {
            this.desc = desc;
        }
        public String getIcon() {
            return icon;
        }
        public void setIcon(String icon) {
            this.icon = icon;
        }
        public float getPressure() {
            return pressure;
        }
        public void setPressure(float pressure) {
            this.pressure = pressure;
        }
        public float getHumidity() {
            return humidity;
        }
        public void setHumidity(float humidity) {
            this.humidity = humidity;
        }

    }

    public  class Temperature {
        private float temp;
        private float minTemp;
        private float maxTemp;

        public float getTemp() {
            return temp;
        }
        public void setTemp(float temp) {
            this.temp = temp;
        }
        public float getMinTemp() {
            return minTemp;
        }
        public void setMinTemp(float minTemp) {
            this.minTemp = minTemp;
        }
        public float getMaxTemp() {
            return maxTemp;
        }
        public void setMaxTemp(float maxTemp) {
            this.maxTemp = maxTemp;
        }

    }

    public  class Wind {
        private float speed;
       // private float deg;
        public float getSpeed() {
            return speed;
        }
        public void setSpeed(float speed) {
            this.speed = speed;
        }

    }

    public  class Rain {
        private String time;
        private float amount;
        public String getTime() {
            return time;
        }
        public void setTime(String time) {
            this.time = time;
        }
        public float getAmount() {
            return amount;
        }
        public void setAmount(float amount) {
            this.amount = amount;
        }



    }

    public  class Snow {
        private String time;
        private float amount;

        public String getTime() {
            return time;
        }
        public void setTime(String time) {
            this.time = time;
        }
        public float getAmount() {
            return amount;
        }
        public void setAmount(float amount) {
            this.amount = amount;
        }

    }

    public  class Clouds {
        private int perc;

        public int getPerc() {
            return perc;
        }
        public void setPerc(int perc) {
            this.perc = perc;
        }

    }

}

