import animals.Animal;
import animals.Cat;
import animals.Dog;
import animals.Duck;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Zoo  {
    public static void main(String[] args) {

        Animal dog1 = new Animal (5);
        System.out.println(dog1);

        Animal dog2 = new Animal (8);
        System.out.println(dog2);

        Animal dog3 = new Animal (6);
        System.out.println(dog3);

        ArrayList<Animal> animals = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Запрос команды add / list / exit :");
            String command = scanner.nextLine().trim().toLowerCase();

            if (command.equals("add")) {
                System.out.println("Какое животное (cat / dog / duck) ?");
                String type = scanner.nextLine().trim().toLowerCase();

                System.out.println("Имя животного? ");
                String name = scanner.nextLine().trim();

                System.out.println("Возраст животного? ");
                Integer age = Integer.parseInt(scanner.nextLine().trim());

                System.out.println("Вес животного? ");
                Integer weight = Integer.parseInt(scanner.nextLine().trim());

                System.out.println("Цвет животного? ");
                String color = scanner.nextLine().trim();

                Animal animal = null;

                if (type.equals("cat")) {
                    animal = new Cat(name, age, weight, color);
                } else if (type.equals("dog")) {
                    animal = new Dog(name, age, weight, color);
                } else if (type.equals("duck")) {
                    animal = new Duck(name, age, weight, color);
                } else {
                    System.out.println("Такого вида животного нет.");
                    continue;
                }

                animals.add(animal);
                System.out.println("Животное добавлено");

            } else if (command.equals("list")) {
                if (animals.isEmpty()) {
                    System.out.println("Список животных пуст.");
                } else {
                    for (Animal a : animals) {
                        System.out.println(a.toString());
                    }
                }

            } else if (command.equals("exit")) {
                System.out.println("Выход из программы.");
                break;

            } else {
                System.out.println("Неизвестная команда. Допустимые команды: add, list, exit.");
            }
        }
        scanner.close(); // закрываем Scanner
    }

}



