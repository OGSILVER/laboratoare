package ;

// ...existing code...

public class ProfException extends Exception {
    public ProfException(String message) {
        super(message);
    }

    // --- validators (throw ProfException if invalid) ---
    public static void validateNonNegativeDouble(double v) throws ProfException {
        if (v < 0) throw new ProfException("Valoare negativa: " + v);
    }
    public static void validateNonNegativeFloat(float v) throws ProfException {
        if (v < 0) throw new ProfException("Valoare negativa: " + v);
    }
    public static void validateNonNegativeShort(short v) throws ProfException {
        if (v < 0) throw new ProfException("Valoare negativa: " + v);
    }
    public static void validateNonNegativeLong(long v) throws ProfException {
        if (v < 0) throw new ProfException("Valoare negativa: " + v);
    }

    // --- standard handling: clear bad token and inform user ---
    public static void handleStandard(Exception e, Scanner sc) {
        System.out.println("Eroare (standard): " + e.getMessage());
        // clear the invalid token / line so next read can proceed
        try { sc.nextLine(); } catch (Exception ignored) {}
        System.out.println("Reincercati...");
    }

    // --- lazy handling: return a safe default and inform user ---
    public static double handleLazyDouble(Exception e, double defaultValue) {
        System.out.println("Eroare (metoda lenesa) la citirea double: " + e.getMessage() + " -> folosesc " + defaultValue);
        return defaultValue;
    }
    public static float handleLazyFloat(Exception e, float defaultValue) {
        System.out.println("Eroare (metoda lenesa) la citirea float: " + e.getMessage() + " -> folosesc " + defaultValue);
        return defaultValue;
    }
    public static short handleLazyShort(Exception e, short defaultValue) {
        System.out.println("Eroare (metoda lenesa) la citirea short: " + e.getMessage() + " -> folosesc " + defaultValue);
        return defaultValue;
    }
    public static long handleLazyLong(Exception e, long defaultValue) {
        System.out.println("Eroare (metoda lenesa) la citirea long: " + e.getMessage() + " -> folosesc " + defaultValue);
        return defaultValue;
    }

    // --- try-in-try handling: nested try to attempt a single retry, then fallback ---
    public static double handleTryInTryDouble(Scanner sc, double fallback) {
        System.out.println("Eroare. Incerc o singura reintroducere (try-in-try):");
        try {
            try {
                System.out.print("Reintroduceti double: ");
                double v = sc.nextDouble();
                validateNonNegativeDouble(v);
                sc.nextLine();
                return v;
            } catch (InputMismatchException | ProfException inner) {
                System.out.println("Reintroducere nereusita: " + inner.getMessage());
                sc.nextLine();
                System.out.println("Se aplica fallback: " + fallback);
                return fallback;
            }
        } catch (Exception outer) {
            System.out.println("Eroare neprevazuta in try-in-try: " + outer.getMessage());
            return fallback;
        }
    }
    public static float handleTryInTryFloat(Scanner sc, float fallback) {
        System.out.println("Eroare. Incerc o singura reintroducere (try-in-try):");
        try {
            try {
                System.out.print("Reintroduceti float: ");
                float v = sc.nextFloat();
                validateNonNegativeFloat(v);
                sc.nextLine();
                return v;
            } catch (InputMismatchException | ProfException inner) {
                System.out.println("Reintroducere nereusita: " + inner.getMessage());
                sc.nextLine();
                System.out.println("Se aplica fallback: " + fallback);
                return fallback;
            }
        } catch (Exception outer) {
            System.out.println("Eroare neprevazuta in try-in-try: " + outer.getMessage());
            return fallback;
        }
    }
    public static short handleTryInTryShort(Scanner sc, short fallback) {
        System.out.println("Eroare. Incerc o singura reintroducere (try-in-try):");
        try {
            try {
                System.out.print("Reintroduceti short: ");
                short v = sc.nextShort();
                validateNonNegativeShort(v);
                sc.nextLine();
                return v;
            } catch (InputMismatchException | ProfException inner) {
                System.out.println("Reintroducere nereusita: " + inner.getMessage());
                sc.nextLine();
                System.out.println("Se aplica fallback: " + fallback);
                return fallback;
            }
        } catch (Exception outer) {
            System.out.println("Eroare neprevazuta in try-in-try: " + outer.getMessage());
            return fallback;
        }
    }
    public static long handleTryInTryLong(Scanner sc, long fallback) {
        System.out.println("Eroare. Incerc o singura reintroducere (try-in-try):");
        try {
            try {
                System.out.print("Reintroduceti long: ");
                long v = sc.nextLong();
                validateNonNegativeLong(v);
                sc.nextLine();
                return v;
            } catch (InputMismatchException | ProfException inner) {
                System.out.println("Reintroducere nereusita: " + inner.getMessage());
                sc.nextLine();
                System.out.println("Se aplica fallback: " + fallback);
                return fallback;
            }
        } catch (Exception outer) {
            System.out.println("Eroare neprevazuta in try-in-try: " + outer.getMessage());
            return fallback;
        }
    }
}

