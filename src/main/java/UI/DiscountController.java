package UI;

import Domain.Client;
import Service.ClientService;
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

public class DiscountController {
    public TableView tableViewCards;
    public TableColumn tableColumnIdClient;
    public TableColumn tableColumnFirstNameClient;
    public TableColumn tableColumnLastNameClient;
    public TableColumn tableColumnCnpClient;
    public TableColumn tableColumnDateOfBirthClient;
    public TableColumn tableColumnDateOfRegistrationClient;
    public TableColumn tableColumnDiscountClient;
    public TextField startDay;
    public TextField startMonth;
    public TextField endDay;
    public TextField endMonth;
    public TextField bonusPoints;
    public Button extraButton;
    public Button cancelButton;
    private ClientService clientService;

    public void setService(ClientService clientService) {
        this.clientService = clientService;
    }

    private ObservableList<Client> clients = FXCollections.observableArrayList();


    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            clients.addAll(clientService.getAll());
            tableViewCards.setItems(clients);
        });
    }

    public void extraPointsClick(ActionEvent actionEvent) {
        try {
            LocalDate begin = LocalDate.of(1990, Integer.parseInt(startMonth.getText()), Integer.parseInt(startDay.getText()));
            LocalDate end = LocalDate.of(1990, Integer.parseInt(endMonth.getText()), Integer.parseInt(endDay.getText()));
            int bonus = Integer.parseInt(bonusPoints.getText());
            clients.clear();
            clients.addAll(clientService.getAll());
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }
    }

    public void cancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void btnClientDiscountUndoClick(ActionEvent actionEvent) {
        clientService.undo();
        clients.clear();
        clients.addAll(clientService.getAll());
    }

    public void btnClientsDiscountRedoClick(ActionEvent actionEvent) {
        clientService.redo();
        clients.clear();
        clients.addAll(clientService.getAll());
    }
}