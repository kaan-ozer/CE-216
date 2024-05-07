package ce216project.view;

import java.util.ArrayList;
import java.util.HashMap;

import ce216project.controller.PageController;
import ce216project.models.Book;
import ce216project.models.Library;
import ce216project.view.widgets.BookListWidget;
import ce216project.view.widgets.BookTileWidget;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class MainPage extends VBox {

    // Main Containers
    private HBox mainLayout = new HBox();
    private VBox leftContainer = new VBox();
    private VBox rightContainer = new VBox();

    // Menu
    private AppMenuBar menuBar = new AppMenuBar();

    // Left Container Widgets
    private VBox checkBoxListContainer = new VBox();
    private TitledPane tagsList = new TitledPane("Tags", new VBox());
    private TitledPane languagesList = new TitledPane("Language", new VBox());
    private Button addBookButton = new Button("Add");
    private HBox addBookButtonBox = new HBox(addBookButton);

    // Right Container Widgets
    // Search Bar
    private HBox searchBarContainer = new HBox();
    private Label searchLabel = new Label("Search");
    private TextField searchBar = new TextField();

    // Books Views
    private ScrollPane booksScroll = new ScrollPane();
    private TilePane booksContainer = new TilePane();

    //Filtering
    private ArrayList<String> selectedTags = new ArrayList<>();
    private ArrayList<String> selectedLanguages = new ArrayList<>();
    private ArrayList<CheckBox> tagCheckboxes = new ArrayList<>();
    private ArrayList<CheckBox> languageCheckboxes = new ArrayList<>();


    public MainPage() {

        //Checkbox Lists at Left Container
        tagsList.setMinWidth(100);
        checkBoxListContainer.getChildren().addAll(tagsList, languagesList);
        checkBoxListContainer.setPadding(new Insets(0, 5, 0, 5));

        // Book add button
        addBookButton.setPrefWidth(100);
        addBookButton.setOnAction(e -> add());
        addBookButtonBox.setPadding(new Insets(10));
        addBookButtonBox.setAlignment(Pos.CENTER);

        // Search Bar at Right Container
        searchLabel.setPadding(new Insets(5, 0, 0, 0));
        searchBarContainer.getChildren().addAll(searchLabel, searchBar);
        searchBarContainer.setSpacing(10);
        searchBarContainer.setPadding(new Insets(5, 10, 5, 10));

        // Books Views
        booksScroll.setContent(booksContainer);
        booksContainer.setHgap(15);
        booksContainer.setVgap(10);
        booksContainer.setPadding(new Insets(10, 10, 10, 10));

        // Main Containers
        rightContainer.getChildren().addAll(booksScroll);
        leftContainer.getChildren().addAll(checkBoxListContainer, addBookButtonBox);
        mainLayout.getChildren().addAll(leftContainer, rightContainer);

        // Resposive design
        VBox.setVgrow(mainLayout, Priority.ALWAYS);
        VBox.setVgrow(booksScroll, Priority.ALWAYS);
        HBox.setHgrow(rightContainer, Priority.ALWAYS);
        HBox.setHgrow(searchBar, Priority.ALWAYS);


        // Books TileView Responsive Design
        booksScroll.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.getWidth();
            int columns = calculateColumns(width);
            booksContainer.setPrefColumns(columns);
        });

        booksContainer.prefWidthProperty().bind(booksScroll.widthProperty()); // Bind width to ScrollPane width
        booksContainer.maxWidthProperty().bind(booksScroll.widthProperty());

        this.getChildren().addAll(menuBar, searchBarContainer, mainLayout);

        // Update Main Page
        this.fillBookTiles(Library.books);
        this.fillCheckLists(Library.tags, tagsList, "tag");
        this.fillCheckLists(Library.languages, languagesList, "language");

        // Listeners for tag checkboxes
        for (CheckBox tagCheckBox : tagCheckboxes) {
            tagCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                    if (newValue) {
                        selectedTags.add(tagCheckBox.getText());
                    } else {
                        selectedTags.remove(tagCheckBox.getText());
                    }
                    updateFilteredBooks();
                }
            });
        }

        // Listeners for language checkboxes
        for (CheckBox langCheckBox : languageCheckboxes) {
            langCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (newValue) {
                        selectedLanguages.add(langCheckBox.getText());
                    } else {
                        selectedLanguages.remove(langCheckBox.getText());
                    }
                    updateFilteredBooks();
                }
            });

        }

        updateFilteredBooks();

        // Listener for search bar
        searchBar.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Call the performSearch method with the new query
                performSearch(newValue);
            }
        });

    }

    private void showEmptyView() {

        Label emptyMessage = new Label("There is no book!");
        emptyMessage.setFont(new Font(30));
        HBox emptyMessageBox = new HBox(emptyMessage);
        emptyMessageBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(emptyMessageBox, Priority.ALWAYS);

        if (this.booksContainer.getChildren().isEmpty()) {
            booksContainer.getChildren().add(emptyMessageBox);
            booksContainer.setAlignment(Pos.TOP_CENTER);
        }


    }

    public void fillCheckLists(HashMap<String, Integer> hashmap, TitledPane checkBoxList, String type) {

        VBox checkBoxVBox = new VBox();
        ScrollPane checkBoxScrollPane = new ScrollPane(checkBoxVBox);
        checkBoxScrollPane.setMaxHeight(800);
        checkBoxScrollPane.setMaxWidth(120);

        if (hashmap.isEmpty()) {
            checkBoxList.setExpanded(false); // Close the titled pane if the hashmap is empty
        }

        for (String key : hashmap.keySet()) {
            HBox checkBoxItemBox = new HBox();
            CheckBox checkBox = new CheckBox(key);
            Label countLabel = new Label(hashmap.get(key).toString().trim());

            if (type.equals("tag")) {
                tagCheckboxes.add(checkBox);
            } else if (type.equals("language")) {
                languageCheckboxes.add(checkBox);
            }

            checkBoxItemBox.getChildren().addAll(checkBox, countLabel);
            checkBoxItemBox.setSpacing(5);
            checkBoxItemBox.setPadding(new Insets(0, 0, 5, 5));

            checkBoxVBox.getChildren().add(checkBoxItemBox);
            checkBoxVBox.setPadding(new Insets(5, 0, 0, 0));

        }

        checkBoxList.setContent(checkBoxScrollPane);
    }

    public void fillBookTiles(ArrayList<Book> books) {

        booksContainer.getChildren().clear();
        if (books.isEmpty()) {
            showEmptyView();
        } else {
            for (Book book : books) {
                BookTileWidget bookTileWidget = new BookTileWidget(book, true);
                booksContainer.getChildren().add(bookTileWidget);
                booksContainer.setAlignment(Pos.TOP_LEFT);
            }
        }

    }

    public void fillBookList(ArrayList<Book> books) {

        VBox booksList = new VBox();
        booksList.setSpacing(5);
        booksList.setPadding(new Insets(10));

        for (Book book : books) {
            BookListWidget bookListWidget = new BookListWidget(book);
            booksList.getChildren().add(bookListWidget);
        }

        this.booksScroll.setContent(booksList);
    }

    public TitledPane getTagsList() {
        return tagsList;
    }

    // Calculates column number for tile pane responsiveness
    private int calculateColumns(double width) {

        int columnWidth = 600;
        int minColumns = 1;
        int maxColumns = (int) width / columnWidth; // Maximum number of columns based on width
        return Math.max(minColumns, maxColumns);
    }

    private void add() {
        NewBookPage newBookPage = new NewBookPage();
        PageController.changeScene(newBookPage, PageController.pagesArray.get(0));
    }

    private void updateFilteredBooks() {

        // Check if no filters are selected
        if (selectedTags.isEmpty() && selectedLanguages.isEmpty()) {
            fillBookTiles(Library.books); // Fill with all books
        } else {
            // Otherwise, filter the books based on selected tags and languages
            ArrayList<Book> filteredBooks = Library.filterBooks(selectedTags, selectedLanguages);
            fillBookTiles(filteredBooks);
        }
    }

    private void performSearch(String query) {
        if (query.isEmpty()) {
            // If the search query is empty, fill with all books
            fillBookTiles(Library.books);
        } else {
            // Otherwise, perform the search based on the query
            ArrayList<Book> searchResults = (ArrayList<Book>) Library.searchBooks(query);
            fillBookTiles(searchResults);
        }
    }

}

