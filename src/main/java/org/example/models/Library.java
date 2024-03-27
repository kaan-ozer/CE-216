package org.example.models;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Library {
    private List<Book> books;

    public Library() {
        loadBooksFromJson();
    }
    public void deleteBook(int Isbn) {
        // Find the book to delete
        Book bookToDelete = null;
        for (Book book : books) {
            if (book.getTitle().equals(Isbn)) {
                bookToDelete = book;
                break;
            }
        }

        if (bookToDelete != null) {
            // Remove the book from the list
            books.remove(bookToDelete);
            // Serialize the updated collection back into JSON and overwrite the file
            saveBooksToJson();
            System.out.println("Book '" + Isbn + "' has been deleted.");
        } else {
            System.out.println("Book '" + Isbn + "' not found.");
        }
    }
    public void editBook(int bookIsbn) {
        boolean bookFound = false;

        // Loop through the books array to find the target object by ID
        for (Book book : books) {
            if (book.getIsbn().equals(bookIsbn)) {
                // Ask the user for new values for various properties
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter new title (press Enter to keep existing): ");
                String newTitle = scanner.nextLine().trim();
                System.out.print("Enter new subtitle (press Enter to keep existing): ");
                String newSubtitle = scanner.nextLine().trim();
                System.out.print("Enter new authors (comma-separated, press Enter to keep existing): ");
                String newAuthors = scanner.nextLine().trim();
                System.out.print("Enter new translators (comma-separated, press Enter to keep existing): ");
                String newTranslators = scanner.nextLine().trim();
                System.out.print("Enter new ISBN (press Enter to keep existing): ");
                String newIsbn = scanner.nextLine().trim();
                System.out.print("Enter new publisher (press Enter to keep existing): ");
                String newPublisher = scanner.nextLine().trim();
                System.out.print("Enter new publication date (YYYY-MM-DD, press Enter to keep existing): ");
                String newDateString = scanner.nextLine().trim();
                System.out.print("Enter new edition (press Enter to keep existing): ");
                String newEdition = scanner.nextLine().trim();
                System.out.print("Enter new cover type (press Enter to keep existing): ");
                String newCover = scanner.nextLine().trim();
                System.out.print("Enter new language (press Enter to keep existing): ");
                String newLanguage = scanner.nextLine().trim();
                System.out.print("Enter new rating (press Enter to keep existing): ");
                String newRating = scanner.nextLine().trim();
                System.out.print("Enter new tags (comma-separated, press Enter to keep existing): ");
                String newTags = scanner.nextLine().trim();

                // Update the fields of the target book object
                if (!newTitle.isEmpty()) {
                    book.setTitle(newTitle);
                }
                if (!newSubtitle.isEmpty()) {
                    book.setSubtitle(newSubtitle);
                }
                if (!newAuthors.isEmpty()) {
                    book.setAuthors(Arrays.asList(newAuthors.split(",")));
                }
                if (!newTranslators.isEmpty()) {
                    book.setTranslators(Arrays.asList(newTranslators.split(",")));
                }
                if (!newIsbn.isEmpty()) {
                    book.setIsbn(newIsbn);
                }
                if (!newPublisher.isEmpty()) {
                    book.setPublisher(newPublisher);
                }
                if (!newDateString.isEmpty()) {
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date newDate = dateFormat.parse(newDateString);
                        book.setDate(newDate);
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Date not updated.");
                    }
                }
                if (!newEdition.isEmpty()) {
                    book.setEdition(Integer.parseInt(newEdition));
                }
                if (!newCover.isEmpty()) {
                    book.setCover(newCover);
                }
                if (!newLanguage.isEmpty()) {
                    book.setLanguage(newLanguage);
                }
                if (!newRating.isEmpty()) {
                    book.setRating(Double.parseDouble(newRating));
                }
                if (!newTags.isEmpty()) {
                    book.setTags(Arrays.asList(newTags.split(",")));
                }

                bookFound = true;
                break;
            }
        }

        if (bookFound) {
            // Serialize the updated collection back into JSON and overwrite the file
            saveBooksToJson();
            System.out.println("Book with ID " + bookIsbn + " has been updated.");
        } else {
            System.out.println("Book with ID " + bookIsbn + " not found.");
        }
    }

    void loadBooksFromJson() {
        try (BufferedReader reader = new BufferedReader(new FileReader("library.json"))) {
            // Deserialize JSON into Book objects and add them to the books list
            // Assume the structure of library.json is an array of Book objects
            // Example: [{ "id": 1, "title": "Book1", "author": "Author1", ... }, { ... }]
            // You may need to adjust the JSON parsing code based on the actual structure
            // For simplicity, I'll skip the actual JSON parsing logic here.
            // Just assume that books are loaded into 'books' list.
            // books = someJsonParsingMethod(reader.readLine());
            // For demonstration, I'm initializing an empty list here
            books = new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error loading books from JSON: " + e.getMessage());
        }
    }

    private void saveBooksToJson() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("library.json"))) {
            // Serialize the books list into JSON format and write it to the file
            // Example: writer.write(someJsonSerializationMethod(books));
            // For demonstration, I'm just writing a placeholder message
            writer.write("Updated JSON content with edited book.");
        } catch (IOException e) {
            System.err.println("Error saving books to JSON: " + e.getMessage());
        }
    }
}

