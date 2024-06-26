package ce216project.view;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.swing.JOptionPane;

import ce216project.controller.PageController;
import ce216project.models.Library;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;

public class AppMenuBar extends MenuBar {

    private Menu fileMenu = new Menu("File");
    private Menu helpMenu = new Menu("Help");

    private MenuItem mNewBook = new MenuItem("New"); // Ctrl+N
    private MenuItem mImportAppend = new MenuItem("Import (Append)"); // Ctrl+O
    private MenuItem mImport = new MenuItem("Import (Overwrite)"); // Ctrl+O
    private MenuItem mExport = new MenuItem("Export"); // Ctrl+E
    private SeparatorMenuItem mFileSeparator = new SeparatorMenuItem();
    private MenuItem mQuit = new MenuItem("Quit");

    private MenuItem mUserGuide = new MenuItem("User Guide"); // Ctrl+H
    private MenuItem mAbout = new MenuItem("About"); // Ctrl+A
    private SeparatorMenuItem mHelpSeparator = new SeparatorMenuItem();
    // private MenuItem mCheckForUpdates = new MenuItem("Check for Updates"); //
    // Ctrl+U

    public AppMenuBar() {

        mNewBook.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        mImportAppend.setAccelerator(KeyCombination.keyCombination("Ctrl+P"));
        mImport.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        mExport.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));

        mUserGuide.setAccelerator(KeyCombination.keyCombination("Ctrl+H"));
        mAbout.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
        // mCheckForUpdates.setAccelerator(KeyCombination.keyCombination("Ctrl+U"));

        mNewBook.setOnAction(e -> add());
        mExport.setOnAction(e -> exportFile());
        mImportAppend.setOnAction(e -> appendFile());
        mImport.setOnAction(e -> importFile());
        mQuit.setOnAction(e -> quit());

        fileMenu.getItems().addAll(mNewBook, mImportAppend, mImport, mExport, mFileSeparator, mQuit);

        mUserGuide.setOnAction(e -> showUserGuide());
        mAbout.setOnAction(e -> showAboutInfo());
        // mCheckForUpdates.setOnAction(e -> checkForUpdates());

        helpMenu.getItems().addAll(mUserGuide, mHelpSeparator, mAbout);

        this.getMenus().addAll(fileMenu, helpMenu);
    }

    private void showUserGuide() {
        try {
            File userGuide = new File("shared/userGuide.pdf");
            if (userGuide.exists()) {
                Desktop.getDesktop().open(userGuide);
            } else {
                System.out.println("User guide not found!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAboutInfo() {
        JOptionPane.showMessageDialog(null, "YourApp v1.0\nDeveloped by My G.", "About YourApp",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void exportFile() {
        try {
            File sourceFile = new File("output/output.json");

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save JSON File");
            fileChooser.setInitialFileName("output.json");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));
            File destFile = fileChooser.showSaveDialog(null);

            if (destFile != null) {
                Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Exported");
                alert.setHeaderText(null);
                alert.setContentText("The JSON file successfully exported to: " + destFile.getAbsolutePath());
                alert.showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void importFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select JSON File to Import");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {

                File destFile = new File("output/" + "output.json");

                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

               

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Imported");
                alert.setHeaderText(null);
                alert.setContentText("The JSON file successfully imported and saved as: " + destFile.getAbsolutePath());
                alert.showAndWait();

                Library.loadBooksFromJson();
                MainPage mainPage = new MainPage();
                PageController.changeScene(mainPage, PageController.pagesArray.get(0));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void appendFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select JSON File to Import");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {

                File destFile = new File("output/output.json");

                byte[] selectedFileData = Files.readAllBytes(selectedFile.toPath());
                String selectedFileContent = new String(selectedFileData);

                if (selectedFileContent.startsWith("[")) {
                    selectedFileContent = selectedFileContent.trim().substring(1);
               
                 
                    if(selectedFileContent.trim().length() - 1 == 0) {
                        selectedFileContent = "";
                    } else {
                        selectedFileContent = selectedFileContent.trim().substring(0, selectedFileContent.trim().length() - 1);
                    }
                }
 

                byte[] destFileData = Files.readAllBytes(destFile.toPath());
                String destFileContent = new String(destFileData);
                String test = "";
                if (destFileContent.startsWith("[")) {
                  
                    destFileContent = destFileContent.trim().substring(1);

                    if(destFileContent.trim().length() - 1 == 0) {
                        destFileContent = "";
                    } else {
                        destFileContent = destFileContent.trim().substring(0, destFileContent.trim().length() - 1);
                    }
                    
                }

               

                FileWriter fileWriter = new FileWriter(destFile);

 

                String text =  "" ;
                text +=  "[" ;
                text +=  !Library.books.isEmpty() ? destFileContent + "," + selectedFileContent : selectedFileContent;
                text +=  "]" ;

                fileWriter.write(text);
                fileWriter.close();

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Appended");
                alert.setHeaderText(null);
                alert.setContentText("The JSON file successfully appended and saved as: " + destFile.getAbsolutePath());
                alert.showAndWait();

                Library.loadBooksFromJson();
                MainPage mainPage = new MainPage();
                PageController.changeScene(mainPage, PageController.pagesArray.get(0));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void add() {
        NewBookPage newBookPage = new NewBookPage();
        PageController.changeScene(newBookPage, PageController.pagesArray.get(0));
    }

    

    private void quit() {
        System.exit(0);
    }
}