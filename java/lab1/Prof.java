import java.io.File;
import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Arrays;
import java.io.FileWriter;
import java.io.IOException;

public class Prof {
    private String nume;
    private String obiect;
    private double rautaciune;
    private float galagie;
    private short nrGrupe;
    private long[] nrStudenti;
    private static int profesori = 0;

    // implicit
    public Prof() {
        this.nume = "Profesor";
        this.obiect = "Nu preda la moment!";
        this.rautaciune = 0d;
        this.galagie = 60f;
        this.nrGrupe = 0;
        this.nrStudenti = new long[0];
        profesori++;
    }

    // partial
    public Prof(String nume, double rautaciune, float galagie) {
        this();
        this.nume = nume;
        this.rautaciune = rautaciune;
        this.galagie = galagie;
    }

    // partial
    public Prof(String nume, String obiect) {
        this();
        this.nume = nume;
        this.obiect = obiect;
    }

    // full
    public Prof(String nume, String obiect, double rautaciune, float galagie, short nrGrupe, long[] nrStudenti) {
        this.nume = nume;
        this.obiect = obiect;
        this.rautaciune = rautaciune;
        this.galagie = galagie;
        this.nrGrupe = nrGrupe;
        this.nrStudenti = Arrays.copyOf(nrStudenti, nrGrupe);
        profesori++;
    }

    // copiere
    public Prof(Prof p) {
        this.nume = p.nume;
        this.obiect = p.obiect;
        this.rautaciune = p.rautaciune;
        this.galagie = p.galagie;
        this.nrGrupe = p.nrGrupe;
        this.nrStudenti = Arrays.copyOf(p.nrStudenti, p.nrGrupe);
        profesori++;
    }

    // citire din fisier
    public Prof(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));

            this.nume = sc.nextLine();
            this.obiect = sc.nextLine();
            this.rautaciune = sc.nextDouble();
            this.galagie = sc.nextFloat();
            this.nrGrupe = sc.nextShort();
            this.nrStudenti = new long[this.nrGrupe];
            sc.nextLine(); // consumă linia rămasă

            String[] stringArr = sc.nextLine().split(" ");

            for (int i = 0; i < nrGrupe && i < stringArr.length; i++) {
                this.nrStudenti[i] = Long.parseLong(stringArr[i]);
            }

            profesori++;
            sc.close();
        } catch (java.io.FileNotFoundException e) {
            System.out.println("Fisierul nu a fost gasit: " + fileName);
            // Set default values if file not found
            this.nume = "Profesor";
            this.obiect = "Nu preda la moment!";
            this.rautaciune = 0d;
            this.galagie = 60f;
            this.nrGrupe = 0;
            this.nrStudenti = new long[0];
            profesori++;
        }
    }

    // setters
    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setObiect(String obiect) {
        this.obiect = obiect;
    }

    public void setRautaciune(double rautaciune) {
        this.rautaciune = rautaciune;
    }

    public void setGalagie(float galagie) {
        this.galagie = galagie;
    }

    public void setNrGrupe(short nrGrupe) {
        if (nrGrupe < 0) throw new IllegalArgumentException("nrGrupe negativ");
        // resize nrStudenti preserving existing values
        this.nrStudenti = Arrays.copyOf(this.nrStudenti, nrGrupe);
        this.nrGrupe = nrGrupe;
    }

    public void setNrStudenti(long[] nrStudenti) {
        this.nrStudenti = Arrays.copyOf(nrStudenti, this.nrGrupe);
    }

    // getters
    public String getNume() {
        return this.nume;
    }

    public String getObiect() {
        return this.obiect;
    }

    public double getRautaciune() {
        return this.rautaciune;
    }

    public float getGalagie() {
        return this.galagie;
    }

    public short getNrGrupe() {
        return this.nrGrupe;
    }

    public long[] getNrStudenti() {
        return this.nrStudenti;
    }

    public long getNrStudentiSingle(int i) {
        if (i < 0 || i >= nrGrupe) throw new IndexOutOfBoundsException("index invalid");
        return this.nrStudenti[i];
    }

    public static int getProfesori() {
        return profesori;
    }

    // metode
    public void afisare() {
        System.out.println("Nume: " + nume);
        System.out.println("Obiect: " + obiect);
        System.out.println("Rautaciune: " + rautaciune);
        System.out.println("Galagie: " + galagie);
        System.out.println("Numar grupe: " + nrGrupe);
        System.out.print("Numar studenti pe grupe: ");
        for (int i = 0; i < nrGrupe; i++) {
            System.out.print(nrStudenti[i] + " ");
        }
        System.out.println();
    }

    public long sumaSarmanilor() {
        long suma = 0L;
        for (int i = 0; i < nrGrupe; i++) {
            suma += nrStudenti[i];
        }
        return suma;
    }

    public void citireProf() {
        Scanner scanner = new Scanner(System.in);

        // choose handling strategy
        int choice = 1; // 1=standard, 2=lazy, 3=try-in-try
        boolean flag;
        do {
            flag = false;
            try {
                System.out.println("Alege modul de tratare erori: 1=standard, 2=lenes, 3=try-in-try");
                System.out.print("Optiune: ");
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice < 1 || choice > 3) {
                    System.out.println("Optiune invalida.");
                    flag = true;
                }
            } catch (InputMismatchException e) {
                ProfException.handleStandard(e, scanner);
                flag = true;
            }
        } while (flag);

        // read name (must be non-empty)
        do {
            flag = false;
            System.out.print("Nume: ");
            this.nume = scanner.nextLine().trim();
            if (this.nume.isEmpty()) {
                System.out.println("Nume gol. Reincercati.");
                flag = true;
            }
        } while (flag);

        // read object (non-empty)
        do {
            flag = false;
            System.out.print("Obiect: ");
            this.obiect = scanner.nextLine().trim();
            if (this.obiect.isEmpty()) {
                System.out.println("Obiect gol. Reincercati.");
                flag = true;
            }
        } while (flag);

        // rautaciune (double)
        do {
            flag = false;
            try {
                System.out.print("Rautaciune (>=0): ");
                double v = scanner.nextDouble();
                ProfException.validateNonNegativeDouble(v);
                this.rautaciune = v;
                scanner.nextLine();
            } catch (InputMismatchException | ProfException e) {
                if (choice == 1) {
                    ProfException.handleStandard(e, scanner);
                    flag = true; // retry
                } else if (choice == 2) {
                    this.rautaciune = ProfException.handleLazyDouble(e, 0.0);
                    flag = false; // lazy: accept default and continue
                } else { // try-in-try
                    this.rautaciune = ProfException.handleTryInTryDouble(scanner, 0.0);
                    flag = false;
                }
            }
        } while (flag);

        // galagie (float)
        do {
            flag = false;
            try {
                System.out.print("Galagie (>=0): ");
                float v = scanner.nextFloat();
                ProfException.validateNonNegativeFloat(v);
                this.galagie = v;
                scanner.nextLine();
            } catch (InputMismatchException | ProfException e) {
                if (choice == 1) {
                    ProfException.handleStandard(e, scanner);
                    flag = true;
                } else if (choice == 2) {
                    this.galagie = ProfException.handleLazyFloat(e, 0f);
                    flag = false;
                } else {
                    this.galagie = ProfException.handleTryInTryFloat(scanner, 0f);
                    flag = false;
                }
            }
        } while (flag);

        // nrGrupe (short)
        do {
            flag = false;
            try {
                System.out.print("Numar grupe (>=0): ");
                short v = scanner.nextShort();
                ProfException.validateNonNegativeShort(v);
                this.nrGrupe = v;
                scanner.nextLine();
            } catch (InputMismatchException | ProfException e) {
                if (choice == 1) {
                    ProfException.handleStandard(e, scanner);
                    flag = true;
                } else if (choice == 2) {
                    this.nrGrupe = ProfException.handleLazyShort(e, (short) 0);
                    flag = false;
                } else {
                    this.nrGrupe = ProfException.handleTryInTryShort(scanner, (short) 0);
                    flag = false;
                }
            }
        } while (flag);

        // allocate array
        if (this.nrGrupe < 0) this.nrGrupe = 0; // safety
        this.nrStudenti = new long[this.nrGrupe];

        // read students per group
        for (int i = 0; i < this.nrGrupe; i++) {
            final int idx = i;
            do {
                flag = false;
                try {
                    System.out.print("Numar studenti grupa " + (idx + 1) + " (>=0): ");
                    long v = scanner.nextLong();
                    ProfException.validateNonNegativeLong(v);
                    this.nrStudenti[idx] = v;
                    scanner.nextLine();
                } catch (InputMismatchException | ProfException e) {
                    if (choice == 1) {
                        ProfException.handleStandard(e, scanner);
                        flag = true;
                    } else if (choice == 2) {
                        this.nrStudenti[idx] = ProfException.handleLazyLong(e, 0L);
                        flag = false;
                    } else {
                        this.nrStudenti[idx] = ProfException.handleTryInTryLong(scanner, 0L);
                        flag = false;
                    }
                }
            } while (flag);
        }
    }

    public void randomProf() {
        Random r = new Random();

        String[] numePosibile = {"Cristian", "Eugen", "Nicolae", "Catalin", "Stanescu", "Maria", "Vlad"};
        String[] obiectePosibile = {"Java", "Fundamentele", "C++", "Antreprenoriat", "Rust", "Criptare"};

        this.nume = numePosibile[r.nextInt(0, 6)];
        this.obiect = obiectePosibile[r.nextInt(0, 5)];

        this.rautaciune = r.nextInt(0, 100);
        this.galagie = r.nextInt(40, 120);
        this.nrGrupe = (short) r.nextInt(0, 12);
        this.nrStudenti = new long[nrGrupe];
        for (int i = 0; i < nrGrupe; i++) {
            this.nrStudenti[i] = Math.abs(r.nextLong() % 35) + 1; // fallback for older JDKs
        }
    }

    public void popularProf(Prof b) {
        int popularitateProfA = 0;
        int popularitateProfB = 0;
        for (int i = 0; i < this.nrGrupe; i++) {
            popularitateProfA += this.nrStudenti[i];
        }
        for (int i = 0; i < b.nrGrupe; i++) {
            popularitateProfB += b.nrStudenti[i];
        }
        if (popularitateProfA >= popularitateProfB) {
            System.out.println("Profesorul cel mai popular este: " + this.nume + ",si preda " + this.obiect);
        } else {
            System.out.println("Profesorul cel mai popular este: " + b.nume + ",si preda " + b.obiect);
        }

    }

    public static void stressCalc(Prof a, Prof b) {

        // how does it affect student
        float stressA = (float) ((a.rautaciune * 0.7) + (a.galagie * 0.3));
        float stressB = (float) ((b.rautaciune * 0.7) + (b.galagie * 0.3));

        if (stressA == stressB) {
            System.out.println("Ambii profesori sunt la fel de stresanti, cu un indice de stress de " + stressA);
        } else if (stressA > stressB) {
            System.out.println("Profesorul " + a.nume + " este mai stresant decit " + b.nume + ", cu un indice de stress de " + stressA + " fata de " + stressB);
        } else {
            System.out.println("Profesorul " + a.nume + " este mai putin stresant decit " + b.nume + ", cu un indice de stress de " + stressA + " fata de " + stressB);
        }

        // comparation of the teachers
        boolean rautaciuneTop = a.rautaciune <= b.rautaciune;
        boolean galagieTop = a.galagie <= b.galagie;
        if (rautaciuneTop ^ galagieTop) {
            if (rautaciuneTop) {
                System.out.println("Amindoi profi sunt stresanti, cel mai rautacios fiind " + b.nume + " cu o rautaciune de " + b.rautaciune + ". Si cel mai galagios fiind " + a.nume + " cu o galagie de " + a.galagie);
            } else {
                System.out.println("Amindoi profi sunt stresanti, cel mai rautacios fiind " + a.nume + " cu o rautaciune de " + a.rautaciune + ". Si cel mai galagios fiind " + b.nume + " cu o galagie de " + b.galagie);
            }
        } else {
            if (rautaciuneTop) {
                System.out.println("Profesorul cel mai stressant este " + b.nume + " cu o rautaciune de " + b.rautaciune + " si o galagie de " + b.galagie);
            } else {
                System.out.println("Profesorul cel mai stressant este " + a.nume + " cu o rautaciune de " + a.rautaciune + " si o galagie de " + a.galagie);
            }
        }

    }

    public void inscrieProf(String fileName, Prof prof) {
        try {
            FileWriter fw = new FileWriter(fileName + ".txt");
            fw.write(prof.nume + "\n");
            fw.write(prof.obiect + "\n");
            fw.write(prof.rautaciune + "\n");
            fw.write(prof.galagie + "\n");
            fw.write(prof.nrGrupe + "\n");
            for (int i = 0; i < prof.nrGrupe; i++) {
                fw.write(prof.nrStudenti[i] + " ");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Eroare la scrierea in fisier: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        // 1. Creare profi cu toți constructorii
        Prof prof1 = new Prof(); // implicit
        Prof prof2 = new Prof("Popescu", 8.5, 90.0f); // partial
        Prof prof3 = new Prof("Ionescu", "Matematica"); // partial
        Prof prof4 = new Prof("Georgescu", "Fizica", 60.0, 70.0f, (short)2, new long[]{25,43}); // full
        Prof prof5 = new Prof(prof4); // copiere
        prof1.citireProf();

        // 2. Afișare după fiecare creare
        System.out.println("--- Prof1 (implicit) ---");
        prof1.afisare();
        System.out.println("--- Prof2 (partial: nume, rautaciune, galagie) ---");
        prof2.afisare();
        System.out.println("--- Prof3 (partial: nume, obiect) ---");
        prof3.afisare();
        System.out.println("--- Prof4 (full) ---");
        prof4.afisare();
        System.out.println("--- Prof5 (copiere prof4) ---");
        prof5.afisare();

        // 3. Vector dinamic de profi cu array simplu
        int cap = 5; // capacitate inițială
        Prof[] univer = new Prof[cap];
        int n = 0;
        // Adăugare profi folosind constructori diferiți
        univer[n++] = prof1;
        univer[n++] = prof2;
        univer[n++] = prof3;
        univer[n++] = prof4;
        univer[n++] = prof5;
        // Adăugăm încă 3 profi, extindem array-ul dacă e nevoie
        Prof[] deAdaugat = new Prof[] {
            new Prof("profText.txt"),
            new Prof("Dumitru", 40.0, 60.0f),
            new Prof("Vasilescu", "Chimie")
        };
        for (Prof p : deAdaugat) {
            if (n == cap) {
                // extindem array-ul
                cap = cap * 2;
                Prof[] nou = new Prof[cap];
                for (int i = 0; i < n; i++) nou[i] = univer[i];
                univer = nou;
            }
            univer[n++] = p;
        }

        // 4. Afișare toată informația despre fiecare prof din univer
        System.out.println("\n--- Toți profii din univer ---");
        for (int i = 0; i < n; i++) {
            System.out.println("\nProf " + (i+1) + ":");
            univer[i].afisare();
        }

        // 5. Comparare câțiva profi după popularitate și stres
        System.out.println("\n--- Comparare popularitate ---");
        univer[0].popularProf(univer[3]);
        univer[1].popularProf(univer[2]);

        System.out.println("\n--- Comparare stres studenti ---");
        Prof.stressCalc(univer[0], univer[3]);
        Prof.stressCalc(univer[1], univer[2]);

        // 6. Găsire și afișare nume profi care predau același obiect ca primul prof
        String obiectPrimul = univer[0].getObiect();
        System.out.println("\n--- Profii care predau acelasi obiect ca primul prof (\"" + obiectPrimul + "\") ---");
        boolean found = false;
        for (int i = 0; i < n; i++) {
            Prof p = univer[i];
            if (p.getObiect() != null && p.getObiect().equals(obiectPrimul)) {
                System.out.println(p.getNume());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Niciun prof nu predă acelasi obiect ca primul prof.");
        }

        // 7. Afișare număr total de profi creați
        System.out.println("\nNumar total de profi creati: " + Prof.getProfesori());

        // 9. Inscrierea profesorului în fișier
            univer[0].inscrieProf("profInscrisManual", univer[0]);


        File folder = new File(".");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt") && !name.equals("profText.txt"));

        if (files != null) {
            System.out.println("\n--- Ștergerea fișierelor .txt existente (excluzând profText.txt) ---");
            
            for(File f : files){
                System.out.println("Șterg: " + f.getName());
                boolean ok = f.delete();
                if (!ok) System.out.println("Nu am putut șterge: " + f.getName());
            }

        }
        for(Prof p : univer){
            if (p == null) continue;
                 try {
                    FileWriter fw = new FileWriter(p.getNume() + ".txt");
                    fw.write(p.getNume() + "\n");
                    fw.write(p.getObiect() + "\n");
                    fw.write(p.getRautaciune() + "\n");
                    fw.write(p.getGalagie() + "\n");
                    fw.write(p.getNrGrupe() + "\n");
                    for(int j = 0; j < p.getNrGrupe(); j++){
                        fw.write(p.getNrStudenti()[j] + " ");
                    }

                     fw.close();
                 } catch (Exception e) {
                     System.out.println("error");
                 }
        }



    }
}
