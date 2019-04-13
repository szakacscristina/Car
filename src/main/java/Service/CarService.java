package Service;

import Domain.Car;
import Repository.IRepository;

import java.util.*;

public class CarService {

    private IRepository<Car> repository;

    private Stack<UndoRedoOperation<Car>> undoableOperations = new Stack<>();
    private Stack<UndoRedoOperation<Car>> redoableeOperations = new Stack<>();

    public CarService(IRepository<Car> repository) {
        this.repository = repository;
    }

    public void addOrUpdate(String id, String model, String boughtYear, double kilometers, int yearOffabrication, boolean inWarranty) {
        Car existing = repository.findById(id);
        if (existing != null) {
            // keep unchanged fields as they were
            if (model.isEmpty()) {
                model = existing.getModel();
            }
            if (boughtYear.isEmpty()) {
                boughtYear = existing.getBoughtYear();
            }
            if (kilometers == 0) {
                kilometers = existing.getKilometers();
            }
            if (yearOffabrication == 0) {
                yearOffabrication = existing.getYearOffabrication();
            }
        }
        Car car = new Car(id, model, boughtYear, kilometers, yearOffabrication, inWarranty);
        repository.upsert(car);
    }

    public void remove(String id) {
        repository.remove(id);
    }

    public List<Car> getAll() {
        return repository.getAll();
    }

    public void searchFields(String[] terms) {
    }

    public List<Car> fullTextSearch(String text) {
        List<Car> found = new ArrayList<>();
        for (Car c : repository.getAll()) {
            if ((c.getId().contains(text) || c.getModel().contains(text) ||
                    String.format(c.getBoughtYear()).contains(text) || Double.toString(c.getKilometers()).contains(text) ||
                    Boolean.toString(c.isInWarranty()).contains(text)) && !found.contains(c)) {
                found.add(c);
            }
        }
        return found;
    }

    public void undo() {
        if (!undoableOperations.empty()) {
            UndoRedoOperation<Car> lastOperation = undoableOperations.pop();
            lastOperation.doUndo();
            redoableeOperations.add(lastOperation);

        }
    }

    public void redo() {
        if (!redoableeOperations.empty()) {
            UndoRedoOperation<Car> lastOperation = redoableeOperations.pop();
            lastOperation.doRedo();
            undoableOperations.add(lastOperation);
        }
    }

    public List<CarByWorkmanshipVM> sortByWorkmanship() {
        Map<String,Integer> frequences = new HashMap<>();
        for (Car b: repository.getAll()) {
            String movieId = b.getId();
            if(frequences.containsKey(movieId)){
                frequences.put(movieId,frequences.get(movieId)+1);
            } else {
                frequences.put(movieId,1);
            }
        }

        List<CarByWorkmanshipVM> orderedCars = new ArrayList<>();
        for (String carId:frequences.keySet()) {
            Car car = repository.findById(carId);
            orderedCars.add(new CarByWorkmanshipVM(car,frequences.get(carId)));

        }
        orderedCars.sort((m1,m2) -> Double.compare(m2.getWorkmanship (), m1.getWorkmanship()));
        return orderedCars;
    }

    public List<CarByWorkmanshipVM> sortByTransactions() {
        return  ;
    }
}

