package com.tracker.repo;

import com.tracker.model.Workout;
import java.util.List;
import java.util.ArrayList;

public class WorkoutRepo {
    private List<Workout> workouts = new ArrayList<>();

    public List<Workout> findAll() {
        return workouts;
    }

    //TODO: find by id method
    //TODO: create method
    //TODO: read method
    //TODO: update method
    //TODO: delete method
}
