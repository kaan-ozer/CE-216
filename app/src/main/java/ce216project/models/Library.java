package ce216project.models;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import ce216project.utils.IOoperations;

public class Library {
    public static ArrayList<Book> books = new ArrayList<Book>();

    public Library() {
        loadBooksFromJson();
    }

    private void loadBooksFromJson() {
        try {
            books = IOoperations.readFromJsonFile("app/output/output.txt");
        } catch (Exception e) {
            System.err.println("Error loading books from JSON: " + e.getMessage());
        }
    }

    public static void saveBooksToJson() {
        try {
            IOoperations.writeToJsonFile("app/output/output.txt", books);
        } catch (Exception e) {
            System.err.println("Error saving books to JSON: " + e.getMessage());
        }
    }

    public static void createBook(Book book) {
        books.add(book);
        saveBooksToJson();
    }

    public void deleteBook(String isbn) {
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

    public void editBook(String bookIsbn) {
        Book bookToEdit = null;

        for (Book book : books) {
            if (book.getIsbn().equals(bookIsbn)) {
                bookToEdit = book;
                break;
            }
        }

        if (bookToEdit != null) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter new title (press Enter to keep existing): ");
            String newTitle = scanner.nextLine().trim();
            if (!newTitle.isEmpty()) {
                bookToEdit.setTitle(newTitle);
            }

            System.out.print("Enter new authors (comma-separated, press Enter to keep existing): ");
            String newAuthors = scanner.nextLine().trim();
            if (!newAuthors.isEmpty()) {
                List<String> authorsList = Arrays.asList(newAuthors.split("\\s*,\\s*"));
                bookToEdit.setAuthors(authorsList);
            }

            saveBooksToJson();

            System.out.println("Book with ISBN '" + bookIsbn + "' has been updated.");
        } else {
            System.out.println("Book with ISBN '" + bookIsbn + "' not found.");
        }
    }

}
