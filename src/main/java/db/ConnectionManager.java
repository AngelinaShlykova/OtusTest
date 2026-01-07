package db;

import java.sql.*;

public class ConnectionManager implements IConnectionManager {

    private static ConnectionManager instance;
    private Connection connection;
    private Statement statement;

    private ConnectionManager() {
        openConnect();
    }

    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    @Override
    public void openConnect() {
        String url = System.getenv("DB_URL");
        String login = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        if (url == null || login == null || password == null) {
            throw new RuntimeException(
                    "Не заданы переменные окружения:\n" +
                            "DB_URL=" + url + "\n" +
                            "DB_USER=" + login + "\n" +
                            "DB_PASSWORD=" + password
            );
        }

        try {
            connection = DriverManager.getConnection(url, login, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к MySQL", e);
        }
    }

    @Override
    public void execute(String sqlRequest) {
        try {
            statement.executeUpdate(sqlRequest);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка выполнения UPDATE: " + sqlRequest, e);
        }
    }

    @Override
    public ResultSet executeQuery(String sqlRequest) {
        try {
            return statement.executeQuery(sqlRequest);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка выполнения SELECT: " + sqlRequest, e);
        }
    }

    @Override
    public void close() {
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка закрытия соединения", e);
        }
    }
}
