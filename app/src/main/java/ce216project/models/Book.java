package ce216project.models;

import java.nio.file.Path;
import java.util.Set;

public class Book {
    private String title;
    private String subtitle;
    private Set<String> authors;
    private Set<String> translators;
    private String isbn;
    private String publisher;
    private String date;
    private int edition;
    private String language;
    private double rating;
    private Set<String> tags;
    private String coverImagePath;

    //DON'T DELETE THIS EMPTY CONSTRUCTOR - GENSON USES IT!
    public Book() {
    }

    public Book(String title, String subtitle, Set<String> authors, Set<String> translators, String isbn, String publisher, String date, int edition, String language, double rating, Set<String> tags, String coverImagePath) {
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
        this.translators = translators;
        this.isbn = isbn;
        this.publisher = publisher;
        this.date = date;
        this.edition = edition;
        this.language = language;
        this.rating = rating;
        this.tags = tags;
        this.coverImagePath = coverImagePath;
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

    public Set<String> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<String> authors) {
        this.authors = authors;
    }

    public Set<String> getTranslators() {
            return translators;
    }

    public void setTranslators(Set<String> translators) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
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

    public Set<String> getTags() {
       
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public String getCoverImagePath(){
        return coverImagePath;
    }

    public void setCoverImagePath(String coverImagePath){
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
                ", coverPath=" + (coverImagePath != null ? coverImagePath.toString() : "null")+
                '}';
    }

   


}