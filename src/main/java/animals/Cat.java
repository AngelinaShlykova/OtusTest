package animals;

public class Cat extends Animal {

    public Cat(String name, Integer age, Integer weight, String color) {
        super(age);
    }

    @Override
    public void say() {
        super.say();
        System.out.println("Мяу");
    }
}
