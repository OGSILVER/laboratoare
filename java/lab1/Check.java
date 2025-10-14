public class Check {
    public static short checkPositiveShort(short a){
        if (a < 0) {
            throw new IllegalArgumentException("Numar Negativ!");
        }else{
            return a;
        }
    }

    public static float checkPositiveFloat(float a){
        if (a < 0) {
            throw new IllegalArgumentException("Numar Negativ!");
        }else{
            return a;
        }
    }

    public static double checkPositiveDouble(double a){
        if (a < 0) {
            throw new IllegalArgumentException("Numar Negativ!");
        }else{
            return a;
        }

    }

    public static long checkPositiveLong(long a){
        if (a < 0) {
            throw new IllegalArgumentException("Numar Negativ!");
        }else{
            return a;
        }
    }

}
