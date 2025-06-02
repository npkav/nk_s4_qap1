package com.tracker.model;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WorkoutTest {
    private Workout workout;

    // setup
    @BeforeEach
    public void setUp() {
        workout = new Workout(1, "Morning Run", "Cardio", "5km run in the park", 30, 300, LocalDate.now());
    }


    // test workout creation
    @Test
    public void testWorkoutCreation() {
        assertNotNull(workout);
        assertEquals(1, workout.getID());
        assertEquals("Morning Run", workout.getName());
        assertEquals("Cardio", workout.getCategory());
        assertEquals("5km run in the park", workout.getDescription());
        assertEquals(30, workout.getDurationMins());
        assertEquals(300, workout.getCalsBurned());
        assertEquals(LocalDate.now(), workout.getDate());
    }


    // test workout setters
    @Test
    public void testWorkoutSetters() {
        workout.setName("Evening Walk");
        workout.setCategory("Light Exercise");
        workout.setDurationMins(45);
        workout.setCalsBurned(150);

        assertEquals("Evening Walk", workout.getName());
        assertEquals("Light Exercise", workout.getCategory());
        assertEquals(45, workout.getDurationMins());
        assertEquals(150, workout.getCalsBurned());
    }


    // test workout tostring
    @Test
    public void testWorkoutToString() {
        String workoutString = workout.toString();
        assertTrue(workoutString.contains("Morning Run"));
        assertTrue(workoutString.contains("Cardio"));
        assertTrue(workoutString.contains("30"));
        assertTrue(workoutString.contains("300"));
    }


    // test negative values
    @Test
    public void testNegativeValues() {
        workout.setDurationMins(-10);
        workout.setCalsBurned(-50);
        
        assertEquals(-10, workout.getDurationMins());
        assertEquals(-50, workout.getCalsBurned());
    }
} 
