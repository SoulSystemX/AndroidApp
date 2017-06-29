package com.cet325.bg69yl.model;

/**
 * Created by Illug on 05/01/2017.
 * Standard detail class to hold information about a place locally for easy access
 */
public class DetailData {

    public int id;
    public String name;
    public String description;
    public String geolocation;
    public String location;
    public int rank;
    public double price;
    public double GBP;
    public double JPY;
    public boolean planToVisit;
    public boolean visited;
    public String datePlanned;
    public String timePlanned;
    public String dateVisited;
    public String timeVisited;
    public String notes;

    public DetailData(){

    }

    public DetailData(int id,
                      String name,
                      String description,
                      String location,
                      String geolocation,
                      boolean planToVisit,
                      boolean visited,
                      int rank,
                      double price,
                      double GBP,
                      double JPY,
                      String datePlanned,
                      String timePlanned,
                      String dateVisited,
                      String timeVisited,
                      String notes ) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
        this.geolocation = geolocation;
        this.planToVisit = planToVisit;
        this.visited = visited;
        this.rank = rank;
        this.price = price;
        this.GBP = GBP;
        this.JPY = JPY;
        this.datePlanned = datePlanned;
        this.timePlanned = timePlanned;
        this.dateVisited = dateVisited;
        this.timeVisited = timeVisited;
        this.notes = notes;
    }


    //Returns a string with key information stored in the class for easy viewing
    @Override
    public String toString() {
        return "Place [id=" + id +
                ", title=" + name +
                ", description=" + description +
                ", geolocation=" + geolocation +
                ", planToVisit=" + planToVisit +
                ", visited=" + visited +
                ", rank=" + rank +
                ", price=" + price +
                ", datePlanned=" + datePlanned +
                ", timePlanned=" + timePlanned +
                ", dateVisited=" + dateVisited +
                ", timeVisited=" + timeVisited +
                ", notes=" + notes +
                "]";
    }

}
