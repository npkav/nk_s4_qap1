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
import com.tracker.model.Goal;

public class GoalRepo {

    // constructor
    public GoalRepo() {DatabaseConfig.initialize();}


    // find all goals
    public List<Goal> findAll() {
        List<Goal> goals = new ArrayList<>();
        MongoCollection<Document> collection = DatabaseConfig.getCollection("goals");
        
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Goal goal = documentToGoal(doc);
                goals.add(goal);
            }
        }
        
        return goals;
    }


    // find goal by id
    public Goal findByID(int id) {
        MongoCollection<Document> collection = DatabaseConfig.getCollection("goals");
        Document filter = new Document("id", id);
        
        Document doc = collection.find(filter).first();
        if (doc != null) {
            return documentToGoal(doc);
        }
        
        return null;
    }


    // create goal
    public Goal create(Goal goal) {
        MongoCollection<Document> collection = DatabaseConfig.getCollection("goals");
        
        int nextID = DatabaseConfig.getNextSequence("goal_id");
        goal.setID(nextID);
        
        Document doc = goalToDocument(goal);
        collection.insertOne(doc);
        
        return goal;
    }


    // update goal
    public Goal update(Goal goal) {
        MongoCollection<Document> collection = DatabaseConfig.getCollection("goals");
        Document filter = new Document("id", goal.getID());
        Document update = new Document("$set", goalToDocument(goal));
        
        UpdateResult result = collection.updateOne(filter, update);
        if (result.getModifiedCount() > 0) {
            return goal;
        }
        
        return null;
    }


    // delete goal
    public boolean delete(int id) {
        MongoCollection<Document> collection = DatabaseConfig.getCollection("goals");
        Document filter = new Document("id", id);
        
        DeleteResult result = collection.deleteOne(filter);
        return result.getDeletedCount() > 0;
    }


    // find completed goals
    public List<Goal> findCompGoals() {
        List<Goal> completed = new ArrayList<>();
        MongoCollection<Document> collection = DatabaseConfig.getCollection("goals");
        Document filter = new Document("completed", true);
        
        try (MongoCursor<Document> cursor = collection.find(filter).iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Goal goal = documentToGoal(doc);
                completed.add(goal);
            }
        }
        
        return completed;
    }


    // get goal count
    public int getGoalCount() {
        MongoCollection<Document> collection = DatabaseConfig.getCollection("goals");
        return (int) collection.countDocuments();
    }


    // convert document to goal
    private Goal documentToGoal(Document doc) {
        Goal goal = new Goal(
            doc.getInteger("id"),
            doc.getString("name"),
            doc.getString("description"),
            doc.getInteger("target_val"),
            doc.getString("unit"),
            LocalDate.parse(doc.getString("target_date"))
        );
        goal.setCurrVal(doc.getInteger("current_val"));
        goal.setCompleted(doc.getBoolean("completed"));
        return goal;
    }


    // convert goal to document
    private Document goalToDocument(Goal goal) {
        return new Document("id", goal.getID())
            .append("name", goal.getName())
            .append("description", goal.getDescription())
            .append("target_val", goal.getTargetVal())
            .append("current_val", goal.getCurrVal())
            .append("unit", goal.getUnit())
            .append("target_date", goal.getTargetDate().toString())
            .append("completed", goal.isCompleted());
    }
} 
