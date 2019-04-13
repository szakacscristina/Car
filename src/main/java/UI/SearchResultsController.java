package UI;

import Domain.Transaction;
import Domain.Client;
import Domain.Car;
import Service.TransactionService;
import Service.ClientService;
import Service.CarService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SearchResultsController {
    public Label result;
    public TextField textToSearch;
    public Button btnSearch;
    public Button btnCancel;
    private CarService carService;
    private ClientService clientService;
    private TransactionService transactionService;

    public void setService(CarService carService, ClientService clientService, TransactionService transactionService) {
        this.carService = carService;
        this.clientService = clientService;
        this.transactionService = transactionService;
    }

    public void btnCancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void btnSearchClick(ActionEvent actionEvent) {
        String txt = textToSearch.getText();
        String found = txt + " found here:\n" + movieSearch(txt) + "\n" + cardSearch(txt) + "\n" + transactionSearch(txt);
        if(txt.length()>=1)
            result.setText(found);
    }

    private String transactionSearch(String text) {
        String transactionsTextFound = "";
        for (Transaction t : transactionService.fullTextSearch(text)) {
            transactionsTextFound += t + "\n";
        }
        return transactionsTextFound;
    }

    private String clientSearch(String text) {
        String clientsTextFound = "";
        for (Client c : clientService.fullTextSearch(text)) {
            clientsTextFound += c + "\n";
        }
        return clientsTextFound;
    }

    private String carSearch(String text) {
        String carsTextFound = "";
        for (Car m : carService.fullTextSearch(text)) {
            carsTextFound += m + "\n";
        }
        return carsTextFound;
    }
}