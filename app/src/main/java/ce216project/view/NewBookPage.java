package ce216project.view;

import ce216project.controller.PageController;
import ce216project.models.Book;
import ce216project.models.Library;
import ce216project.view.widgets.BookField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.nio.file.Path;
import java.util.List;
import java.util.Arrays;

public class NewBookPage extends VBox{

    private final boolean isEditable = true;
    
    private Path coverImagePath;

    private AppMenuBar menuBar = new AppMenuBar();
    private HBox mainLayout = new HBox();

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

    // Buttons
    private Button saveButton = new Button("Save");
    private Button cancelButton = new Button("Cancel");
    private Button chooseCoverButton = new Button("Cover Image");
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
         

        leftBookFields.getChildren().addAll(title,subtitle,publisher,date,isbn,language,edition,chooseCoverButton);
        leftBookFields.setSpacing(10);

        // Right Book Fields
        authors = new BookField("Authors", "", isEditable, false);
        translators = new BookField("Translators", "", isEditable, false);
        tags = new BookField("Tags", "", isEditable, false);

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
        buttonsContainer.setAlignment(Pos.CENTER);

        mainLayout.setSpacing(15);
        mainLayout.setPadding(new Insets(10,10,20,10));
        mainLayout.getChildren().addAll(leftBookFields,rightBookFields);
        
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
        Path coverImagePathInput = fileChooser.showOpenDialog(new Stage()).toPath();
        this.coverImagePath = coverImagePathInput;
        System.out.println(coverImagePathInput);
    }

    private void save(){
        
        String titleInput = title.getTextField().getText();
        String subTitleInput = subtitle.getTextField().getText();
        String publisherInput = publisher.getTextField().getText();
        String dateInput = date.getTextField().getText();
        String isbnInput = isbn.getTextField().getText();
        String languageInput = language.getTextField().getText();
        int editionInput = Integer.parseInt(edition.getTextField().getText());

        String[] authorsInput =  authors.getTextArea().getText().trim().split(",");
        List<String> authorsList = Arrays.asList(authorsInput);
        String[] translatorsInput = translators.getTextArea().getText().trim().split(",");
        List<String> translatorsList = Arrays.asList(translatorsInput);
        String[] tagsInput = tags.getTextArea().getText().trim().split(",");
        List<String> tagsList = Arrays.asList(tagsInput);

        Book newBook = new Book(titleInput, subTitleInput, authorsList, translatorsList, isbnInput, publisherInput, dateInput, editionInput, languageInput, 5.0, tagsList, coverImagePath);
        Library.books.add(newBook);

        cancel(); 
    }
    
}
