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
import com.tracker.model.Goal;


public class GoalServiceTest {
    private GoalService goalService;

    // setup
    @BeforeEach
    public void setUp() {
        goalService = new GoalService();
        clearDatabase();
    }


    // clear database
    private void clearDatabase() {
        MongoCollection<Document> goalsCollection = DatabaseConfig.getCollection("goals");
        MongoCollection<Document> countersCollection = DatabaseConfig.getCollection("counters");
        
        goalsCollection.deleteMany(new Document());
        
        countersCollection.deleteOne(new Document("_id", "goal_id"));
        countersCollection.insertOne(new Document("_id", "goal_id").append("sequence_value", 0));
    }


    // test create goal
    @Test
    public void testCreateGoal() {
        Goal goal = goalService.createGoal("Run 100km", "Run 100km this month", 100, "km", LocalDate.now().plusDays(30));
        
        assertNotNull(goal);
        assertEquals("Run 100km", goal.getName());
        assertEquals("Run 100km this month", goal.getDescription());
        assertEquals(100, goal.getTargetVal());
        assertEquals("km", goal.getUnit());
        assertTrue(goal.getID() > 0);
        assertFalse(goal.isCompleted());
    }


    // test get all goals
    @Test
    public void testGetAllGoals() {
        assertTrue(goalService.getAllGoals().isEmpty());
        
        goalService.createGoal("Goal 1", "First goal", 50, "units", LocalDate.now().plusDays(10));
        goalService.createGoal("Goal 2", "Second goal", 100, "units", LocalDate.now().plusDays(20));
        
        assertEquals(2, goalService.getAllGoals().size());
    }


    // test update goal progress
    @Test
    public void testUpdateGoalProgress() {
        Goal goal = goalService.createGoal("Workout Days", "Workout 20 days", 20, "days", LocalDate.now().plusDays(30));
        int goalID = goal.getID();
        
        Goal updated = goalService.updateGoalProg(goalID, 10);
        assertNotNull(updated);
        assertEquals(10, updated.getCurrVal());
        assertEquals(50.0, updated.getProgPct(), 0.1);
        assertFalse(updated.isCompleted());
        
        goalService.updateGoalProg(goalID, 20);
        Goal completed = goalService.getGoalByID(goalID);
        assertTrue(completed.isCompleted());
        
        Goal notFound = goalService.updateGoalProg(999, 5);
        assertNull(notFound);
    }


    // test delete goal
    @Test
    public void testDeleteGoal() {
        Goal goal = goalService.createGoal("Test Goal", "Test description", 10, "units", LocalDate.now());
        int goalID = goal.getID();
        
        assertTrue(goalService.deleteGoal(goalID));
        assertNull(goalService.getGoalByID(goalID));
        assertFalse(goalService.deleteGoal(999));
    }


    // test completed goals
    @Test
    public void testCompletedGoals() {
        assertEquals(0, goalService.getCompCount());
        
        Goal goal1 = goalService.createGoal("Goal 1", "Description 1", 10, "units", LocalDate.now());
        Goal goal2 = goalService.createGoal("Goal 2", "Description 2", 20, "units", LocalDate.now());
        
        goalService.updateGoalProg(goal1.getID(), 10);
        goalService.updateGoalProg(goal2.getID(), 15);
        
        assertEquals(1, goalService.getCompCount());
        assertEquals(2, goalService.getTotGoalCount());
        
        assertEquals(1, goalService.getCompGoals().size());
        assertTrue(goalService.getCompGoals().get(0).isCompleted());
    }
} 
