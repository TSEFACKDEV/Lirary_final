package library_final.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;

public class DashboardController {

    @FXML
    private void goToUsers(ActionEvent event) {
        loadView(event, "/library_final/view/librarian/User.fxml", "Manage Users");
    }

    @FXML
    private void goToCopies(ActionEvent event) {
        loadView(event, "/library_final/view/copy/Copy.fxml", "Manage Copies");
    }

    @FXML
    private void goToAddBook(ActionEvent event) {
        loadView(event, "/library_final/view/books/AddBook.fxml", "Add Book");
    }

    @FXML
    private void goToLoans(ActionEvent event) {
        loadView(event, "/library_final/view/loan/Loan.fxml", "Manage Loans");
    }

    private void loadView(ActionEvent event, String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Optionally, show an alert here
        }
    }
}
