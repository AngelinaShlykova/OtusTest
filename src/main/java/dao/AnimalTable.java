package dao;

import dto.AnimalDto;
import utils.ConnectionManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnimalTable implements IAnimalTable {

    private final ConnectionManager cm = ConnectionManager.getInstance();

    @Override
    public void save(AnimalDto animal) {
        String sql = String.format(
                "INSERT INTO animals (name, type, color) VALUES ('%s', '%s', '%s')",
                animal.getName().replace("'", "''"),
                animal.getType().replace("'", "''"),
                animal.getColor().replace("'", "''")
        );
        cm.executeUpdate(sql);
    }

    @Override
    public void update(AnimalDto animal) {
        String sql = String.format(
                "UPDATE animals SET name = '%s', type = '%s', color = '%s' WHERE id = %d",
                animal.getName().replace("'", "''"),
                animal.getType().replace("'", "''"),
                animal.getColor().replace("'", "''"),
                animal.getId()
        );
        cm.executeUpdate(sql);
    }

    @Override
    public List<AnimalDto> findAll() {
        return executeQuery("SELECT id, name, type, color FROM animals");
    }

    @Override
    public List<AnimalDto> findByType(String type) {
        String sql = "SELECT id, name, type, color FROM animals WHERE type = '" + type.replace("'", "''") + "'";
        return executeQuery(sql);
    }

    private List<AnimalDto> executeQuery(String sql) {
        List<AnimalDto> animals = new ArrayList<>();
        try (ResultSet rs = cm.executeQuery(sql)) {
            while (rs.next()) {
                AnimalDto animal = mapResultSetToAnimalDto(rs);
                animals.add(animal);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса: " + sql, e);
        }
        return animals;
    }

    private AnimalDto mapResultSetToAnimalDto(ResultSet rs) throws SQLException {
        AnimalDto animal = new AnimalDto();
        animal.setId(rs.getInt("id"));
        animal.setName(rs.getString("name"));
        animal.setType(rs.getString("type"));
        animal.setColor(rs.getString("color"));
        return animal;
    }
}
