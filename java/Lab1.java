public class Lab1 {
    public static class Prof {
        private String nume;
        private String obiect;
        private double rautaciune;
        private float galagie;
        private short nrGrupe;
        private long nrStudenti[];
        private static int profesori = 0;

        //implicit
        public Prof(){
            this.nume = "Profesor";
            this.obiect = "Nu preda la moment!";
            this.rautaciune = 0d;
            this.galagie = 60f;
            this.nrGrupe = 0;
            this.nrStudenti = new long[0];
            profesori++;
        }

        //partial
        public Prof(String nume, double rautaciune, float galagie){
            this.nume = nume;
            this.rautaciune = rautaciune;
            this.galagie = galagie;
            profesori++;
        }
        
        //partial
        public Prof(String nume, String obiect){
            this.nume = nume;
            this.obiect = obiect;
            profesori++;
        }



        //full
        public Prof(String nume, String obiect, double rautaciune, float galagie, short nrGrupe, long nrStudenti){
            this.nume = nume;
            this.obiect = obiect;
            this.rautaciune = rautaciune;
            this.galagie = galagie;
            this.nrGrupe = nrGrupe;
            this.nrStudenti = new long[nrGrupe];
            for(int i=0; i<nrGrupe; i++){
                this.nrStudenti[i] = nrStudenti;
            }
            profesori++;
        }

        //copiere
        public Prof(Prof p){
            this.nume = p.nume;
            this.obiect = p.obiect;
            this.rautaciune = p.rautaciune;
            this.galagie = p.galagie;
            this.nrGrupe = p.nrGrupe;
            this.nrStudenti = new long[p.nrGrupe];
            for(int i=0; i<p.nrGrupe; i++){
                this.nrStudenti[i] = p.nrStudenti[i];
            }
            profesori++;
        }
        //setters
        public void setNume(String nume){
            this.nume = nume;
        }
        public void setObiect(String obiect){
            this.obiect = obiect;
        }   
        public void setRautaciune(double rautaciune){
            this.rautaciune = rautaciune;
        }
        public void setGalagie(float galagie){
            this.galagie = galagie;
        }
        public void setNrGrupe(short nrGrupe){
            this.nrGrupe = nrGrupe;
        }
        public void setNrStudenti(long nrStudenti[]){
            this.nrStudenti = new long[nrGrupe];
            for(int i=0; i<nrGrupe; i++){
                this.nrStudenti[i] = nrStudenti[i];
            }
        }
        //getters
        public String getNume(){
            return this.nume;
        }
        public String getObiect(){
            return this.obiect;
        }
        public double getRautaciune(){
            return this.rautaciune;
        }
        public float getGalagie(){
            return this.galagie;
        }
        public short getNrGrupe(){
            return this.nrGrupe;
        }
        public long[] getNrStudenti(){
            return this.nrStudenti;
        }
        public static int getProfesori(){
            return profesori;
        }


    //metode
        public void afisare(){
            System.out.println("Nume: " + nume);
            System.out.println("Obiect: " + obiect);
            System.out.println("Rautaciune: " + rautaciune);
            System.out.println("Galagie: " + galagie);
            System.out.println("Numar grupe: " + nrGrupe);
            System.out.print("Numar studenti pe grupe: ");
            for(int i=0; i<nrGrupe; i++){
                System.out.print(nrStudenti[i] + " ");
            }
            System.out.println("Numar profesori inregistrati: " + profesori);
        }

        public int sumaSarmanilor(){
            int suma = 0;
            for(int i = 0; i<nrGrupe; i++){
                suma += nrStudenti[i];
            }
            return suma;
        }

        public void citireProf(){
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            System.out.print("Nume: ");
            this.nume = scanner.nextLine();
            System.out.print("Obiect: ");
            this.obiect = scanner.nextLine();
            System.out.print("Rautaciune: ");
            this.rautaciune = scanner.nextDouble();
            System.out.print("Galagie: ");
            this.galagie = scanner.nextFloat();
            System.out.print("Numar grupe: ");
            this.nrGrupe = scanner.nextShort();
            this.nrStudenti = new long[nrGrupe];
            for(int i=0; i<nrGrupe; i++){
                System.out.print("Numar studenti grupa " + (i+1) + ": ");
                this.nrStudenti[i] = scanner.nextLong();
            }
        }


    }
    
    
    

    public static void main(String[] args){

        Prof p1 = new Prof();
        p1.setGalagie(20f);
        p1.afisare();
        System.out.println();

        Prof p2 = new Prof();
        p2.citireProf();
        p2.afisare();
        System.out.println("Suma sarmanilor: " + p2.sumaSarmanilor());
        System.out.println();
    } 

}
