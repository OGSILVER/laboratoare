import java.util.Random;


public abstract class Human implements celebration {
    String name;
    int age;
    static final int maxAge = 200;

    Human(){
        String[] randName = {"Ion", "Vasile", "Macim", "Alex", "Sasha"};
        name = randName[new Random().nextInt(randName.length)];
        age = new Random().nextInt(maxAge);
    }

    Human(String a, int b){
        this.name = a;
        this.age = b;
    }

    void print(){
        System.out.println("Hello my name is " + name + ", I am " + age + " years old.");
    }

    abstract void sayHi();



}


