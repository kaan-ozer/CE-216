package ce216project.utils;

import com.owlike.genson.Genson;


import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


 
import java.util.ArrayList;
import java.util.Arrays;  
public class IOoperations {

    private static Genson genson = new Genson();

 
    public static void writeToJsonFile(String filename, Object object) {
        File outputDir = new File("output");
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }
        
        String json = genson.serialize(object);

        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
    public static void appendToJsonFile(String filename, Object object) {
         

        File outputDir = new File("output");
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }

        String json = genson.serialize(object);

        try (FileWriter fileWriter = new FileWriter(filename, true)) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Book> readFromJsonFile(String filename ) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(filename));
            String jsonString = new String(jsonData);
            Book[] books =  genson.deserialize(jsonString, Book[].class);
            return new ArrayList<>(Arrays.asList(books));

        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
