package org.example;
import java.io.*;
import java.util.Scanner;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.ArrayList;

public class Products { // CRUD Operation with Products
    int id;
//    private static final String FILE_PATH = "/Users/gokuld/IdeaProjects/product/src/main/java/org/example/product.txt";
//    private static final String temp_FILE_PATH = "/Users/gokuld/IdeaProjects/product/src/main/java/org/example/temp.txt";
//    private static final String purchase_FILE_PATH = "/Users/gokuld/IdeaProjects/product/src/main/java/org/example/purchase.txt";
    Scanner sc = new Scanner(System.in);
    MongoDatabase database;
    MongoCollection<Document> collection;
    Products(){
        database = MongoConnect.getDatabase();
        System.out.println("Mongo Database : " + database.getName());
        collection = database.getCollection("product");
//        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
//            String line;
//            while((line = reader.readLine()) != null){
//                String[] details = line.split(",");
//                id = Long.parseLong(details[0]);
//            }
//        } catch(IOException e){
//            System.out.println("Error reading from file: " + e.getMessage());
//        }

        FindIterable<Document> documents = collection.find();
        boolean flag = false;
        for (Document document : documents) {
            id = document.getInteger("prod_id");
        }
        id++;
    }

    static void printProducts(Document document){
        System.out.println("\n---------------------------------------");
//        System.out.println("Prod Id : " + specificProd.prodId);
//        System.out.println("Prod Name : " + specificProd.prodName);
//        System.out.println("Prod Stock : " + specificProd.prodStock);
//        System.out.println("Prod Price : " + specificProd.prodPrice);
        int pid = document.getInteger("prod_id",-1);
        String name = document.getString("prod_name");
        int pstock = document.getInteger("prod_stock", -1);
        double pric = document.getDouble("prod_price");

        System.out.println("Id : " + pid);
        System.out.println("Name : " + name);
        System.out.println("Stock : " + pstock);
        System.out.println("Price : " + pric);
        System.out.println("----------------------------------------\n");
    }

    public boolean checkProduct(int prodId){
//        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
//            String line;
//            while((line = reader.readLine()) != null){
//                String[] details = line.split(",");
//                if(Long.parseLong(details[0])==prodId){
//                    return true;
//                }
//            }
//        } catch(IOException e){
//            System.out.println("Error reading from file: " + e.getMessage());
//        }
        long count = collection.countDocuments(new Document("prod_id", prodId));
        if(count > 0) {
            return true;
        }
        return false;
    }

    public synchronized void addProduct(){
        System.out.println("Enter the Product name: ");
        String name = sc.nextLine();
        System.out.println("Enter the Product Stock: ");
        int stock = Integer.parseInt(sc.nextLine());
        if(stock < 0){
            System.out.println("Enter valid Stock!");
            return;
        }
        System.out.println("Enter the Product Price: ");
        double price = Double.parseDouble(sc.nextLine());
        if(price < 0) {
            System.out.println("Enter valid Price!");
            return;
        }
        Category category = new Category();
        category.displayCategory();
//        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))){
//            writer.write(String.format("%d,%s,%d,%f,%s%n", id, name, stock, price, "false"));
//            System.out.println("Product Added Successfully! Id - " + id);
//            id++;
//        } catch(IOException e){
//            System.out.println("Error writing to file: " + e.getMessage());
//        }
        Document doc = category.getDoc();
        Document document = new Document("prod_id", id)
                .append("prod_name", name)
                .append("prod_stock", stock)
                .append("prod_price", price)
                .append("prod_category", doc)
                .append("flag", false);

        id++;
        collection.insertOne(document);
        System.out.println("Product inserted Successfully");
    }

    public void getSpecificProd(int id){
//        if(!checkProduct(id)){
//            System.out.println("Invalid Product Key!");
//            return;
//        } else {
//            try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
//                String line;
//                while((line = reader.readLine()) != null){
//                    String[] details = line.split(",");
//                    if(Long.parseLong(details[0])==id){
//                        ProductDetails specificProd = new ProductDetails(Long.parseLong(details[0]),details[1], Integer.parseInt(details[2]), Double.parseDouble(details[3]));
//                        if(details[4].equals("false")){
//                            printProducts(specificProd);
//                        } else {
//                            System.out.println("Sorry,Product Deleted Can't buy!");
//                        }
//                    }
//                }
//            } catch(IOException e){
//                System.out.println("Error reading from file: " + e.getMessage());
//            }
            FindIterable<Document> documents = collection.find(new Document("prod_id", id));
            boolean flag = false;
            for (Document document : documents) {
                flag = document.getBoolean("flag", false);
                if(!flag){
                    printProducts(document);
                } else {
                    System.out.println("\nProduct Deleted \n");
                }
            }
    }

    public void getAllProd(){
//        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
//            String line;
//            while((line = reader.readLine()) != null){
//                String[] details = line.split(",");
//                ProductDetails specificProd = new ProductDetails(Long.parseLong(details[0]),details[1], Integer.parseInt(details[2]), Double.parseDouble(details[3]));
//                if(details[4].equals("false")){
//                    printProducts(specificProd);
//                } else {
//                    System.out.println("Sorry,Product Deleted Can't buy!");
//                }
//            }
//        } catch(IOException e){
//            System.out.println("Error reading from file: " + e.getMessage());
//        }
        MongoCollection<Document> coll = database.getCollection("product");

        FindIterable<Document> documents = coll.find();
        boolean flag = false;
        for (Document document : documents) {
            flag = document.getBoolean("flag", false);
            if(!flag){
                printProducts(document);
            }
        }
    }

    public synchronized void updateStock(int prodId, int stock){

//        FileCreation tempFile = new FileCreation("Temp");
//        boolean updated = false;
//        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
//            BufferedWriter writer = new BufferedWriter((new FileWriter(temp_FILE_PATH)))){
//            String line;
//            while((line=reader.readLine())!=null){
//                String[] details = line.split(",");
//                long id = Long.parseLong(details[0]);
//                if(id==prodId){
//                    if(details[4].equals("true")){
//                        System.out.println("Sorry,Product Deleted Can't buy!");
//                        return;
//                    }
//                    line = String.format("%d,%s,%d,%f,%s", id, details[1], stock, Double.parseDouble(details[3]), details[4]);
//                    updated = true;
//                }
//                writer.write(line);
//                writer.newLine();
//            }
//        } catch (IOException e){
//            System.out.println("Error processing the file: " + e.getMessage());
//        }
//
//        if (!updated) {
//            System.out.println("Product ID not found. No updates made.");
//            if (tempFile.TempFile.exists()) {
//                tempFile.TempFile.delete(); // Cleanup temporary file
//            }
//            return;
//        }
//
//        tempFile.deleteFile();

        Document filter = new Document("prod_id", prodId);
        Document existingDoc = collection.find(filter).first();
        boolean flag = existingDoc.getBoolean("flag", false);
        if(flag){
            System.out.println("Product Deleted!!!");
            return;
        } else {
            Document update = new Document("$set", new Document("prod_stock", stock));
            collection.updateOne(filter, update);

            System.out.println("Stock updated successfully for Id : " + prodId);
            getSpecificProd(prodId);
        }
    }

    public synchronized void updatePrice(int prodId, double price){

//        FileCreation tempFile = new FileCreation("Temp");
//        boolean updated = false;
//        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
//            BufferedWriter writer = new BufferedWriter((new FileWriter(temp_FILE_PATH)))){
//            String line;
//            while((line=reader.readLine())!=null){
//                String[] details = line.split(",");
//                long id = Long.parseLong(details[0]);
//                if(id==prodId){
//                    if(details[4].equals("true")){
//                        System.out.println("Sorry,Product Deleted Can't buy!");
//                        return;
//                    }
//                    line = String.format("%d,%s,%d,%f,%s", id, details[1], Integer.parseInt(details[2]), price,details[4]);
//                    updated = true;
//                }
//                writer.write(line);
//                writer.newLine();
//            }
//        } catch (IOException e){
//            System.out.println("Error processing the file: " + e.getMessage());
//        }
//
//        if (!updated) {
//            System.out.println("Product ID not found. No updates made.");
//            if (tempFile.TempFile.exists()) {
//                tempFile.TempFile.delete(); // Cleanup temporary file
//            }
//            return;
//        }
//
//        tempFile.deleteFile();

        Document filter = new Document("prod_id", prodId);
        Document existingDoc = collection.find(filter).first();
        boolean flag = existingDoc.getBoolean("flag", false);
        if(flag){
            System.out.println("Product Deleted!!!");
            return;
        }
        Document update = new Document("$set", new Document("prod_price", price));
        collection.updateOne(filter, update);
        System.out.println("Price updated successfully for Id : " + prodId);
        getSpecificProd(prodId);
    }

    public synchronized void purchaseProduct(){
//        public synchronized void purchaseProduct(int prodId, int quantity){
        int choice = 1;
        int quantity = 0;
        int prodId = -1;
        ArrayList<Cart> cart = new ArrayList<>();
        while(choice==1){
                    System.out.println("Enter the product ID to be Purchased : ");
                    prodId = Integer.parseInt(sc.nextLine());
                    boolean check = checkProduct(prodId);
                    if(!check){
                        System.out.println("Enter valid Product Id");
                        return;
                    }
            Document filter = new Document("prod_id", prodId);
            Document existingDoc = collection.find(filter).first();
            boolean flag = existingDoc.getBoolean("flag", false);
            if(flag){
                System.out.println("Product Deleted!!!");
                return;
            }
                    System.out.println("Enter the Quantity of products to be Purchased : ");
                    quantity = Integer.parseInt(sc.nextLine());

                    if(quantity <= 0){
                        System.out.println("Enter valid Quantity!");
                        return;
                    }

            FindIterable<Document> documents = collection.find(new Document("prod_id", prodId));
            int prodStock = 0;
            double price = 0;
            for (Document document : documents) {
                prodStock = document.getInteger("prod_stock", -1); // -1 is default if field not found
                price = document.getDouble("prod_price"); // -1 is default if field not found
                System.out.println("prod_id: " + prodId + " prod_stock: " + prodStock + " " + "prod_price : " +price);
            }
            if(prodStock < quantity){
                System.out.println("Required Quantity not available");
                return;
            }

            Cart orderCart = new Cart(prodId,quantity,price);
            cart.add(orderCart);
            System.out.println("Do you want to purchase another Product: [1] / [0]");
            choice = Integer.parseInt(sc.nextLine());

        }
        updateOrders(cart);

//        FileCreation tempFile = new FileCreation("Temp");
//        int availQuantity = 0;
//        boolean purchased = false;
//        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
//            BufferedWriter writer = new BufferedWriter((new FileWriter(temp_FILE_PATH)))) {
//            String line;
//            while((line = reader.readLine()) != null){
//                String[] details = line.split(",");
//                if(Long.parseLong(details[0])==prodId){
//                    if(details[4].equals("true")){
//                        System.out.println("Sorry,Product Deleted Can't buy!");
//                        return;
//                    }
//                   availQuantity = Integer.parseInt(details[2]);
//                   purchased = true;
//                    if(availQuantity < quantity ){
//                        System.out.println("Required Quantity not available");
//                        return;
//                    }
//                    line = String.format("%d,%s,%d,%f,%s", prodId, details[1], (availQuantity-quantity), Double.parseDouble(details[3]), details[4]);
//                }
//                writer.write(line);
//                writer.newLine();
//            }
//        } catch(IOException e){
//            System.out.println("Error reading from file: " + e.getMessage());
//        }
//
//        if (!purchased) {
//            System.out.println("Product ID not found. No updates made.");
//            if (tempFile.TempFile.exists()) {
//                tempFile.TempFile.delete(); // Cleanup temporary file
//            }
//            return;
//        }
//
//        tempFile.deleteFile();
//
//
//        try(BufferedWriter writer = new BufferedWriter(new FileWriter(purchase_FILE_PATH, true))){
//            writer.write(String.format("%d,%d%n", prodId, quantity));
//            System.out.println("Product Added Successfully! Id - " + prodId);
//        } catch(IOException e){
//            System.out.println("Error writing to file: " + e.getMessage());
//        }

    }

    public void updateOrders(ArrayList<Cart> cart){
        int totalPrice = 0;
        double totalQty = 0;
        for(Cart c : cart){
            totalQty++;
            FindIterable<Document> documents = collection.find(new Document("prod_id", c.prodId));
            int prodStock = 0;
            double price = 0;
            for (Document document : documents) {
                prodStock = document.getInteger("prod_stock", -1); // -1 is default if field not found
                price = document.getDouble("prod_price"); // -1 is default if field not found

                Document filter = new Document("prod_id", c.prodId);
                Document update = new Document("$set", new Document("prod_stock", prodStock-c.prodStock));
                collection.updateOne(filter, update);
            }
            totalPrice = (int) (totalPrice + (c.prodStock*price));
        }
            PostgresqlConnect.insertOrder(totalPrice, (int)totalQty);

        for(Cart c : cart){
            FindIterable<Document> documents = collection.find(new Document("prod_id", c.prodId));

            PostgresqlConnect.insertListOrder(c.prodId,c.prodPrice, c.prodStock);

        }
    }

    public synchronized void deleteProduct(int prodId){
//        FileCreation tempFile = new FileCreation("Temp");
//        boolean updated = false;
//        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
//            BufferedWriter writer = new BufferedWriter((new FileWriter(temp_FILE_PATH)))){
//            String line;
//            while((line=reader.readLine())!=null){
//                String[] details = line.split(",");
//                long id = Long.parseLong(details[0]);
//                if(id!=prodId){
//                    writer.write(line);
//                    writer.newLine();
//                } else{
//                    line = String.format("%d,%s,%d,%f,%s", prodId, details[1], Integer.parseInt(details[2]), Double.parseDouble(details[3]), "true");
//                    updated = true;
//                    writer.write(line);
//                    writer.newLine();
//                }
//            }
//        } catch (IOException e){
//            System.out.println("Error processing the file: " + e.getMessage());
//        }
//
//        if (!updated) {
//            System.out.println("Product ID not found. No updates made.");
//            if (tempFile.TempFile.exists()) {
//                tempFile.TempFile.delete(); // Cleanup temporary file
//            }
//            return;
//        }
//
//        tempFile.deleteFile();

        Document filter = new Document("prod_id", prodId);
        Document update = new Document("$set", new Document("flag", true));
        collection.updateOne(filter, update);

        System.out.println("Product Deleted Successfully");
    }

    public void purchaseHistory(){
        System.out.println("Purchased History : ");
        PostgresqlConnect.history();
//        try(BufferedReader reader = new BufferedReader(new FileReader(purchase_FILE_PATH))) {
//            String line;
//            while((line = reader.readLine()) != null){
//                String[] details = line.split(",");
//                System.out.println("\n---------------------------------------");
//                System.out.println("Prod Id : " + details[0]);
//                System.out.println("Prod Quantity : " + details[1]);
//                System.out.println("----------------------------------------\n");
//            }
//        } catch(IOException e){
//            System.out.println("Error reading from file: " + e.getMessage());
//        }
    }
}
