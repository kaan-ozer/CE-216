package ce216project.view;

import java.util.*;

import ce216project.controller.PageController;
import ce216project.models.Book;
import ce216project.models.Library;
import ce216project.view.widgets.BookField;
import ce216project.view.widgets.BookTileWidget;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;



public class DetailsPage extends VBox {

    private Book book;
    private boolean isEditable = false;
    private int pageIndex;

    // Main Containers
    private AppMenuBar menuBar = new AppMenuBar();
    private HBox mainLayout = new HBox();
    private HBox rightContainer = new HBox();
    private VBox leftContainer = new VBox();


    // Left Container Widgets
    // Edit and Delete Buttons
    private VBox buttonsContainer = new VBox();
    private Button editButton = new Button("Edit");
    private Button deleteButton = new Button("Delete");
    private Button backButton = new Button("Back");

    // Cover Image
    private BookTileWidget bookTileWidget;
    
    // Right Container Widgets
    // Left Book Fields
    private VBox leftBookFields = new VBox();
    private BookField title;
    private BookField subtitle;
    private BookField publisher;
    private BookField date;
    private BookField isbn;
    private BookField language;
    private BookField edition;

    // Right Book Fields
    private VBox rightBookFields = new VBox();
    private BookField authors;
    private BookField translators;
    private BookField tags;


    
    public DetailsPage(Book book,boolean isEditable,int pageIndex) {
        this.book = book;
        this.pageIndex = pageIndex;

        bookTileWidget = new BookTileWidget(book,false);

        // Right Container Widgets
        // Left Book Fields Container
        title = new BookField("Title",book.getTitle(),isEditable,true);
        subtitle = new BookField("Subtitle", book.getSubtitle(), isEditable, true);
        publisher= new BookField("Publisher", book.getPublisher(), isEditable, true);
        date = new BookField("Date", book.getDate(), isEditable, true);
        isbn= new BookField("ISBN", book.getIsbn(), isEditable, true);
        language = new BookField("Language", book.getLanguage(), isEditable, true);
        edition = new BookField("Edition", Integer.toString(book.getEdition()), isEditable, true);

        leftBookFields.getChildren().addAll(title,subtitle,publisher,date,isbn,language,edition);
        leftBookFields.setSpacing(10);
        leftBookFields.setPadding(new Insets(20));
        
        // Right Book Fields Container
        authors = new BookField("Authors", book.getAuthors().toString(), isEditable, false);
        translators = new BookField("Translators", book.getTranslators().toString(), isEditable, false);
        tags = new BookField("Tags", book.getTags().toString(), isEditable, false);

        rightBookFields.getChildren().addAll(authors,translators,tags);
        rightBookFields.setSpacing(10);
        rightBookFields.setPadding(new Insets(20));
        
        rightContainer.getChildren().addAll(leftBookFields,rightBookFields);

        // Left Container Widgets
        // Buttons
        backButton.setOnAction(e -> back());
        backButton.setPrefWidth(100);
        editButton.setOnAction(e -> edit());
        editButton.setPrefWidth(100);
        deleteButton.setPrefWidth(100);
        deleteButton.setOnAction(e -> delete(book.getIsbn()));
        buttonsContainer.getChildren().addAll(editButton,deleteButton,backButton);
        buttonsContainer.setAlignment(Pos.CENTER);
        buttonsContainer.setSpacing(10);
        

        leftContainer.getChildren().addAll(bookTileWidget,buttonsContainer);
        leftContainer.setSpacing(10);
        leftContainer.setPadding(new Insets(20));

        // Main Layout
        mainLayout.getChildren().setAll(leftContainer,rightContainer);
        mainLayout.setSpacing(20);

        this.getChildren().addAll(menuBar,mainLayout);
    }

    // Getter and setter for editable property
    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    // Update editable property for UI
    private void updateEditable(){

        title.getTextField().setEditable(isEditable);
        subtitle.getTextField().setEditable(isEditable);
        publisher.getTextField().setEditable(isEditable);
        date.getTextField().setEditable(isEditable);
        isbn.getTextField().setEditable(isEditable);
        language.getTextField().setEditable(isEditable);
        edition.getTextField().setEditable(isEditable);

        authors.getTextArea().setEditable(isEditable);
        translators.getTextArea().setEditable(isEditable);
        tags.getTextArea().setEditable(isEditable);
    }

    public void setPageIndex (int pageIndex) {
        this.pageIndex = pageIndex;
    }

    private void edit() {

        isEditable = true;
        updateEditable();

     
       

        Button saveButton = new Button("Save");
        saveButton.setPrefWidth(100);
        System.out.println();
        System.out.println("--------------------");
        System.out.println("CONSTRUCTOR: " + title.getTextField().getText());
        System.out.println("--------------------");
        System.out.println();

        saveButton.setOnAction(e ->  {
               book.setTitle(title.getTextField().getText()); 
               book.setSubtitle(subtitle.getTextField().getText());
               book.setPublisher(publisher.getTextField().getText());
               book.setDate(date.getTextField().getText());
               book.setIsbn(isbn.getTextField().getText());
               book.setLanguage(language.getTextField().getText());
               book.setEdition(Integer.parseInt(edition.getTextField().getText()));
               String[] authorsInput =  authors.getTextArea().getText().trim().split(",");
               List<String> authorsList = Arrays.asList(authorsInput);
               book.setAuthors(authorsList);
               String[] translatorsInput = translators.getTextArea().getText().trim().split(",");
               List<String> translatorsList = Arrays.asList(translatorsInput);
               book.setTranslators(translatorsList);
               String[] tagsInput = tags.getTextArea().getText().trim().split(",");
               List<String> tagsList = Arrays.asList(tagsInput);
               book.setTags(tagsList);

         
            System.out.println();
            System.out.println("--------------------");
            System.out.println("Book: " + book );
            System.out.println("--------------------");
            System.out.println();
    
            saveEdit(book); 
        }); 


        Button cancelButton = new Button("Cancel");
        cancelButton.setPrefWidth(100);
        cancelButton.setOnAction(e -> editCancel());
        
        buttonsContainer.getChildren().clear();
        buttonsContainer.getChildren().addAll(saveButton,cancelButton);

    }

    private void saveEdit(Book book){
        System.out.println();
        System.out.println("--------------------");
        System.out.println("IN function : " + book);
        System.out.println("--------------------");
        System.out.println();
         
  
        Library.saveBooksToJson();
        // bookTileWidget = new BookTileWidget(book, false);
        PageController.closeWindow(PageController.pagesArray.get(pageIndex),pageIndex);
        MainPage mainPage = new MainPage();
        PageController.changeScene(mainPage, PageController.pagesArray.get(0));
       
    }

    private void editCancel() {
        isEditable = false;
        updateEditable();

        buttonsContainer.getChildren().clear();
        buttonsContainer.getChildren().addAll(editButton,deleteButton,backButton);
        
    }

    private void delete(String isbn) {

        Library.deleteBook(isbn);

        PageController.closeWindow(PageController.pagesArray.get(pageIndex),pageIndex);
        MainPage mainPage = new MainPage();
        PageController.changeScene(mainPage, PageController.pagesArray.get(0));
    }

           
        
    private void back(){
        
        PageController.closeWindow(PageController.pagesArray.get(pageIndex),pageIndex);
        MainPage mainPage = new MainPage();
        PageController.changeScene(mainPage, PageController.pagesArray.get(0));
    }

    

    

    
}
