package pl.sda.arppl4.hibernaterental.dao;

import pl.sda.arppl4.hibernaterental.model.Car;

import java.util.List;
import java.util.Optional;

public class CarDao implements ICarDao{


    @Override
    public void addsCar(Car car) {

    }

    @Override
    public void removeCar(Car car) {

    }

    @Override
    public Optional<Car> listOneCar(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Car> listeAllCars() {
        return null;
    }

    @Override
    public void updateCar(Car car) {

    }
}
