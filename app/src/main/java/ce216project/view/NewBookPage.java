package ce216project.view;

import ce216project.controller.PageController;
import ce216project.models.Book;
import ce216project.models.Library;
import ce216project.view.widgets.BookField;
import ce216project.view.widgets.ImagePicker;
import ce216project.view.widgets.ItemField;
import ce216project.view.widgets.ItemFieldBody;

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

import java.util.*;



public class NewBookPage extends VBox{

    private final boolean isEditable = true;


    private AppMenuBar menuBar = new AppMenuBar();
    private HBox mainLayout = new HBox();

    private ImagePicker imagePicker;

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

    // Buttons
    private Button saveButton = new Button("Save");
    private Button cancelButton = new Button("Cancel");
    //private Button chooseCoverButton = new Button("Cover Image");
    private HBox buttonsContainer = new HBox();
    
    public NewBookPage() {

        // Left Book Fields
        title = new BookField("Title", "", isEditable, true);
        subtitle = new BookField("Subtitle", "", isEditable, true);
        publisher = new BookField("Publisher", "", isEditable, true);
        date = new BookField("Date", "", isEditable, true);
        isbn = new BookField("ISBN", "", isEditable, true);
        language = new BookField("Language", "", isEditable, true);
        edition = new BookField("Edition", "", isEditable, true);
        
        //Image Picker
        imagePicker = new ImagePicker(isEditable,null);

        // Rating Spinner
        ratingSpinner = new Spinner<>(0.0, 5.0, 0.0, 0.5);
        ratingSpinner.setEditable(true);
        ratingSpinner.setPrefWidth(150);
        rateLabel.setPrefWidth(60);
        rateBox.getChildren().addAll(rateLabel,ratingSpinner);
        
        leftBookFields.getChildren().addAll(title,subtitle,publisher,date,isbn,language,edition,rateBox);
        leftBookFields.setSpacing(10);

        Set<String> authorsArrayList = new HashSet<>();
        Set<String> translatorsArrayList = new HashSet<>();
        Set<String> tagsArrayList = new HashSet<>();

        authorsListView = new ListView<ItemFieldBody>();
        authorsListView.setPrefHeight(120);

        translatorsListView = new ListView<ItemFieldBody>();
        translatorsListView.setPrefHeight(120);

        tagsListView = new ListView<ItemFieldBody>();
        tagsListView.setPrefHeight(120);

        authors = populateList(authorsArrayList, authorsListView,"Authors");
        translators = populateList(translatorsArrayList, translatorsListView,"Translators");
        tags = populateList(tagsArrayList, tagsListView,"Tags");

        rightBookFields.getChildren().addAll(authors,translators,tags);
        rightBookFields.setSpacing(10);

        // Buttons
        saveButton.setPrefWidth(100);
        saveButton.setOnAction(e -> save());
        cancelButton.setPrefWidth(100);
        cancelButton.setOnAction(e -> cancel());
        //chooseCoverButton.setOnAction(e -> getCoverImage());
        buttonsContainer.getChildren().addAll(saveButton,cancelButton);
        buttonsContainer.setSpacing(10);
        buttonsContainer.setPadding(new Insets(0,0,10,0));
        buttonsContainer.setAlignment(Pos.CENTER);

        mainLayout.setSpacing(15);
        mainLayout.setPadding(new Insets(10,10,20,10));
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(imagePicker,leftBookFields,rightBookFields);
 
        this.getChildren().addAll(menuBar,mainLayout,buttonsContainer);

    }   

    private void cancel() {
        MainPage mainPage = new MainPage();
        PageController.changeScene(mainPage, PageController.pagesArray.get(0));
    }

    private void save(){
        
        String titleInput = title.getTextField().getText();
        String subTitleInput = subtitle.getTextField().getText();
        String publisherInput = publisher.getTextField().getText();
        String dateInput = date.getTextField().getText();
        String isbnInput = isbn.getTextField().getText();
        String languageInput = language.getTextField().getText().toLowerCase();
 
        Library.addLanguages(languageInput);
        if ( edition.getTextField().getText().isEmpty() ||
        edition.getTextField().getText().isBlank()) {
            edition.getTextField().setText("0"); 
        }  

    try {
            int editionInput =   Integer.parseInt(edition.getTextField().getText());
            double rateInput = ratingSpinner.getValue();
    
            // Authors
            String[] updatedAuthors = new String[authorsListView.getItems().size()];
            
            for(int i = 0 ; i < authorsListView.getItems().size() ; i++ ){
                updatedAuthors[i] =  authorsListView.getItems().get(i).getTextField().getText().toLowerCase(); 
            }
        
            Set<String> authorsList = new HashSet<>(Arrays.asList(updatedAuthors));
    
            String[] updatedTranslators = new String[translatorsListView.getItems().size()];
            
            // Translators
            for(int i = 0 ; i < translatorsListView.getItems().size() ; i++ ){
            updatedTranslators[i] =  translatorsListView.getItems().get(i).getTextField().getText().toLowerCase();
            }
            Set<String> translatorsList = new HashSet<>(Arrays.asList(updatedTranslators));
            
            // Tags
            String[] updatedTags = new String[tagsListView.getItems().size()];
            
            for(int i = 0 ; i < tagsListView.getItems().size() ; i++ ){
                updatedTags[i] =  tagsListView.getItems().get(i).getTextField().getText().toLowerCase(); 
            }

            Set<String> tagsList = new HashSet<>(Arrays.asList(updatedTags));
            Library.addTags(tagsList);
        
            String coverImagePath = imagePicker.getImagePath();

            Book newBook = new Book(titleInput, subTitleInput, authorsList, translatorsList, isbnInput, publisherInput, dateInput, editionInput, languageInput, rateInput, tagsList,coverImagePath);
    
            String isbnRegex = "\\d{1,}-?\\d{0,}";

            if (!newBook.getIsbn().matches(isbnRegex)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Creation failed: Invalid ISBN format. ISBN must be in the format '0-9'.");
                alert.showAndWait();
                return;
            }

            if (newBook.getIsbn() == null || newBook.getIsbn().trim().isEmpty() || newBook.getTitle() == null || newBook.getTitle().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Creation failed: ISBN and Title are required.");
                alert.showAndWait();
                return;
            }

            Library.createBook(newBook);
            cancel(); 
        }
        catch ( NumberFormatException e1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Edition field must contain only number values.");
            alert.showAndWait();
            return;
        }
    }

    private  ItemField populateList(Set<String> arrayList,ListView<ItemFieldBody> listView, String labelName) {
    
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

    
    
}
