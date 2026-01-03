package animals;

public class Duck extends Animal implements Flying {
    public Duck(String name, Integer age, Integer weight, String color) {
        super(age);
    }

    @Override
    public void say() {
        super.say();
        System.out.println("Кря");
    }

    @Override
    public void fly() {
        System.out.println("Я лечу");
    }
}
