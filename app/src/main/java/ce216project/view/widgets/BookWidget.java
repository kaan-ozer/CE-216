package ce216project.view.widgets;

import java.nio.file.Path;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BookWidget extends StackPane {


    private ImageView imageView;
    private Path coverImagePath;
    private Image coverImage;

    private Rectangle rectangle = new Rectangle(75,150);
    private String bookTitle;


    public BookWidget (Path coverImagePath, String bookTitle) {


        if(!coverImagePath.equals(null)){
            this.coverImagePath = coverImagePath;
            coverImage = new Image(coverImagePath.toString());
            imageView.setImage(coverImage);
            imageView.setSmooth(true);
            imageView.setCache(true);
            imageView.setX(75);
            imageView.setY(150);
            this.getChildren().add(imageView);
        }
        else{
            if(!bookTitle.equals(null) || bookTitle.isEmpty()){
                this.bookTitle = bookTitle;
                Label titleLabel = new Label(bookTitle);
                rectangle.setFill(Color.ALICEBLUE);
                this.setAlignment(Pos.CENTER);
                this.getChildren().addAll(rectangle,titleLabel);

            }
            else{
                rectangle.setFill(Color.ALICEBLUE);
                this.getChildren().addAll(rectangle);
            }
        }
    }

 
}
