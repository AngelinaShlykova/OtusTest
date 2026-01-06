package service;

import dao.IAnimalTable;
import dto.AnimalDto;

import java.awt.*;
import java.util.List;

public class AnimalService {
    private final IAnimalTable animalTable;

    public AnimalService(IAnimalTable animalTable) {
        this.animalTable = animalTable;
    }

    public void saveAnimal(AnimalDto animal) {
        animalTable.save(animal);
    }

    public void updateAnimal(AnimalDto animal) {
        animalTable.update(animal);
    }

    public List<AnimalDto> findAll() {
        return animalTable.findAll();
    }

    public List<AnimalDto> findByType(String type) {
        return animalTable.findByType(type);
    }
}
