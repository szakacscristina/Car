package UI;

import Domain.Transaction;
import Domain.Client;
import Domain.Car;
import Service.TransactionService;
import Service.ClientService;
import Service.CarService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {
    public TableView tableViewCars;
    public TableColumn tableColumnIdCar;
    public TableColumn tableColumnModelCar;
    public TableColumn tableColumnBoughtYearCar;
    public TableColumn tableColumnKilometersCar;
    public TableColumn tableColumnYearOfFabricationCar;
    public TableColumn tableColumnInWarranty;

    public TableView tableViewClients;
    public TableColumn tableColumnIdClient;
    public TableColumn tableColumnFirstNameClient;
    public TableColumn tableColumnLastNameClient;
    public TableColumn tableColumnCnpClient;
    public TableColumn tableColumnDateOfBirthClient;
    public TableColumn tableColumnDateOfRegistrationClient;
    public TableColumn tableColumnDiscountClient;

    public TableView tableViewTransactions;
    public TableColumn tableColumnIdTransaction;
    public TableColumn tableColumnIdCarTransaction;
    public TableColumn tableColumnIdClientTransaction;
    public TableColumn tableColumnDateOfTransaction;
    public TableColumn tableColumnTimeOfTransaction;

    private CarService carService;
    private ClientService clientService;
    private TransactionService transactionService;

    private ObservableList<Car> movies = FXCollections.observableArrayList();
    private ObservableList<Client> cards = FXCollections.observableArrayList();
    private ObservableList<Transaction> bookings = FXCollections.observableArrayList();


    public void setServices(CarService carService, ClientService clientService, TransactionService transactionService) {
        this.carService = carService;
        this.clientService = clientService;
        this.transactionService = transactionService;
    }

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            movies.addAll(carService.getAll());
            tableViewCars.setItems(cars);
            cards.addAll(clientService.getAll());
            tableViewClients.setItems(clients);
            bookings.addAll(transactionService.getAll());
            tableViewTransactions.setItems(transactions);
        });
    }

    public void btnAddCarsClick(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/carAdd.fxml"));
        upsertCar(fxmlLoader, "Car add");
    }


    public void btnCarDeleteClick(ActionEvent actionEvent) {
        Car selected = (Car) tableViewCars.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                carService.remove(selected.getId());
                movies.clear();
                movies.addAll(carService.getAll());
            } catch (RuntimeException rex) {
                Common.showValidationError(rex.getMessage());
            }
        }
    }

    public void btnUpdateCarClick(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/carUpdate.fxml"));
        upsertCar(fxmlLoader, "Movie update");
    }

    public void upsertCar(FXMLLoader fxmlLoader, String title) {
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 200);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            CarController controller = fxmlLoader.getController();
            controller.setService(carService);
            stage.showAndWait();
            cars.clear();
            cars.addAll(carService.getAll());
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: Car update.", e);
        }
    }


    public void btnAddClientClick(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/cardAdd.fxml"));
        upsertClient(fxmlLoader, "Card add");
    }


    public void btnUpdateClientClick(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/cardUpdate.fxml"));
        upsertClient(fxmlLoader, "Card update");
    }


    public void upsertClient(FXMLLoader fxmlLoader, String title) {
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 300);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            ClientController controller = fxmlLoader.getController();
            controller.setService(clientService);
            stage.showAndWait();
            clients.clear();
            clients.addAll(clientService.getAll());
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: Card ", e);
        }
    }

    public void btnDeleteClientClick(ActionEvent actionEvent) {
        Client selected = (Client) tableViewClients.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                clientService.remove(selected.getId());
                clients.clear();
                clients.addAll(clientService.getAll());
            } catch (RuntimeException rex) {
                Common.showValidationError(rex.getMessage());
            }
        }
    }


    public void btnAddTransactionClick(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/transactionAdd.fxml"));
        upsertTransaction(fxmlLoader, "Transaction add");
    }


    public void btnUpdateTransactionClick(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/transactionUpdate.fxml"));
        upsertTransaction(fxmlLoader, "Transaction update");
    }


    public void upsertTransaction(FXMLLoader fxmlLoader, String title) {
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 300);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            TransactionController controller = fxmlLoader.getController();
            controller.setService(transactionService);
            stage.showAndWait();
            transactions.clear();
            transactions.addAll(transactionService.getAll());

            clients.clear();
            clients.addAll(clientService.getAll());
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: Transaction ", e);
        }
    }

    public void btnDeleteTransactionClick(ActionEvent actionEvent) {
        Transaction selected = (Transaction) tableViewTransactions.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                transactionService.remove(selected.getId());
                transactions.clear();
                transactios.addAll(transactionService.getAll());

                clients.clear();
                clients.addAll(clientService.getAll());
            } catch (RuntimeException rex) {
                Common.showValidationError(rex.getMessage());
            }
        }
    }

    public void searchClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/searchResults.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
            Stage stage = new Stage();
            stage.setTitle("Full Text Search");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            SearchResultsController controller = fxmlLoader.getController();
            controller.setService(carService, clientService, transactionService);
            stage.showAndWait();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: Full Text Search add.", e);
        }
    }

    public void customTransactionSearch(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/customTransactionSearch.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 600, 600);
            Stage stage = new Stage();
            stage.setTitle("Custom Booking Search");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            CustomTransactionSearchController controller = fxmlLoader.getController();
            controller.setService(transactionService);
            stage.showAndWait();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: Custom Transaction Search add.", e);
        }
    }

    public void carsByTransactions(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/showCarsOrderedByTransactions.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 600, 600);
            Stage stage = new Stage();
            stage.setTitle("Cars ordered by transactions");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            ShowCarsOrderedByWorkmanshipController controller = fxmlLoader.getController();
            controller.setService(carService);
            stage.showAndWait();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: Movies ordered by transactions add.", e);
        }
    }

    public void clientsByDiscount(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/showClientsByDiscount.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
            Stage stage = new Stage();
            stage.setTitle("Clients ordered by discounts");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            ShowClientsByDiscountController controller = fxmlLoader.getController();
            controller.setService(clientService);
            stage.showAndWait();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: Clients ordered by discount add.", e);
        }
    }

    public void deleteTransactions(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/deleteAllTransactionsBetweenTwoDates.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
            Stage stage = new Stage();
            stage.setTitle("Deleting transactions between two dates");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            DeleteAllTransactionsBetweenTwoDatesController controller = fxmlLoader.getController();
            controller.setService(transactionService);
            stage.showAndWait();
            bookings.clear();
            bookings.addAll(transactionService.getAll());
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: Delete transactions add.", e);
        }
    }

    public void Discount(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/Discount.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            Stage stage = new Stage();
            stage.setTitle("Lucky bonus");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            DiscountController controller = fxmlLoader.getController();
            controller.setService(clientService);
            stage.showAndWait();
            //
            clients.clear();
            clients.addAll(clientService.getAll());
            //
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: Extra bonus points add.", e);
        }
    }

    public void btnCarUndoClick(ActionEvent actionEvent) {
        carService.undo();
        cars.clear();
        cars.addAll(carService.getAll());
    }

    public void btnCarRedoClick(ActionEvent actionEvent) {
        carService.redo();
        cars.clear();
        cars.addAll(carService.getAll());
    }

    public void btnCardUndoClick(ActionEvent actionEvent) {
        clientService.undo();
        clients.clear();
        clients.addAll(clientService.getAll());
    }

    public void btnClientRedoClick(ActionEvent actionEvent) {
        clientService.redo();
        clients.clear();
        clients.addAll(clientService.getAll());
    }

    public void btnTransactionUndoClick(ActionEvent actionEvent) {
        transactionService.undo();
        transactions.clear();
        transactions.addAll(transactionService.getAll());
    }

    public void btnTransactionredoClick(ActionEvent actionEvent) {
        transactionService.redo();
        transactions.clear();
        transactions.addAll(transactionService.getAll());
    }
}