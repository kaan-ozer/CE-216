package org.example.models;import java.lang.reflect.Field;import java.text.SimpleDateFormat;import java.util.*;import java.util.stream.Collectors;public class searchFeature {    private final Library library;    private List<Book> books;    public searchFeature(Library library) {        this.library = library;        loadBooksUsingReflection();    }    private void loadBooksUsingReflection() {        try {            Field booksField = Library.class.getDeclaredField("books");            booksField.setAccessible(true);            this.books = (List<Book>) booksField.get(library);        } catch (NoSuchFieldException | IllegalAccessException e) {            throw new RuntimeException("Reflection failed to access books field", e);        }    }    public List<Book> search(Map<String, String> filters) {        // Ensure the books are reloaded from JSON in case they were updated        library.loadBooksFromJson();        // Reload books using reflection every time a search is performed to get the updated list        loadBooksUsingReflection();        return books.stream()                .filter(book -> matchesFilters(book, filters))                .collect(Collectors.toList());    }    private boolean matchesFilters(Book book, Map<String, String> filters) {        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());        for (Map.Entry<String, String> filter : filters.entrySet()) {            String key = filter.getKey();            String value = filter.getValue().toLowerCase();            try {                switch (key) {                    case "title":                        if (!book.getTitle().toLowerCase().contains(value)) return false;                        break;                    case "subtitle":                        if (!book.getSubtitle().toLowerCase().contains(value)) return false;                        break;                    case "authors":                        if (book.getAuthors().stream().noneMatch(author -> author.toLowerCase().contains(value))) return false;                        break;                    case "translators":                        if (book.getTranslators().stream().noneMatch(translator -> translator.toLowerCase().contains(value))) return false;                        break;                    case "isbn":                        if (!book.getIsbn().toLowerCase().equals(value)) return false;                        break;                    case "publisher":                        if (!book.getPublisher().toLowerCase().contains(value)) return false;                        break;                    case "date":                        String bookDate = dateFormat.format(book.getDate());                        if (!bookDate.equals(value)) return false;                        break;                    case "edition":                        if (!Integer.toString(book.getEdition()).equals(value)) return false;                        break;                    case "cover":                        if (!book.getCover().toLowerCase().contains(value)) return false;                        break;                    case "language":                        if (!book.getLanguage().toLowerCase().equals(value)) return false;                        break;                    case "rating":                        if (!Double.toString(book.getRating()).equals(value)) return false;                        break;                    case "tags":                        if (book.getTags().stream().noneMatch(tag -> tag.toLowerCase().contains(value))) return false;                        break;                    // Implement additional filters as necessary                    default:                        break;                }            } catch (Exception e) {                System.err.println("Error matching filter: " + key + " with value " + value);                return false;            }        }        return true;    }}