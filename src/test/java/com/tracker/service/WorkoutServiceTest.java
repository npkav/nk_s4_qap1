package com.tracker.service;

import java.time.LocalDate;

import org.bson.Document;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mongodb.client.MongoCollection;
import com.tracker.config.DatabaseConfig;
import com.tracker.model.Workout;

public class WorkoutServiceTest {
    private WorkoutService workoutService;

    // setup
    @BeforeEach
    public void setUp() {workoutService = new WorkoutService(); clearDatabase();}

    // clear database
    private void clearDatabase() {
        MongoCollection<Document> workoutsCollection = DatabaseConfig.getCollection("workouts");
        MongoCollection<Document> countersCollection = DatabaseConfig.getCollection("counters");
        
        workoutsCollection.deleteMany(new Document());
        
        countersCollection.deleteOne(new Document("_id", "workout_id"));
        countersCollection.insertOne(new Document("_id", "workout_id").append("sequence_value", 0));
    }

    // test add workout
    @Test
    public void testAddWorkout() {
        Workout workout = workoutService.addWorkout("Push-ups", "Strength", "100 push-ups", 15, 100, LocalDate.now());
        
        assertNotNull(workout);
        assertEquals("Push-ups", workout.getName());
        assertEquals("Strength", workout.getCategory());
        assertEquals(15, workout.getDurationMins());
        assertEquals(100, workout.getCalsBurned());
        assertTrue(workout.getID() > 0);
    }

    // test get all workouts
    @Test
    public void testGetAllWorkouts() {
        assertTrue(workoutService.getAllWorkouts().isEmpty());
        
        workoutService.addWorkout("Running", "Cardio", "5km run", 30, 300, LocalDate.now());
        workoutService.addWorkout("Cycling", "Cardio", "10km bike ride", 25, 200, LocalDate.now());
        
        assertEquals(2, workoutService.getAllWorkouts().size());
    }

    // test get workout by id
    @Test
    public void testGetWorkoutByID() {
        Workout workout = workoutService.addWorkout("Swimming", "Cardio", "1km swim", 45, 400, LocalDate.now());
        int id = workout.getID();
        
        Workout retrieved = workoutService.getWorkoutByID(id);
        assertNotNull(retrieved);
        assertEquals("Swimming", retrieved.getName());
        
        Workout notFound = workoutService.getWorkoutByID(999);
        assertNull(notFound);
    }

    // test delete workout
    @Test
    public void testDeleteWorkout() {
        Workout workout = workoutService.addWorkout("Yoga", "Flexibility", "Morning yoga", 60, 150, LocalDate.now());
        int id = workout.getID();
        
        assertTrue(workoutService.deleteWorkout(id));
        assertNull(workoutService.getWorkoutByID(id));
        assertFalse(workoutService.deleteWorkout(999));
    }

    // test total calories and time
    @Test
    public void testTotalCaloriesAndTime() {
        assertEquals(0, workoutService.getTotCalsBurned());
        assertEquals(0, workoutService.getTotWorkoutTime());
        
        workoutService.addWorkout("Run", "Cardio", "Morning run", 30, 300, LocalDate.now());
        workoutService.addWorkout("Weights", "Strength", "Weight training", 45, 200, LocalDate.now());
        
        assertEquals(500, workoutService.getTotCalsBurned());
        assertEquals(75, workoutService.getTotWorkoutTime());
        assertEquals(2, workoutService.getWorkoutCount());
    }
} 
