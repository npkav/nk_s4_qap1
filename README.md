# Fitness Tracker Application

---

## Semester 4 QAP 1
### Dev Ops + SDAT

Nick Kavanagh

2025-05-23 -- 2025-06-01

---

## Project Description
This is a CLI application written in Java that allows a user to enter and track their specific workouts and goals. You interface with the application via a text menu, and the application will prompt you for input on what you'd like to do for each given action.

The test cases I focused on are:
- CRUD operations for workouts
    - Add a workout
    - View all workouts
    - Update a workout
    - Delete a workout
- CRUD operations for goals
    - Add a goal
    - View all goals
    - Update a goal
    - Delete a goal
- Goal completion detection
- Goal progress tracking
- Goal statistics

## Clean Code Principles
I believe my code meets clean code principles in the following ways:
- I try to use meaningful variable and function names
- I try to use frequent comments to segment and explain the code 
- I try to maintain consistent naming conventions
- I try to keep each method focused on a single responsibility
- I try to follow a layered architecture, keeping each layer in a separate package

## Difficulties
I had trouble with GitHub Actions to the point of trying it on another repo entirely. I used AI for this in an attempt to understand but still am unsure about the implementation.

## Technologies Used
- Java
- Maven
- JUnit
- MongoDB
- IntelliJ IDEA
- VSCode
- GitHub
- GitHub Actions

## Core Functionalities
- Add a workout
- View all workouts
- Update a workout
- Delete a workout
- Add a goal
- View all goals
- Update a goal
- Delete a goal
- View all completed goals
- View workout and goal statistics

## Installation
- Clone the repository
- Run `mvn compile` to compile the project
- Run `mvn package` to package the project
- Run `mvn exec:java -Dexec.mainClass="com.tracker.App"` to run the application

## Usage
- Run the application
- Select an option from the menu
- Follow the prompts to interact with the application

## Updates
- [Changelog](./LOG.md)
