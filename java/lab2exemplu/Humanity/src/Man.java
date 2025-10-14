import java.util.Random;

public class Man extends Human {
    double money;
    boolean orientation;


    Man(){
        super();
        this.money = new Random().nextDouble() * 10000;
        this.orientation = (new Random().nextInt(10) < 5);
    }


    Man(String a, int b, double c){
        this.name = a;
        this.age = b;
        this.money = c;

    }

    void print(){
        super.print();
        System.out.println("I have " + money + "$" + ", and i like " + (orientation?"men":"women"));
    }

    void drive(){
        if (money<7000){
            System.out.println("I came here driving my Prius!");
        }else{
            System.out.println("I came here wit ha Bolt.");
        }
    }

    @Override
    void sayHi() {
        if (orientation){
            System.out.println("saluuuut");
        }else{
            System.out.println("zdarova");
        }
    }

    public void wineDay(){
        name = "?";
        money = 0;
        orientation = !orientation;
    }
}
