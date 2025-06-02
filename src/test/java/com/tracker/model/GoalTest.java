package com.tracker.model;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class GoalTest {
    private Goal goal;

    // setup
    @BeforeEach
    public void setUp() {
        goal = new Goal(1, "Lose Weight", "Lose 10 pounds", 10, "pounds", LocalDate.now().plusDays(30));
    }


    // test goal creation
    @Test
    public void testGoalCreation() {
        assertNotNull(goal);
        assertEquals(1, goal.getID());
        assertEquals("Lose Weight", goal.getName());
        assertEquals("Lose 10 pounds", goal.getDescription());
        assertEquals(10, goal.getTargetVal());
        assertEquals(0, goal.getCurrVal());
        assertEquals("pounds", goal.getUnit());
        assertFalse(goal.isCompleted());
        assertEquals(0.0, goal.getProgPct());
    }


    // test goal progress
    @Test
    public void testGoalProgress() {
        goal.setCurrVal(5);
        assertEquals(5, goal.getCurrVal());
        assertEquals(50.0, goal.getProgPct(), 0.1);
        assertFalse(goal.isCompleted());

        goal.setCurrVal(10);
        assertEquals(10, goal.getCurrVal());
        assertEquals(100.0, goal.getProgPct(), 0.1);
        assertTrue(goal.isCompleted());
    }


    // test goal over achievement
    @Test
    public void testGoalOverAchievement() {
        goal.setCurrVal(15);
        assertEquals(15, goal.getCurrVal());
        assertEquals(100.0, goal.getProgPct(), 0.1);
        assertTrue(goal.isCompleted());
    }


    // test zero target value
    @Test
    public void testZeroTargetValue() {
        Goal zeroGoal = new Goal(2, "Test Goal", "Test", 0, "units", LocalDate.now());
        assertEquals(0.0, zeroGoal.getProgPct());
    }


    // test goal tostring
    @Test
    public void testGoalToString() {
        String goalString = goal.toString();
        assertTrue(goalString.contains("Lose Weight"));
        assertTrue(goalString.contains("10"));
        assertTrue(goalString.contains("pounds"));
        assertTrue(goalString.contains("0.0%"));
    }
} 
