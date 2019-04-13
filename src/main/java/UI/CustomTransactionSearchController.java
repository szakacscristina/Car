package UI;

import Domain.Transaction;
import Service.TransactionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomTransactionSearchController {
    public TableView tableViewTransactions;
    public TableColumn tableColumnIdTransaction;
    public TableColumn tableColumnIdCarTransaction;
    public TableColumn tableColumnIdClientTransaction;
    public TableColumn tableColumnDateOfTransaction;
    public TableColumn tableColumnTimeOfTransaction;

    public Button transactionSearch;
    public Button btnCancel;

    public TextField startHour;
    public TextField startMinutes;
    public TextField endHour;
    public TextField endMinutes;

    private TransactionService transactionService;

    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();

    public void setService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    public void btnSearchClick(ActionEvent actionEvent) {
        try {
            transactions.clear();
            LocalTime begin = LocalTime.of(Integer.parseInt(startHour.getText()), Integer.parseInt(startMinutes.getText()));
            LocalTime end = LocalTime.of(Integer.parseInt(endHour.getText()), Integer.parseInt(endMinutes.getText()));
            transactions.addAll(transactionService.transactionsByPeriod(begin, end));
            tableViewTransactions.setItems(transactions);
        } catch (RuntimeException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: Transaction update.", e);
            Common.showValidationError(e.getMessage());

        }
    }

    public void btnCancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
