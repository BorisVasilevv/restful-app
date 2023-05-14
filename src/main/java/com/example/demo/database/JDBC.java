package com.example.demo.database;

import java.sql.*;

public class JBDC {

    public static final String NAME_USER="root";

    public static final String PASSWORD="root";

    public static final String URL="jdbc:mysql://localhost:3306/";

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


    public static void addUser(User user){
        try {
            String update ="insert into users (name,email,password) values (?,?,?)";
            PreparedStatement preparedStatement= connection.prepareStatement(update);
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3,user.getPassword());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static User findUserByEmail(String email){
        try {
            ResultSet resultSet= statement.executeQuery(String.format("SELECT * FROM users " +
                    "WHERE email=\'%s\'",email));
            if(!resultSet.next()) return null;

            String name=resultSet.getString(2);
            String password=resultSet.getString(4);

            return new User(name,password,email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
