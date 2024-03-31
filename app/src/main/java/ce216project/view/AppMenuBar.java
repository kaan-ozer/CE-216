package ce216project.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;

public class AppMenuBar extends MenuBar {

    // Menu
    private Menu fileMenu = new Menu("File");
    private Menu helpMenu = new Menu("Help");

    // File Menu
    private MenuItem mNewBook = new MenuItem("New"); // Ctrl+N
    private MenuItem mImport = new MenuItem("Import"); // Ctrl+O
    private MenuItem mExport = new MenuItem("Export"); // Ctrl+E
    private SeparatorMenuItem mFileSeperator = new SeparatorMenuItem();
    private MenuItem mQuit = new MenuItem("Quit");
    
    public AppMenuBar(){
        
        mNewBook.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        mImport.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        mExport.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));

        fileMenu.getItems().addAll(mNewBook,mImport,mExport,mFileSeperator,mQuit);
        this.getMenus().addAll(fileMenu,helpMenu);
    }


    


    
}
