package com.tracker.service;

import java.time.LocalDate;
import java.util.List;

import com.tracker.model.Goal;
import com.tracker.repo.GoalRepo;

public class GoalService {
    private final GoalRepo goalRepo;


    // constructor
    public GoalService() {this.goalRepo = new GoalRepo();}


    // create goal
    public Goal createGoal(String name, String description, int targetVal, String unit, LocalDate targetDate) {
        Goal goal = new Goal(0, name, description, targetVal, unit, targetDate);
        return goalRepo.create(goal);
    }


    // get all goals
    public List<Goal> getAllGoals() {return goalRepo.findAll();}


    // get goal by id
    public Goal getGoalByID(int id) {return goalRepo.findByID(id);}


    // update goal progress
    public Goal updateGoalProg(int id, int newVal) {
        Goal goal = goalRepo.findByID(id);
        if (goal != null) {goal.setCurrVal(newVal);return goalRepo.update(goal);}
        return null;
    }


    // delete goal
    public boolean deleteGoal(int id) {return goalRepo.delete(id);}


    // get completed goals
    public List<Goal> getCompGoals() {return goalRepo.findCompGoals();}


    // get completed goal count
    public int getCompCount() {return goalRepo.findCompGoals().size();}


    // get total goal count
    public int getTotGoalCount() {return goalRepo.getGoalCount();}
} 
