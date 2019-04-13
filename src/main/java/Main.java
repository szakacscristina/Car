import Domain.*;
import Repository.IRepository;
import Repository.InMemoryRepository;
import Service.CarService;
import Service.ClientService;
import Service.TransactionService;
import UI.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Entity c;

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UI/MainWindow.fxml"));
        Parent root = fxmlLoader.load();

        MainController controller =  fxmlLoader.getController();

        IValidator<Car> carValidator = new CarValidator();
        IValidator<Client> clientValidator = new ClientValidator();
        IValidator<Transaction> transactionValidator = new TransactionValidator();

        IRepository<Car> carRepository = new InMemoryRepository<>(carValidator);
        IRepository<Client> clientRepository = new InMemoryRepository<>(clientValidator);
        IRepository<Transaction> transactionRepository = new InMemoryRepository<>(transactionValidator);

        CarService carService = new CarService(carRepository);
        carService.addOrUpdate("1", "bmw", "1234", 200, 1007, true);
        carService.addOrUpdate("2", "mercedes", "6645", 250, 3008, false);
        carService.addOrUpdate("3", "logan", "6534", 150, 2008, false);

        ClientService clientService = new ClientService(clientRepository);
        clientService.addOrUpdate(c.getId(), "1","ana","sara","1234567890112","12.45.6789","34.67.1234");
        clientService.addOrUpdate(c.getId(), "2","bfg","iudygg","6543872456123","45.78.3456","78.67.2345");
        clientService.addOrUpdate(c.getId(), "3","suff","ugeyduu","7645398623451","56.78.1234","56.78.2345");

        TransactionService transactionService = new TransactionService(transactionRepository, carRepository);
        transactionService.addOrUpdate("1","1","1",765,"12.45.7899","13", 3646, 10);
        transactionService.addOrUpdate("2","2","2",678,"23.67.4567","14", 56, 10);
        transactionService.addOrUpdate("3","3","3",876,"23.67.2345","15", 578, 10);
        controller.setServices(carService, clientService, transactionService);

        primaryStage.setTitle("Car manager");
        primaryStage.setScene(new Scene(root, 650, 500));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
