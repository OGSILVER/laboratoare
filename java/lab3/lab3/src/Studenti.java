public class Studenti {
    String nume;
    short grupa;
    long StudentID;
    int note[] = new int[5];


    public Studenti(String nume, short grupa, long StudentID, int[] note) {
        this.nume = nume;
        this.grupa = grupa;
        this.StudentID = StudentID;
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
        System.out.println("StudentID: " + StudentID);
        System.out.print("Note: ");
        for (int nota : note) {
            System.out.print(nota + " ");
        }
        System.out.println("\nMedia: " + medie());
    }



}
