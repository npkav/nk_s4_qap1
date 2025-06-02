package com.tracker.model;

import java.time.LocalDate;


public class Workout {
    private String name;
    private String category;
    private String description;
    private int durationMins;
    private int calsBurned;
    private LocalDate date;
    private int id;


    // constructor
    public Workout(int id, String name, String category, String description, int durationMins, int calsBurned, LocalDate date) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.durationMins = durationMins;
        this.calsBurned = calsBurned;
        this.date = date;
    }


    // getters and setters
    public int getID() {return id;}


    public void setID(int id) {this.id = id;}


    public String getName() {return name;}


    public void setName(String name) {this.name = name;}


    public String getCategory() {return category;}


    public void setCategory(String category) {this.category = category;}


    public String getDescription() {return description;}


    public void setDescription(String description) {this.description = description;}


    public int getDurationMins() {return durationMins;}


    public void setDurationMins(int durationMins) {this.durationMins = durationMins;}


    public int getCalsBurned() {return calsBurned;}


    public void setCalsBurned(int calsBurned) {this.calsBurned = calsBurned;}


    public LocalDate getDate() {return date;}


    public void setDate(LocalDate date) {this.date = date;}


    // tostring
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("WORKOUT DETAILS\n");
        sb.append("=====================================\n");
        sb.append(String.format("ID: %d\n", id));
        sb.append(String.format("NAME: %s\n", name));
        sb.append(String.format("CATEGORY: %s\n", category));
        sb.append(String.format("DESCRIPTION: %s\n", description));
        sb.append(String.format("DURATION: %d minutes\n", durationMins));
        sb.append(String.format("CALORIES BURNED: %d\n", calsBurned));
        sb.append(String.format("DATE: %s\n", date));
        sb.append("=====================================");
        return sb.toString();
    }
}
