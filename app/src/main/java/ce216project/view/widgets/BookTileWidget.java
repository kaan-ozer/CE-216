package ce216project.view.widgets;

import ce216project.controller.PageController;
import ce216project.models.Book;
import ce216project.view.DetailsPage;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.input.MouseEvent;


public class BookTileWidget extends VBox {

    private static final int XSIZE = 100;
    private static final int YSIZE = 150;

    private StackPane coverTile = new StackPane();

    private ImageView imageView = new ImageView();
    private Image coverImage;
    private Button detailsButton = new Button();

    private Rectangle rectangle = new Rectangle(XSIZE,YSIZE);
    private VBox coverTitle = new VBox();
    private Book book;

    private boolean isClickable;


    public BookTileWidget (Book book, boolean isClickable) {
        
        this.book = book;
        if(book.getCoverImagePath() != null){
 
            coverImage = new Image("file:"+book.retrieveCoverImgFullPath(),XSIZE,YSIZE,false,true);
            imageView.setImage(coverImage);
            imageView.setSmooth(true);
            imageView.setCache(true);
            imageView.setX(XSIZE);
            imageView.setY(YSIZE);
            coverTile.getChildren().add(imageView);
            if(isClickable == true){
                this.makeClickable();
            }
        }
        else{
            rectangle.setFill(Color.RED);
            if(!book.getTitle().isBlank() || !book.getTitle().isEmpty()){
                coverTitle = formatBookCoverTitle(book.getTitle());
                coverTile.setAlignment(Pos.CENTER);
                coverTile.getChildren().addAll(rectangle,coverTitle);
                if(isClickable == true){
                    this.makeClickable();
                }
            }
            else{
                coverTile.getChildren().addAll(rectangle);
                if(isClickable == true){
                    this.makeClickable();
                }
            }
        }

        this.getChildren().addAll(coverTile,new Label(book.getTitle()));
        this.setAlignment(Pos.CENTER);
        this.setSpacing(5);

        
    }

    private void makeClickable () {

        Button detailsButton = new Button("button");
        detailsButton.setOpacity(0);
        detailsButton.setPrefSize(XSIZE, YSIZE);
        detailsButton.setOnAction(e -> openDetailsPage());
        coverTile.getChildren().add(detailsButton);

        this.setOnMouseEntered(e -> applyHoverEffect(e));
        this.setOnMouseExited(e -> removeHoverEffect(e));
    }

    private void openDetailsPage(){
        int i = PageController.pagesArray.size();
        PageController.openNewWindow(new DetailsPage(book,false,i));

    }

    private void applyHoverEffect(MouseEvent event) {
        this.setScaleX(1.1); // Increase size slightly
        this.setScaleY(1.1);
        for (Node wordNode : coverTitle.getChildren()) {
            if (wordNode instanceof Label) {
                Label wordLabel = (Label) wordNode;
                wordLabel.setStyle("-fx-font-weight: bold;");
            }
        }

        this.setCursor(Cursor.HAND); // Change cursor to clickable
    }

    private void removeHoverEffect(MouseEvent event) {
        this.setScaleX(1); // Restore original size
        this.setScaleY(1);
        for (Node wordNode : coverTitle.getChildren()) {
            if (wordNode instanceof Label) {
                Label wordLabel = (Label) wordNode;
                wordLabel.setStyle(null); // Remove the style
            }
        }
        this.setCursor(Cursor.DEFAULT); // Change cursor back to default
    }

    private VBox formatBookCoverTitle(String title) {
        VBox bookCoverTitle = new VBox();
        bookCoverTitle.setAlignment(Pos.CENTER);
        String[] titleWords = title.split(" ");
        
        for(String word : titleWords) {
            Label lword = new Label(word.trim());
            bookCoverTitle.getChildren().add(lword);
        }

        return bookCoverTitle;
    }

    public boolean isClickable() {
        return isClickable;
    }

    public void setClickable(boolean isClickable) {
        this.isClickable = isClickable;
    }

    public Button getDetailsButton() {
        return detailsButton;
    }

    public void setDetailsButton(Button detailsButton) {
        this.detailsButton = detailsButton;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Image getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(Image coverImage) {
        this.coverImage = coverImage;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    

}
