package pl.sda.arppl4.hibernaterental.dao;

import pl.sda.arppl4.hibernaterental.model.Car;

import java.util.List;
import java.util.Optional;

public interface ICarDao {

    public void addCar(Car car);
    // Delete
    public void removeCar(Car car);
    // Read
    public Optional<Car> getOneCar(Long id);
    // Read
    public List<Car> getAllCars();
    // Update
    public void updateCar(Car car);

}
