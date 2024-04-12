package ce216project.models;

import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

public class Book {
    private String title;
    private String subtitle = "";
    private List<String> authors = new ArrayList<>();
    private List<String> translators = new ArrayList<>();
    private String isbn;
    private String publisher = "";
    private String date = "";
    private int edition;
    private String language = "";
    private double rating;
    private List<String> tags = new ArrayList<>();
    private Path coverImagePath;

    public Book() {
    }

    // Main constructor with mandatory fields and handling of optional fields
    public Book(String title, String subtitle, List<String> authors, List<String> translators, String isbn,
            String publisher, String date, int edition, String language, double rating, List<String> tags,
            Path coverImagePath) {
        this.setTitle(title); // Set title with validation
        this.setSubtitle(subtitle);
        this.setAuthors(authors);
        this.setTranslators(translators);
        this.setIsbn(isbn); // Set ISBN with validation
        this.setPublisher(publisher);
        this.setDate(date);
        this.setEdition(edition);
        this.setLanguage(language);
        this.setRating(rating);
        this.setTags(tags);
        this.setCoverImagePath(coverImagePath);
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle != null ? subtitle : "";
    }

    public void setAuthors(List<String> authors) {
        this.authors = (authors != null) ? authors : new ArrayList<>();
    }

    public void setTranslators(List<String> translators) {
        this.translators = (translators != null) ? translators : new ArrayList<>();
    }

    public void setIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be empty.");
        }
        this.isbn = isbn;
    }

    public void setPublisher(String publisher) {
        this.publisher = (publisher != null) ? publisher : "";
    }

    public void setDate(String date) {
        this.date = (date != null) ? date : "";
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public void setLanguage(String language) {
        this.language = (language != null) ? language : "";
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setTags(List<String> tags) {
        this.tags = (tags != null) ? tags : new ArrayList<>();
    }

    public void setCoverImagePath(Path coverImagePath) {
        this.coverImagePath = coverImagePath;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", authors=" + authors +
                ", translators=" + translators +
                ", isbn='" + isbn + '\'' +
                ", publisher='" + publisher + '\'' +
                ", date=" + date +
                ", edition=" + edition +
                ", language='" + language + '\'' +
                ", rating=" + rating +
                ", tags=" + tags +
                ", coverPath=" + (coverImagePath != null ? coverImagePath.toString() : "null") +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public List<String> getTranslators() {
        return translators;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getDate() {
        return date;
    }

    public int getEdition() {
        return edition;
    }

    public String getLanguage() {
        return language;
    }

    public double getRating() {
        return rating;
    }

    public List<String> getTags() {
        return tags;
    }

    public Path getCoverImagePath() {
        return coverImagePath;
    }

}