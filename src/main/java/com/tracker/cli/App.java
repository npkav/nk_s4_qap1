package com.tracker.cli;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import com.tracker.model.Goal;
import com.tracker.model.Workout;
import com.tracker.service.GoalService;
import com.tracker.service.WorkoutService;


public class App {
    private final WorkoutService workoutService;
    private final GoalService goalService;


    public App() {
        this.workoutService = new WorkoutService();
        this.goalService = new GoalService();
    }


    public void start() {
        System.out.println("=== FITNESS TRACKER ===");
        
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                showMainMenu();
                int choice = getIntInput("CHOICE: ", scanner);
                
                switch (choice) {
                    case 1 -> addWorkout(scanner);
                    case 2 -> viewWorkouts();
                    case 3 -> createGoal(scanner);
                    case 4 -> updateGoalProg(scanner);
                    case 5 -> viewGoals();
                    case 6 -> viewStats();
                    case 7 -> {
                        System.out.println("EXITING...");
                        running = false;
                    }
                    default -> System.out.println("INVALID");
                }
            }
        }
    }


    // main menu
    private void showMainMenu() {
        System.out.println("""
            
            === MENU ===
            1. ADD WORKOUT
            2. VIEW WORKOUTS
            3. ADD GOAL
            4. UPDATE GOAL
            5. VIEW GOALS
            6. STATS
            7. EXIT
            =============
            """);
    }


    // add workout
    private void addWorkout(Scanner scanner) {
        System.out.println("\n--- ADD WORKOUT ---");
        
        String name = getStringInput("NAME: ", scanner);
        String category = getStringInput("CATEGORY: ", scanner);
        String description = getStringInput("DESCRIPTION: ", scanner);
        int duration = getIntInput("DURATION (MINS): ", scanner);
        int calories = getIntInput("CALORIES: ", scanner);
        LocalDate date = getDateInput("DATE (YYYY-MM-DD) [ENTER=TODAY]: ", scanner);
        
        Workout workout = workoutService.addWorkout(name, category, description, duration, calories, date);
        System.out.println("ADDED ID: " + workout.getID());
    }


    // view workouts
    private void viewWorkouts() {
        System.out.println("\n--- WORKOUTS ---");
        var workouts = workoutService.getAllWorkouts();
        
        if (workouts.isEmpty()) {
            System.out.println("NONE FOUND");
        } else {
            workouts.forEach(w -> System.out.printf("ID:%d | %s | %s | %dmin | %dcal | %s%n",
                w.getID(), w.getName(), w.getCategory(), w.getDurationMins(), w.getCalsBurned(), w.getDate()));
        }
    }


    // create goal
    private void createGoal(Scanner scanner) {
        System.out.println("\n--- ADD GOAL ---");
        
        String name = getStringInput("NAME: ", scanner);
        String description = getStringInput("DESCRIPTION: ", scanner);
        int targetVal = getIntInput("TARGET: ", scanner);
        String unit = getStringInput("UNIT: ", scanner);
        LocalDate targetDate = getDateInput("TARGET DATE (YYYY-MM-DD): ", scanner);
        
        Goal goal = goalService.createGoal(name, description, targetVal, unit, targetDate);
        System.out.println("ADDED ID: " + goal.getID());
    }


    // update goal
    private void updateGoalProg(Scanner scanner) {
        System.out.println("\n--- UPDATE GOAL ---");
        
        var goals = goalService.getAllGoals();
        if (goals.isEmpty()) {
            System.out.println("NO GOALS FOUND");
            return;
        }
        
        System.out.println("GOALS:");
        goals.forEach(g -> System.out.printf("ID:%d | %s | %d/%d %s%n",
            g.getID(), g.getName(), g.getCurrVal(), g.getTargetVal(), g.getUnit()));
        
        int goalID = getIntInput("GOAL ID: ", scanner);
        int newProg = getIntInput("PROGRESS: ", scanner);
        
        Goal updated = goalService.updateGoalProg(goalID, newProg);
        if (updated != null) {
            System.out.printf("UPDATED: %.1f%%%n", updated.getProgPct());
            if (updated.isCompleted()) {
                System.out.println("COMPLETED!");
            }
        } else {
            System.out.println("NOT FOUND");
        }
    }


    // view goals
    private void viewGoals() {
        System.out.println("\n--- GOALS ---");
        var goals = goalService.getAllGoals();
        
        if (goals.isEmpty()) {
            System.out.println("None found");
        } else {
            goals.forEach(g -> System.out.printf("ID:%d | %s | %d/%d %s | %.1f%% | %s%n",
                g.getID(), g.getName(), g.getCurrVal(), g.getTargetVal(), g.getUnit(),
                g.getProgPct(), g.isCompleted() ? "DONE" : "ACTIVE"));
        }
    }


    // view stats
    private void viewStats() {
        System.out.println("\n--- STATS ---");
        System.out.println("WORKOUTS: " + workoutService.getWorkoutCount());
        System.out.println("CALORIES: " + workoutService.getTotCalsBurned());
        System.out.println("TIME: " + workoutService.getTotWorkoutTime() + "min");
        
        System.out.println("GOALS: " + goalService.getTotGoalCount());
        System.out.println("COMPLETED: " + goalService.getCompCount());
        
        if (goalService.getTotGoalCount() > 0) {
            double rate = (double) goalService.getCompCount() / goalService.getTotGoalCount() * 100;
            System.out.printf("RATE: %.1f%%%n", rate);
        }
    }


    // get string input
    private String getStringInput(String prompt, Scanner scanner) {
        System.out.print(prompt);
        return scanner.nextLine();
    }


    // get int input
    private int getIntInput(String prompt, Scanner scanner) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("INVALID NUMBER - TRY AGAIN");
            }
        }
    }


    // get date input
    private LocalDate getDateInput(String prompt, Scanner scanner) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                
                if (input.isEmpty()) {
                    return LocalDate.now();
                }
                
                return LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                System.out.println("INVALID DATE FORMAT - TRY AGAIN");
            }
        }
    }
}
