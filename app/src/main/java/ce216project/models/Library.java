package ce216project.models;

import java.io.File;

import java.util.*;

import ce216project.utils.IOoperations;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Library {
 
   
    public static ArrayList<Book> books = new ArrayList<Book>();
    public static HashMap<String,Integer> tags = new HashMap<>();
    public static HashMap<String,Integer> languages = new HashMap<>();

 

    public Library() {
        loadBooksFromJson();
    }

    public static void loadBooksFromJson() {
        try {

            tags = new HashMap<>();
            languages = new HashMap<>();

            if(languages.size() == 0){
                languages.put("english", 0);
                languages.put("turkish", 0);
                languages.put("german", 0);
            }

            if(tags.size() == 0){
                tags.put("horror", 0);
                tags.put("fiction", 0);
                tags.put("thriller", 0);
            }
    
            
            books = IOoperations.readFromJsonFile();
            String projectPath = System.getProperty("user.dir");
            String imagesPath = projectPath + File.separator + "shared" + File.separator + "images" + File.separator;
            books.forEach(item ->{
            if (item.getCoverImagePath() != null) {
                item.setCoverImagePath(imagesPath + item.getCoverImagePath().trim().split(imagesPath)[1]);
            }

            books.forEach(book ->{

                addLanguages(book.getLanguage());
                addTags(book.getTags());
                    
                if (book.getCoverImagePath() != null) {
                    book.setCoverImagePath(imagesPath + book.getCoverImagePath().trim().split(imagesPath)[1]);
                }  

               
            });
        });
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading books from JSON: " + e.getMessage());
        }
    }

    public static void loadTagsFromJson(){

    }

    public static void saveBooksToJson() {
        try {
            System.out.println(books.get(0).getCoverImagePath());
            IOoperations.writeToJsonFile("app/output/output.txt", books);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error saving books to JSON: " + e.getMessage() );
        }
    }


    public static void addTags(Set<String> tagsInputList){


        for(String tagInput : tagsInputList){
            if (tags.containsKey(tagInput)) {
                int newCount = tags.get(tagInput) + 1; 
                tags.put(tagInput, newCount);
            } else {
                tags.put(tagInput, 1); // initialize count to 1 for new tags
            }
        }
    }

    public static void createBook(Book book) {
        if (book.getIsbn() == null || book.getIsbn().trim().isEmpty() || book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Creation failed: ISBN and Title are required.");
            alert.showAndWait();
            return;
        }
        books.add(book);
        saveBooksToJson();
    }

    public static void editBook(Book editedBook, String newISBN) {
     
        Book bookToEdit = null;
        for (Book book : books) {
            if (book.getIsbn().equals(editedBook.getIsbn())) {
                bookToEdit = book;
                bookToEdit.setIsbn(newISBN);
                bookToEdit.setTitle(editedBook.getTitle());
                bookToEdit.setSubtitle(editedBook.getSubtitle());
                bookToEdit.setAuthors(editedBook.getAuthors());
                bookToEdit.setTranslators(editedBook.getTranslators());
                bookToEdit.setPublisher(editedBook.getPublisher());
                bookToEdit.setDate(editedBook.getDate());
                bookToEdit.setEdition(editedBook.getEdition());
                bookToEdit.setLanguage(editedBook.getLanguage());
                bookToEdit.setRating(editedBook.getRating());
                bookToEdit.setTags(editedBook.getTags()); 
                break;
            }
        }

        if (bookToEdit != null) {
            saveBooksToJson(); 
            loadBooksFromJson(); 
        }  
    }
    public static void deleteBook(String isbn) {
        Book bookToDelete = null;

        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                bookToDelete = book;
                break;
            }
        }

        if (bookToDelete != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete the book with ISBN '" + isbn + "'?");

            Book finalBookToDelete = bookToDelete;
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    books.remove(finalBookToDelete);
                    saveBooksToJson(); 
                    loadBooksFromJson();
                    System.out.println("Book with ISBN '" + isbn + "' has been deleted.");
                } else {
                    System.out.println("Deletion canceled. Book with ISBN '" + isbn + "' was not deleted.");
                }
            });
        } else {
            System.out.println("Book with ISBN '" + isbn + "' not found.");
        }
    }

    public static void addLanguages(String languageInput) {

      
        if(languages.containsKey(languageInput)) {
            int newCount = languages.get(languageInput) + 1;
            languages.put(languageInput, newCount);
        } else {
            languages.put(languageInput,1);
        }
    }

    public static ArrayList<Book> filterBooks(ArrayList<String> tagFilters, ArrayList<String> languageFilters){
        
        ArrayList<Book> filteredBooks = new ArrayList<>();
        
        System.out.println("Filter at Library class");
        for (Book book : books) {
            boolean tagMatch = false;
            boolean languageMatch = false;
    
            // Check if the book matches any of the selected tags
            if (!tagFilters.isEmpty()) {
                for (String tag : tagFilters) {
                    if (book.getTags().contains(tag)) {
                        tagMatch = true;
                        break;
                    }
                }
            }
    
            // Check if the book matches any of the selected languages
            else if (languageFilters.isEmpty() || languageFilters.contains(book.getLanguage())) {
                languageMatch = true;
            }
    
            if (tagMatch || languageMatch) {
                filteredBooks.add(book);
            }
    
        }
    
        return filteredBooks;
    }


}


