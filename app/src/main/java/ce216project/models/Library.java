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
 
    
            
            books = IOoperations.readFromJsonFile();
        
            books.forEach(book ->{
                if (book.getCoverImagePath() != null) {
                    try {

                        book.setCoverImagePath(book.getCoverImagePath());
                    }catch(ArrayIndexOutOfBoundsException err) {

                        // err.printStackTrace();
                        System.err.println("Error loading book cover image: " + err.getMessage());
                    }
                }

                addLanguages(book.getLanguage());
                addTags(book.getTags());
             
               
         
        });
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading books from JSON: " + e.getMessage());
        }
    }



    public static void addTags(Set<String> bookTags){

        //taglerini aldık bir kitabın
        for(String tag : bookTags) {
            if (tags.containsKey(tag)) {
                int newCount = tags.get(tag) + 1;

                
                tags.put(tag, newCount);  
            } else {
              
                tags.put(tag, 1);  
                
            }
        }
    }
    public static void addLanguages(String languageInput) {

        if (languageInput == null || languageInput.isEmpty()) {
            return;
        }

        if(languages.containsKey(languageInput)) {
            int newCount = languages.get(languageInput) + 1;
            languages.put(languageInput, newCount);
        } else {
            languages.put(languageInput,1);
        }
    }

    public static void saveBooksToJson() {
        try {
          
            IOoperations.writeToJsonFile("app/output/output.txt", books);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error saving books to JSON: " + e.getMessage() );
        }
    }




    public static void createBook(Book book) {
      
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
                bookToEdit.setCoverImagePath(editedBook.getCoverImagePath());
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
                    
                }  
            });
        }  
    }

  
    public static ArrayList<Book> filterBooks(ArrayList<String> tagFilters, ArrayList<String> languageFilters){
        
        ArrayList<Book> filteredBooks = new ArrayList<>();
        
        
        for (Book book : books) {
            boolean tagMatch = tagFilters.isEmpty() || book.getTags().stream().anyMatch(tagFilters::contains);
            boolean languageMatch = languageFilters.isEmpty() || languageFilters.contains(book.getLanguage());
    
            if (tagMatch && languageMatch) {
                filteredBooks.add(book);
            }
        }
    
        return filteredBooks;
    }


}


