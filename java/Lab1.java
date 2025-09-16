public class Lab1 {
    public class Prof {
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
            System.out.println();
        }


    }
    
    
    

    public static void main(String[] args){
        
    } 

}
