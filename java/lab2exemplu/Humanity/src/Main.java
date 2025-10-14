//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Human[] party = new Human[6];
        for (int i = 0 ; i < party.length ; i+=2){
            party[i] = new Man();
            party[i+1] = new Woman();
        }

        for (int i = 0; i < party.length ; i++){
            party[i].print();
            System.out.println();
        }

        Human Vasea = new Man();
        Vasea.print();
        ((Man)Vasea).drive();


        //de gasit toate femeile la petrecere si de facut machiaj


        for ( int i = 0; i < party.length; i++){
            if (party[i] instanceof Woman){
                ((Woman) party[i]).makeUp();
            }
        }

        //de gasit barbatii la petrecere si de pus la volan

        for ( int i = 0; i < party.length; i++){
            if (party[i] instanceof Man){
                ((Man) party[i]).drive();
            }
        }

        for (int i = 0; i<party.length; i++){
            party[i].sayHi();
            party[i].wineDay();
            party[i].sayHi();
        }

    }
}