package ce216project.view;

import ce216project.controller.PageController;
import ce216project.models.Book;
import ce216project.models.Library;
import ce216project.view.widgets.BookField;
import ce216project.view.widgets.ItemField;
import ce216project.view.widgets.ItemFieldBody;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class NewBookPage extends VBox{

    private final boolean isEditable = true;

    private final String projectPath = System.getProperty("user.dir");
    private final String imagesPath = projectPath + "/shared/images";
    
    private String coverImagePath;

    private AppMenuBar menuBar = new AppMenuBar();
    private HBox mainLayout = new HBox();

    // Image Preview
    private VBox imageContainer = new VBox();
    private ImageView imagePreview = new ImageView();
    private Rectangle imageRectangle = new Rectangle(100, 150);


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

    // Buttons
    private Button saveButton = new Button("Save");
    private Button cancelButton = new Button("Cancel");
    private Button chooseCoverButton = new Button("Cover Image");
    private HBox buttonsContainer = new HBox();
    


    public NewBookPage() {

        // Image Preview
        imageContainer.getChildren().addAll(imageRectangle,chooseCoverButton);
        imageContainer.setSpacing(15);
        imageContainer.setPadding(new Insets(20));
        imagePreview.setX(100);
        imagePreview.setY(150);

        // Left Book Fields
        title = new BookField("Title", "", isEditable, true);
        subtitle = new BookField("Subtitle", "", isEditable, true);
        publisher = new BookField("Publisher", "", isEditable, true);
        date = new BookField("Date", "", isEditable, true);
        isbn = new BookField("ISBN", "", isEditable, true);
        language = new BookField("Language", "", isEditable, true);
        edition = new BookField("Edition", "", isEditable, true);
         

        leftBookFields.getChildren().addAll(title,subtitle,publisher,date,isbn,language,edition);
        leftBookFields.setSpacing(10);


        ArrayList<String> authorsArrayList = new ArrayList<>();
        ArrayList<String> translatorsArrayList = new ArrayList<>();
        ArrayList<String> tagsArrayList = new ArrayList<>();

        authorsList = new ListView<ItemFieldBody>();
        authorsList.setPrefHeight(120);

        translatorsList = new ListView<ItemFieldBody>();
        translatorsList.setPrefHeight(120);

        tagsList = new ListView<ItemFieldBody>();
        tagsList.setPrefHeight(120);

        authors = populateList(authorsArrayList, authorsList,"Authors");
        translators = populateList(translatorsArrayList, translatorsList,"Translators");
        tags = populateList(tagsArrayList, tagsList,"Tags");

        rightBookFields.getChildren().addAll(authors,translators,tags);
        rightBookFields.setSpacing(10);

        // Buttons
        saveButton.setPrefWidth(100);
        saveButton.setOnAction(e -> save());
        cancelButton.setPrefWidth(100);
        cancelButton.setOnAction(e -> cancel());
        chooseCoverButton.setOnAction(e -> getCoverImage());
        buttonsContainer.getChildren().addAll(saveButton,cancelButton);
        buttonsContainer.setSpacing(10);
        buttonsContainer.setPadding(new Insets(0,0,10,0));
        buttonsContainer.setAlignment(Pos.CENTER);

        mainLayout.setSpacing(15);
        mainLayout.setPadding(new Insets(10,10,20,10));
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(imageContainer,leftBookFields,rightBookFields);

        
        this.getChildren().addAll(menuBar,mainLayout,buttonsContainer);


    }   

    private void cancel() {
        MainPage mainPage = new MainPage();
        PageController.changeScene(mainPage, PageController.pagesArray.get(0));
    }

    private void getCoverImage() {

 
        
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files", "*.png","*.jpeg","*.jpg");
        fileChooser.setTitle("Select Cover Image");
        fileChooser.getExtensionFilters().add(extFilter);
        File coverImagePathFile = fileChooser.showOpenDialog(new Stage());
        if(coverImagePathFile == null) return;
        
       
        this.coverImagePath = coverImagePathFile.toPath().toString();
     
        if (  this.coverImagePath != null &&   this.coverImagePath.startsWith(imagesPath)) {
           
            Image prewCoverImage = new Image("file:" + coverImagePath,100,150,false,true);
            this.imagePreview.setImage(prewCoverImage);
            this.imageContainer.getChildren().remove(0);
            this.imageContainer.getChildren().add(0,imagePreview);
        } else {
            System.out.println(this.coverImagePath);
            System.out.println(imagesPath);
            System.out.println(this.coverImagePath.startsWith(imagesPath)); 
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Selected photo must be inside of the project under images folder!");
            alert.showAndWait();
        }
        
         

      
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
      
        int editionInput =   Integer.parseInt(edition.getTextField().getText());
 

        String[] updatedAuthors = new String[authorsList.getItems().size()];
          
        for(int i = 0 ; i < authorsList.getItems().size() ; i++ ){
            updatedAuthors[i] =  authorsList.getItems().get(i).getTextField().getText().toLowerCase(); 
        }
    
        List<String> authorsList = Arrays.asList(updatedAuthors);
   
        String[] updatedTranslators = new String[translatorsList.getItems().size()];
        
        for(int i = 0 ; i < translatorsList.getItems().size() ; i++ ){
            updatedTranslators[i] =  translatorsList.getItems().get(i).getTextField().getText().toLowerCase();
            
        }
        List<String> translatorsList = Arrays.asList(updatedTranslators);
    
        String[] updatedTags = new String[tagsList.getItems().size()];
        
        for(int i = 0 ; i < tagsList.getItems().size() ; i++ ){
            updatedTags[i] =  tagsList.getItems().get(i).getTextField().getText().toLowerCase(); 
        }

        List<String> tagsList = Arrays.asList(updatedTags);
        Library.addTags(tagsList);
    


        // String[] authorsInput =  authors.getTextArea().getText().trim().split(",");
        // List<String> authorsList = Arrays.asList(authorsInput);
        // String[] translatorsInput = translators.getTextArea().getText().trim().split(",");
        // List<String> translatorsList = Arrays.asList(translatorsInput);
        // String[] tagsInput = tags.getTextArea().getText().trim().split(",");
        // List<String> tagsList = Arrays.asList(tagsInput);

       

        Book newBook = new Book(titleInput, subTitleInput, authorsList, translatorsList, isbnInput, publisherInput, dateInput, editionInput, languageInput, 5.0, tagsList,  this.coverImagePath);
        Library.createBook(newBook);

        cancel(); 
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

    
    
}
