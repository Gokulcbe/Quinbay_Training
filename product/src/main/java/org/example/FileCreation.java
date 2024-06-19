package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileCreation {
    public File productFile;
    public File TempFile;
    public File PurchaseFile;

    public void createFile(File file){
        try{
            if(file.createNewFile()){
                System.out.println("File Created: " + file.getName());
            } else {
                System.out.println("File already exists");
            }

            FileWriter writer = new FileWriter(file);
            writer.close();
            System.out.println("Successfully wrote to file");
        } catch(IOException e){
            System.out.println("An error occured");
            e.printStackTrace();
        }
    }

    FileCreation(String type){
        if(type.equals("Product")){
            this.productFile = new File("/Users/gokuld/IdeaProjects/product/src/main/java/org/example/product.txt");
            createFile(productFile);
        }
        else if(type.equals("Purchase")){
            this.PurchaseFile = new File("/Users/gokuld/IdeaProjects/product/src/main/java/org/example/purchase.txt");
            createFile(PurchaseFile);
        }else {
            this.TempFile = new File("/Users/gokuld/IdeaProjects/product/src/main/java/org/example/temp.txt");
            createFile(TempFile);
        }
    }

    public void deleteFile(){
        if(productFile != null && !productFile.delete()){
            System.out.println("Could not delete the original file");
            return;
        }
        Path source = Paths.get("/Users/gokuld/IdeaProjects/product/src/main/java/org/example/temp.txt");
        try{
            Files.move(source, source.resolveSibling("product1.txt"));
        } catch(IOException e){
            e.printStackTrace();
        }

        try{
            Files.delete(Paths.get("/Users/gokuld/IdeaProjects/product/src/main/java/org/example/product.txt"));
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Temp File deleted!");

        source = Paths.get("/Users/gokuld/IdeaProjects/product/src/main/java/org/example/product1.txt");
        try{
            Files.move(source, source.resolveSibling("product.txt"));
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
