package UI;

import Domain.Car;
import Service.CarService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class CarController {
    public TextField txtCarModel;
    public TextField txtCarBoughtYear;
    public TextField txtCarKilometers;
    public TextField txtCarYearOfFabrication;
    public CheckBox chkInWarranty;
    public Button btnAdd;
    public Button btnUpdate;
    public Button btnCancel;
    public Spinner spnId;
    private CarService carService;

    public void setService(CarService movieService) {
        this.carService = movieService;
    }

    public void btnCancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void btnAddClick(ActionEvent actionEvent) {
        try {
            Car c = upsertClick();
            carService.addOrUpdate(c.getId(),c.getModel(),c.getBoughtYear(),c.getKilometers(), c.getYearOffabrication(),c.isInWarranty());
            btnCancelClick(actionEvent);
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }
    }

    public void btnUpdateClick(ActionEvent actionEvent) {
        try {
            Car c = upsertClick();
            carService.addOrUpdate(c.getId(),c.getModel(),c.getBoughtYear(),c.getKilometers(),c.getYearOffabrication(),c.isInWarranty());
            btnCancelClick(actionEvent);
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }
    }

    private Car upsertClick(){
        try {
            String id = String.valueOf(spnId.getValue());
            String model = txtCarModel.getText();
            String boughtYear = txtCarBoughtYear.getText();
            double kilometers = Double.parseDouble(txtCarKilometers.getText());
            int yearOfFabrication = (int) Integer.parseInt(String.valueOf(txtCarYearOfFabrication));
            boolean inWarranty = chkInWarranty.isSelected();

            return new Car(id, model, boughtYear, kilometers, yearOfFabrication, inWarranty);
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }

        return null;

    }
}
