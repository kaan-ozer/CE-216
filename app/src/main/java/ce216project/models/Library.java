package ce216project.models;

import java.util.*;

import ce216project.utils.IOoperations;

public class Library {
 
    public static ArrayList<Book> books = new ArrayList<Book>();
    public static HashMap<String,Integer> tags = new HashMap<>();
    public static HashMap<String,Integer> languages = new HashMap<>();

 

    public Library() {
        loadBooksFromJson();
    }

    public static void loadBooksFromJson() {
        try {
            books = IOoperations.readFromJsonFile();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading books from JSON: " + e.getMessage());
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

    public static void deleteBook(String isbn) {
        Book bookToDelete = null;

        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                bookToDelete = book;
                break;
            }
        }

        if (bookToDelete != null) {
            books.remove(bookToDelete);
            saveBooksToJson(); // Persist the updated books list to JSON
            System.out.println("Book with ISBN '" + isbn + "' has been deleted.");
        } else {
            System.out.println("Book with ISBN '" + isbn + "' not found.");
        }
    }

    public static void editBook(Book editedBook) {

        System.out.println("Edit book method called: " + editedBook);
        Book bookToEdit = null;

        for (Book book : books) {
            if (book.getIsbn().equals(editedBook.getIsbn())) {
                bookToEdit = book;
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
                bookToEdit.setCoverImagePath(null);
                break;
            }
        }

        if (bookToEdit != null) {
            
           saveBooksToJson();

            System.out.println("Book with ISBN '" + editedBook + "' has been updated.");
        } else {
            System.out.println("Book with ISBN '" + editedBook + "' not found.");
        }
    }


    public static void addTags(List<String> tagsInputList){

        for(String tagInput : tagsInputList){
            if (tags.containsKey(tagInput)) {
                int newCount = tags.get(tagInput) + 1; 
                tags.put(tagInput, newCount);
            } else {
                tags.put(tagInput, 1); // initialize count to 1 for new tags
            }
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

}
