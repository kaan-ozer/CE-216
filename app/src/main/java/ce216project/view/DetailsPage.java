package ce216project.view;

import java.util.*;

import ce216project.controller.PageController;
import ce216project.models.Book;
import ce216project.models.Library;
import ce216project.view.widgets.BookField;
import ce216project.view.widgets.BookTileWidget;
import ce216project.view.widgets.ImagePicker;
import ce216project.view.widgets.ItemFieldBody;
import ce216project.view.widgets.ItemField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
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
    private HBox rateBox = new HBox();
    private Label rateLabel = new Label("Rating");
    private Spinner<Double> ratingSpinner;
    

    // Right Book Fields
    private VBox rightBookFields = new VBox();
    private ItemField authors;
    private ItemField translators;
    private ItemField tags;

    private ListView<ItemFieldBody> authorsListView;
    private ListView<ItemFieldBody> translatorsListView;
    private ListView<ItemFieldBody> tagsListView;


    
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
 
        String editionNumber = book.getEdition() == 0 ? ""  : Integer.toString(book.getEdition());
        edition = new BookField("Edition", editionNumber, isEditable, true);

        // Rating Spinner
        ratingSpinner = new Spinner<>(0.0, 5.0, book.getRating(), 0.5);
        ratingSpinner.setEditable(isEditable);
        ratingSpinner.setDisable(!isEditable);
        ratingSpinner.setStyle("-fx-opacity: 1.0;"); 
        ratingSpinner.getEditor().setStyle("-fx-opacity: 1.0;");
        ratingSpinner.setPrefWidth(150);
        rateLabel.setPrefWidth(60);
        rateBox.getChildren().addAll(rateLabel,ratingSpinner);

        leftBookFields.getChildren().addAll(title,subtitle,publisher,date,isbn,language,edition,rateBox);
        leftBookFields.setSpacing(10);
        leftBookFields.setPadding(new Insets(20)); 

        ArrayList<String> authorsArrayList = new ArrayList<>(book.getAuthors());
        ArrayList<String> translatorsArrayList = new ArrayList<>(book.getTranslators());
        ArrayList<String> tagsArrayList = new ArrayList<>(book.getTags());

        authorsListView = new ListView<ItemFieldBody>();
        authorsListView.setPrefHeight(120);

        translatorsListView = new ListView<ItemFieldBody>();
        translatorsListView.setPrefHeight(120);

        tagsListView = new ListView<ItemFieldBody>();
        tagsListView.setPrefHeight(120);
 
        authors = populateList(authorsArrayList, authorsListView,"Authors");
        translators = populateList(translatorsArrayList, translatorsListView,"translators");
        tags = populateList(tagsArrayList, tagsListView,"Tags");


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

    private  ItemField populateList(ArrayList<String> arrayList,ListView<ItemFieldBody> listView, String labelName) {
    
        listView.setPrefHeight(120);

        int id = 0; 
      
        for (String item: arrayList) {
            
            Button deleteButton = new Button("X");
            deleteButton.setVisible(isEditable);

            deleteButton.setOnAction(e -> {  
                ItemFieldBody itemField = listView.getItems().get(listView.getItems().indexOf(deleteButton.getParent()));
                listView.getSelectionModel().select(itemField); 
                listView.getItems().remove(itemField);
            });

            ItemFieldBody itemFieldBody = new ItemFieldBody( "" + (id + 1), item, isEditable, deleteButton);
            HBox.setHgrow(itemFieldBody, Priority.ALWAYS); 
            listView.getItems().add(itemFieldBody);
  
            id++; 
        }
        
        Button addItemButton = new Button("+");
        ItemField newItem = new ItemField( labelName,"", listView, isEditable, addItemButton);   

        addItemButton.setOnAction(e -> {
            Button deleteFromList = new Button("X");

            deleteFromList.setOnAction(a -> {
                ItemFieldBody itemField = listView.getItems().get(listView.getItems().indexOf(deleteFromList.getParent()));
                listView.getSelectionModel().select(itemField); 
                listView.getItems().remove(itemField);
            });

            ItemFieldBody newItemField = new ItemFieldBody( "" + (listView.getItems().size() + 1), newItem.getTextField().getText() , true, deleteFromList);
            listView.getItems().add(newItemField);
        
        });

        return newItem;
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
        authors.getTextField().setEditable(isEditable);
        authors.getInputField().setVisible(isEditable);
        ratingSpinner.setEditable(isEditable);
        ratingSpinner.setDisable(!isEditable);


        for(ItemFieldBody itemfield : authorsListView.getItems()){
            itemfield.getTextField().setEditable(isEditable);
            itemfield.getButton().setVisible(isEditable);
        }

        translators.getTextField().setEditable(isEditable);
        translators.getInputField().setVisible(isEditable);
        
      
        for(ItemFieldBody itemfield : translatorsListView.getItems()){
            itemfield.getTextField().setEditable(isEditable);
            itemfield.getButton().setVisible(isEditable);
        }
       
        tags.getTextField().setEditable(isEditable);
        tags.getInputField().setVisible(isEditable);
        
      
        for(ItemFieldBody itemfield : tagsListView.getItems()){
            itemfield.getTextField().setEditable(isEditable);
            itemfield.getButton().setVisible(isEditable);
        }
       
       
    }

    public void setPageIndex (int pageIndex) {
        this.pageIndex = pageIndex;
    }

    private void edit() {

        isEditable = true;
        updateEditable(); 

        Button saveButton = new Button("Save");
        saveButton.setPrefWidth(100);
        
        ImagePicker imagePicker = new ImagePicker(true, book.getCoverImagePath());
        imagePicker.setPadding(new Insets(0));
        leftContainer.getChildren().remove(0);
        leftContainer.getChildren().add(0,imagePicker);


        saveButton.setOnAction(e ->  { 
 
            int editionNumber;
            try {
                Book editedBook = new Book();

                if ( edition.getTextField().getText().isEmpty() ||
                edition.getTextField().getText().isBlank()) {
                    edition.getTextField().setText("0"); 
                }  
                
               
                editionNumber = Integer.parseInt(edition.getTextField().getText());
                  

                
                editedBook.setEdition(editionNumber);
              
                editedBook.setTitle(title.getTextField().getText());
                editedBook.setSubtitle(subtitle.getTextField().getText());
                editedBook.setPublisher(publisher.getTextField().getText());
                editedBook.setDate(date.getTextField().getText());
                editedBook.setIsbn(this.book.getIsbn());
                editedBook.setLanguage(language.getTextField().getText().toLowerCase());
                editedBook.setRating(ratingSpinner.getValue()); 
                editedBook.setCoverImagePath(imagePicker.getImagePath());

                if (isbn.getTextField().getText() == null || isbn.getTextField().getText().trim().isEmpty()  || editedBook.getTitle() == null || editedBook.getTitle().trim().isEmpty() ) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Update failed: ISBN and Title are required.");
                    alert.showAndWait();
                    return;
                }
                
                // Authors
                String[] updatedAuthors = new String[authorsListView.getItems().size()];
            
                for(int i = 0 ; i < authorsListView.getItems().size() ; i++ ){
                    updatedAuthors[i] =  authorsListView.getItems().get(i).getTextField().getText(); 
                }

                Set<String> authorsSet = new HashSet<>(Arrays.asList(updatedAuthors));
                editedBook.setAuthors(authorsSet);

                // Translators
                String[] updatedTranslators = new String[translatorsListView.getItems().size()];
            
                for(int i = 0 ; i < translatorsListView.getItems().size() ; i++ ){
                    updatedTranslators[i] =  translatorsListView.getItems().get(i).getTextField().getText(); 
                }
            
                Set<String> translatorsSet = new HashSet<>(Arrays.asList(updatedTranslators));
                editedBook.setTranslators(translatorsSet);

                // Tags
                String[] updatedTags = new String[tagsListView.getItems().size()];
            
                for(int i = 0 ; i < tagsListView.getItems().size() ; i++ ){
                    updatedTags[i] =  tagsListView.getItems().get(i).getTextField().getText();
                }

                Set<String> tagsSet = new HashSet<>(Arrays.asList(updatedTags));
                editedBook.setTags(tagsSet);
        
                saveEdit(editedBook,isbn.getTextField().getText()); 
            } catch (NumberFormatException err) {
              
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Edition must be a number.");
                alert.showAndWait();
                return;
            }
           
        }); 

        Button cancelButton = new Button("Cancel");
        cancelButton.setPrefWidth(100);
        cancelButton.setOnAction(e -> editCancel());
        
        buttonsContainer.getChildren().clear();
        buttonsContainer.getChildren().addAll(saveButton,cancelButton);

    }

    private void saveEdit(Book editedBook,String newISBN){
         
        Library.editBook(editedBook,newISBN);
        PageController.closeWindow(PageController.pagesArray.get(pageIndex),pageIndex);
        MainPage mainPage = new MainPage();
        PageController.changeScene(mainPage, PageController.pagesArray.get(0));
       
    }

    private void editCancel() {
        isEditable = false;
        updateEditable();

        leftContainer.getChildren().remove(0);
        leftContainer.getChildren().add(0, bookTileWidget);
        
        buttonsContainer.getChildren().clear();
        buttonsContainer.getChildren().addAll(editButton,deleteButton,backButton);

        title.getTextField().setText(book.getTitle());
        subtitle.getTextField().setText(book.getSubtitle());
        publisher.getTextField().setText(book.getPublisher());
        date.getTextField().setText(book.getDate());
        isbn.getTextField().setText(book.getIsbn());
        language.getTextField().setText(book.getLanguage()); 
        edition.getTextField().setText  (Integer.toString(book.getEdition()));
    
        authorsListView.getItems().clear();
        book.getAuthors().forEach(author -> {

            Button deleteButton = new Button("X");
            deleteButton.setVisible(isEditable);

            deleteButton.setOnAction(e -> {  
                ItemFieldBody itemField = authorsListView.getItems().get(authorsListView.getItems().indexOf(deleteButton.getParent()));
                authorsListView.getSelectionModel().select(itemField); 
                authorsListView.getItems().remove(itemField);
            });

            ItemFieldBody itemFieldBody = new ItemFieldBody("" + (authorsListView.getItems().size() + 1), author, isEditable, deleteButton);
            
            authorsListView.getItems().add(itemFieldBody);
        });
       
        translatorsListView.getItems().clear();
        book.getTranslators().forEach(author -> {

            Button deleteButton = new Button("X");
            deleteButton.setVisible(isEditable);

            deleteButton.setOnAction(e -> {  
                ItemFieldBody itemField = translatorsListView.getItems().get(translatorsListView.getItems().indexOf(deleteButton.getParent()));
                translatorsListView.getSelectionModel().select(itemField); 
                translatorsListView.getItems().remove(itemField);
            });

            ItemFieldBody itemFieldBody = new ItemFieldBody("" + (translatorsListView.getItems().size() + 1), author, isEditable, deleteButton);
            
            translatorsListView.getItems().add(itemFieldBody);
        });

        tagsListView.getItems().clear();

        book.getTags().forEach(author -> {

            Button deleteButton = new Button("X");
            deleteButton.setVisible(isEditable);

            deleteButton.setOnAction(e -> {  
                ItemFieldBody itemField = tagsListView.getItems().get(tagsListView.getItems().indexOf(deleteButton.getParent()));
                tagsListView.getSelectionModel().select(itemField); 
                tagsListView.getItems().remove(itemField);
            });

            ItemFieldBody itemFieldBody = new ItemFieldBody("" + (tagsListView.getItems().size() + 1), author, isEditable, deleteButton);
            
            tagsListView.getItems().add(itemFieldBody);
        });


       
        BookTileWidget updatedBookTile = new BookTileWidget(book, false);
       
        bookTileWidget = updatedBookTile;
        
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
