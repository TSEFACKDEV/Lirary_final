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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import library_final.model.DAO.UserDAO;
import library_final.model.entity.User;

public class LoginController implements Initializable {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private UserDAO userDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialisation du DAO (ajustez selon votre gestion de connexion)
        userDAO = new UserDAO(library_final.config.DatabaseConnection.getConnection());
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de connexion",
                    "Veuillez entrer un nom d'utilisateur et un mot de passe");
            return;
        }

        User user = userDAO.login(email, password);

        if (user == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur de connexion",
                    "Email ou mot de passe incorrect.");
            return;
        }

        // Redirection selon le rôle (exemple)
        String dashboardPath = "/library_final/view/librarian/Dashboard.fxml";
        if ("Administrator".equalsIgnoreCase(user.getClass().getSimpleName())) {
            dashboardPath = "/library_final/view/admin/Dashboard.fxml";
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(dashboardPath));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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