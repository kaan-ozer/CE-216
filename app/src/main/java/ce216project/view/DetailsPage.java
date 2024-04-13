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
    private ItemField translators;
    private ItemField tags;

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

        ArrayList<String> authorsArrayList = new ArrayList<>(book.getAuthors());
        ArrayList<String> translatorsArrayList = new ArrayList<>(book.getTranslators());
        ArrayList<String> tagsArrayList = new ArrayList<>(book.getTags());

        authorsList = new ListView<ItemFieldBody>();
        authorsList.setPrefHeight(120);

        translatorsList = new ListView<ItemFieldBody>();
        translatorsList.setPrefHeight(120);

        tagsList = new ListView<ItemFieldBody>();
        tagsList.setPrefHeight(120);
 
        authors = populateList(authorsArrayList, authorsList,"Authors");
        translators = populateList(translatorsArrayList, translatorsList,"translators");
        tags = populateList(tagsArrayList, tagsList,"Tags");


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
                // ArrayList<String> newArrayList = new ArrayList<>();
                // newArrayList.addAll(arrayList);
                // newArrayList.remove(listView.getItems().indexOf(deleteButton.getParent()));
                // book.setAuthors(newArrayList);
                // Library.saveBooksToJson();
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

        for(ItemFieldBody itemfield : authorsList.getItems()){
            itemfield.getTextField().setEditable(isEditable);
            itemfield.getButton().setVisible(isEditable);
        }

        translators.getTextField().setEditable(isEditable);
        translators.getInputField().setVisible(isEditable);
        
      
        for(ItemFieldBody itemfield : translatorsList.getItems()){
            itemfield.getTextField().setEditable(isEditable);
            itemfield.getButton().setVisible(isEditable);
        }
       
        tags.getTextField().setEditable(isEditable);
        tags.getInputField().setVisible(isEditable);
        
      
        for(ItemFieldBody itemfield : tagsList.getItems()){
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
      

        saveButton.setOnAction(e ->  { 
            Book editedBook = new Book();
            editedBook.setTitle(title.getTextField().getText());
            editedBook.setSubtitle(subtitle.getTextField().getText());
            editedBook.setPublisher(publisher.getTextField().getText());
            editedBook.setDate(date.getTextField().getText());
            editedBook.setIsbn(isbn.getTextField().getText());
            editedBook.setLanguage(language.getTextField().getText());
            if(!editedBook.getLanguage().equalsIgnoreCase(book.getLanguage())){
                Library.addLanguages(editedBook.getLanguage());
            }
            editedBook.setEdition(Integer.parseInt(edition.getTextField().getText()));


            String[] updatedAuthors = new String[authorsList.getItems().size()];
          
            for(int i = 0 ; i < authorsList.getItems().size() ; i++ ){
                updatedAuthors[i] =  authorsList.getItems().get(i).getTextField().getText(); 
            }
      
            List<String> authorsList = Arrays.asList(updatedAuthors);
            editedBook.setAuthors(authorsList);

        
            String[] updatedTranslators = new String[translatorsList.getItems().size()];
          
            for(int i = 0 ; i < translatorsList.getItems().size() ; i++ ){
                updatedTranslators[i] =  translatorsList.getItems().get(i).getTextField().getText();
                
            }
        
            
            editedBook.setTranslators(Arrays.asList(updatedTranslators));

            String[] updatedTags = new String[tagsList.getItems().size()];
          
            for(int i = 0 ; i < tagsList.getItems().size() ; i++ ){
                updatedTags[i] =  tagsList.getItems().get(i).getTextField().getText();
              
            }
        

            editedBook.setTags(Arrays.asList(updatedTags));
            
            // String[] tagsInput = tags.getTextArea().getText().trim().split(",");
            // List<String> tagsList = Arrays.asList(tagsInput);
            // editedBook.setTags(tagsList);

     
            saveEdit(editedBook); 
        }); 



        Button cancelButton = new Button("Cancel");
        cancelButton.setPrefWidth(100);
        cancelButton.setOnAction(e -> editCancel());
        
        buttonsContainer.getChildren().clear();
        buttonsContainer.getChildren().addAll(saveButton,cancelButton);

    }

    private void saveEdit(Book editedBook){
       
         
        Library.editBook(editedBook);
        PageController.closeWindow(PageController.pagesArray.get(pageIndex),pageIndex);
        MainPage mainPage = new MainPage();
        PageController.changeScene(mainPage, PageController.pagesArray.get(0));
       
    }

    private void editCancel() {
        isEditable = false;
        updateEditable();

        //leftContainer.getChildren().remove(0);
        buttonsContainer.getChildren().clear();
        buttonsContainer.getChildren().addAll(editButton,deleteButton,backButton);

        System.out.println(book.getTitle());
        BookTileWidget updatedBookTile = new BookTileWidget(book, false);
        System.out.println(updatedBookTile.getBook().getTitle());
        bookTileWidget = updatedBookTile;

        //leftContainer.getChildren().add(0, updatedBookTile);
        //leftContainer.getChildren().add(1,buttonsContainer);
        
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
