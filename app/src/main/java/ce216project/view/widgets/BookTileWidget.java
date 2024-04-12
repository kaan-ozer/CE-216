package ce216project.view.widgets;

import ce216project.controller.PageController;
import ce216project.models.Book;
import ce216project.view.DetailsPage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BookTileWidget extends StackPane {

    private static final int XSIZE = 100;
    private static final int YSIZE = 150;

    private ImageView imageView = new ImageView();
    private Image coverImage;
    private Button detailsButton = new Button();

    private Rectangle rectangle = new Rectangle(XSIZE,YSIZE);
    private Label titleLabel = new Label();
    private Book book;

    private boolean isClickable;


    public BookTileWidget (Book book, boolean isClickable) {
        
        this.book = book;
        if(book.getCoverImagePath() != null){
            coverImage = new Image("file:"+book.getCoverImagePath().toString(),XSIZE,YSIZE,false,true);
            imageView.setImage(coverImage);
            imageView.setSmooth(true);
            imageView.setCache(true);
            imageView.setX(XSIZE);
            imageView.setY(YSIZE);
            this.getChildren().add(imageView);
            if(isClickable == true){
                this.makeClickable();
            }
        }
        else{
            rectangle.setFill(Color.RED);
            if(!book.getTitle().isBlank() || !book.getTitle().isEmpty()){
                titleLabel = new Label(book.getTitle());
                this.setAlignment(Pos.CENTER);
                this.getChildren().addAll(rectangle,titleLabel);
                if(isClickable == true){
                    this.makeClickable();
                }
            }
            else{
                this.getChildren().addAll(rectangle);
                if(isClickable == true){
                    this.makeClickable();
                }
            }
        }
    }

    private void makeClickable () {

        Button detailsButton = new Button("button");
        detailsButton.setOpacity(0);
        detailsButton.setPrefSize(XSIZE, YSIZE);
        detailsButton.setOnAction(e -> openDetailsPage());
        this.getChildren().add(detailsButton);
    }

    private void openDetailsPage(){
        int i = PageController.pagesArray.size();
        PageController.openNewWindow(new DetailsPage(book,false,i));

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

    public Label getTitleLabel() {
        return titleLabel;
    }

    public void setTitleLabel(Label titleLabel) {
        this.titleLabel = titleLabel;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    

}
