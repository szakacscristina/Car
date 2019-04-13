package Service;

import Domain.Car;

public class CarByWorkmanshipVM {
    private Car car;
    private double workmanship;

    public CarByWorkmanshipVM(Car car, double workmanship) {
        this.car = car;
        this.workmanship = workmanship;

    }


    public Car getCar() {
        return car;
    }

    public double getWorkmanship() {
        return workmanship;
    }
}
