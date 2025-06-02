# Changelog

---

### v0.1 - 2025-05-23 
- Created base Maven project
- Initial commit

### v0.1.1 - 2025-05-23
- Created packages for the tracker app as well as the cli, model, repo, and service layers
- Added some TODOs for initial direction
- Did some initial routing
- Began constructing some of the classes
- Started keeping docs with a readme and changelog

### v0.1.2 - 2025-05-23
- Updated pom.xml to include JUnit 5 (5.13.0) via junit-jupiter
- Updated Workout model:
  - Added id, durationMins, calsBurned, date fields
- Created Goal model:
  - Added id, currVal, targetVal, targetUnit, description, completionDate fields
  - Progress tracking with percentage calculation by currVal/targetVal
  - Auto-completion detection when currVal equals targetVal

  ### v0.1.3 - 2025-05-24
- Completed WorkoutRepo with full CRUD ops:
  - findAll(), findByID(), create(), update(), delete()
- Created GoalRepo with full CRUD ops:
  - findAll(), findByID(), create(), update(), delete()
  - findCompGoals() for filtering completed goals

### v0.1.4 - 2025-05-25
- Updated WorkoutService:
  - Workout management (add, get, update, delete)
  - Stat calculations (total calories, workout time, count)
- Created GoalService:
  - Goal creation and progress tracking
  - Completion status tracking
  - Stats for completed goals vs total goals

### v0.1.5 - 2025-05-26
- Implemented DatabaseConfig for connection management
- Added auto-incrementing IDs

### v0.1.6 - 2025-05-28
- Filled out CLI interface in App.java
- Added try-with-resources for scanner cleanup

### v0.1.7 - 2025-05-31
- Created unit tests:
  - WorkoutTest: validation and edge cases
  - GoalTest: progress calculation and completion logic
  - WorkoutServiceTest: service layer testing
  - GoalServiceTest: goal management and statistics
- Tests cover both positive and negative scenarios
- All tests passing successfully

### v0.1.8 - 2025-06-01
- Added exec-maven-plugin for easy application running via mvn compile exec:java

### v1.0.0 - 2025-06-01
- Added README.md
- Set up GitHub Actions and uploaded to new repo to test
- Fitness Tracker Complete

---