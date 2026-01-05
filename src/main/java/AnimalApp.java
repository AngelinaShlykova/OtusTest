import animals.Animal;
import animals.Color;
import factory.AnimalFactory;
import factory.AnimalType;

import java.util.ArrayList;
import java.util.Arrays;
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

                AnimalType animalType = null;
                List<String> validTypes = Arrays.asList("cat", "dog", "duck");

                while (animalType == null) {
                    System.out.println("Какое животное? (cat/dog/duck):");
                    String typeInput = scanner.next().trim().toLowerCase();

                    if (validTypes.contains(typeInput)) {
                        animalType = AnimalType.valueOf(typeInput.toUpperCase());
                    } else {
                        System.out.println("Неизвестный тип животного: " + typeInput);
                    }
                }

                Animal animal = animalFactory.create(animalType);
                if (animal == null) {
                    System.out.println("Не удалось создать животное.");
                    continue;
                }

                scanner.nextLine();

                System.out.println("Имя животного? :");
                String nameInput = scanner.nextLine();
                if (nameInput == null || nameInput.trim().isEmpty()) {
                    System.out.println("Имя не может быть пустым");
                    continue;
                }
                animal.setName(nameInput.trim());

                int age = 0;
                while (true) {
                    System.out.println("Возраст животного ?: ");
                    String ageInput = scanner.nextLine().trim();

                    if (ageInput.matches("^[1-9]\\d?$")) {
                        age = Integer.parseInt(ageInput);
                        break;
                    } else {
                        System.out.println("Возраст должен быть целым числом от 1 до 99");
                    }
                }
                animal.setAge(age);

                int weight = 0;
                while (true) {
                    System.out.println("Вес животного ?: ");
                    String weightInput = scanner.nextLine().trim();

                    if (weightInput.matches("^[1-9]\\d?$")) {
                        weight = Integer.parseInt(weightInput);
                        break;
                    } else {
                        System.out.println("Вес должен быть целым числом от 1 до 99");
                    }
                }
                animal.setWeight(weight);

                String colorStr = "";
                while (colorStr.isEmpty()) {
                    System.out.println("Цвет животного ?: ");
                    colorStr = scanner.nextLine().trim();
                    if (colorStr.isEmpty()) {
                        System.out.println("Цвет не может быть пустым");
                    }
                }
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
