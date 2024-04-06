package ce216project.view;

import ce216project.view.widgets.BookField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NewBookPage extends VBox{

    private final boolean isEditable = true;

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

        leftBookFields.getChildren().addAll(title,subtitle,publisher,date,isbn,language,edition);
        leftBookFields.setSpacing(10);

        // Right Book Fields
        authors = new BookField("Authors", "", isEditable, false);
        translators = new BookField("Translators", "", isEditable, false);
        tags = new BookField("Tags", "", isEditable, false);

        rightBookFields.getChildren().addAll(authors,translators,tags);
        rightBookFields.setSpacing(10);

        // Buttons
        saveButton.setPrefWidth(100);
        cancelButton.setPrefWidth(100);
        buttonsContainer.getChildren().addAll(saveButton,cancelButton);
        buttonsContainer.setSpacing(10);
        buttonsContainer.setAlignment(Pos.CENTER);

        mainLayout.setSpacing(15);
        mainLayout.setPadding(new Insets(10,10,20,10));
        mainLayout.getChildren().addAll(leftBookFields,rightBookFields);
        
        this.getChildren().addAll(menuBar,mainLayout,buttonsContainer);


    }   

    private void cancel() {
        
    }
    
}
