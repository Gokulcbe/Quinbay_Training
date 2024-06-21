package org.example;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Main { // Interface with the User
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice = 1;
//        FileCreation file = new FileCreation("Product");
//        FileCreation purchaseFile = new FileCreation("Purchase");
        Products prod = new Products();
        MongoConnect mongo = new MongoConnect();
        mongo.getDatabase();
        PostgresqlConnect postgre = new PostgresqlConnect();
        while(choice >= 1 && choice <= 8){
            System.out.println("Welcome to Quinbay!");
            System.out.println("Enter 1 - Add the Product");
            System.out.println("Enter 2 - view Specific product");
            System.out.println("Enter 3 - View all Product");
            System.out.println("Enter 4 - Update Stocks");
            System.out.println("Enter 5 - Update Price");
            System.out.println("Enter 6 - Purchase the Product");
            System.out.println("Enter 7 - Delete the Product");
            System.out.println("Enter 8 - Purchase History");
            System.out.println("Enter 9 - Exit");

            choice = Integer.parseInt(sc.nextLine());

            switch(choice){
                case 1 : {
                    Thread addProductThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Add Product Thread Started!");
                            prod.addProduct();
                            System.out.println("Add Product Thread Stopped!");

                        }
                    });
                    addProductThread.start();
                    try {
                        addProductThread.join();
                    } catch (InterruptedException e) {
                        System.out.println("Add Product Thread interrupted: " + e.getMessage());
                    }
                    break;
                }

                case 2: {
                    System.out.println("Enter the Product ID : ");
                    int id = Integer.parseInt(sc.nextLine());
                    prod.getSpecificProd(id);
                    break;
                }

                case 3 : {
                    prod.getAllProd();
                    break;
                }

                case 4 : {
                    System.out.println("Enter the Product Id : ");
                    int id = Integer.parseInt(sc.nextLine());
                    boolean check = prod.checkProduct(id);
                    if(!check){
                        System.out.println("Enter valid Product Id");
                        break;
                    }
                    System.out.println("Enter the Stock to be Updated : ");
                    int stock = Integer.parseInt(sc.nextLine());
                    if(stock<0){
                        System.out.println("Enter valid Stock!");
                        break;
                    }
                    Thread updateStockThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Update Stock Thread Started!");
                            prod.updateStock(id, stock);
                            System.out.println("Update Stock Thread Stopped!");

                        }
                    });
                    updateStockThread.start();
                    try {
                        updateStockThread.join();
                    } catch (InterruptedException e) {
                        System.out.println("Update Stock Thread interrupted: " + e.getMessage());
                    }
                    break;
                }

                case 5 : {
                    System.out.println("Enter the Product Id : ");
                    int id = Integer.parseInt(sc.nextLine());
                    boolean check = prod.checkProduct(id);
                    if(!check){
                        System.out.println("Enter valid Product Id");
                        break;
                    }
                    System.out.println("Enter the price to be Updated : ");
                    double price = Double.parseDouble(sc.nextLine());
                    if(price < 0){
                        System.out.println("Enter Valid Price!");
                        break;
                    }
                    Thread updatePriceThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Update Price Thread Started!");
                            prod.updatePrice(id, price);
                            System.out.println("Update Price Thread Stopped!");

                        }
                    });
                    updatePriceThread.start();
                    try {
                        updatePriceThread.join();
                    } catch (InterruptedException e) {
                        System.out.println("Update Price Thread interrupted: " + e.getMessage());
                    }
                    break;
                }

                case 6 : {
                    Thread purchaseProductThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Purchase Product Thread Started!");
                            prod.purchaseProduct();
                            System.out.println("Purchase Product Thread Stopped!");

                        }
                    });
                    purchaseProductThread.start();
                    try {
                        purchaseProductThread.join();
                    } catch (InterruptedException e) {
                        System.out.println("Purchase Product Thread interrupted: " + e.getMessage());
                    }
                    break;
                }

                case 7 : {
                    System.out.println("Enter the Product ID to be deleted : ");
                    int id = Integer.parseInt(sc.nextLine());
                    boolean check = prod.checkProduct(id);
                    if(!check){
                        System.out.println("Enter valid Product Id");
                        break;
                    }
                    Thread deleteProductThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Delete Product Thread Started!");
                            prod.deleteProduct(id);
                            System.out.println("Delete Product Thread Stopped!");

                        }
                    });
                    deleteProductThread.start();
                    try {
                        deleteProductThread.join();
                    } catch (InterruptedException e) {
                        System.out.println("Delete Product Thread interrupted: " + e.getMessage());
                    }
                    break;
                }

                case 8 : {
                    prod.purchaseHistory();
                    break;
                }
            }
        }
    }
}