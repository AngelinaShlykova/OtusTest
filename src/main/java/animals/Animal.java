package animals;

public class Animal {

    private static final String name = "Бобик";
    private static final double weight = 15.0;
    private static final String color = "черный";
    private Integer age;


    public Integer getAge() {
        return age;
    }

    public void say() {
        System.out.println("Я говорю");
    }

    public void go() {
        System.out.println("Я иду");
    }

    public void drink() {
        System.out.println("Я пью");
    }

    public void eat() {
        System.out.println("Я ем");
    }

    @Override
    public String toString() {
        String ageNew = String.valueOf(getAge());
        return "Привет! Меня зовут " + name +
                ", мне " + ageNew + " лет, " +
                "я вешу - " + weight + " кг, " +
                "мой цвет - " + color;
    }

    public Animal (Integer age) {
        this.age = age;
    }


}
