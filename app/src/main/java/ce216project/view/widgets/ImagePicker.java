package ce216project.view.widgets;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ImagePicker extends VBox{
    
    private final String projectPath = System.getProperty("user.dir");
    private final String imagesPath = projectPath + File.separator + "shared" + File.separator + "images";


    private ImageView imagePreview = new ImageView();
    private Image image;
    private Rectangle imageRectangle = new Rectangle(100, 150);
    private Button imagePickButton = new Button("Cover Image");
    private String imagePath;

    private boolean isEditable;

    public ImagePicker(boolean isEditable, String imagePath) {
        
        this.imagePath = imagePath;

        if(imagePath == null){
            // Initial state with no image
           this.getChildren().addAll(imageRectangle,imagePickButton);
        } else {
            // Initial state with image for edit view
            image = new Image("file:" + imagePath,100,150,false,true);
            imagePreview.setImage(image);
            this.getChildren().addAll(imagePreview,imagePickButton);
        }

        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.TOP_CENTER);
        
        imagePreview.setX(100);
        imagePreview.setY(150);

        imagePickButton.setPrefWidth(100);
        imagePickButton.setOnAction(e -> getCoverImage());

    }


    private void getCoverImage() {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files", "*.png","*.jpeg","*.jpg");
        fileChooser.setTitle("Select Cover Image");
        fileChooser.getExtensionFilters().add(extFilter);
        File ImagePathFile = fileChooser.showOpenDialog(new Stage());
        if(ImagePathFile == null) return;
        
       
        this.imagePath = ImagePathFile.toPath().toString(); 
     
        if (this.imagePath.startsWith(imagesPath)) {
            image = new Image("file:" + imagePath,100,150,false,true);
            this.imagePreview.setImage(image);
            this.getChildren().remove(0);
            this.getChildren().add(0,imagePreview);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Selected photo must be inside of the project under images folder!");
            alert.showAndWait();
        }
      
    }

    public String getImagePath() {
        return imagePath;
    }


    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    public boolean isEditable() {
        return isEditable;
    }


    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    
}
