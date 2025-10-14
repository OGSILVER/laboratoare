import java.util.Random;


public class Woman extends Human{
    float beauty;
    byte bitchness;

    Woman(){
        super();
        this.beauty = new Random().nextFloat();
        this.bitchness = (byte) (new Random().nextInt(127));
    }

    void  print(){
        super.print();
        System.out.println("I am " + ((beauty<0.5)?"not":"vey") + " beautiful nad have " + bitchness + " bitchness!");
    }

    void makeUp(){
        beauty = 1f;
        System.out.println("I am the best");
    }

    @Override
    void sayHi() {
        if(bitchness < 100){
            System.out.println("saluuuut");
        }else{
            System.out.println("3.14!...");
        }
    }

    public void wineDay(){
        beauty = 0.1f;
        bitchness = (byte) 120;
    }
}


