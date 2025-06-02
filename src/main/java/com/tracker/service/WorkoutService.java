package com.tracker.service;

import java.time.LocalDate;
import java.util.List;

import com.tracker.model.Workout;
import com.tracker.repo.WorkoutRepo;

public class WorkoutService {
    private final WorkoutRepo workoutRepo;

    public WorkoutService() {this.workoutRepo = new WorkoutRepo();}

    // add workout
    public Workout addWorkout(String name, String category, String description, int durationMins, int calsBurned, LocalDate date) {
        Workout workout = new Workout(0, name, category, description, durationMins, calsBurned, date);
        return workoutRepo.create(workout);
    }

    // get all workouts
    public List<Workout> getAllWorkouts() {return workoutRepo.findAll();}

    // get workout by id
    public Workout getWorkoutByID(int id) {return workoutRepo.findByID(id);}

    // update workout
    public Workout updateWorkout(Workout workout) {return workoutRepo.update(workout);}

    // delete workout
    public boolean deleteWorkout(int id) {return workoutRepo.delete(id);}

    // get total calories burned
    public int getTotCalsBurned() {
        return workoutRepo.findAll().stream()
                .mapToInt(Workout::getCalsBurned)
                .sum();
    }

    // get total workout time
    public int getTotWorkoutTime() {
        return workoutRepo.findAll().stream()
                .mapToInt(Workout::getDurationMins)
                .sum();
    }

    // get workout count
    public int getWorkoutCount() {return workoutRepo.getWorkoutCount();}
}
