package com.tracker.repo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.tracker.config.DatabaseConfig;
import com.tracker.model.Workout;

public class WorkoutRepo {

    public WorkoutRepo() {DatabaseConfig.initialize();}


    // find all workouts
    public List<Workout> findAll() {
        List<Workout> workouts = new ArrayList<>();
        MongoCollection<Document> collection = DatabaseConfig.getCollection("workouts");
        
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Workout workout = documentToWorkout(doc);
                workouts.add(workout);
            }
        }
        
        return workouts;
    }


    // find workout by id
    public Workout findByID(int id) {
        MongoCollection<Document> collection = DatabaseConfig.getCollection("workouts");
        Document filter = new Document("id", id);
        
        Document doc = collection.find(filter).first();
        if (doc != null) {
            return documentToWorkout(doc);
        }
        
        return null;
    }


    // create workout
    public Workout create(Workout workout) {
        MongoCollection<Document> collection = DatabaseConfig.getCollection("workouts");
        
        int nextID = DatabaseConfig.getNextSequence("workout_id");
        workout.setID(nextID);
        
        Document doc = workoutToDocument(workout);
        collection.insertOne(doc);
        
        return workout;
    }


    // update workout
    public Workout update(Workout workout) {
        MongoCollection<Document> collection = DatabaseConfig.getCollection("workouts");
        Document filter = new Document("id", workout.getID());
        Document update = new Document("$set", workoutToDocument(workout));
        
        UpdateResult result = collection.updateOne(filter, update);
        if (result.getModifiedCount() > 0) {
            return workout;
        }
        
        return null;
    }


    // delete workout
    public boolean delete(int id) {
        MongoCollection<Document> collection = DatabaseConfig.getCollection("workouts");
        Document filter = new Document("id", id);
        
        DeleteResult result = collection.deleteOne(filter);
        return result.getDeletedCount() > 0;
    }


    // get workout count
    public int getWorkoutCount() {
        MongoCollection<Document> collection = DatabaseConfig.getCollection("workouts");
        return (int) collection.countDocuments();
    }


    // convert document to workout
    private Workout documentToWorkout(Document doc) {
        return new Workout(
            doc.getInteger("id"),
            doc.getString("name"),
            doc.getString("category"),
            doc.getString("description"),
            doc.getInteger("duration_mins"),
            doc.getInteger("cals_burned"),
            LocalDate.parse(doc.getString("workout_date"))
        );
    }


    // convert workout to document
    private Document workoutToDocument(Workout workout) {
        return new Document("id", workout.getID())
            .append("name", workout.getName())
            .append("category", workout.getCategory())
            .append("description", workout.getDescription())
            .append("duration_mins", workout.getDurationMins())
            .append("cals_burned", workout.getCalsBurned())
            .append("workout_date", workout.getDate().toString());
    }
}
