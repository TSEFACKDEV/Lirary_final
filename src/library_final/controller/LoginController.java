package library_final.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = emailField.getText();
        String password = passwordField.getText();

        // Validation basique
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de connexion", 
                     "Veuillez entrer un nom d'utilisateur et un mot de passe");
            return;
        }

        // Ici vous devriez ajouter votre logique d'authentification
        System.out.println("Tentative de connexion avec: " + username);

        try {
            // Chargement de la nouvelle vue
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/library_final/view/librarian/Dashboard.fxml"));
            Parent root = loader.load();
            
            // Création de la nouvelle scène
            Scene scene = new Scene(root);
            
            // Récupération de la fenêtre actuelle
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // Configuration de la nouvelle scène
            stage.setScene(scene);
            stage.setTitle("Dashboard");
            stage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", 
                      "Impossible de charger la page Dashboard: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}