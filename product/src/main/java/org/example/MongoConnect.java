package org.example;
import com.mongodb.client.*;

public class MongoConnect {
    public static MongoDatabase database;
    public static MongoDatabase getDatabase(){
        if (database == null) {
            try {
                // Connection string for local MongoDB server
                String connectionString = "mongodb://localhost:27017";

                // Create MongoClient and initialize the database instance
                MongoClient mongoClient = MongoClients.create(connectionString);
                database = mongoClient.getDatabase("Products");

                System.out.println("Connected to MongoDB successfully.");
            } catch (Exception e) {
                System.err.println("An error occurred while connecting to MongoDB:");
                e.printStackTrace();
            }
        }
        return database;
    }

}
