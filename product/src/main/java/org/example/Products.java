package org.example;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Products { // CRUD Operation with Products
    HashMap<Integer, ProductDetails> store;
    int id;
    private static final String FILE_PATH = "/Users/gokuld/IdeaProjects/product/src/main/java/org/example/product.txt";
    private static final String temp_FILE_PATH = "/Users/gokuld/IdeaProjects/product/src/main/java/org/example/temp.txt";
    private static final String purchase_FILE_PATH = "/Users/gokuld/IdeaProjects/product/src/main/java/org/example/purchase.txt";
    Scanner sc = new Scanner(System.in);
    Products(){
        id = 1;
    }

    static void printProducts(ProductDetails specificProd){
        System.out.println("\n---------------------------------------");
        System.out.println("Prod Id : " + specificProd.prodId);
        System.out.println("Prod Name : " + specificProd.prodName);
        System.out.println("Prod Stock : " + specificProd.prodStock);
        System.out.println("Prod Price : " + specificProd.prodPrice);
        System.out.println("----------------------------------------\n");
    }

    public boolean checkProduct(int prodId){
        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while((line = reader.readLine()) != null){
                String[] details = line.split(",");
                if(Integer.parseInt(details[0])==prodId){
                    return true;
                }
            }
        } catch(IOException e){
            System.out.println("Error reading from file: " + e.getMessage());
        }
        return false;
    }

    public void addProduct(){
        System.out.println("Enter the Product name: ");
        String name = sc.nextLine();
        System.out.println("Enter the Product Stock: ");
        int stock = Integer.parseInt(sc.nextLine());
        if(stock < 0){
            System.out.println("Enter valid Stock!");
            return;
        }
        System.out.println("Enter the Product Price: ");
        int price = Integer.parseInt(sc.nextLine());
        if(price < 0) {
            System.out.println("Enter valid Price!");
            return;
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))){
            writer.write(String.format("%d,%s,%d,%d,%s%n", id, name, stock, price, "false"));
            System.out.println("Product Added Successfully! Id - " + id);
            id++;
        } catch(IOException e){
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void getSpecificProd(int id){
        if(!checkProduct(id)){
            System.out.println("Invalid Product Key!");
            return;
        } else {
            try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while((line = reader.readLine()) != null){
                    String[] details = line.split(",");
                    if(Integer.parseInt(details[0])==id){
                        ProductDetails specificProd = new ProductDetails(Integer.parseInt(details[0]),details[1], Integer.parseInt(details[2]), Integer.parseInt(details[3]));
                        if(details[4].equals("false")){
                            printProducts(specificProd);
                        } else {
                            System.out.println("Sorry,Product Deleted Can't buy!");
                        }
                    }
                }
            } catch(IOException e){
                System.out.println("Error reading from file: " + e.getMessage());
            }
        }
    }

    public void getAllProd(){
//        for(Map.Entry<Integer, ProductDetails> product : store.entrySet()){
//            printProducts(product.getValue());
//        }
        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while((line = reader.readLine()) != null){
                String[] details = line.split(",");
                ProductDetails specificProd = new ProductDetails(Integer.parseInt(details[0]),details[1], Integer.parseInt(details[2]), Integer.parseInt(details[3]));
                if(details[4].equals("false")){
                    printProducts(specificProd);
                } else {
                    System.out.println("Sorry,Product Deleted Can't buy!");
                }
            }
        } catch(IOException e){
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }

    public void updateStock(int prodId, int stock){
        FileCreation tempFile = new FileCreation("Temp");
        boolean updated = false;
        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            BufferedWriter writer = new BufferedWriter((new FileWriter(temp_FILE_PATH)))){
            String line;
            while((line=reader.readLine())!=null){
                String[] details = line.split(",");
                int id = Integer.parseInt(details[0]);
                if(id==prodId){
                    if(details[4].equals("true")){
                        System.out.println("Sorry,Product Deleted Can't buy!");
                        return;
                    }
                    line = String.format("%d,%s,%d,%d,%s", id, details[1], stock, Integer.parseInt(details[3]), details[4]);
                    updated = true;
                }
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e){
            System.out.println("Error processing the file: " + e.getMessage());
        }

        if (!updated) {
            System.out.println("Product ID not found. No updates made.");
            if (tempFile.TempFile.exists()) {
                tempFile.TempFile.delete(); // Cleanup temporary file
            }
            return;
        }

        tempFile.deleteFile();

        System.out.println("Stock updated successfully for Id : " + prodId);
//        printProducts(updatedDetails);
        getSpecificProd(prodId);
    }

    public void updatePrice(int prodId, int price){
//        ProductDetails updatedDetails = store.get(prodId);
//        updatedDetails.prodPrice = price;
//        store.put(prodId, updatedDetails);

        FileCreation tempFile = new FileCreation("Temp");
        boolean updated = false;
        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            BufferedWriter writer = new BufferedWriter((new FileWriter(temp_FILE_PATH)))){
            String line;
            while((line=reader.readLine())!=null){
                String[] details = line.split(",");
                int id = Integer.parseInt(details[0]);
                if(id==prodId){
                    if(details[4].equals("true")){
                        System.out.println("Sorry,Product Deleted Can't buy!");
                        return;
                    }
                    line = String.format("%d,%s,%d,%d,%s", id, details[1], Integer.parseInt(details[2]), price,details[4]);
                    updated = true;
                }
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e){
            System.out.println("Error processing the file: " + e.getMessage());
        }

        if (!updated) {
            System.out.println("Product ID not found. No updates made.");
            if (tempFile.TempFile.exists()) {
                tempFile.TempFile.delete(); // Cleanup temporary file
            }
            return;
        }

        tempFile.deleteFile();

        System.out.println("Price updated successfully for Id : " + prodId);
//        printProducts(updatedDetails);
        getSpecificProd(prodId);
    }

    public void purchaseProduct(int prodId, int quantity){
//        ProductDetails requiredProduct = store.get(prodId);
        if(quantity <= 0){
            System.out.println("Enter valid Quantity!");
            return;
        }
//        if(requiredProduct.prodStock < quantity){
//            System.out.println("Required Quantity not available");
//            return;
//        }
//        requiredProduct.prodStock = requiredProduct.prodStock-quantity;
//        if(purchased.containsKey(prodId)){
//            purchased.put(prodId, purchased.get(prodId)+quantity);
//        } else {
//            purchased.put(prodId, quantity);
//        }
//        store.put(prodId, requiredProduct);
        FileCreation tempFile = new FileCreation("Temp");
        int availQuantity = 0;
        boolean purchased = false;
        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            BufferedWriter writer = new BufferedWriter((new FileWriter(temp_FILE_PATH)))) {
            String line;
            while((line = reader.readLine()) != null){
                String[] details = line.split(",");
                if(details[4].equals("true")){
                    System.out.println("Sorry,Product Deleted Can't buy!");
                    return;
                }
                if(Integer.parseInt(details[0])==prodId){
                   availQuantity = Integer.parseInt(details[2]);
                   purchased = true;
                    if(availQuantity < quantity ){
                        System.out.println("Required Quantity not available");
                        return;
                    }
                    line = String.format("%d,%s,%d,%d,%s", prodId, details[1], (availQuantity-quantity), Integer.parseInt(details[3]), details[4]);
                }
                writer.write(line);
                writer.newLine();
            }
        } catch(IOException e){
            System.out.println("Error reading from file: " + e.getMessage());
        }

        if (!purchased) {
            System.out.println("Product ID not found. No updates made.");
            if (tempFile.TempFile.exists()) {
                tempFile.TempFile.delete(); // Cleanup temporary file
            }
            return;
        }

        tempFile.deleteFile();


        try(BufferedWriter writer = new BufferedWriter(new FileWriter(purchase_FILE_PATH, true))){
            writer.write(String.format("%d,%d%n", prodId, quantity));
            System.out.println("Product Added Successfully! Id - " + prodId);
        } catch(IOException e){
            System.out.println("Error writing to file: " + e.getMessage());
        }
        System.out.println("Product " + prodId + " purchased Successfully!");
    }

    public void deleteProduct(int prodId){
//        if(purchased.containsKey(prodId)){
//            System.out.println("\n---------------------------------------");
//            System.out.println("Product already Purchased!!!");
//            System.out.println("Purchased Quantity : " + purchased.get(prodId));
//            System.out.println("\n---------------------------------------");
//
//            System.out.println("Do You sure Want to delete!!! Y/N");
//            String option = sc.nextLine();
//            if(option.equals("Y")){
//                store.remove(prodId);
//                purchased.remove(prodId);
//                System.out.println("\n---------------------------------------");
//                System.out.println("Product " + prodId + " was deleted Successfully!");
//                System.out.println("\n---------------------------------------");
//            } else {
//                System.out.println("\n---------------------------------------");
//                System.out.println("Product not deleted!");
//                System.out.println("\n---------------------------------------");
//            }
//        } else {
//            store.remove(prodId);
//            System.out.println("Product " + prodId + " was deleted Successfully");
//        }
        FileCreation tempFile = new FileCreation("Temp");
        boolean updated = false;
        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            BufferedWriter writer = new BufferedWriter((new FileWriter(temp_FILE_PATH)))){
            String line;
            while((line=reader.readLine())!=null){
                String[] details = line.split(",");
                int id = Integer.parseInt(details[0]);
                if(id!=prodId){
                    writer.write(line);
                    writer.newLine();
                } else{
                    line = String.format("%d,%s,%d,%d,%s", prodId, details[1], Integer.parseInt(details[2]), Integer.parseInt(details[3]), "true");
                    updated = true;
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e){
            System.out.println("Error processing the file: " + e.getMessage());
        }

        if (!updated) {
            System.out.println("Product ID not found. No updates made.");
            if (tempFile.TempFile.exists()) {
                tempFile.TempFile.delete(); // Cleanup temporary file
            }
            return;
        }

        tempFile.deleteFile();
        System.out.println("Product Deleted Successfully");
    }

    public void purchaseHistory(){
        System.out.println("Purchased History : ");
//        System.out.println("\n---------------------------------------");
//        for(Map.Entry<Integer, Integer> purchasedProd : purchased.entrySet()){
//            System.out.println("ProdId :" + purchasedProd.getKey() );
//            System.out.println("PurchasedQuantity : " + purchasedProd.getValue() +"\n");
//
//        }
//        System.out.println("\n---------------------------------------");

        try(BufferedReader reader = new BufferedReader(new FileReader(purchase_FILE_PATH))) {
            String line;
            while((line = reader.readLine()) != null){
                String[] details = line.split(",");
                System.out.println("\n---------------------------------------");
                System.out.println("Prod Id : " + details[0]);
                System.out.println("Prod Quantity : " + details[1]);
                System.out.println("----------------------------------------\n");
            }
        } catch(IOException e){
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }
}
