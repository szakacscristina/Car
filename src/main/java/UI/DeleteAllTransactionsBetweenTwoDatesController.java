package UI;

import Domain.Transaction;
import Service.TransactionService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class DeleteAllTransactionsBetweenTwoDatesController {
    public TableView tableViewBookings;
    public TableColumn tableColumnIdTransaction;
    public TableColumn tableColumnIdCarTransaction;
    public TableColumn tableColumnIdClientTransaction;
    public TableColumn tableColumnDateOfTransaction;
    public TableColumn tableColumnTimeOfTransaction;
    public Button deleteButton;
    public Button cancelButton;
    private TransactionService transactionService;
    public TextField startDay;
    public TextField startMonth;
    public TextField startYear;
    public TextField endDay;
    public TextField endMonth;
    public TextField endYear;

    public void setService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            transactions.addAll(transactionService.getAll());
            tableViewBookings.setItems(transactions);
});
        }

    public void deleteTransactionsClick(ActionEvent actionEvent) {
        try {
            LocalDate begin = LocalDate.of(Integer.parseInt(startYear.getText()), Integer.parseInt(startMonth.getText()), Integer.parseInt(startDay.getText()));
            LocalDate end = LocalDate.of(Integer.parseInt(endYear.getText()), Integer.parseInt(endMonth.getText()), Integer.parseInt(endDay.getText()));
            transactionService.removeTransactionsByPeriod(begin, end);
            transactions.clear();
            transactions.addAll(transactionService.getAll());
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }
    }

    public void cancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void btnTransactionsDeleteUndoClick(ActionEvent actionEvent) {
        transactionService.undo();
        transactions.clear();
        transactions.addAll(transactionService.getAll());

    }

    public void btnTransactionsDeleteRedoClick(ActionEvent actionEvent) {
        transactionService.redo();
        transactions.clear();
        transactions.addAll(transactionService.getAll());
    }
}
