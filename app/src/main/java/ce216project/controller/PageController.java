package ce216project.controller;

import java.util.ArrayList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PageController {

    private static final int INITIALPAGEXSIZE = 800;
    private static final int INITIALPAGEYSIZE = 450;

    public static ArrayList<Stage> pagesArray = new ArrayList<Stage>();

    public static void openNewWindow (Parent rootNode){
        
        Scene scene = new Scene(rootNode,INITIALPAGEXSIZE,INITIALPAGEYSIZE);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Librarian");
        pagesArray.add(stage);
        stage.show();
    }

    public static void changeScene(Parent rootNode){

        
    }

    public static void closeWindow (Stage stage, int index){
        stage.close();
    }
    
    }
