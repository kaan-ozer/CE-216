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

    // File Menu Items
    private MenuItem mNewBook = new MenuItem("New"); // Ctrl+N
    private MenuItem mImport = new MenuItem("Import"); // Ctrl+O
    private MenuItem mExport = new MenuItem("Export"); // Ctrl+E
    private SeparatorMenuItem mFileSeparator = new SeparatorMenuItem();
    private MenuItem mQuit = new MenuItem("Quit");

    // Help Menu Items
    private MenuItem mUserGuide = new MenuItem("User Guide"); // Ctrl+H
    private MenuItem mAbout = new MenuItem("About"); // Ctrl+A
    private SeparatorMenuItem mHelpSeparator = new SeparatorMenuItem();
    private MenuItem mCheckForUpdates = new MenuItem("Check for Updates"); // Ctrl+U

    public AppMenuBar() {

        mNewBook.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        mImport.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        mExport.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));

        mUserGuide.setAccelerator(KeyCombination.keyCombination("Ctrl+H"));
        mAbout.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
        mCheckForUpdates.setAccelerator(KeyCombination.keyCombination("Ctrl+U"));

        mQuit.setOnAction(e -> quit());

        fileMenu.getItems().addAll(mNewBook, mImport, mExport, mFileSeparator, mQuit);

        mUserGuide.setOnAction(e -> showUserGuide());
        mAbout.setOnAction(e -> showAboutInfo());
        mCheckForUpdates.setOnAction(e -> checkForUpdates());

        helpMenu.getItems().addAll(mUserGuide, mHelpSeparator, mCheckForUpdates, mAbout);

        this.getMenus().addAll(fileMenu, helpMenu);
    }

    private void showUserGuide() {
        // Logic to show user guide
    }

    private void showAboutInfo() {
        // Logic to display about information
    }

    private void checkForUpdates() {
        // Logic to check for software updates
    }

    private void quit() {
        System.exit(0);
    }
}