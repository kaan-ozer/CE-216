package org.example.models;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Book {
    private String title;
    private String subtitle;
    private List<String> authors;
    private List<String> translators;
    private String isbn;
    private String publisher;
    private Date date;
    private int edition;
    private String cover;
    private String language;
    private double rating;
    private List<String> tags;

    public Book(String title, String subtitle, List<String> authors, List<String> translators, String isbn, String publisher, Date date, int edition, String cover, String language, double rating, List<String> tags) {
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
        this.translators = translators;
        this.isbn = isbn;
        this.publisher = publisher;
        this.date = date;
        this.edition = edition;
        this.cover = cover;
        this.language = language;
        this.rating = rating;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public List<String> getTranslators() {
        return translators;
    }

    public void setTranslators(List<String> translators) {
        this.translators = translators;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
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
                ", cover='" + cover + '\'' +
                ", language='" + language + '\'' +
                ", rating=" + rating +
                ", tags=" + tags +
                '}';
    }

}
