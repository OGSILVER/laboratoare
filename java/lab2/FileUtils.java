import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class FileUtils {
    public static void writefile(String filename, String content) {
        // Implementation to write content to a file
        try {
            FileOutputStream fout = new FileOutputStream(filename + ".txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(content);
            oos.close();
            fout.close();
        } catch (Exception e) {
            System.out.println("avem eroare!!! " + e.getMessage());
        }
    }
}
