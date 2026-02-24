import java.util.Scanner;

// Clasa de baza pentru exceptii
class ProfException extends Exception {
    public ProfException(String message) {
        super(message);
    }
}

// Exceptie pentru nume invalid
class NumeInvalidException extends ProfException {
    private String numeGresit;
    
    public NumeInvalidException(String message, String numeGresit) {
        super(message);
        this.numeGresit = numeGresit;
    }
    
    public String prelucrare() {
        System.out.println(getMessage());
        Scanner sc = new Scanner(System.in);
        String numeCorect;
        
        do {
            System.out.print("Introdu un nume valid: ");
            numeCorect = sc.nextLine();
            
            if (numeCorect != null && !numeCorect.trim().isEmpty()) {
                break;
            }
            System.out.println("Numele nu poate fi gol!");
        } while (true);
        
        return numeCorect;
    }
}

// Exceptie pentru obiect invalid
class ObiectInvalidException extends ProfException {
    private String obiectGresit;
    
    public ObiectInvalidException(String message, String obiectGresit) {
        super(message);
        this.obiectGresit = obiectGresit;
    }
    
    public String prelucrare() {
        System.out.println(getMessage());
        Scanner sc = new Scanner(System.in);
        String obiectCorect;
        
        do {
            System.out.print("Introdu un obiect valid: ");
            obiectCorect = sc.nextLine();
            
            if (obiectCorect != null && !obiectCorect.trim().isEmpty()) {
                break;
            }
            System.out.println("Obiectul nu poate fi gol!");
        } while (true);
        
        return obiectCorect;
    }
}

// Exceptie pentru rautaciune invalida
class RautaciuneInvalidException extends ProfException {
    private double valoareGresita;
    
    public RautaciuneInvalidException(String message, double valoareGresita) {
        super(message);
        this.valoareGresita = valoareGresita;
    }
    
    public double prelucrare() {
        System.out.println(getMessage() + " (valoare introdusa: " + valoareGresita + ")");
        Scanner sc = new Scanner(System.in);
        double valoareCorecta;
        
        do {
            System.out.print("Introdu o valoare intre 0 si 100: ");
            while (!sc.hasNextDouble()) {
                System.out.println("Introdu un numar valid!");
                sc.next();
            }
            valoareCorecta = sc.nextDouble();
            
            if (valoareCorecta >= 0 && valoareCorecta <= 100) {
                break;
            }
            System.out.println("Valoarea trebuie sa fie intre 0 si 100!");
        } while (true);
        
        return valoareCorecta;
    }
}

// Exceptie pentru galagie invalida
class GalagieInvalidException extends ProfException {
    private float valoareGresita;
    
    public GalagieInvalidException(String message, float valoareGresita) {
        super(message);
        this.valoareGresita = valoareGresita;
    }
    
    public float prelucrare() {
        System.out.println(getMessage() + " (valoare introdusa: " + valoareGresita + ")");
        Scanner sc = new Scanner(System.in);
        float valoareCorecta;
        
        do {
            System.out.print("Introdu o valoare pozitiva pentru galagie: ");
            while (!sc.hasNextFloat()) {
                System.out.println("Introdu un numar valid!");
                sc.next();
            }
            valoareCorecta = sc.nextFloat();
            
            if (valoareCorecta >= 0) {
                break;
            }
            System.out.println("Galagia trebuie sa fie pozitiva!");
        } while (true);
        
        return valoareCorecta;
    }
}

// Exceptie pentru numar grupe invalid
class NrGrupeInvalidException extends ProfException {
    private short valoareGresita;
    
    public NrGrupeInvalidException(String message, short valoareGresita) {
        super(message);
        this.valoareGresita = valoareGresita;
    }
    
    public short prelucrare() {
        System.out.println(getMessage() + " (valoare introdusa: " + valoareGresita + ")");
        Scanner sc = new Scanner(System.in);
        short valoareCorecta;
        
        do {
            System.out.print("Introdu un numar pozitiv de grupe: ");
            while (!sc.hasNextShort()) {
                System.out.println("Introdu un numar valid!");
                sc.next();
            }
            valoareCorecta = sc.nextShort();
            
            if (valoareCorecta >= 0) {
                break;
            }
            System.out.println("Numarul de grupe trebuie sa fie pozitiv!");
        } while (true);
        
        return valoareCorecta;
    }
}

// Exceptie pentru numar studenti invalid
class NrStudentiInvalidException extends ProfException {
    private long valoareGresita;
    private int numarGrupa;
    
    public NrStudentiInvalidException(String message, long valoareGresita, int numarGrupa) {
        super(message);
        this.valoareGresita = valoareGresita;
        this.numarGrupa = numarGrupa;
    }
    
    public long prelucrare() {
        System.out.println(getMessage() + " (valoare introdusa: " + valoareGresita + ")");
        Scanner sc = new Scanner(System.in);
        long valoareCorecta;
        
        do {
            System.out.print("Introdu numar studenti pozitiv pentru grupa " + numarGrupa + ": ");
            while (!sc.hasNextLong()) {
                System.out.println("Introdu un numar valid!");
                sc.next();
            }
            valoareCorecta = sc.nextLong();
            
            if (valoareCorecta > 0) {
                break;
            }
            System.out.println("Numarul de studenti trebuie sa fie pozitiv!");
        } while (true);
        
        return valoareCorecta;
    }
}