import dto.AnimalDto;
import factory.AnimalFactoryTable;
import service.AnimalService;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AnimalDbApp {

    private static final List<String> VALID_TYPES = Arrays.asList("cat", "dog", "duck");

    public static void main(String[] args) {
        AnimalService service = new AnimalService(new AnimalFactoryTable().getProd());
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Меню управления животными:");
            System.out.println("1. Добавить животное");
            System.out.println("2. Показать всех животных");
            System.out.println("3. Найти по типу (cat/dog/duck)");
            System.out.println("4. Редактировать животное");
            System.out.println("5. Выход");
            System.out.print("Выберите действие: ");

            int choice = getIntInput(scanner);

            switch (choice) {
                case 1:
                    addAnimal(service, scanner);
                    break;
                case 2:
                    showAllAnimals(service);
                    break;
                case 3:
                    filterByType(service, scanner);
                    break;
                case 4:
                    editAnimal(service, scanner);
                    break;
                case 5:
                    System.out.println("Выход из программы.");
                    return;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, введите число от 1 до 5.");
            }
        }
    }

    private static void addAnimal(AnimalService service, Scanner scanner) {
        String typeInput = null;
        while (typeInput == null) {
            System.out.println("Какое животное? (cat/dog/duck):");
            String input = scanner.nextLine().trim().toLowerCase();
            if (VALID_TYPES.contains(input)) {
                typeInput = input.toUpperCase();
            } else {
                System.out.println("Неизвестный тип животного: " + input + ". Допустимые: cat, dog, duck");
            }
        }

        String name = "";
        while (name.isEmpty()) {
            System.out.println("Имя животного:");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Имя не может быть пустым.");
            }
        }

        String color = "";
        while (color.isEmpty()) {
            System.out.println("Цвет животного:");
            color = scanner.nextLine().trim();
            if (color.isEmpty()) {
                System.out.println("Цвет не может быть пустым.");
            }
        }

        AnimalDto animal = new AnimalDto();
        animal.setName(name);
        animal.setType(typeInput);
        animal.setColor(color);
        service.saveAnimal(animal);
        System.out.println("Животное успешно сохранено в базе данных.");
    }

    private static void editAnimal(AnimalService service, Scanner scanner) {
        int id = 0;
        while (true) {
            System.out.println("Введите ID животного для редактирования:");
            String idStr = scanner.nextLine().trim();
            if (idStr.matches("\\d+") && !idStr.equals("0")) {
                id = Integer.parseInt(idStr);
                break;
            }
            System.out.println("ID должен быть положительным целым числом.");
        }

        String typeInput = null;
        while (typeInput == null) {
            System.out.println("Новый тип (cat/dog/duck):");
            String input = scanner.nextLine().trim().toLowerCase();
            if (VALID_TYPES.contains(input)) {
                typeInput = input.toUpperCase();
            } else {
                System.out.println("Неизвестный тип: " + input + ". Допустимые: cat, dog, duck");
            }
        }

        String name = "";
        while (name.isEmpty()) {
            System.out.println("Новое имя:");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Имя не может быть пустым.");
            }
        }

        String color = "";
        while (color.isEmpty()) {
            System.out.println("Новый цвет:");
            color = scanner.nextLine().trim();
            if (color.isEmpty()) {
                System.out.println("Цвет не может быть пустым.");
            }
        }

        AnimalDto animal = new AnimalDto();
        animal.setId(id);
        animal.setName(name);
        animal.setType(typeInput);
        animal.setColor(color);
        service.updateAnimal(animal);
        System.out.println("Животное с ID=" + id + " успешно обновлено");
    }

    private static void showAllAnimals(AnimalService service) {
        List<AnimalDto> animals = service.findAll();
        if (animals.isEmpty()) {
            System.out.println("В базе данных нет животных.");
        } else {
            System.out.println("Все животные:");
            animals.forEach(System.out::println);
        }
    }

    private static void filterByType(AnimalService service, Scanner scanner) {
        String typeInput = null;
        while (typeInput == null) {
            System.out.println("Введите тип для фильтрации (cat/dog/duck):");
            String input = scanner.nextLine().trim().toLowerCase();
            if (VALID_TYPES.contains(input)) {
                typeInput = input.toUpperCase();
            } else {
                System.out.println("Неизвестный тип: " + input + ". Допустимые: cat, dog, duck");
            }
        }

        List<AnimalDto> animals = service.findByType(typeInput);
        if (animals.isEmpty()) {
            System.out.println("Животных типа '" + typeInput + "' не найдено.");
        } else {
            System.out.println("Животные типа '" + typeInput + "':");
            animals.forEach(System.out::println);
        }
    }

    private static int getIntInput(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.matches("\\d+")) {
                return Integer.parseInt(input);
            }
            System.out.print("Введите число: ");
        }
    }
}