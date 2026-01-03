package animals;

public class Dog extends Animal {
    public Dog(String name, Integer age, Integer weight, String color) {
        super(age);
    }

    @Override
    public void say() {
        super.say();
        System.out.println("Гав");
    }
}
