package ce216project.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
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
    private TitledPane tagsList = new TitledPane();
    private TitledPane languagesList = new TitledPane();
    

    // Right Container Widgets
    // Search Bar
    private HBox searchBarContainer = new HBox();
    private Label searchLabel = new Label("Search");
    private TextField searchBar = new TextField();
    private Button searchButton = new Button("Search");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             



    private VBox booksContainer = new VBox();


    public MainPage(){

        checkBoxListContainer.getChildren().addAll(tagsList,languagesList);

        // Search Bar
        searchLabel.setPadding(new Insets(5,5,0,5));
        
        searchBarContainer.getChildren().addAll(searchLabel,searchBar,searchButton);

        
        rightContainer.getChildren().addAll(searchBarContainer,booksContainer);
        leftContainer.getChildren().addAll(checkBoxListContainer);

        mainLayout.getChildren().addAll(leftContainer,rightContainer);

        this.getChildren().addAll(menuBar,mainLayout);

        
    }

    




    

    
}
