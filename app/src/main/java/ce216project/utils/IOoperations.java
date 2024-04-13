package ce216project.utils;

import com.owlike.genson.Genson;

import ce216project.models.Book;

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

        File outputFile = new File("output/output.txt");

        try {
            if (outputFile.createNewFile()) {
                System.out.println("File Created: " + outputFile.getName());
            } 
        } catch (IOException e) {
            System.out.println("File couldn't be created.");
            e.printStackTrace();
        }

        
        String json = genson.serialize(object);

        try (FileWriter fileWriter = new FileWriter(outputFile)) {
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

    public static ArrayList<Book> readFromJsonFile() {
        try {
             
            byte[] jsonData = Files.readAllBytes(Paths.get("output/output.txt"));
            String jsonString = new String(jsonData); 
            Book[] books =  genson.deserialize(jsonString, Book[].class);
            ArrayList<Book> bookList;
            if (books == null || books.length == 0) {
                bookList = new ArrayList<>();  
            } else {
                bookList = new ArrayList<>(Arrays.asList(books));
            }
            return bookList;

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
