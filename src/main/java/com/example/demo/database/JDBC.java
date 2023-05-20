package com.example.demo.database;

import com.example.demo.model.Product;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;

@Component
public class JDBC {


    public static final String NAME_USER="root";

    public static final String PASSWORD="root";

    public static final String URL="jdbc:mysql://localhost:3306/bdnar";

    public static Connection connection;

    public static Statement statement;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL,NAME_USER,PASSWORD);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    static {
        try {
            statement=connection.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void addPurchase(Product product){
        try {
            String update ="insert into purchase (name,price,status) values (?,?,?)";
            PreparedStatement preparedStatement= connection.prepareStatement(update);
            preparedStatement.setString(1,product.getName());
            preparedStatement.setString(2, Integer.toString(product.getPrice()));
            preparedStatement.setString(3, product.getStatus().toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removePurchase(String id){
        try {
            String update =String.format("DELETE FROM purchase WHERE id=%s", id);

            PreparedStatement preparedStatement= connection.prepareStatement(update);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Product> selectAllPurchase(){
        ArrayList<Product> products=new ArrayList<>();
        try {
            ResultSet resultSet= statement.executeQuery(String.format("SELECT * FROM purchase"));
            while (resultSet.next()){

                products.add(new Product(resultSet.getInt(1),
                        resultSet.getString(2),resultSet.getInt(3),resultSet.getString(4)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    public static boolean isElemExist(String id){
        try {
            ResultSet resultSet= statement.executeQuery(String.format("SELECT * FROM purchase WHERE id='%s'", id));
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void updatePurchase(String  id, Product product){
        try {
            String update=String.format("SELECT * FROM purchase " +
                    "SET name='%s', price=%s, status='%s' WHERE id=%s",
                    product.getName(),product.getPrice(), product.getStatus().toString(),id);
            PreparedStatement preparedStatement= connection.prepareStatement(update);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
