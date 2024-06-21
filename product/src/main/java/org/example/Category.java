package org.example;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.Scanner;

public class Category {
    MongoDatabase database;
    MongoCollection<Document> collection;
    Document result;
    Category(){
        database = MongoConnect.getDatabase();
        System.out.println("Mongo Database : " + database.getName());
        collection = database.getCollection("category");
    }
    public  void displayCategory(){
        Scanner sc = new Scanner(System.in);
        MongoCollection<Document> coll = database.getCollection("category");

        FindIterable<Document> documents = coll.find();
        for (Document document : documents) {
            System.out.println("\n---------------------------------------");
            int pid = document.getInteger("category_id", -1);
            String name = document.getString("category_name");

            System.out.println("Id : " + pid);
            System.out.println("Name : " + name);
            System.out.println("---------------------------------------");

        }
        int choice = -1;
        while(choice <= 0 || choice > 10){
            System.out.println("Select the Category : ");
            choice = Integer.parseInt(sc.nextLine());
            if(choice <= 0 || choice > 10){
                System.out.println("Invalid Choice!");
            }
        }
        System.out.println("Categoty : " + choice);
        Document filter = new Document("category_id", choice);
        int pid = filter.getInteger("category_id", -1);
        FindIterable<Document> category = collection.find(new Document("category_id", pid));
        for (Document categ : category) {
            result = categ;
        }
    }

    public Document getDoc(){
        return result;
    }
}
