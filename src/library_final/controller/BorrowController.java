package library_final.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import library_final.model.DAO.LoanDAO;
import library_final.model.entity.Loan;

import java.io.IOException;
import java.util.Optional;

public class BorrowController {

    @FXML
    private TableView<Loan> loanTable;
    @FXML
    private TableColumn<Loan, Integer> idColumn;
    @FXML
    private TableColumn<Loan, String> borrowerColumn;
    @FXML
    private TableColumn<Loan, String> bookColumn;
    @FXML
    private TableColumn<Loan, String> dateColumn;

    private ObservableList<Loan> loanList;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        borrowerColumn.setCellValueFactory(cellData -> cellData.getValue().borrowerProperty());
        bookColumn.setCellValueFactory(cellData -> cellData.getValue().bookProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        loadLoans();
    }

    private void loadLoans() {
        loanList = FXCollections.observableArrayList(LoanDAO.getAllLoans());
        loanTable.setItems(loanList);
    }

    @FXML
    private void handleAddLoan(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/library_final/view/loan/AddLoan.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Ajouter un prêt");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.showAndWait();
        loadLoans();
    }

    @FXML
    private void handleEditLoan(ActionEvent event) throws IOException {
        Loan selectedLoan = loanTable.getSelectionModel().getSelectedItem();
        if (selectedLoan != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/library_final/view/loan/EditLoan.fxml"));
            Parent root = loader.load();

            EditLoanController controller = loader.getController();
            controller.setLoan(selectedLoan);

            Stage stage = new Stage();
            stage.setTitle("Modifier le prêt");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            loadLoans();
        } else {
            showAlert("Veuillez sélectionner un prêt à modifier.");
        }
    }

    @FXML
    private void handleDeleteLoan(ActionEvent event) {
        Loan selectedLoan = loanTable.getSelectionModel().getSelectedItem();
        if (selectedLoan != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Supprimer le prêt ?");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer ce prêt ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                LoanDAO.deleteLoan(selectedLoan.getId());
                loadLoans();
            }
        } else {
            showAlert("Veuillez sélectionner un prêt à supprimer.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Avertissement");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static class EditLoanController {

        public EditLoanController() {
        }
    }
}
