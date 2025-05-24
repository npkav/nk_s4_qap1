package com.tracker.cli;

import com.tracker.service.WorkoutService;

public class App {
    public void start() {
        WorkoutService service = new WorkoutService();
        service.initialize();
        System.out.println("doink");
    }
}