package ce216project;

import java.util.ArrayList;

import ce216project.controller.PageController;
import ce216project.models.Book;
import ce216project.models.Library;
import ce216project.view.MainPage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{


    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {        
        
        Book book1 = new Book("TestTitle", "some subtitle", new ArrayList<String>(), new ArrayList<String>(), "12312312312", "testPublisher", "12.02.2001", 2, "STYLESHEET_CASPIAN", 3, new ArrayList<String>(), null);
       

        // for(int i=0; i < 200 ; i++){
        //     Library.books.add(book1);
        // }

        MainPage mainPage = new MainPage();
        
        Scene scene = new Scene(mainPage,800,450);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Librarian");
        PageController.pagesArray.add(primaryStage);
        primaryStage.show();
    }

    

   
}
