package UI;

import Domain.Client;
import Service.ClientService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShowClientsByDiscountController {
    public TableView tableViewOrderedClients;
    public TableColumn tableColumnIdClient;
    public TableColumn tableColumnFirstNameClient;
    public TableColumn tableColumnLastNameClient;
    public TableColumn tableColumnCnpClient;
    public TableColumn tableColumnDateOfBirthClient;
    public TableColumn tableColumnDateOfRegistrationClient;
    public TableColumn tableColumnDiscountClient;
    private ClientService clientService;

    private ObservableList<Client> clients = FXCollections.observableArrayList();

    public void setService(ClientService clientService) {
        this.clientService = clientService;
    }

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            try {
                List<Client> clientsOrdered = clientService.sortedClientsByDiscount();

                clients.addAll(clientsOrdered);
                tableViewOrderedClients.setItems(clients);
            } catch (RuntimeException e) {
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE, "Failed to create new Window: Cars By Transactions.", e);
            }
        });
    }


}
