package library_final;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Library_final extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Charge le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/login/Login.fxml"));
            Parent root = loader.load();
            
            // Configure la scène
            Scene scene = new Scene(root);
            
            // Charge l'icône de l'application
            try (InputStream iconStream = getClass().getResourceAsStream("../image/open-book.png")) {
                if (iconStream != null) {
                    primaryStage.getIcons().add(new Image(iconStream));
                } else {
                    System.err.println("Warning: Application icon not found!");
                }
            }
            
            // Configure la fenêtre principale
            primaryStage.setTitle("Library Management System");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(Library_final.class.getName()).log(Level.SEVERE, "Failed to load FXML or images", ex);
            showErrorAlert("Critical Error", "Could not load application resources");
            System.exit(1);
        }
    }

    private void showErrorAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}