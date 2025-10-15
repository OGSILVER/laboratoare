import jdk.jfr.FlightRecorder;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args)
    {


        Card arr[] = new Card[5];
        arr[0] = new RareCard();
        arr[1] = new RareCard(200, 30, "RareCard1", 5);
        arr[2] = new EpicCard(300, 40, "EpicCard1", 10);
        arr[3] = new EpicCard();
        arr[4] = new LegendaryCard(400,60,"LegendaryCard1",15,100);


        FlyingTroop fliers[] = new FlyingTroop[2];
        fliers[0] = new EpicCard();
        fliers[1] = new LegendaryCard();


        ((RareCard) arr[0]).jumpBridge();
        ((EpicCard) arr[2]).dodgeAbility();
        ((LegendaryCard) arr[4]).useUltimate();


        for(Card c : arr){
            System.out.println(c);
        }

        for(FlyingTroop c : fliers){
            System.out.println(c);
        }


    }
}
