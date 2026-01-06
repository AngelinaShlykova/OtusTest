package dao;

import dto.AnimalDto;

import java.util.List;

public interface IAnimalTable {
    List<AnimalDto> findAll();
    void save(AnimalDto animal);
    void update(AnimalDto animal);
    List<AnimalDto> findByType(String type);
}
