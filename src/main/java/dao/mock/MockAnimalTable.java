package dao.mock;

import dao.IAnimalTable;
import dto.AnimalDto;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MockAnimalTable implements IAnimalTable {

    private static final AtomicInteger idGenerator = new AtomicInteger(1);
    private final List<AnimalDto> animals = new ArrayList<>();

    public MockAnimalTable() {
        animals.add(new AnimalDto(idGenerator.getAndIncrement(), "Барсик", "CAT", "серый"));
        animals.add(new AnimalDto(idGenerator.getAndIncrement(), "Муся", "CAT", "белая"));
    }

    @Override
    public void save(AnimalDto animal) {
        animal.setId(idGenerator.getAndIncrement());
        animals.add(animal);
    }

    @Override
    public void update(AnimalDto animal) {
        for (int i = 0; i < animals.size(); i++) {
            if (animals.get(i).getId() == animal.getId()) {
                animals.set(i, animal);
                return;
            }
        }
        throw new RuntimeException("Животное с id=" + animal.getId() + " не найдено");
    }

    @Override
    public List<AnimalDto> findAll() {
        return new ArrayList<>(animals);
    }

    @Override
    public List<AnimalDto> findByType(String type) {
        List<AnimalDto> result = new ArrayList<>();
        for (AnimalDto animal : animals) {
            if (animal.getType().equals(type)) {
                result.add(animal);
            }
        }
        return result;
    }
}
