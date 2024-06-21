package org.example;

import javax.xml.namespace.QName;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class PostgresqlConnect {
    static String url;
    static String user;
    static String password;
    static int latestId;
    PostgresqlConnect(){
        url = "jdbc:postgresql://localhost:5432/postgres";
        user = "gokuld";
        password = "1234";

        String version = "SELECT version()";

        try(Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(version)){
            if(resultSet.next()){
                System.out.println(resultSet.getString(1));
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    static public void insertOrder(double price, int quantity){
        System.out.println( "price : " + price + " " + "quantity : " + quantity);
        String query = "INSERT INTO public.orders(price, quantity, status) VALUES (?, ?, ?) RETURNING *";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setDouble(1, price);
            preparedStatement.setInt(2, quantity);
            preparedStatement.setString(3, "Shipped");

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("order_id"); // Assuming 'id' is a column in your 'orders' table
                double returnedPrice = resultSet.getDouble("price");
                int returnedQuantity = resultSet.getInt("quantity");
                String status = resultSet.getString("status");

                System.out.println("Inserted Row -> ID: " + id + ", Price: " + returnedPrice + ", Quantity: " + returnedQuantity + ", Status: " + status);
                latestId = id;
            }
//            int row = preparedStatement.executeUpdate();
//            System.out.println(row + " row(s) inserted.");
            System.out.println("latestId : " + latestId);
        } catch (SQLException e){
            System.out.println("Message: " + e.getMessage());
        }


    }

    static void insertListOrder(int prodId, double price, int quantity){
        String query = "INSERT INTO public.order_list(orders_id, product_id, orderlist_quantity, orderlist_price) VALUES (?, ?, ?, ?) RETURNING *";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setInt(1, latestId);
            preparedStatement.setInt(2, prodId);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setDouble(4,price);


            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("orderlist_id");
                int ordersid = resultSet.getInt("orders_id"); // Assuming 'id' is a column in your 'orders' table

                int prodid = resultSet.getInt("product_id"); // Assuming 'id' is a column in your 'orders' table

                int returnedQuantity = resultSet.getInt("orderlist_quantity");
                double returnedPrice = resultSet.getDouble("orderlist_price");

                System.out.println("Inserted Row -> ID: " + id + ", ordersId: " + ordersid + ", prodid: " + prodid + ", price : " + returnedPrice + ", Quantity: " + returnedQuantity);
            }

        } catch (SQLException e){
            System.out.println("Message orderlist: " + e.getMessage());
        }
    }


    static int GetOrdersId(){
        return latestId;
    }

    static public void history(){
        String query = "SELECT* FROM public.orders";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)){
            while (resultSet.next()) {
                // Assuming you have columns like id, order_name, and order_date
                int order_id = resultSet.getInt("order_id");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                String status = resultSet.getString("status");
                System.out.println("ID: " + order_id + ", Order price: " + price + ", quantity: " + quantity
                + ", Status : "  + status);
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
