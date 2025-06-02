package com.tracker.config;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class DatabaseConfig {
    private static MongoClient mongoClient;
    private static MongoDatabase database;


    // db initializer
    private static void init() {
        if (database == null) {
            mongoClient = MongoClients.create("mongodb://localhost:27017");
            String dbName = System.getProperty("db.name", "workout_tracker");
            database = mongoClient.getDatabase(dbName);
            setupCounters();
        }
    }


    // setup counters
    private static void setupCounters() {
        MongoCollection<Document> counters = database.getCollection("counters");
        
        if (counters.countDocuments(new Document("_id", "workout_id")) == 0) {
            counters.insertOne(new Document("_id", "workout_id").append("sequence_value", 0));
        }
        
        if (counters.countDocuments(new Document("_id", "goal_id")) == 0) {
            counters.insertOne(new Document("_id", "goal_id").append("sequence_value", 0));
        }
    }


    // initialize db
    public static void initialize() {init();}


    // get collection
    public static MongoCollection<Document> getCollection(String collectionName) {init();return database.getCollection(collectionName);}


    // get next sequence
    public static int getNextSequence(String sequenceName) {
        init();
        MongoCollection<Document> counters = database.getCollection("counters");
        Document filter = new Document("_id", sequenceName);
        Document update = new Document("$inc", new Document("sequence_value", 1));
        Document result = counters.findOneAndUpdate(filter, update);
        
        if (result != null) {
            return result.getInteger("sequence_value") + 1;
        } else {
            counters.insertOne(new Document("_id", sequenceName).append("sequence_value", 1));
            return 1;
        }
    }
} 