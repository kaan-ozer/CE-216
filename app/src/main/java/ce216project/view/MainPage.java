package ce216project.view;

import java.util.ArrayList;
import java.util.HashMap;

import ce216project.models.Book;
import ce216project.view.widgets.BookWidget;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;



public class MainPage extends VBox{

    // Main Containers
    private HBox mainLayout = new HBox();
    private VBox leftContainer = new VBox();
    private VBox rightContainer = new VBox();

    // Menu
    private AppMenuBar menuBar = new AppMenuBar();

    // Left Container Widgets
    private VBox checkBoxListContainer = new VBox();
    private TitledPane tagsList = new TitledPane("Tags", new Label());
    private TitledPane languagesList = new TitledPane("Language",new Label());
    

    // Right Container Widgets
    // Search Bar
    private HBox searchBarContainer = new HBox();
    private Label searchLabel = new Label("Search");
    private TextField searchBar = new TextField();
    private Button searchButton = new Button("Search");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  


    // Books Grid
    private VBox booksContainer = new VBox();


    public MainPage() {

        //Checkbox Lists at Left Container
        tagsList.setMinWidth(100);
        checkBoxListContainer.getChildren().addAll(tagsList,languagesList);
        checkBoxListContainer.setPadding(new Insets(5, 0, 0, 5));

        // Search Bar at Right Container
        searchLabel.setPadding(new Insets(5,0,0,0));
        searchBarContainer.getChildren().addAll(searchLabel,searchBar,searchButton);
        searchBarContainer.setSpacing(10);
        searchBarContainer.setPadding(new Insets(5, 10, 0, 10));

        // Books Grid View
        booksContainer.setSpacing(15);
        booksContainer.setPadding(new Insets(10, 10, 10, 0));

        
        // Main Containers
        rightContainer.getChildren().addAll(searchBarContainer,booksContainer);
        leftContainer.getChildren().addAll(checkBoxListContainer);

        mainLayout.getChildren().addAll(leftContainer,rightContainer);


        // Resposive design
        VBox.setVgrow(mainLayout, Priority.ALWAYS);
        HBox.setHgrow(rightContainer, Priority.ALWAYS);
        HBox.setHgrow(searchBar, Priority.ALWAYS);
        

        this.getChildren().addAll(menuBar,mainLayout);
        
    }

    public void fillCheckLists (HashMap<String,Integer> hashmap) {
        for(String key : hashmap.keySet()){
            HBox checkBoxItemBox = new HBox();
            CheckBox checkBox = new CheckBox(key);
            Label countLabel = new Label(hashmap.get(key).toString().trim());
            
            checkBoxItemBox.getChildren().addAll(countLabel,checkBox);
            checkBoxItemBox.setSpacing(5);
        }
    }

    public void fillBooks (ArrayList<Book> books) {

        for(Book book : books){
            BookWidget bookWidget = new BookWidget(book.getCoverImagePath(), book.getTitle());
            booksContainer.getChildren().add(bookWidget);
        }

    }

    




    

    
}
