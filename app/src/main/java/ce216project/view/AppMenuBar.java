package ce216project.view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;

public class AppMenuBar extends MenuBar {

    private Menu fileMenu = new Menu("File");
    private Menu helpMenu = new Menu("Help");

    private MenuItem mNewBook = new MenuItem("New"); // Ctrl+N
    private MenuItem mImport = new MenuItem("Import"); // Ctrl+O
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
        mImport.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        mExport.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));

        mUserGuide.setAccelerator(KeyCombination.keyCombination("Ctrl+H"));
        mAbout.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
        // mCheckForUpdates.setAccelerator(KeyCombination.keyCombination("Ctrl+U"));

        mQuit.setOnAction(e -> quit());

        fileMenu.getItems().addAll(mNewBook, mImport, mExport, mFileSeparator, mQuit);

        mUserGuide.setOnAction(e -> showUserGuide());
        mAbout.setOnAction(e -> showAboutInfo());
        // mCheckForUpdates.setOnAction(e -> checkForUpdates());

        helpMenu.getItems().addAll(mUserGuide, mHelpSeparator, mAbout);

        this.getMenus().addAll(fileMenu, helpMenu);
    }

    private void showUserGuide() {
        try {
            File userGuide = new File("src/main/resources/userGuide.pdf");
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

    /*
     * private void checkForUpdates() {
     * String updateUrl = "http://yourappserver.com/api/check-updates";
     * try {
     * 
     * @SuppressWarnings("deprecation")
     * URL url = new URL(updateUrl);
     * HttpURLConnection conn = (HttpURLConnection) url.openConnection();
     * conn.setRequestMethod("GET");
     * int responseCode = conn.getResponseCode();
     * if (responseCode == HttpURLConnection.HTTP_OK) {
     * BufferedReader in = new BufferedReader(new
     * InputStreamReader(conn.getInputStream()));
     * String inputLine;
     * StringBuilder response = new StringBuilder();
     * while ((inputLine = in.readLine()) != null) {
     * response.append(inputLine);
     * }
     * in.close();
     * 
     * String currentVersion = "1.0";
     * String latestVersion = response.toString();
     * if (!currentVersion.equals(latestVersion)) {
     * 
     * JOptionPane.showMessageDialog(null, "A new version is available!",
     * "Update Available",
     * JOptionPane.INFORMATION_MESSAGE);
     * } else {
     * JOptionPane.showMessageDialog(null, "You have the latest version.",
     * "No Updates",
     * JOptionPane.INFORMATION_MESSAGE);
     * }
     * } else {
     * System.out.println("No response from update server.");
     * }
     * } catch (IOException e) {
     * e.printStackTrace();
     * }
     * }
     */

    private void quit() {
        System.exit(0);
    }
}