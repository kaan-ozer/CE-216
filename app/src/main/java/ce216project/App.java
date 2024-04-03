package ce216project;

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
        MainPage mainPage = new MainPage();
        Scene scene = new Scene(mainPage,800,450);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Librarian");
        primaryStage.show();
    }

    

   
}
