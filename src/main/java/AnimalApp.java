import animals.Animal;
import animals.Color;
import factory.AnimalFactory;
import factory.AnimalType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AnimalApp {


    public static void main(String[] args) {
        List<Animal> animals = new ArrayList<>();
        AnimalFactory animalFactory = new AnimalFactory();

        Scanner scanner = new Scanner(System.in);
        Command currentCommand = null;



        while (currentCommand != Command.EXIT) {
            currentCommand = getCommand(scanner);
            if (currentCommand == Command.LIST) {
                if (animals.isEmpty()) {
                    System.out.println("Список пуст");
                }
                for (Animal animal : animals) {
                    System.out.println(animal);
                }
            } else if (currentCommand == Command.ADD) {

                System.out.println("Какое животное? (cat/dog/duck):");
                String typeInput = scanner.next().trim().toLowerCase();

                AnimalType animalType;
                try {
                    animalType = AnimalType.valueOf(typeInput.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Неизвестный тип животного: " + typeInput);
                    continue;
                }

                Animal animal = animalFactory.create(animalType);
                if (animal == null) {
                    System.out.println("Не удалось создать животное.");
                    continue;
                }

                scanner.nextLine();

                System.out.println("Имя животного? :");
                String name = scanner.nextLine().trim();
                animal.setName(name);

                System.out.println("Возраст животного ?:");
                int age = Integer.parseInt(scanner.nextLine().trim());
                animal.setAge(age);

                System.out.println("Вес животного ?:");
                int weight = Integer.parseInt(scanner.nextLine().trim());
                animal.setWeight(weight);

                System.out.println("Цвет животного ?:");
                String colorStr = scanner.nextLine().trim();
                animal.setColor(Color.fromString(colorStr));

                animals.add(animal);
                animal.say();
            }
        }
    }

    private static Command getCommand(Scanner scanner) {
        String commandInput = null;
        while (Command.doeNotContains(commandInput)) {
            if (commandInput != null) {
                System.out.println("Введена неверная команда");
            }
            System.out.printf("Введите одну из команд (%s) :", String.join("/", Command.NAMES));
            commandInput = scanner.next();
        }
        return Command.fromString(commandInput);
    }
}
