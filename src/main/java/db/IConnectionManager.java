package db;

import java.sql.ResultSet;

public interface IConnectionManager {
    void openConnect();
    void execute(String sqlRequest);
    ResultSet executeQuery(String sqlRequest);
    void close();
}
