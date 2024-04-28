package ce216project.utils;

import com.owlike.genson.Genson;

import ce216project.models.Book;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;  
public class IOoperations {

    private static File outputFile = null;

    private static Genson genson = new Genson();

    private static void isFileExists() {
        File outputDir = new File("output");
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }

         outputFile = new File("output/output.json");

        try {
            if (outputFile.createNewFile()) {
                System.out.println("File Created: " + outputFile.getName());

                try (FileWriter fileWriter = new FileWriter(outputFile)) {
                    fileWriter.write("[]");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } 
        } catch (IOException e) {
            System.out.println("File couldn't be created.");
            e.printStackTrace();
        }
    }

 
    public static void writeToJsonFile(String filename, Object object) {
       
        isFileExists();

        
        String json = genson.serialize(object);

        try (FileWriter fileWriter = new FileWriter(outputFile)) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
    public static void appendToJsonFile(String filename, Object object) {

        isFileExists();

        String json = genson.serialize(object);

        try (FileWriter fileWriter = new FileWriter(filename, true)) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 
public static ArrayList<Book> readFromJsonFile() {
    try {
        isFileExists();

        byte[] jsonData = Files.readAllBytes(Paths.get("output/output.json"));
        String jsonString = new String(jsonData);
        ArrayList<Book> bookList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonString);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

          
            String title = jsonObject.optString("title");
            String isbn = jsonObject.optString("isbn");

            if(title == null || title.equals("null") || title.equals("") || isbn == null || isbn.equals("null") || isbn.equals("")){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Reading Error");
                alert.setHeaderText(null);
                alert.setContentText("A book must meet the condition of having at least title and isbn. Therefore, this book couldn't be read properly. Please check the JSON file.");
                alert.showAndWait();

                continue;
            }



            String subtitle = jsonObject.optString("subtitle");
            String publisher = jsonObject.optString("publisher");
            String date = jsonObject.optString("date");
            String language = jsonObject.optString("language"); 


            int edition = 0;
            String editionStr = jsonObject.optString("edition");
            if (editionStr != null && !editionStr.isEmpty() && Character.isDigit(editionStr.charAt(0))) {
                try {
                      edition = Integer.parseInt(editionStr); 
                } catch (NumberFormatException e) { 

                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Reading Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Edition couldn't be read properly. Please check the JSON file. Book ISBN:" + isbn);
                    alert.showAndWait(); 
                }
            }  

          
            double rating = jsonObject.optDouble("rating");
            if (Double.isNaN(rating)) { 
                rating = 0.0;
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Reading Error");
                alert.setHeaderText(null);
                alert.setContentText("Rating couldn't be read properly. Please check the JSON file. Book ISBN:" + isbn);
                alert.showAndWait(); 
            }
            

            Set<String> authors = new HashSet<>();
            String authorsCheck = jsonObject.optString("authors"); 
         
            if(authorsCheck.startsWith("[") && authorsCheck.endsWith("]")) {
                JSONArray authorsArray = jsonObject.optJSONArray("authors");
                if (authorsArray != null) {
                    for (int j = 0; j < authorsArray.length(); j++) {
                        authors.add(authorsArray.optString(j));
                    }
                }
            } else {  
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Reading Error");
                alert.setHeaderText(null);
                alert.setContentText("Authors couldn't be read properly. Please check the JSON file. Book ISBN:" + isbn);
                alert.showAndWait();
            }
          

            Set<String> translators = new HashSet<>();
            String translatorsCheck = jsonObject.optString("translators"); 

            if(translatorsCheck.startsWith("[") && translatorsCheck.endsWith("]")) {
                JSONArray translatorsArray = jsonObject.optJSONArray("translators");
                if (translatorsArray != null) {
                    for (int j = 0; j < translatorsArray.length(); j++) {
                        authors.add(translatorsArray.optString(j));
                    }
                }
            } else {  
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Reading Error");
                alert.setHeaderText(null);
                alert.setContentText("Translators couldn't be read properly. Please check the JSON file. Book ISBN:" + isbn);
                alert.showAndWait();
            }

            Set<String> tags = new HashSet<>();
            String tagsCheck = jsonObject.optString("tags"); 
            
            if(tagsCheck.startsWith("[") && tagsCheck.endsWith("]")) {            
                JSONArray tagsArray = jsonObject.optJSONArray("tags");
                if (tagsArray != null) {
                    for (int j = 0; j < tagsArray.length(); j++) {
                        authors.add(tagsArray.optString(j));
                    }
                }
            } else {  
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Reading Error");
                alert.setHeaderText(null);
                alert.setContentText("Tags couldn't be read properly. Please check the JSON file. Book ISBN:" + isbn);
                alert.showAndWait();
            }
          
 
             String coverImagePath = jsonObject.optString("coverImagePath");


             if(coverImagePath == null || coverImagePath.equals("null") || coverImagePath.equals("")) {
                coverImagePath = null;
             } else if(!coverImagePath.startsWith(File.separator)) {
                coverImagePath = null;
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Reading Error");
                alert.setHeaderText(null);
                alert.setContentText("Cover Image Path had been stored in a invalid way. It couldn't be read properly. Please check the JSON file. Book ISBN:" + isbn);
                alert.showAndWait();
           
             } else {
              
                File coverImageFile;
                if (File.separatorChar=='\\') { 
                    coverImageFile = new File(System.getProperty("user.dir").trim().split("app")[0] + "app" + coverImagePath.replace('/', File.separatorChar)); 
                } else { 
                    coverImageFile = new File(System.getProperty("user.dir").trim().split("app")[0] + "app" + coverImagePath.replace('\\', File.separatorChar));
                } 
                if(!coverImageFile.exists()) {
                    coverImagePath = null;
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Reading Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Cover Image couldn't be found in project. Therefore, it couldn't be read properly. Book ISBN:" + isbn);
                    alert.showAndWait();
                }
             }
 
            Book book = new Book(title, subtitle, authors, translators, isbn, publisher, date, edition, language, rating, tags, coverImagePath);
            bookList.add(book);
        }

        return bookList;

    } catch (IOException e) {
        e.printStackTrace();
        return null;
    } catch (JSONException e) {
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
