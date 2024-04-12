package ce216project.view.widgets;


import ce216project.controller.PageController;
import ce216project.models.Book;
import ce216project.view.DetailsPage;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class BookListWidget extends StackPane {

    private Book book;

    private HBox mainContainer = new HBox();
    private Label lTile = new Label();
    private Label lsubtitle = new Label();
    private Label lpublisher = new Label();
    private Label lFirstAuthor = new Label();
    private Label lDate = new Label();

    private Button detailsButton = new Button();

    public BookListWidget(Book book) {

        this.book = book;

        lTile = new Label(book.getTitle());
        lsubtitle = new Label(book.getSubtitle());
        lpublisher = new Label(book.getPublisher());
        lFirstAuthor = new Label(book.getAuthors().get(0));
        lDate = new Label(book.getDate());

        mainContainer.getChildren().addAll(lTile,lsubtitle,lpublisher,lFirstAuthor,lDate);
        mainContainer.setSpacing(5);
        mainContainer.setPadding(new Insets(5));
        mainContainer.setBackground(new Background(new BackgroundFill(Color.ANTIQUEWHITE,CornerRadii.EMPTY,Insets.EMPTY)));
        

        detailsButton.setOpacity(0);
        detailsButton.setOnAction(e -> openDetailsPage());
        detailsButton.setPrefSize(300, 10);

        this.getChildren().addAll(mainContainer,detailsButton);
        HBox.setHgrow(this, Priority.ALWAYS);

    }

    private void openDetailsPage(){
        int i = PageController.pagesArray.size();
        PageController.openNewWindow(new DetailsPage(book,false,i));

    }
    
}
