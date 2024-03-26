package ce216project.utils;

import ce216project.models.Book;
import com.owlike.genson.Genson;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

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

    public static void main(String[] args) {
        List<String> authors1 = List.of("Author1", "Author2");
        List<String> translators1 = List.of("Translator1", "Translator2");
        List<String> tags1 = List.of("Tag1", "Tag2");

        Book book1 = new Book(
                "Title1",
                "Subtitle1",
                authors1,
                translators1,
                "ISBN1234",
                "Publisher1",
                new Date(),
                1,
                "Cover1",
                "Language1",
                4.5,
                tags1
        );

        List<String> authors2 = List.of("Author3", "Author4");
        List<String> translators2 = List.of("Translator3", "Translator4");
        List<String> tags2 = List.of("Tag3", "Tag4");

        Book book2 = new Book(
                "Title2",
                "Subtitle2",
                authors2,
                translators2,
                "ISBN5678",
                "Publisher2",
                new Date(),
                2,
                "Cover2",
                "Language2",
                3.5,
                tags2
        );

        List<Book> books = List.of(book1, book2);


        //writeToJsonFile("example.json", books);


        appendToJsonFile("example.json", books);


        //resetJsonFile("example.json");
    }
}
