package ce216project.utils;

import com.owlike.genson.Genson;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class IOoperations {

    public static void writeToJsonFile(String filename, Object object) {
        Genson genson = new Genson();
        String json = genson.serialize(object);

        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendToJsonFile(String filename, Object object) {
        Genson genson = new Genson();
        String json = genson.serialize(object);

        try (FileWriter fileWriter = new FileWriter(filename, true)) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void resetJsonFile(String filename) {
        try {
            Files.deleteIfExists(Paths.get(filename));
            Files.createFile(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}
