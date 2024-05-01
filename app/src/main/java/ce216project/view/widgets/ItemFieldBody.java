package ce216project.view.widgets;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class ItemFieldBody extends HBox {

    private String label;
    private String content;
    private boolean isEditable;
    private Button deleteButton;

    private Label fieldLabel;
    private TextField textField;

    public ItemFieldBody(String Label, String content, boolean isEditable, Button deleteButton) {

        fieldLabel = new Label(Label);
        fieldLabel.setPrefWidth(30);
        fieldLabel.setPadding(new Insets(5, 0, 0, 0));

        this.deleteButton = deleteButton;

        textField = new TextField(content);

        HBox.setMargin(textField, new Insets(0, 10, 0, 0));
        textField.setEditable(isEditable);

        this.getChildren().addAll(fieldLabel, textField, this.deleteButton);

    }

    public TextField getTextField() {
        return textField;
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
    }

    public Button getButton() {
        return deleteButton;
    }

}
