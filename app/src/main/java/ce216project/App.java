package ce216project;

import java.util.ArrayList;
import ce216project.controller.PageController;
import ce216project.models.Book;
import ce216project.models.Library;
 

import ce216project.utils.IOoperations;

 
import ce216project.view.MainPage;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
 
    public void start(Stage primaryStage) throws Exception {

            

       

            MainPage mainPage = new MainPage();

            Library.loadBooksFromJson();

            Book book1 = new Book("TestTitle", "some subtitle", new ArrayList<>(), new ArrayList<>(), "12312312312", "testPublisher", "12.02.2001", 2, "STYLESHEET_MODENA", 3.5, new ArrayList<>(), null);
            
            
            // for(int i=0; i < 1 ; i++){
            //     Library.createBook(book1); 
            // } 
            
           mainPage.fillBookTiles(Library.books);
        
           
 
     

        Scene scene = new Scene(mainPage,800,450);
 
        primaryStage.setScene(scene);
        primaryStage.setTitle("Librarian");
        PageController.pagesArray.add(primaryStage);
        primaryStage.show();
    }

}
