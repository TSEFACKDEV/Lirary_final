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
import library_final.model.DAO.BookDAO;
import library_final.model.entity.Book;

import java.io.IOException;
import java.util.List;

public class BookController {

    @FXML
    private TableView<Book> booksTable;
    @FXML
    private TableColumn<Book, Integer> colId;
    @FXML
    private TableColumn<Book, String> colTitle;
    @FXML
    private TableColumn<Book, String> colAuthor;
    @FXML
    private TableColumn<Book, String> colIsbn;
    @FXML
    private TableColumn<Book, Integer> colYear;
    @FXML
    private TableColumn<Book, String> colPosition;
    @FXML
    private TableColumn<Book, String> colStatus;

    @FXML private TextField txtTitle;
    @FXML private TextField txtAuthor;
    @FXML private TextField txtIsbn;
    @FXML private TextField txtYear;
    @FXML private TextField txtPosition;
    @FXML private TextField txtStatus;

    private final BookDAO bookDAO = new BookDAO();
    private ObservableList<Book> bookList;
    private Book editingBook = null;

    @FXML
    public void initialize() {
        // Initialisation des colonnes
        colId.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getIdBook()).asObject());
        colTitle.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitle()));
        colAuthor.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAuthor()));
        colIsbn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getIsbn()));
        colYear.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getYearPublication()).asObject());
        colPosition.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPosition()));
        colStatus.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatus()));

        loadBooks();
    }

    private void loadBooks() {
        List<Book> books = bookDAO.findAll();
        bookList = FXCollections.observableArrayList(books);
        booksTable.setItems(bookList);
    }

    @FXML
    private void handleAddBook(ActionEvent event) {
        openBookForm(null, "Ajouter un livre");
    }

    @FXML
    private void handleEditBook(ActionEvent event) {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            showAlert("Sélection requise", "Veuillez sélectionner un livre à modifier.");
            return;
        }
        openBookForm(selectedBook, "Modifier un livre");
    }

    private void openBookForm(Book book, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                book == null ? "/library_final/view/books/AddBook.fxml" : "/library_final/view/books/EditBook.fxml"));
            loader.setController(this); // Utilise ce contrôleur
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            if (book != null) {
                setBookForm(book);
            } else {
                clearBookForm();
            }

            stage.showAndWait();
            loadBooks();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible d'ouvrir la fenêtre.");
        }
    }

    public void setBookForm(Book book) {
        this.editingBook = book;
        txtTitle.setText(book.getTitle());
        txtAuthor.setText(book.getAuthor());
        txtIsbn.setText(book.getIsbn());
        txtYear.setText(String.valueOf(book.getYearPublication()));
        txtPosition.setText(book.getPosition());
        txtStatus.setText(book.getStatus());
    }

    public void clearBookForm() {
        this.editingBook = null;
        txtTitle.clear();
        txtAuthor.clear();
        txtIsbn.clear();
        txtYear.clear();
        txtPosition.clear();
        txtStatus.clear();
    }

    @FXML
    private void handleSaveBook(ActionEvent event) {
        String title = txtTitle.getText();
        String author = txtAuthor.getText();
        String isbn = txtIsbn.getText();
        int year = Integer.parseInt(txtYear.getText());
        String position = txtPosition.getText();
        String status = txtStatus.getText();

        if (editingBook == null) {
            // Ajout
            Book newBook = new Book(0, title, author, isbn, year, position, status);
            bookDAO.save(newBook);
            showAlert("Succès", "Livre ajouté !");
        } else {
            // Edition
            editingBook.setTitle(title);
            editingBook.setAuthor(author);
            editingBook.setIsbn(isbn);
            editingBook.setYearPublication(year);
            editingBook.setPosition(position);
            editingBook.setStatus(status);
            bookDAO.update(editingBook);
            showAlert("Succès", "Livre modifié !");
        }
        // Fermer la fenêtre
        ((Stage) txtTitle.getScene().getWindow()).close();
    }

    @FXML
    private void handleDeleteBook(ActionEvent event) {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            showAlert("Sélection requise", "Veuillez sélectionner un livre à supprimer.");
            return;
        }
        boolean deleted = bookDAO.delete(selectedBook);
        if (deleted) {
            showAlert("Succès", "Livre supprimé.");
            loadBooks();
        } else {
            showAlert("Erreur", "La suppression a échoué.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
