/*
Varianta № 00

Se da clasa Snusmumrel cu 2 campuri: câmp de tip intreg (int) pentru păstrarea numărul de farici si un vector dinamic de tip real (float), în care se păstrează informaţia despre fiecare faric (greutatea în kg);
 De descris program, ce contine:

1. Variabila statică (câmpul clasei date) pentru calcularea numărului de obiecte create a clasei date
2. Constructor implicit al clasei date
3. Constructor cu un parametru intreg al clasei date
4. Constructor de copiere al clasei date
5. Metoda set pentru primul câmp (dimensiunea vectorului dinamic) cu realocarea memoriei p/u câmpul al doilea
6. Metoda abstracta ai clasei date void print()
7. Clasa mostenită de la clasa dată – Marfufica cu un câmp nou intreg (short) - lungimea cozii
8. Constructor cu 2 parametrii a clasei mostenite (int, short) cu apelarea la constructor cu parametru din clasa părinte 
9. Interfata Frica, ce contine metoda void ghemui() si implementarea acestei interfete in clasa mostenită
10. In metoda main clasei mostenite de creat un vector dinamic de tip – clasa dată (clasa părinte), si de completat vectorul creat cu obiectele clasei mostenite.

Nota = num. de puncte indeplinite
 */
interface Frica {

    void ghemui();
}

abstract class Smusmumrel {

    int nrFarici;
    float[] greutatea;
    static int nrObiecte;

    Smusmumrel() {
        nrFarici = 2;
        greutatea = new float[nrFarici];
        nrObiecte++;
    }

    public Smusmumrel(int x) {
        if (x >= 0) {
            nrFarici = x;
            greutatea = new float[nrFarici];
        }
        nrObiecte++;
    }

    Smusmumrel(Smusmumrel ZiuZea) {
        nrObiecte++;
        nrFarici = ZiuZea.nrFarici;
        greutatea = new float[nrFarici];
        for (int i = 0; i < nrFarici; i++) {
            greutatea[i] = ZiuZea.greutatea[i];
        }
    }

    void setFarici(int newNr) {
        if (newNr > 0 && newNr != nrFarici) {
            float tmp[] = new float[nrFarici];
            for (int i = 0; i < nrFarici; i++) {
                tmp[i] = greutatea[i];
            }
            greutatea = new float[newNr];
            int min = nrFarici < newNr ? nrFarici : newNr;
            for (int i = 0; i < min; i++) {
                greutatea[i] = tmp[i];
            }
            nrFarici = newNr;
        }
    }

    abstract void print();
}

class Marfufica extends Smusmumrel implements Frica {

    short lungCozii;

    Marfufica(int x, short y) {
        super(x);
        lungCozii = y;
    }

    void print() {
    }

    void ghemui() {
    }

    public static void main(String[] args) {
        Smusmumrel v[] = new Smusmumrel[10];
        for (int i = 0; i < v.length; i++) {
            v[i] = new Marfufica();
        }
    }
}
