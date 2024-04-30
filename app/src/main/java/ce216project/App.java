package ce216project;

import java.util.ArrayList;

import ce216project.controller.PageController;
import ce216project.models.Book;
import ce216project.models.Library;
import ce216project.view.MainPage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
   
        Library.loadBooksFromJson();
       
        MainPage mainPage = new MainPage();

        mainPage.fillBookTiles(Library.books);
           
        Scene scene = new Scene(mainPage,800,450);
        System.out.println("Filling");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Librarian");
        PageController.pagesArray.add(primaryStage);
        primaryStage.show();
    }

}
