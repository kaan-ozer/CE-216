package ce216project.view.widgets;

import java.nio.file.Path;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BookWidget extends StackPane {

    private static final int XSIZE = 75;
    private static final int YSIZE = 150;

    private ImageView imageView = new ImageView();
    private Path coverImagePath; 
    private Image coverImage;

    private Rectangle rectangle = new Rectangle(75,150);
    private String bookTitle;

    private boolean isClickable;


    public BookWidget (Path coverImagePath, String bookTitle) {


        if(!coverImagePath.equals(null)){
            this.coverImagePath = coverImagePath;
            coverImage = new Image(coverImagePath.toString());
            imageView.setImage(coverImage);
            imageView.setSmooth(true);
            imageView.setCache(true);
            imageView.setX(XSIZE);
            imageView.setY(YSIZE);

            this.getChildren().add(imageView);
            this.makeClickable();

        }
        else{
            if(!bookTitle.equals(null) || bookTitle.isEmpty()){
                this.bookTitle = bookTitle;
                Label titleLabel = new Label(bookTitle);
                rectangle.setFill(Color.ALICEBLUE);
                this.setAlignment(Pos.CENTER);
                this.getChildren().addAll(rectangle,titleLabel);
                this.makeClickable();

            }
            else{
                rectangle.setFill(Color.ALICEBLUE);
                this.getChildren().addAll(rectangle);
                this.makeClickable();
            }
        }
    }

    public void makeClickable () {

        Button detailsButton = new Button();
        detailsButton.setVisible(false);
        detailsButton.setScaleX(XSIZE);
        detailsButton.setScaleY(YSIZE);

        this.getChildren().add(detailsButton);
    }
 
}
