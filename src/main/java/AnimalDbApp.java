import tables.AnimalTable;
import java.util.*;

public class AnimalDbApp {

    public static void main(String[] args) {
        AnimalTable animalTable = new AnimalTable();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Меню управление животными");
            System.out.println("1. Добавить животное");
            System.out.println("2. Показать всех животных");
            System.out.println("3. Найти по типу (CAT/DOG/DUCK)");
            System.out.println("4. Редактировать животное");
            System.out.println("5. Выход");
            System.out.print("Выберите действие: ");

            int choice = getIntInput(scanner);

            switch (choice) {
                case 1:
                    addAnimal(animalTable, scanner);
                    break;
                case 2:
                    showAllAnimals(animalTable);
                    break;
                case 3:
                    filterByType(animalTable, scanner);
                    break;
                case 4:
                    editAnimal(animalTable, scanner);
                    break;
                case 5:
                    System.out.println("Выход из программы.");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private static void addAnimal(AnimalTable table, Scanner scanner) {
        System.out.print("Имя: ");
        String name = scanner.nextLine().trim();

        System.out.print("Тип (CAT/DOG/DUCK) ");
        String type = scanner.nextLine().trim().toUpperCase();

        System.out.print("Цвет: ");
        String color = scanner.nextLine().trim();

        if (name.isEmpty() || type.isEmpty()) {
            System.out.println("Имя и тип обязательны!");
            return;
        }

        Map<String, Object> animal = new HashMap<>();
        animal.put("name", name);
        animal.put("type", type);
        animal.put("color", color);

        table.save(animal);
        System.out.println("Животное сохранено в базе данных");
    }

    private static void showAllAnimals(AnimalTable table) {
        List<Map<String, Object>> animals = (List<Map<String, Object>>) table.findAll();
        if (animals.isEmpty()) {
            System.out.println("В базе данных пока нет животных.");
        } else {
            System.out.println("Все животные:");
            for (Map<String, Object> animal : animals) {
                System.out.printf("ID=%s, Имя=%s, Тип=%s, Цвет=%s%n",
                        animal.get("id"),
                        animal.get("name"),
                        animal.get("type"),
                        animal.get("color")
                );
            }
        }
    }

    private static void filterByType(AnimalTable table, Scanner scanner) {
        System.out.print("Введите тип (CAT / DOG / DUCK): ");
        String type = scanner.nextLine().trim().toUpperCase();

        List<Map<String, Object>> animals = table.findByType(type);
        if (animals.isEmpty()) {
            System.out.println("Животных типа '" + type + "' не найдено.");
        } else {
            System.out.println("Животные типа '" + type + "':");
            for (Map<String, Object> animal : animals) {
                System.out.printf("ID=%s, Имя=%s, Тип=%s, Цвет=%s%n",
                        animal.get("id"),
                        animal.get("name"),
                        animal.get("type"),
                        animal.get("color")
                );
            }
        }
    }

    private static void editAnimal(AnimalTable table, Scanner scanner) {
        System.out.print("Введите ID животного для редактирования: ");
        int id = getIntInput(scanner);

        System.out.print("Новое имя: ");
        String name = scanner.nextLine().trim();

        System.out.print("Новый тип (CAT / DOG / DUCK): ");
        String type = scanner.nextLine().trim().toUpperCase();

        System.out.print("Новый цвет: ");
        String color = scanner.nextLine().trim();

        if (name.isEmpty() || type.isEmpty()) {
            System.out.println("Имя и тип обязательны");
            return;
        }

        Map<String, Object> animal = new HashMap<>();
        animal.put("id", id);
        animal.put("name", name);
        animal.put("type", type);
        animal.put("color", color);

        table.update(animal);
        System.out.println("Животное с ID=" + id + " обновлено");
    }

    private static int getIntInput(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Введите число: ");
            }
        }
    }
}