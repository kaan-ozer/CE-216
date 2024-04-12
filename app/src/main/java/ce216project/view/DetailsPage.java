package ce216project.view;

import java.util.*;

import ce216project.controller.PageController;
import ce216project.models.Book;
import ce216project.models.Library;
import ce216project.view.widgets.BookField;
import ce216project.view.widgets.BookTileWidget;
import ce216project.view.widgets.ItemFieldBody;
import ce216project.view.widgets.ItemField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
    private ItemField authors;
    private BookField translators;
    private BookField tags;

    private ListView<ItemFieldBody> authorsList;
    private ListView<ItemFieldBody> translatorsList;
    private ListView<ItemFieldBody> tagsList;


    
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

       
        
        
        language = new BookField("Language",  book.getLanguage() , isEditable, true);
        edition = new BookField("Edition", Integer.toString(book.getEdition()), isEditable, true);

        leftBookFields.getChildren().addAll(title,subtitle,publisher,date,isbn,language,edition);
        leftBookFields.setSpacing(10);
        leftBookFields.setPadding(new Insets(20));
        String authorsAsString = this.joinWithCommas(book.getAuthors());
        String translatorsAsString = this.joinWithCommas(book.getTranslators());
        String tagsAsString = this.joinWithCommas(book.getTags());

        

       
        authorsList = new ListView<ItemFieldBody>();
        authorsList.setPrefHeight(120);
        
        int id = 0; 
    

        for (String author: book.getAuthors()) {
            
            Button deleteButton = new Button("X");
            deleteButton.setVisible(isEditable);

            deleteButton.setOnAction(e -> {
                System.out.println("Delete button clicked");
                System.out.println("ne abi bu : " + authorsList.getItems().indexOf(deleteButton.getParent()));
                ItemFieldBody itemField = authorsList.getItems().get(authorsList.getItems().indexOf(deleteButton.getParent()));
                authorsList.getSelectionModel().select(itemField);
                List<String> authors = new ArrayList<>(book.getAuthors());
                authors.remove(authorsList.getItems().indexOf(deleteButton.getParent()));
                book.setAuthors(authors);
                Library.saveBooksToJson();
                authorsList.getItems().remove(itemField);
            });
               
            
            ItemFieldBody authorField = new ItemFieldBody( "" + (id + 1), author, isEditable, deleteButton);
            HBox.setHgrow(authorField, Priority.ALWAYS); 
            authorsList.getItems().add(authorField);

          
            id++; 
        }
        
        Button addAuthorButton = new Button("+");
 
        authors = new ItemField( "Authors","", authorsList, isEditable, addAuthorButton);

       
         
        addAuthorButton.setOnAction(e -> {

            Button deleteFromList = new Button("X");
         

            deleteFromList.setOnAction(a -> {
                ItemFieldBody itemField = authorsList.getItems().get(authorsList.getItems().indexOf(deleteFromList.getParent()));
                authorsList.getSelectionModel().select(itemField); 
                authorsList.getItems().remove(itemField);
            });

            System.out.println("Add button clicked");
            System.out.println(authors.getTextField().getText());
            ItemFieldBody authorField = new ItemFieldBody( "" + (authorsList.getItems().size() + 1), authors.getTextField().getText() , true, deleteFromList);
            authorsList.getItems().add(authorField);
        
        });


        translators = new BookField("Translators", translatorsAsString, isEditable, false);
        tags = new BookField("Tags", tagsAsString, isEditable, false);

        rightBookFields.getChildren().addAll( authors,translators,tags);
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
        edition.getTextField().setEditable(isEditable)
        ;
         
        authors.getTextField().setEditable(isEditable);
        authors.getInputField().setVisible(isEditable);
        
        for(ItemFieldBody itemfield : authorsList.getItems()){
            itemfield.getTextField().setEditable(isEditable);
            itemfield.getButton().setVisible(isEditable);
        }
       
       
        
       
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
            Book editedBook = new Book();
            editedBook.setTitle(title.getTextField().getText());
            editedBook.setSubtitle(subtitle.getTextField().getText());
            editedBook.setPublisher(publisher.getTextField().getText());
            editedBook.setDate(date.getTextField().getText());
            editedBook.setIsbn(isbn.getTextField().getText());
            editedBook.setLanguage(language.getTextField().getText());
            editedBook.setEdition(Integer.parseInt(edition.getTextField().getText()));


            String[] updatedAuthors = new String[authorsList.getItems().size()];
            System.out.println(authorsList.getItems().size() + "size");
            for(int i = 0 ; i < authorsList.getItems().size() ; i++ ){
                updatedAuthors[i] =  authorsList.getItems().get(i).getTextField().getText();
                System.out.println(updatedAuthors[i] + "updatedAuthors");
            }
            book.setAuthors(Arrays.asList(updatedAuthors));
         
            // String[] authorsInput = authors.getTextArea().getText().trim().split(",");
            String[] authorsInput = updatedAuthors;
            List<String> authorsList = Arrays.asList(authorsInput);
            editedBook.setAuthors(authorsList);
            String[] translatorsInput = translators.getTextArea().getText().trim().split(",");
            List<String> translatorsList = Arrays.asList(translatorsInput);
            editedBook.setTranslators(translatorsList);
            String[] tagsInput = tags.getTextArea().getText().trim().split(",");
            List<String> tagsList = Arrays.asList(tagsInput);
            editedBook.setTags(tagsList);

     
            saveEdit(editedBook); 
        }); 


        Button cancelButton = new Button("Cancel");
        cancelButton.setPrefWidth(100);
        cancelButton.setOnAction(e -> editCancel());
        
        buttonsContainer.getChildren().clear();
        buttonsContainer.getChildren().addAll(saveButton,cancelButton);

    }

    private void saveEdit(Book editedBook){
        System.out.println();
        System.out.println("--------------------");
        System.out.println("IN function : " + editedBook);
        System.out.println("--------------------");
        System.out.println();
         
        Library.editBook(editedBook);
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

    private  String joinWithCommas(List<String> items) {
        if (items == null || items.isEmpty()) {
            return "";
        }
        
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            result.append(items.get(i));
            if (i < items.size() - 1) {
                result.append(",");
            }
        }
        
        return result.toString();
    }
    
    

    

    
}
