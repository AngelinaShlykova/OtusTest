package factory;

import dao.AnimalTable;
import dao.IAnimalTable;
import dao.mock.MockAnimalTable;

public class AnimalFactoryTable {

    public IAnimalTable getProd() {
        return new AnimalTable();
    }

    public IAnimalTable getMock() {
        return new MockAnimalTable();
    }
}
