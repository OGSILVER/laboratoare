import java.util.Random;

public class Studenti {
    private String nume;
    private short grupa;
    private long studentID;
    private int[] note = new int[5];

    public Studenti() {
        Random rand = new Random();
        String[] numePosibile = {"Ana", "Mihai", "Ion", "Elena", "Andrei", "Maria"};
        this.nume = numePosibile[rand.nextInt(numePosibile.length)];
        this.grupa = (short) (rand.nextInt(5) + 1);
        this.studentID = 100000 + rand.nextInt(900000);
        for (int i = 0; i < note.length; i++) {
            note[i] = rand.nextInt(10) + 1;
        }
    }

    public Studenti(String nume, short grupa, long studentID, int[] note) {
        this.nume = nume;
        this.grupa = grupa;
        this.studentID = studentID;
        this.note = note;
    }

    public float medie() {
        int suma = 0;
        for (int nota : note) {
            suma += nota;
        }
        return (float) suma / note.length;
    }

    public void afisare() {
        System.out.println("Nume: " + nume);
        System.out.println("Grupa: " + grupa);
        System.out.println("StudentID: " + studentID);
        System.out.print("Note: ");
        for (int nota : note) {
            System.out.print(nota + " ");
        }
        System.out.println("\nMedia: " + medie());
    }

    // 🔹 Nested try example
    public void prelucrareNote() {
        try {
            try {
                int rezultat = 10 / 0;
                System.out.println("Rezultatul este: " + rezultat);
            } catch (ArithmeticException e) {
                throw new NoteProcessingException("Eroare la procesarea notelor: " + e.getMessage());
            }
        } catch (NoteProcessingException e) {
            System.out.println("S-a prins exceptia personalizata: " + e.getMessage());
            e.prelucrare();
        }
    }



    // Getters & Setters
    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }
    public short getGrupa() { return grupa; }
    public void setGrupa(short grupa) { this.grupa = grupa; }
    public long getStudentID() { return studentID; }
    public void setStudentID(long studentID) { this.studentID = studentID; }
    public int[] getNote() { return note; }
    public void setNote(int[] note) { this.note = note; }
}
