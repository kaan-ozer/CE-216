package ce216project.controller;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PageController {

    private static final int INITIALPAGEXSIZE = 800;
    private static final int INITIALPAGEYSIZE = 450;

    public static void openNewWindow (Parent rootNode){
            
        Scene scene = new Scene(rootNode,INITIALPAGEXSIZE,INITIALPAGEYSIZE);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Librarian");
        stage.show();

    }
    
    }
