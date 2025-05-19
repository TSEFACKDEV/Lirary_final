package library_final.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import library_final.model.DAO.CopyDAO;
import library_final.model.entity.Copy;
import library_final.model.DAO.BookDAO;
import library_final.model.entity.Book;

import java.util.List;

public class CopyController {

    // Pour AddCopy.fxml
    @FXML private ChoiceBox<Book> choiceBook; // fx:id="choiceBook"
    @FXML private TextField txtCopyCode;      // fx:id="txtCopyCode"
    @FXML private Button btnAddCopy;          // fx:id="btnAddCopy"
    @FXML private Label lblAddCopyStatus;     // fx:id="lblAddCopyStatus"

    // Pour Copy.fxml (affichage des copies)
    @FXML private TableView<Copy> tableCopies; // fx:id="tableCopies"
    @FXML private TableColumn<Copy, Integer> colCopyId; // fx:id="colCopyId"
    @FXML private TableColumn<Copy, String> colCopyCode; // fx:id="colCopyCode"
    @FXML private TableColumn<Copy, String> colCopyStatus; // fx:id="colCopyStatus"

    private final CopyDAO copyDAO = new CopyDAO();
    private final BookDAO bookDAO = new BookDAO();

    @FXML
    public void initialize() {
        // Initialisation pour AddCopy.fxml
        if (choiceBook != null) {
            List<Book> books = bookDAO.findAll();
            choiceBook.setItems(FXCollections.observableArrayList(books));
        }

        // Initialisation pour Copy.fxml
        if (tableCopies != null) {
            colCopyId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getIdCopy()).asObject());
            colCopyCode.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCode()));
            colCopyStatus.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getStatus()));
            refreshCopyTable();
        }
    }

    @FXML
    private void handleAddCopy(ActionEvent event) {
        Book selectedBook = choiceBook.getValue();
        String code = txtCopyCode.getText();

        if (selectedBook == null || code == null || code.isEmpty()) {
            lblAddCopyStatus.setText("Veuillez remplir tous les champs.");
            return;
        }

        Copy copy = new Copy(selectedBook.getIdBook(), code);
        boolean success = copyDAO.create(copy);

        if (success) {
            lblAddCopyStatus.setText("Exemplaire ajouté !");
            txtCopyCode.clear();
            refreshCopyTable();
        } else {
            lblAddCopyStatus.setText("Erreur lors de l'ajout.");
        }
    }

    private void refreshCopyTable() {
        if (tableCopies != null) {
            List<Copy> copies = copyDAO.findAll();
            ObservableList<Copy> observableCopies = FXCollections.observableArrayList(copies);
            tableCopies.setItems(observableCopies);
        }
    }

    // Pour EditBook.fxml : méthode d'exemple pour éditer une copie
    public void updateCopy(Copy copy, String newCode, String newStatus) {
        copy.setCode(newCode);
        copy.setStatus(newStatus);
        copyDAO.update(copy);
        refreshCopyTable();
    }

    
}
