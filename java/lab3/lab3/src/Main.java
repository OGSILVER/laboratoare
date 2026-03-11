import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Prof {
    private String nume;
    private String obiect;
    private double rautaciune;
    private float galagie;
    private ArrayList<Studenti> nrStudenti;
    private static int profesori = 0;

    // Constructor implicit
    public Prof() {
        this.nume = "Anonim";
        this.obiect = "Necunoscut";
        this.rautaciune = 0.0;
        this.galagie = 60.0f;
        this.nrStudenti = new ArrayList<>();
        profesori++;
    }

    // Constructor cu parametri (simplu)
    public Prof(String nume, String obiect) {
        this.nume = nume;
        this.obiect = obiect;
        this.rautaciune = 0.0;
        this.galagie = 60.0f;
        this.nrStudenti = new ArrayList<>();
        profesori++;
    }

    // Constructor complet
    public Prof(String nume, String obiect, double rautaciune, float galagie, ArrayList<Studenti> nrStudenti) {
        this.nume = nume;
        this.obiect = obiect;
        this.rautaciune = rautaciune;
        this.galagie = galagie;
        this.nrStudenti = new ArrayList<>(nrStudenti);
        profesori++;
    }

    // Constructor de copiere
    public Prof(Prof p) {
        this.nume = p.nume;
        this.obiect = p.obiect;
        this.rautaciune = p.rautaciune;
        this.galagie = p.galagie;
        this.nrStudenti = new ArrayList<>(p.nrStudenti);
        profesori++;
    }

    // Getteri
    public String getNume() { return nume; }
    public String getObiect() { return obiect; }
    public double getRautaciune() { return rautaciune; }
    public float getGalagie() { return galagie; }
    public ArrayList<Studenti> getNrStudenti() { return nrStudenti; }
    public static int getProfesori() { return profesori; }

    // Setteri cu excepții
    public void setNume(String nume) throws NumeInvalidException {
        if (nume == null || nume.trim().isEmpty()) {
            throw new NumeInvalidException("Nume invalid!", nume);
        }
        this.nume = nume;
    }

    public void setObiect(String obiect) throws ObiectInvalidException {
        if (obiect == null || obiect.trim().isEmpty()) {
            throw new ObiectInvalidException("Obiect invalid!", obiect);
        }
        this.obiect = obiect;
    }

    public void setRautaciune(double rautaciune) throws RautaciuneInvalidException {
        if (rautaciune < 0 || rautaciune > 100) {
            throw new RautaciuneInvalidException("Rautaciune invalida (0-100)!", rautaciune);
        }
        this.rautaciune = rautaciune;
    }

    public void setGalagie(float galagie) throws GalagieInvalidException {
        if (galagie < 0) {
            throw new GalagieInvalidException("Galagie invalida!", galagie);
        }
        this.galagie = galagie;
    }

    public void setNrStudenti(ArrayList<Studenti> nrStudenti) {
        this.nrStudenti = new ArrayList<>(nrStudenti);
    }

    public void setNrStudenti(int newNumarStudenti){
        if (newNumarStudenti > 0 && newNumarStudenti != nrStudenti.size() && newNumarStudenti < 200) {
            int diff = nrStudenti.size() - newNumarStudenti;

            if (diff > 0){
                for (int i = 0; i < newNumarStudenti; i++) {
                    nrStudenti.add(new Studenti());
                }
            }else{
                for (int i = 0; i > diff; i--) {
                    nrStudenti.removeFirst();
                }

            }
        }
    }

    // Afișare
    public void afisare() {
        System.out.println("Nume: " + nume);
        System.out.println("Obiect: " + obiect);
        System.out.println("Rautaciune: " + rautaciune + "%");
        System.out.println("Galagie: " + galagie + " dB");
        System.out.println("Studenti:");
        for (Studenti s : nrStudenti) {
            System.out.println("  - " + s);
        }
    }

    // Suma studenților
    public long sumaSarmanilor() {
        return nrStudenti.size();
    }


    public void citireProf() {
        Scanner sc = new Scanner(System.in);
        boolean flag;

        // Nume
        do {
            flag = false;
            try {
                System.out.print("Nume: ");
                this.nume = sc.nextLine();
                if (this.nume == null || this.nume.trim().isEmpty()) {
                    throw new NumeInvalidException("Nume invalid!", this.nume);
                }
            } catch (NumeInvalidException e) {
                flag = true;
                this.nume = e.prelucrare();
            }
        } while (flag);

        // Obiect
        do {
            flag = false;
            try {
                System.out.print("Obiect: ");
                this.obiect = sc.nextLine();
                if (this.obiect == null || this.obiect.trim().isEmpty()) {
                    throw new ObiectInvalidException("Obiect invalid!", this.obiect);
                }
            } catch (ObiectInvalidException e) {
                flag = true;
                this.obiect = e.prelucrare();
            }
        } while (flag);

        // Rautaciune
        do {
            flag = false;
            try {
                System.out.print("Rautaciune (0-100): ");
                while (!sc.hasNextDouble()) {
                    System.out.println("Introdu un numar valid!");
                    sc.next();
                }
                this.rautaciune = sc.nextDouble();
                if (rautaciune < 0 || rautaciune > 100) {
                    throw new RautaciuneInvalidException("Rautaciune invalida!", rautaciune);
                }
            } catch (RautaciuneInvalidException e) {
                flag = true;
                this.rautaciune = e.prelucrare();
            } catch (Exception e){
                flag = true;
                System.out.println("gresit!");

            }
        } while (flag);

        // Galagie
        do {
            flag = false;
            try {
                System.out.print("Galagie (dB): ");
                while (!sc.hasNextFloat()) {
                    System.out.println("Introdu un numar valid!");
                    sc.next();
                }
                this.galagie = sc.nextFloat();
                if (galagie < 0) {
                    throw new GalagieInvalidException("Galagie invalida!", galagie);
                }
            } catch (GalagieInvalidException e) {
                flag = true;
                this.galagie = e.prelucrare();
            }
        } while (flag);

        // Citire studenti
        this.nrStudenti = new ArrayList<>();
        System.out.print("Cati studenti vrei sa adaugi? ");
        int nr = sc.nextInt();
        sc.nextLine(); // consume newline

        for (int i = 0; i < nr; i++) {
            System.out.println("Student #" + (i + 1) + ":");
            this.nrStudenti.add(new Studenti());
        }
    }

    // Valori random
    public void randomProf() {
        Random r = new Random();

        String[] numePosibile = {"Popescu", "Ionescu", "Marinescu", "Vasilescu", "Dumitru"};
        String[] obiectePosibile = {"Matematica", "Fizica", "Java", "C++", "Baze de date"};

        this.nume = numePosibile[r.nextInt(numePosibile.length)];
        this.obiect = obiectePosibile[r.nextInt(obiectePosibile.length)];
        this.rautaciune = r.nextDouble() * 100;
        this.galagie = 40 + r.nextFloat() * 80;

        this.nrStudenti = new ArrayList<>();
        int nrStud = 1 + r.nextInt(5);
        for (int i = 0; i < nrStud; i++) {
            this.nrStudenti.add(new Studenti("Student" + (i + 1), (short)(i + 1), 100000 + i, new int[]{5,6,7,8,9}));
        }
    }

    // Comparare popularitate
    public void popularProf(Prof b) {
        if (this.sumaSarmanilor() >= b.sumaSarmanilor()) {
            System.out.println("Profesorul cel mai popular: " + this.nume + " (" + this.obiect + ")");
        } else {
            System.out.println("Profesorul cel mai popular: " + b.nume + " (" + b.obiect + ")");
        }
    }

    // Functie statica - stres
    public static void stresulStudentului(Prof a, Prof b) {
        double stresA = a.rautaciune * 0.7 + a.galagie * 0.3;
        double stresB = b.rautaciune * 0.7 + b.galagie * 0.3;

        System.out.println("Stres " + a.nume + ": " + String.format("%.2f", stresA));
        System.out.println("Stres " + b.nume + ": " + String.format("%.2f", stresB));

        if (stresA > stresB)
            System.out.println(a.nume + " este mai stresant cu " + String.format("%.2f", stresA - stresB));
        else if (stresB > stresA)
            System.out.println(b.nume + " este mai stresant cu " + String.format("%.2f", stresB - stresA));
        else
            System.out.println("Ambii profesori sunt la fel de stresanti");
    }

    // Salvare în fișier
    public void salvareInFisier(String fileName) {
        try (FileWriter fw = new FileWriter(fileName)) {
            fw.write(nume + "\n");
            fw.write(obiect + "\n");
            fw.write(rautaciune + "\n");
            fw.write(galagie + "\n");
            for (Studenti s : nrStudenti) {
                fw.write(s.toString() + "\n");
            }
            System.out.println("Salvat in: " + fileName);
        } catch (IOException e) {
            System.out.println("Eroare la salvare: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("=== TEST EXCEPTII - CREARE PROFESORI ===\n");

        // Constructor implicit
        Prof p1 = new Prof();
        System.out.println("Prof 1 (constructor implicit):");
        p1.afisare();
        System.out.println();

        // Test METODA LENESA - setteri care arunca exceptii
        System.out.println("=== TEST METODA LENESA (SETTERI) ===");
        Prof p2 = new Prof();
        boolean flag = false;

        p2.citireProf();

        System.out.println("\nProf 2 dupa setari:");
        p2.afisare();
        System.out.println();


        ArrayList<Studenti> lista = new ArrayList<>();
        lista.add(new Studenti("Ion Popescu", (short)1, 123456, new int[]{8,9,7,8,9}));
        lista.add(new Studenti("Maria Ionescu", (short)2, 123457, new int[]{10,9,10,9,10}));
        lista.add(new Studenti("Andrei Vasile", (short)3, 123458, new int[]{7,8,7,8,7}));


        System.out.println("Prof 3 (constructor complet):");

        System.out.println();

        // METODA STANDARD - Citire de la tastatură cu exceptii
        System.out.println("=== TEST METODA STANDARD (CITIRE) ===");
        Prof p4 = new Prof();
        p4.citireProf();
        System.out.println("\nProf 4 citit:");
        p4.afisare();
        System.out.println();

        // Valori aleatoare
        Prof p5 = new Prof();
        p5.randomProf();
        System.out.println("Prof 5 (valori aleatoare):");
        p5.afisare();
        System.out.println();

        // ArrayList dinamic de profi
        System.out.println("\n=== ARRAYLIST PROFESORI ===\n");
        ArrayList<Prof> univer = new ArrayList<>();

        univer.add(p1);
        univer.add(p2);

        univer.add(p4);
        univer.add(p5);

        // Adaugam cateva cu constructor simplu
        univer.add(new Prof("Vasilescu", "Java"));

        ArrayList<Studenti> lista2 = new ArrayList<>();
        lista2.add(new Studenti("Student1", (short)1, 200000, new int[]{6,7,8,9,10}));
        lista2.add(new Studenti("Student2", (short)2, 200001, new int[]{5,6,7,8,9}));


        // Afișare cu for-each
        System.out.println("Toti profii din univer:");
        int cnt = 1;
        for (Prof p : univer) {
            System.out.println("\n--- Profesor " + cnt++ + " ---");
            p.afisare();
        }

        // Comparări
        System.out.println("\n\n=== COMPARARI ===\n");
        if (univer.size() >= 3) {
            univer.get(0).popularProf(univer.get(2));
            Prof.stresulStudentului(univer.get(1), univer.get(2));
        }

        // Număr total de profesori
        System.out.println("\n=== TOTAL PROFESORI CREATI: " + Prof.getProfesori() + " ===");

        // Salvare in fisi
    }
}
