package UI;

import Domain.Client;
import Service.ClientService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Domain.Entity;

import java.time.LocalDate;

public class ClientController {
    public TextField txtClientFirstName;
    public TextField txtClientLastName;
    public TextField txtClientCNP;
    public TextField txtClientDateOfBirth;
    public TextField txtClientMonthB;
    public TextField txtClientYearB;
    public TextField txtDateOfRegistration;
    public TextField txtPaidPrice;
    public TextField txtClientDayR;
    public TextField txtClientMonthR;
    public TextField txtClientYearR;
    public TextField txtClientPaidPrice;
    public Button btnAdd;
    public Button btnUpdate;
    public Button btnCancel;
    public Spinner spnId;

    private ClientService clientService;

    public void setService(ClientService clientService) {
        this.clientService = clientService;
    }

    public void btnAddClick(ActionEvent actionEvent) {
        try {
            Client c = upsertClick();
            clientService.addOrUpdate(c.getId(),c.getFirstName(),c.getLastName(),c.getCNP(),c.getDateOfBirth(),c.getDateOfRegistration(),c.getPaidPrice());
            btnCancelClick(actionEvent);
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }
    }

    public void btnUpdateClick(ActionEvent actionEvent) {
        try {
            Client c = upsertClick();
            clientService.addOrUpdate(c.getId(),c.getFirstName(),c.getLastName(),c.getCNP(),c.getDateOfBirth(),c.getDateOfRegistration(),c.getPaidPrice());
            btnCancelClick(actionEvent);
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }
    }

    public void btnCancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private Client upsertClick(){
        try {
            String firstName = txtClientFirstName.getText();
            String lastName = txtClientLastName.getText();
            String id = String.valueOf(spnId.getValue());
            String CNP = txtClientCNP.getText();
            LocalDate dateOfBirth = LocalDate.of(Integer.parseInt(txtClientDateOfBirth.getText()),
                    Integer.parseInt(txtClientMonthB.getText()), Integer.parseInt(txtClientDateOfBirth.getText()));
            LocalDate registrationDate = LocalDate.of(Integer.parseInt(txtClientYearR.getText()),
                    Integer.parseInt(txtClientMonthR.getText()), Integer.parseInt(txtClientDayR.getText()));
            int bonusPoints = Integer.parseInt(txtClientPaidPrice.getText());
            String dateOfRegistration = txtDateOfRegistration.getText();
            String PaidPrice = txtPaidPrice.getText();

            return new Client(id, firstName, lastName , CNP, dateOfBirth, dateOfRegistration, PaidPrice);
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }

        return null;

    }

}
