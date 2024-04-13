package ce216project.view.widgets;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField; 


public class ItemField extends HBox{


 
    private String label;
    private String content;
    private ListView<ItemFieldBody>  itemField;
    private HBox inputField;
    private boolean isEditable; 
    private Button addButton;

    private Label fieldLabel;
    private TextField textField; 
    

    public ItemField( String Label, String content,ListView<ItemFieldBody> itemField,boolean isEditable,Button addButton){
       
      
        fieldLabel = new Label(Label);
        fieldLabel.setPrefWidth(60);
        fieldLabel.setPadding(new Insets(5, 0,10,0));

        this.addButton = addButton;

        this.itemField = itemField;
        VBox.setMargin(itemField, new Insets(0, 0, 10,0));

        this.textField = new TextField(content); 
        HBox.setMargin(textField, new Insets(0, 10, 0,0));
        textField.setEditable(isEditable);
        
        inputField = new HBox();
        inputField.getChildren().addAll(textField,addButton);
        inputField.setVisible(isEditable);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(itemField,inputField);
        
        this.getChildren().addAll(fieldLabel,vBox); 
    }

    public TextField getTextField() {
        return textField;
    }


    public void setTextField(TextField textField) {
        this.textField = textField;
    } 

    public Button getButton() {
        return addButton;
    }

    public HBox getInputField() {
        return inputField;
    }


    
}
