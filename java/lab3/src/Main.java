import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Prof {
    // Câmpuri obligatorii
    private String nume;
    private String obiect;
    private double rautaciune;
    private float galagie;
    private short nrGrupe;
    private long[] nrStudenti;
    private static int profesori = 0;

    // Constructor implicit (standard)
    public Prof() {
        this.nume = "Anonim";
        this.obiect = "Necunoscut";
        this.rautaciune = 0.0;
        this.galagie = 60.0f;
        this.nrGrupe = 0;
        this.nrStudenti = new long[0];
        profesori++;
    }

    // Constructor cu parametri (versiunea 1)
    public Prof(String nume, String obiect) {
        this.nume = nume;
        this.obiect = obiect;
        this.rautaciune = 0.0;
        this.galagie = 60.0f;
        this.nrGrupe = 0;
        this.nrStudenti = new long[0];
        profesori++;
    }

    // Constructor cu parametri (versiunea 2 - complet)
    public Prof(String nume, String obiect, double rautaciune, float galagie, short nrGrupe, long[] nrStudenti) {
        this.nume = nume;
        this.obiect = obiect;
        this.rautaciune = rautaciune;
        this.galagie = galagie;
        this.nrGrupe = nrGrupe;
        this.nrStudenti = new long[nrGrupe];
        for (int i = 0; i < nrGrupe; i++) {
            this.nrStudenti[i] = nrStudenti[i];
        }
        profesori++;
    }

    // Constructor de copiere
    public Prof(Prof p) {
        this.nume = p.nume;
        this.obiect = p.obiect;
        this.rautaciune = p.rautaciune;
        this.galagie = p.galagie;
        this.nrGrupe = p.nrGrupe;
        this.nrStudenti = new long[p.nrGrupe];
        for (int i = 0; i < p.nrGrupe; i++) {
            this.nrStudenti[i] = p.nrStudenti[i];
        }
        profesori++;
    }

    // Constructor din fișier - cu TRY IN TRY (nested)
    public Prof(String fileName) {
        try {
            // Outer try - pentru IOException
            Scanner sc = new Scanner(new File(fileName));
            
            try {
                // Inner try - pentru custom exceptions si format exceptions
                this.nume = sc.nextLine();
                if (this.nume == null || this.nume.trim().isEmpty()) {
                    throw new NumeInvalidException("Nume invalid in fisier!", this.nume);
                }
                
                this.obiect = sc.nextLine();
                if (this.obiect == null || this.obiect.trim().isEmpty()) {
                    throw new ObiectInvalidException("Obiect invalid in fisier!", this.obiect);
                }
                
                this.rautaciune = sc.nextDouble();
                if (this.rautaciune < 0 || this.rautaciune > 100) {
                    throw new RautaciuneInvalidException("Rautaciune invalida in fisier!", this.rautaciune);
                }
                
                this.galagie = sc.nextFloat();
                if (this.galagie < 0) {
                    throw new GalagieInvalidException("Galagie invalida in fisier!", this.galagie);
                }
                
                this.nrGrupe = sc.nextShort();
                if (this.nrGrupe < 0) {
                    throw new NrGrupeInvalidException("Numar grupe invalid in fisier!", this.nrGrupe);
                }
                
                sc.nextLine();
                this.nrStudenti = new long[this.nrGrupe];
                
                if (this.nrGrupe > 0) {
                    String[] arr = sc.nextLine().split(" ");
                    for (int i = 0; i < nrGrupe; i++) {
                        this.nrStudenti[i] = Long.parseLong(arr[i]);
                        if (this.nrStudenti[i] <= 0) {
                            throw new NrStudentiInvalidException("Numar studenti invalid in fisier!", this.nrStudenti[i], i + 1);
                        }
                    }
                }
                
                profesori++;
                
            } catch (NumeInvalidException e) {
                System.out.println("Eroare in fisier: " + e.getMessage());
                this.nume = e.prelucrare();
            } catch (ObiectInvalidException e) {
                System.out.println("Eroare in fisier: " + e.getMessage());
                this.obiect = e.prelucrare();
            } catch (RautaciuneInvalidException e) {
                System.out.println("Eroare in fisier: " + e.getMessage());
                this.rautaciune = e.prelucrare();
            } catch (GalagieInvalidException e) {
                System.out.println("Eroare in fisier: " + e.getMessage());
                this.galagie = e.prelucrare();
            } catch (NrGrupeInvalidException e) {
                System.out.println("Eroare in fisier: " + e.getMessage());
                this.nrGrupe = e.prelucrare();
                this.nrStudenti = new long[this.nrGrupe];
            } catch (NrStudentiInvalidException e) {
                System.out.println("Eroare in fisier: " + e.getMessage());
                // Re-citim toate valorile pentru studenti pentru a fi siguri ca toate sunt corecte
                Scanner scNou = new Scanner(System.in);
                System.out.println("Va rugam sa reintroduceti toti studentii pentru toate grupele:");
                for (int i = 0; i < this.nrGrupe; i++) {
                    boolean flag = false;
                    do {
                        flag = false;
                        try {
                            System.out.print("Numar studenti grupa " + (i + 1) + ": ");
                            this.nrStudenti[i] = scNou.nextLong();
                            if (this.nrStudenti[i] <= 0) {
                                throw new NrStudentiInvalidException("Numarul trebuie sa fie pozitiv!", this.nrStudenti[i], i + 1);
                            }
                        } catch (NrStudentiInvalidException ex) {
                            flag = true;
                            this.nrStudenti[i] = ex.prelucrare();
                        }
                    } while (flag);
                }
            } catch (Exception e) {
                System.out.println("Eroare la citirea din fisier: " + e.getMessage());
                this.nume = "Anonim";
                this.obiect = "Necunoscut";
                this.rautaciune = 0.0;
                this.galagie = 60.0f;
                this.nrGrupe = 0;
                this.nrStudenti = new long[0];
                profesori++;
            } finally {
                sc.close();
            }
            
        } catch (IOException e) {
            // Outer catch - pentru erori de fisier
            System.out.println("Eroare la deschiderea fisierului: " + fileName);
            System.out.println("Detalii: " + e.getMessage());
            this.nume = "Anonim";
            this.obiect = "Necunoscut";
            this.rautaciune = 0.0;
            this.galagie = 60.0f;
            this.nrGrupe = 0;
            this.nrStudenti = new long[0];
            profesori++;
        }
    }

    // Getteri
    public String getNume() {
        return nume;
    }

    public String getObiect() {
        return obiect;
    }

    public double getRautaciune() {
        return rautaciune;
    }

    public float getGalagie() {
        return galagie;
    }

    public short getNrGrupe() {
        return nrGrupe;
    }

    public long[] getNrStudenti() {
        return nrStudenti;
    }

    public static int getProfesori() {
        return profesori;
    }

    // SETTERI - METODA LENESA (Lazy Method)
    // Acestia arunca exceptii si lasa apelantul sa le prinda
    
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
            throw new RautaciuneInvalidException("Rautaciune invalida (trebuie 0-100)!", rautaciune);
        }
        this.rautaciune = rautaciune;
    }

    public void setGalagie(float galagie) throws GalagieInvalidException {
        if (galagie < 0) {
            throw new GalagieInvalidException("Galagie invalida (trebuie pozitiva)!", galagie);
        }
        this.galagie = galagie;
    }

    public void setNrGrupe(short nrGrupe) throws NrGrupeInvalidException {
        if (nrGrupe < 0) {
            throw new NrGrupeInvalidException("Numar grupe invalid (trebuie pozitiv)!", nrGrupe);
        }
        this.nrGrupe = nrGrupe;
        this.nrStudenti = new long[nrGrupe];
    }

    public void setNrStudenti(long[] nrStudenti) {
        this.nrStudenti = new long[this.nrGrupe];
        for (int i = 0; i < this.nrGrupe; i++) {
            this.nrStudenti[i] = nrStudenti[i];
        }
    }

    // Metoda de afișare
    public void afisare() {
        System.out.println("Nume: " + nume);
        System.out.println("Obiect: " + obiect);
        System.out.println("Rautaciune: " + rautaciune + "%");
        System.out.println("Galagie: " + galagie + " dB");
        System.out.println("Numar grupe: " + nrGrupe);
        System.out.print("Numar studenti pe grupe: ");
        for (int i = 0; i < nrGrupe; i++) {
            System.out.print(nrStudenti[i] + " ");
        }
        System.out.println();
    }

    // Suma studenților
    public long sumaSarmanilor() {
        long suma = 0;
        for (int i = 0; i < nrGrupe; i++) {
            suma += nrStudenti[i];
        }
        return suma;
    }

    // CITIRE DE LA TASTATURA - METODA STANDARD cu do-while si flag
    public void citireProf() {
        Scanner sc = new Scanner(System.in);
        boolean flag = false;
        
        // Citire nume cu validare
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
        
        // Citire obiect cu validare
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
        
        // Citire rautaciune cu validare
        do {
            flag = false;
            try {
                System.out.print("Rautaciune (0-100): ");
                while (!sc.hasNextDouble()) {
                    System.out.println("Introdu un numar valid!");
                    sc.next();
                    System.out.print("Rautaciune (0-100): ");
                }
                this.rautaciune = sc.nextDouble();
                if (this.rautaciune < 0 || this.rautaciune > 100) {
                    throw new RautaciuneInvalidException("Rautaciune invalida!", this.rautaciune);
                }
            } catch (RautaciuneInvalidException e) {
                flag = true;
                this.rautaciune = e.prelucrare();
            }
        } while (flag);
        
        // Citire galagie cu validare
        do {
            flag = false;
            try {
                System.out.print("Galagie (dB): ");
                while (!sc.hasNextFloat()) {
                    System.out.println("Introdu un numar valid!");
                    sc.next();
                    System.out.print("Galagie (dB): ");
                }
                this.galagie = sc.nextFloat();
                if (this.galagie < 0) {
                    throw new GalagieInvalidException("Galagie invalida!", this.galagie);
                }
            } catch (GalagieInvalidException e) {
                flag = true;
                this.galagie = e.prelucrare();
            }
        } while (flag);
        
        // Citire numar grupe cu validare
        do {
            flag = false;
            try {
                System.out.print("Numar grupe: ");
                while (!sc.hasNextShort()) {
                    System.out.println("Introdu un numar valid!");
                    sc.next();
                    System.out.print("Numar grupe: ");
                }
                this.nrGrupe = sc.nextShort();
                if (this.nrGrupe < 0) {
                    throw new NrGrupeInvalidException("Numar grupe invalid!", this.nrGrupe);
                }
                this.nrStudenti = new long[this.nrGrupe];
            } catch (NrGrupeInvalidException e) {
                flag = true;
                this.nrGrupe = e.prelucrare();
                this.nrStudenti = new long[this.nrGrupe];
            }
        } while (flag);
        
        // Citire numarul de studenti pentru fiecare grupa
        for (int i = 0; i < nrGrupe; i++) {
            do {
                flag = false;
                try {
                    System.out.print("Numar studenti grupa " + (i + 1) + ": ");
                    while (!sc.hasNextLong()) {
                        System.out.println("Introdu un numar valid!");
                        sc.next();
                        System.out.print("Numar studenti grupa " + (i + 1) + ": ");
                    }
                    this.nrStudenti[i] = sc.nextLong();
                    if (this.nrStudenti[i] <= 0) {
                        throw new NrStudentiInvalidException("Numar studenti invalid!", this.nrStudenti[i], i + 1);
                    }
                } catch (NrStudentiInvalidException e) {
                    flag = true;
                    this.nrStudenti[i] = e.prelucrare();
                }
            } while (flag);
        }
    }

    // Valori aleatoare
    public void randomProf() {
        Random r = new Random();
        
        String[] numePosibile = {"Popescu", "Ionescu", "Marinescu", "Vasilescu", "Dumitru"};
        String[] obiectePosibile = {"Matematica", "Fizica", "Java", "C++", "Baze de date"};
        
        this.nume = numePosibile[r.nextInt(numePosibile.length)];
        this.obiect = obiectePosibile[r.nextInt(obiectePosibile.length)];
        this.rautaciune = r.nextDouble() * 100;
        this.galagie = 40 + r.nextFloat() * 80;
        this.nrGrupe = (short) (1 + r.nextInt(5));
        this.nrStudenti = new long[nrGrupe];
        for (int i = 0; i < nrGrupe; i++) {
            this.nrStudenti[i] = 15 + r.nextInt(20);
        }
    }

    // Comparare popularitate
    public void popularProf(Prof b) {
        long popularA = this.sumaSarmanilor();
        long popularB = b.sumaSarmanilor();
        
        if (popularA >= popularB) {
            System.out.println("Profesorul cel mai popular este: " + this.nume + ", preda " + this.obiect);
        } else {
            System.out.println("Profesorul cel mai popular este: " + b.nume + ", preda " + b.obiect);
        }
    }

    // Functie statică - calculare stres
    public static void stresulStudentului(Prof a, Prof b) {
        double stresA = a.rautaciune * 0.7 + a.galagie * 0.3;
        double stresB = b.rautaciune * 0.7 + b.galagie * 0.3;
        
        System.out.println("Stres " + a.nume + ": " + String.format("%.2f", stresA));
        System.out.println("Stres " + b.nume + ": " + String.format("%.2f", stresB));
        
        if (stresA > stresB) {
            System.out.println(a.nume + " este mai stresant cu " + String.format("%.2f", (stresA - stresB)));
        } else if (stresB > stresA) {
            System.out.println(b.nume + " este mai stresant cu " + String.format("%.2f", (stresB - stresA)));
        } else {
            System.out.println("Ambii profesori sunt la fel de stresanti");
        }
    }

    // Salvare în fișier
    public void salvareInFisier(String fileName) {
        try {
            FileWriter fw = new FileWriter(fileName);
            fw.write(nume + "\n");
            fw.write(obiect + "\n");
            fw.write(rautaciune + "\n");
            fw.write(galagie + "\n");
            fw.write(nrGrupe + "\n");
            for (int i = 0; i < nrGrupe; i++) {
                fw.write(nrStudenti[i] + " ");
            }
            fw.close();
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
        
        // Test setter nume
        do {
            flag = false;
            try {
                System.out.print("Introdu nume pentru p2: ");
                Scanner sc = new Scanner(System.in);
                String numeNou = sc.nextLine();
                p2.setNume(numeNou);
            } catch (NumeInvalidException e) {
                flag = true;
                String numeCorect = e.prelucrare();
                try {
                    p2.setNume(numeCorect);
                } catch (NumeInvalidException ex) {
                    // Nu ar trebui sa ajunga aici daca prelucrare() functioneaza corect
                }
            }
        } while (flag);
        
        // Test setter rautaciune
        do {
            flag = false;
            try {
                System.out.print("Introdu rautaciune pentru p2 (0-100): ");
                Scanner sc = new Scanner(System.in);
                double rautaciuNeNoua = sc.nextDouble();
                p2.setRautaciune(rautaciuNeNoua);
            } catch (RautaciuneInvalidException e) {
                flag = true;
                double valoareCorecta = e.prelucrare();
                try {
                    p2.setRautaciune(valoareCorecta);
                } catch (RautaciuneInvalidException ex) {
                    // Nu ar trebui sa ajunga aici
                }
            }
        } while (flag);
        
        System.out.println("\nProf 2 dupa setari:");
        p2.afisare();
        System.out.println();
        
        // Constructor cu parametri (versiune completă)
        Prof p3 = new Prof("Ionescu", "Fizica", 45.5, 85.0f, (short) 3, new long[]{25, 30, 28});
        System.out.println("Prof 3 (constructor complet):");
        p3.afisare();
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
        univer.add(p3);
        univer.add(p4);
        univer.add(p5);
        
        // Adaugam cateva cu constructor simplu
        univer.add(new Prof("Vasilescu", "Java"));
        univer.add(new Prof("Marinescu", "C++", 60.0, 70.0f, (short) 2, new long[]{20, 25}));
        
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
        
        // TEST TRY IN TRY - salvare si citire din fisier
        System.out.println("\n=== TEST TRY IN TRY (FISIER) ===");
        p3.salvareInFisier("test_prof.txt");
        
        System.out.println("\nCitire din fisier cu validare:");
        Prof pDinFisier = new Prof("test_prof.txt");
        System.out.println("Prof citit din fisier:");
        pDinFisier.afisare();
    }
}