package ce216project.view.widgets;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class BookField extends HBox{

    private String label;
    private String content;
    private boolean isEditable;
    private boolean isTextField;

    private Label fieldLabel;
    private TextField textField;
    private TextArea textArea;
    

    public BookField(String Label, String content, boolean isEditable, boolean isTextField){

        fieldLabel = new Label(Label);
        fieldLabel.setPrefWidth(60);
        fieldLabel.setPadding(new Insets(5, 0,0,0));
        this.getChildren().add(fieldLabel);
        
        
        if(isTextField == true){
            TextField textField = new TextField(content);
            textField.setEditable(isEditable);
            this.getChildren().add(textField);
            this.textField = textField;
            
        } else { 
            TextArea textArea = new TextArea(content);
            textArea.setEditable(isEditable);
            textArea.setMaxWidth(200);
            textArea.setMaxHeight(100);
            this.setSpacing(10);
            this.getChildren().add(textArea);
            this.textArea = textArea;
        }
        
        
         
    }








    public TextField getTextField() {
        return textField;
    }


    public void setTextField(TextField textField) {
        this.textField = textField;
    }


    public TextArea getTextArea() {
        return textArea;
    }


    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }



    
}
