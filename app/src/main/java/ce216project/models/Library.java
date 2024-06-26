package ce216project.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import ce216project.utils.IOoperations;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Library {

    public static ArrayList<Book> books = new ArrayList<Book>();
    public static HashMap<String, Integer> tags = new HashMap<>();
    public static HashMap<String, Integer> languages = new HashMap<>();

    public Library() {
        loadBooksFromJson();
    }

    public static void loadBooksFromJson() {

        try {
            tags = new HashMap<>();
            languages = new HashMap<>();

            books = IOoperations.readFromJsonFile();

            books.forEach(book -> {
                addLanguages(book.getLanguage());
                addTags(book.getTags());
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading books from JSON: " + e.getMessage());
        }
    }

    public static void addTags(Set<String> bookTags) {

        // taglerini aldık bir kitabın
        for (String tag : bookTags) {
            if (tag == null || tag.isEmpty() || tag.isBlank()) {
                return;
            }
            if (tags.containsKey(tag)) {
                int newCount = tags.get(tag) + 1;
                tags.put(tag.trim(), newCount);
            } else {
                tags.put(tag.trim(), 1);
            }
        }
    }

    public static void addLanguages(String languageInput) {

        if (languageInput == null || languageInput.isEmpty() || languageInput.isBlank()) {
            return;
        }

        if (languages.containsKey(languageInput)) {

            int newCount = languages.get(languageInput) + 1;
            languages.put(languageInput.trim(), newCount);
        } else {
            languages.put(languageInput.trim(), 1);
        }
    }

    public static void saveBooksToJson() {

        try {
            IOoperations.writeToJsonFile("app/output/output.json", books);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error saving books to JSON: " + e.getMessage());
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
                bookToEdit.setTitle(editedBook.getTitle().trim());
                bookToEdit.setSubtitle(editedBook.getSubtitle().trim());
                bookToEdit.setAuthors(editedBook.getAuthors());
                bookToEdit.setTranslators(editedBook.getTranslators());
                bookToEdit.setPublisher(editedBook.getPublisher().trim());
                bookToEdit.setDate(editedBook.getDate().trim());
                bookToEdit.setEdition(editedBook.getEdition());
                bookToEdit.setLanguage(editedBook.getLanguage().trim());
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

    public static ArrayList<Book> filterBooks(ArrayList<String> tagFilters, ArrayList<String> languageFilters) {

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

    public static List<Book> searchBooks(String query) {
        List<Book> results = new ArrayList<>();
        query = query.toLowerCase();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(query) ||
                    book.getSubtitle().toLowerCase().contains(query) ||
                    containsIgnoreCase(book.getAuthors(), query) ||
                    book.getPublisher().toLowerCase().contains(query) ||
                    containsIgnoreCase(book.getTranslators(), query) || book.getDate().toLowerCase().contains(query)
                    || book.getIsbn().contains(query)) {
                results.add(book);
            }
        }
        return results;
    }

    // Method to iterate over authors and translators query
    private static boolean containsIgnoreCase(Set<String> set, String query) {
        for (String element : set) {
            if (element.toLowerCase().contains(query)) {
                return true;
            }
        }
        return false;
    }

}
