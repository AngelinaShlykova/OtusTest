package tables;

import db.ConnectionManager;
import db.IConnectionManager;

public abstract class AbsTable {

    protected IConnectionManager connectionManager;
    protected String tableName;

    public AbsTable(String tableName) {
        this.tableName = tableName;
        this.connectionManager = ConnectionManager.getInstance();
    }

    public void create(String... columns) {
        String columnsStr = String.join(", ", columns);
        String sql = String.format("CREATE TABLE IF NOT EXISTS %s (%s)", tableName, columnsStr);
        connectionManager.execute(sql);
    }

    public void delete() {
        String sql = String.format("DROP TABLE IF EXISTS %s", tableName);
        connectionManager.execute(sql);
    }

    public boolean exist() {
        String sql = "SHOW TABLES LIKE '" + tableName + "'";
        try {
            java.sql.ResultSet rs = connectionManager.executeQuery(sql);
            return rs.next();
        } catch (Exception e) {
            return false;
        }
    }

    public abstract void save(Object entity);
    public abstract void update(Object entity);
    public abstract Object findById(int id);
    public abstract Object findAll();
}
