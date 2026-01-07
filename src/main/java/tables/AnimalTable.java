package tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimalTable extends AbsTable {

    public AnimalTable() {
        super("animals");
        create(
                "id INT AUTO_INCREMENT PRIMARY KEY",
                "name VARCHAR(255) NOT NULL",
                "type ENUM('CAT','DOG','DUCK') NOT NULL",
                "color VARCHAR(50)"
        );
    }

    @Override
    public void save(Object entity) {
        if (!(entity instanceof Map)) {
            throw new IllegalArgumentException("Ожидается Map<String, Object>");
        }
        @SuppressWarnings("unchecked")
        Map<String, Object> animal = (Map<String, Object>) entity;

        String name = getString(animal, "name");
        String type = getString(animal, "type");
        String color = getString(animal, "color");

        if (name == null || type == null) {
            throw new IllegalArgumentException("Имя и тип обязательны");
        }

        String sql = String.format(
                "INSERT INTO animals (name, type, color) VALUES ('%s', '%s', '%s')",
                name.replace("'", "''"),
                type.replace("'", "''"),
                color.replace("'", "''")
        );
        connectionManager.execute(sql);
    }

    @Override
    public void update(Object entity) {
        if (!(entity instanceof Map)) {
            throw new IllegalArgumentException("Ожидается Map<String, Object>");
        }
        @SuppressWarnings("unchecked")
        Map<String, Object> animal = (Map<String, Object>) entity;

        int id = getInt(animal, "id");
        String name = getString(animal, "name");
        String type = getString(animal, "type");
        String color = getString(animal, "color");

        if (id <= 0 || name == null || type == null) {
            throw new IllegalArgumentException("ID, имя и тип обязательны");
        }

        String checkSql = "SELECT COUNT(*) FROM animals WHERE id = " + id;
        try {
            ResultSet rs = connectionManager.executeQuery(checkSql);
            if (rs.next() && rs.getInt(1) == 0) {
                throw new RuntimeException("Животное с ID=" + id + " не найдено в базе данных.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при проверке существования животного", e);
        }

        String sql = String.format(
                "UPDATE animals SET name = '%s', type = '%s', color = '%s' WHERE id = %d",
                name.replace("'", "''"),
                type.replace("'", "''"),
                color.replace("'", "''"),
                id
        );
        connectionManager.execute(sql);
    }

    @Override
    public Object findById(int id) {
        String sql = "SELECT id, name, type, color FROM animals WHERE id = " + id;
        try {
            ResultSet rs = connectionManager.executeQuery(sql);
            if (rs.next()) {
                return mapResultSetToMap(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске по ID", e);
        }
        return null;
    }

    @Override
    public Object findAll() {
        List<Map<String, Object>> animals = new ArrayList<>();
        String sql = "SELECT id, name, type, color FROM animals";
        try {
            ResultSet rs = connectionManager.executeQuery(sql);
            while (rs.next()) {
                animals.add(mapResultSetToMap(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении всех животных", e);
        }
        return animals;
    }

    public List<Map<String, Object>> findByType(String type) {
        List<Map<String, Object>> animals = new ArrayList<>();
        String sql = "SELECT id, name, type, color FROM animals WHERE type = '" + type.replace("'", "''") + "'";
        try {
            ResultSet rs = connectionManager.executeQuery(sql);
            while (rs.next()) {
                animals.add(mapResultSetToMap(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при фильтрации по типу", e);
        }
        return animals;
    }

    private Map<String, Object> mapResultSetToMap(ResultSet rs) throws SQLException {
        Map<String, Object> animal = new HashMap<>();
        animal.put("id", rs.getInt("id"));
        animal.put("name", rs.getString("name"));
        animal.put("type", rs.getString("type"));
        animal.put("color", rs.getString("color"));
        return animal;
    }

    private String getString(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value != null ? value.toString() : null;
    }

    private int getInt(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return 0;
    }
}
