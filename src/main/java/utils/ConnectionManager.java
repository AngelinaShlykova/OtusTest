package utils;

import java.sql.*;

public class ConnectionManager {
    private final String connection_url = "jdbc:mysql://localhost:3306/animal_db";
    private final String login = "student";
    private final String password = "student";

    private Connection connection;
    private Statement statement;

    private static ConnectionManager instance;

    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    private ConnectionManager() {
        try {
            connection = DriverManager.getConnection(connection_url, login, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к БД", e);
        }
    }

    public ResultSet executeQuery(String query) {
        try {
            return statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка выполнения запроса: " + query, e);
        }
    }

    public void executeUpdate(String query) {
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка выполнения UPDATE: " + query, e);
        }
    }

    public void close() {
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка закрытия соединения", e);
        }
    }
}
