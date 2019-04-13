package UI;

import Service.CarByWorkmanshipVM;
import Service.CarService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShowCarsOrderedByWorkmanshipController {
    public TableColumn tableColumnTransactionsNumber;
    public TableView tableViewCars;
    public TableColumn tableColumnCarModel;
    private CarService carService;

    private ObservableList<CarByWorkmanshipVM> cars = FXCollections.observableArrayList();

    public void setService(CarService movieService) {
        this.carService = movieService;
    }

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            try {
                List<CarByWorkmanshipVM> orderedCars = carService.sortByTransactions();
                cars.addAll(orderedCars);
                tableViewCars.setItems(cars);
            } catch (RuntimeException e) {
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE, "Failed to create new Window: Cars By Bookings.", e);
            }
        });
    }
}
