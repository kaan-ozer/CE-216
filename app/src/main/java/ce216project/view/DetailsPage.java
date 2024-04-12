package ce216project.view;

import ce216project.controller.PageController;
import ce216project.models.Book;
import ce216project.view.widgets.BookField;
import ce216project.view.widgets.BookTileWidget;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
        System.out.println(pageIndex);
        backButton.setOnAction(e -> PageController.closeWindow(PageController.pagesArray.get(pageIndex),pageIndex));
        editButton.setOnAction(e -> edit());
        buttonsContainer.getChildren().addAll(editButton,deleteButton,backButton);
        editButton.setPrefWidth(100);
        deleteButton.setPrefWidth(100);
        backButton.setPrefWidth(100);
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

    public void setPageIndex (int pageIndex) {
        this.pageIndex = pageIndex;
    }

    private void edit() {
        isEditable = true;
        DetailsPage root = new DetailsPage(book, true, pageIndex);
        PageController.changeScene(root, PageController.pagesArray.get(pageIndex));

        Button saveButton = new Button("Save");
        saveButton.setPrefWidth(100);
        Button cancelButton = new Button("Cancel");
        cancelButton.setPrefWidth(100);
        cancelButton.setOnAction(e -> editCancel());
        
        root.buttonsContainer.getChildren().clear();
        root.buttonsContainer.getChildren().addAll(saveButton,cancelButton);

    }

    private void editCancel() {
        isEditable = false;
        DetailsPage root = new DetailsPage(book, false, pageIndex);
        PageController.changeScene(root, PageController.pagesArray.get(pageIndex));
    }
           
        
        

    

    

    
}
