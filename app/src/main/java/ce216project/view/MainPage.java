package ce216project.view;

import java.util.ArrayList;
import java.util.HashMap;


import ce216project.models.Book;
import ce216project.view.widgets.BookWidget;
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
import java.io.FileNotFoundException;


public class MainPage extends VBox{

    // Main Containers
    private HBox mainLayout = new HBox();
    private VBox leftContainer = new VBox();
    private VBox rightContainer = new VBox();

    // Menu
    private AppMenuBar menuBar = new AppMenuBar();

    // Left Container Widgets
    private VBox checkBoxListContainer = new VBox();
    private TitledPane tagsList = new TitledPane("Tags", new VBox());
    private TitledPane languagesList = new TitledPane("Language",new VBox());
    private Button addBookButton = new Button("Add");
    private HBox addBookButtonBox = new HBox(addBookButton);
    
    // Right Container Widgets
    // Search Bar
    private HBox searchBarContainer = new HBox();
    private Label searchLabel = new Label("Search");
    private TextField searchBar = new TextField();
    private Button searchButton = new Button("Search");

    // Books Views
    private ScrollPane booksScroll = new ScrollPane();
    private TilePane booksContainer = new TilePane();
    


    public MainPage() throws FileNotFoundException {

        //Checkbox Lists at Left Container
        tagsList.setMinWidth(100);
        checkBoxListContainer.getChildren().addAll(tagsList,languagesList);
        checkBoxListContainer.setPadding(new Insets(5, 0, 0, 5));

        // Book add button
        addBookButtonBox.setAlignment(Pos.CENTER);
        addBookButton.setPrefWidth(100);
        addBookButtonBox.setPadding(new Insets(10));
        addBookButton.setOnAction(e -> add());
        

        // Search Bar at Right Container
        searchLabel.setPadding(new Insets(5,0,0,0));
        searchBarContainer.getChildren().addAll(searchLabel,searchBar,searchButton);
        searchBarContainer.setSpacing(10);
        searchBarContainer.setPadding(new Insets(5, 10, 5, 10));

        // Books Views
        booksScroll.setContent(booksContainer);
        booksContainer.setHgap(15);
        booksContainer.setVgap(10);
        booksContainer.setPadding(new Insets(10, 10, 10, 10));

        // Main Containers
        rightContainer.getChildren().addAll(searchBarContainer,booksScroll);
        leftContainer.getChildren().addAll(checkBoxListContainer,addBookButtonBox);
        mainLayout.getChildren().addAll(leftContainer,rightContainer);

        // Resposive design
        VBox.setVgrow(mainLayout, Priority.ALWAYS);
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
        
        this.getChildren().addAll(menuBar,mainLayout);
        
    }

    public void fillCheckLists (HashMap<String,Integer> hashmap,TitledPane checkBoxList) {

        VBox checkBoxVBox = new VBox();
        ScrollPane checkBoxScrollPane = new ScrollPane(checkBoxVBox);
        checkBoxScrollPane.setMaxHeight(250);
        
        for(String key : hashmap.keySet()){
            HBox checkBoxItemBox = new HBox();
            CheckBox checkBox = new CheckBox(key);
            Label countLabel = new Label(hashmap.get(key).toString().trim());
            
            checkBoxItemBox.getChildren().addAll(checkBox,countLabel);
            checkBoxItemBox.setSpacing(5);
            checkBoxItemBox.setPadding(new Insets(0, 0, 5, 5));

            checkBoxVBox.getChildren().add(checkBoxItemBox);
            checkBoxVBox.setPadding(new Insets(5,0,0,0));

        }

        checkBoxList.setContent(checkBoxScrollPane);
    }

    public void fillBooks (ArrayList<Book> books) {

        for(Book book : books){
            BookWidget bookWidget = new BookWidget(book,true);
            booksContainer.getChildren().add(bookWidget);
        }

    }

    public TitledPane getTagsList(){
        return tagsList;
    }

    // Calculates column number for tile pane responsiveness
    private int calculateColumns(double width) {
        
        int columnWidth = 200; 
        int minColumns = 1; 
        int maxColumns = (int) width / columnWidth; // Maximum number of columns based on width
        return Math.max(minColumns, maxColumns);
    }

    private void add(){}

    

    




    

    
}
