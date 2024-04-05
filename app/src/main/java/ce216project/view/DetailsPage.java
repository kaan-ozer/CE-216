package ce216project.view;

import ce216project.models.Book;
import ce216project.view.widgets.BookWidget;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class DetailsPage extends VBox {

    private Book book;
    private boolean editMode = false;

    // Main Containers
    private AppMenuBar appMenuBar = new AppMenuBar();
    private HBox mainLayout = new HBox();
    private HBox rightContainer = new HBox();
    private VBox leftContainer = new VBox();


    // Left Container Widgets
    // Edit and Delete Buttons
    private VBox buttonsContainer = new VBox();
    private Button editButton = new Button("Edit");
    private Button deleteButton = new Button("Delete");

    // Cover Image
    private BookWidget bookWidget;
    
    // Right Container Widgets
    private HBox leftBookFields = new HBox();
    private VBox leftBookFieldLabels = new VBox();
    private VBox leftBookFieldTexts = new VBox();
    private Label lTitle = new Label("Title");
    private TextField fTitle = new TextField();
    private Label lSubTitle = new Label("Subtitle");
    private TextField fSubtitle = new TextField();
    private Label lPublisher = new Label("Publisher");
    private TextField fPublisher = new TextField();
    private Label lDate = new Label("Date");
    private TextField fDate = new TextField();
    private Label lISBN = new Label("ISBN");
    private TextField fISBN = new TextField();
    private Label lLanguage = new Label("Language");
    private TextField fLanguage = new TextField();
    private Label lEdition = new Label("Edition");
    private TextField fEdition = new TextField();

    
    public DetailsPage(Book book,boolean editMode) {
        this.book = book;

        bookWidget = new BookWidget(book,false);

        // Right Container Widgets

        leftBookFieldLabels.getChildren().addAll(lTitle,lSubTitle,lPublisher,lDate,lISBN,lLanguage,lEdition);
        leftBookFieldLabels.setSpacing(13);
        leftBookFieldLabels.setAlignment(Pos.CENTER);
        leftBookFieldTexts.getChildren().addAll(fTitle,fSubtitle,fPublisher,fDate,fISBN,fLanguage,fEdition);
        leftBookFieldTexts.setSpacing(5);
        leftBookFieldTexts.setAlignment(Pos.CENTER);
        leftBookFields.getChildren().addAll(leftBookFieldLabels,leftBookFieldTexts);
        leftBookFields.setSpacing(10);
        rightContainer.getChildren().addAll(leftBookFields);
        
        // Left Container Widgets
        buttonsContainer.getChildren().addAll(editButton,deleteButton);
        buttonsContainer.setAlignment(Pos.CENTER);
        buttonsContainer.setSpacing(10);

        leftContainer.getChildren().addAll(bookWidget,buttonsContainer);
        leftContainer.setSpacing(10);
        leftContainer.setPadding(new Insets(20));

        // Main Layout
        mainLayout.getChildren().setAll(leftContainer,rightContainer);
        mainLayout.setSpacing(20);

        this.getChildren().addAll(appMenuBar,mainLayout);
    }

    
}
