package UI;

import Domain.Transaction;
import Service.TransactionService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;

public class TransactionController {
    public Spinner spnId;
    public Spinner spnIdCar;
    public Spinner spnIdClientCard;
    public TextField txtTransactionYear;
    public TextField txtTransactionDay;
    public TextField txtTransactionMonth;
    public TextField txtTransactionHour;
    public TextField txtTransactionMinutes;
    public TextField txtTransactionPieceTotal;
    public TextField txtTransactionWorkmanship;
    public TextField txtTransactionDiscount;
    public Button btnAdd;
    public Button btnUpdate;
    public Button btnCancel;
    private TransactionService transactionService;

    public void setService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void btnAddClick(ActionEvent actionEvent) {
        try {
            Transaction t = upsertClick();

            transactionService.addOrUpdate(t.getId(), t.getIdCar(), t.getIdClientCard(),t.getPieceTotal(), t.getDate(), t.getTime(),t.getWorkmanshipTotal(),t.getDiscount());
            btnCancelClick(actionEvent);
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }
    }

    public void btnUpdateClick(ActionEvent actionEvent) {
        try {
            Transaction  t = upsertClick();

            transactionService.addOrUpdate(t.getId(), t.getIdCar(), t.getIdClientCard(),t.getPieceTotal(), t.getDate(), t.getTime(),t.getWorkmanshipTotal(), t.getDiscount());
            btnCancelClick(actionEvent);
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }
    }

    public void btnCancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private Transaction upsertClick(){
        try {
            String id = String.valueOf(spnId.getValue());
            String idCar = String.valueOf(spnIdCar.getValue());
            String idClientCard = String.valueOf(spnIdClientCard.getValue());
            Double pieceTotal = Double.parseDouble(txtTransactionPieceTotal.getText());
            LocalDate date = LocalDate.of(Integer.parseInt(txtTransactionYear.getText()),
                    Integer.parseInt(txtTransactionMonth.getText()), Integer.parseInt(txtTransactionDay.getText()));
            LocalTime time = LocalTime.of(Integer.parseInt(txtTransactionHour.getText()),
                    Integer.parseInt(txtTransactionMinutes.getText()));
            Double workmanship = Double.parseDouble(txtTransactionWorkmanship.getText());
            Double discount = Double.parseDouble(txtTransactionDiscount.getText());

            return new Transaction(id, idCar, idClientCard,date, time,pieceTotal, workmanship, discount);
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }

        return null;

    }
}