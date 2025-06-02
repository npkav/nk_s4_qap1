package com.tracker.model;

import java.time.LocalDate;


public class Goal {
    private int id;
    private String name;
    private String description;
    private int targetVal;
    private int currentVal;
    private String unit;
    private LocalDate targetDate;
    private boolean completed;


    // constructor
    public Goal(int id, String name, String description, int targetVal, String unit, LocalDate targetDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.targetVal = targetVal;
        this.currentVal = 0;
        this.unit = unit;
        this.targetDate = targetDate;
        this.completed = false;
    }


    // getters and setters
    public int getID() {return id;}


    public void setID(int id) {this.id = id;}


    public String getName() {return name;}


    public void setName(String name) {this.name = name;}


    public String getDescription() {return description;}


    public void setDescription(String description) {this.description = description;}


    public int getTargetVal() {return targetVal;}


    public void setTargetVal(int targetVal) {this.targetVal = targetVal;}


    public int getCurrVal() {return currentVal;}


    public void setCurrVal(int currentVal) {
        this.currentVal = currentVal;
        this.completed = (currentVal >= targetVal);
    }


    public String getUnit() {return unit;}


    public void setUnit(String unit) {this.unit = unit;}


    public LocalDate getTargetDate() {return targetDate;}


    public void setTargetDate(LocalDate targetDate) {this.targetDate = targetDate;}


    // is completed
    public boolean isCompleted() {return completed;}


    public void setCompleted(boolean completed) {this.completed = completed;}


    // get progress percentage
    public double getProgPct() {
        if (targetVal == 0) return 0.0;
        return Math.min(100.0, (double) currentVal / targetVal * 100);
    }


    // tostring
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("GOAL SUMMARY\n");
        sb.append("=====================================\n");
        sb.append(String.format("ID: %d\n", id));
        sb.append(String.format("NAME: %s\n", name));
        sb.append(String.format("DESCRIPTION: %s\n", description));
        sb.append(String.format("TARGET: %d %s\n", targetVal, unit));
        sb.append(String.format("CURRENT PROGRESS: %d %s\n", currentVal, unit));
        sb.append(String.format("PROGRESS PERCENTAGE: %.1f%%\n", getProgPct()));
        sb.append(String.format("TARGET DATE: %s\n", targetDate));
        sb.append(String.format("STATUS: %s\n", completed ? "COMPLETED" : "IN PROGRESS"));
        sb.append("=====================================");
        return sb.toString();
    }
} 
