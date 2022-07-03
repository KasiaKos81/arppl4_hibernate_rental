package pl.sda.arppl4.hibernaterental.parser;

import pl.sda.arppl4.hibernaterental.dao.GenericDao;
import pl.sda.arppl4.hibernaterental.model.BodyType;
import pl.sda.arppl4.hibernaterental.model.Car;
import pl.sda.arppl4.hibernaterental.model.CarRental;
import pl.sda.arppl4.hibernaterental.model.Transmission;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CarCommandLineParser {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final Scanner scanner;
    private final GenericDao <Car> daoCar;
    private final GenericDao<CarRental> daoCarRental;

    public CarCommandLineParser(Scanner scanner, GenericDao<Car> daoCar, GenericDao<CarRental> daoCarRental) {
        this.scanner = scanner;
        this.daoCar = daoCar;
        this.daoCarRental = daoCarRental;
    }

    public void ogarnij() {
        String command;

        do {
            System.out.println("Please, give command: add/addRental/remove/returnCar/getOne/getAll/update or 'quit' to exit");
            command = scanner.next();

            switch (command) {
                case "add":
                    handleAddCommand();
                    break;
                case "getAll":
                    handlegetAllCommand();
                    break;
                case "remove":
                    handleRemoveCommand();
                    break;
                case "update":
                    handleUpdateCommand();
                    break;
                case "addRental":
                    handleAddRentalCommand();
                    break;
                case "returnCar":
                    handleReturnCommand();
                    break;
                case "check":
                    handleCheckCommand();
                    break;
            }
        }

        while (!command.equals("quit"));
    }

    private void handleCheckCommand() {
        System.out.println("Provide id of the car");
        Long id = scanner.nextLong();

        Optional<Car> carOptional = daoCar.getOneCar(id, Car.class);
        if (carOptional.isPresent()) {
            Car car = carOptional.get();

            if(checkIfTheCarIsAvailable(car)){
                System.out.println("Car is available");
            } else {
                System.out.println("Car is not available");
            }

            } else {
                System.out.println("404 car not found");
            }
        }

        private boolean checkIfTheCarIsAvailable(Car car){
        Optional<CarRental> optionalCarRental = findActiveRental(car);
        return !optionalCarRental.isPresent();
        }



    private void handleReturnCommand() {
        System.out.println("Provide id of the car");
        Long id = scanner.nextLong();

        Optional<Car> carOptional = daoCar.getOneCar(id, Car.class);
        if (carOptional.isPresent()) {
            Car car = carOptional.get();

            Optional<CarRental> optionalCarRental = findActiveRental(car);
            if (optionalCarRental.isPresent()) {
                CarRental carRental = optionalCarRental.get();

                carRental.setReturnDateTime(LocalDateTime.now());

                daoCarRental.updateCar(carRental);

            } else {
                System.out.println("404 car not found");
            }
        } else {
            System.out.println("404 not found");
        }
    }

    private Optional <CarRental> findActiveRental(Car car) {
        if (car.getCarRentals().isEmpty()) {
            return Optional.empty();
        }
        for (CarRental carRental : car.getCarRentals()) {
            if (carRental.getReturnDateTime() == null) {
                return Optional.of(carRental);
            }

        }
        return Optional.empty();

    }

    private void handleUpdateCommand() {
        System.out.println("Provide the id of the car you want to update");
        Long id = scanner.nextLong();

        Optional<Car> carOptional = daoCar.getOneCar(id, Car.class);
        if (carOptional.isPresent()) {
            Car car = carOptional.get();

            System.out.println("tell what you want to change: name, brand, date, bodyType, transmission, seats, capacity");
            String change = scanner.next();

            switch (change){
                case "name":
                    System.out.println("provide name");
                    String name = scanner.next();
                    car.setName(name);
                    break;
                case "brand":
                    System.out.println("provide brand");
                    String brand = scanner.next();
                    car.setBrand(brand);
                    break;
                case "date":
                    LocalDate date = loadProductionDateFromUser();
                   car.setDate(date);
                    break;
                case "bodyType":
                    BodyType bodyType = loadBodyTypeFromUser();
                    car.setBodyType(bodyType);
                    break;
                case "transmission":
                    Transmission transmission = loadTransmissionTypeFromUser();
                    car.setTransmission(transmission);
                    break;
                case "seats":
                    System.out.println("Provide number of seats");
                    Double seats = scanner.nextDouble();
                    car.setSeats(seats);
                    break;
                case "capacity":
                    System.out.println("set capacity");
                    Double capacity = scanner.nextDouble();
                    car.setCapacity(capacity);
                    break;

                default:
                    System.out.println("Field with this name is not handled");
            }
            daoCar.updateCar(car);
            System.out.println("Car has been updated");
        } else {
            System.out.println("404 not found");
        }
    }

    private void handleAddRentalCommand() {
        System.out.println("Provide the id of the car");
        Long id = scanner.nextLong();

        Optional<Car> carOptional = daoCar.getOneCar(id, Car.class);
        if(carOptional.isPresent()){
            Car car = carOptional.get();

            if(checkIfTheCarIsAvailable(car)) {

                System.out.println("Give name");
                String name = scanner.next();

                System.out.println("Give Last Name");
                String lastName = scanner.next();

                LocalDateTime dateTimeStart = LocalDateTime.now();
                System.out.println("date and time of rental start: " + dateTimeStart);

                CarRental carRental = new CarRental(name, lastName, dateTimeStart, car);
                daoCarRental.addCar(carRental);
            } else {
                System.out.println("404 not found - car is rented");
            }

        } else {
            System.out.println("404 not found - no car with such id");
        }
    }



    private void handleRemoveCommand() {
        System.out.println("Provide the id of the car you want to remove");
        Long id = scanner.nextLong();

        Optional<Car> carOptional = daoCar.getOneCar(id, Car.class);
        if(carOptional.isPresent()){
            Car car = carOptional.get();
            daoCar.removeCar(car);
            System.out.println("Car removed");
        } else {
            System.out.println("404 not found");
        }
    }

    private void handlegetAllCommand() {
        List<Car> carList = daoCar.getAllCars(Car.class);
        for (Car car : carList) {
            System.out.println(car);

        }
        System.out.println();
    }


    private void handleAddCommand() {
        System.out.println("Provide car name:");
        String name = scanner.next();

        System.out.println("Provide car brand:");
        String brand = scanner.next();

        LocalDate date = loadProductionDateFromUser();

        BodyType bodyType = loadBodyTypeFromUser();

        System.out.println("Provide number of seats");
        Double seats = scanner.nextDouble();

        Transmission transmission = loadTransmissionTypeFromUser();

        System.out.println("Provide capacity");
        Double capacity = scanner.nextDouble();

        Car car = new Car(name, brand, date, bodyType, seats, transmission, capacity);
        daoCar.addCar(car);

    }

    private LocalDate loadProductionDateFromUser() {
        LocalDate productionDate = null;
        do {
            try {
                System.out.println("Provide production date:");
                String productionDateString = scanner.next();

                productionDate = LocalDate.parse(productionDateString, FORMATTER);

                LocalDate today = LocalDate.now();
                if(productionDate.isAfter(today)){
                    // błąd product date jest po dzisiejszym dniu
                    throw new IllegalArgumentException("Date is after today");
                }

            } catch (DateTimeException iae) {
                productionDate = null; // żeby wyczyściło jak ktoś wpisał złą date
                System.err.println("Wrong date formatt, should be yyyy-MM-dd");
            } catch (IllegalArgumentException iae){
                productionDate = null;
                System.err.println("Date is after today");
            }
        } while (productionDate == null);

        return productionDate;
    }

    private BodyType loadBodyTypeFromUser() {
        BodyType bodyType = null;
        do {
            try {
                System.out.println("Provide body type CABRIO, HATCHBACK, SUV or SEDAN:");
                String typeString = scanner.next();

                bodyType = BodyType.valueOf(typeString.toUpperCase());
            } catch (IllegalArgumentException iae) {
                System.err.println("wrong type, please provide CABRIO, HATCHBACK, SUV, SEDAN");
            }
        } while (bodyType == null);
        return bodyType;
}

    private Transmission loadTransmissionTypeFromUser() {
        Transmission transmission = null;
        do {
            try {
                System.out.println("Provide transmission type: AUTO or MANUAL");
                String transmissionString = scanner.next();

                transmission = Transmission.valueOf((transmissionString.toUpperCase()));
            } catch (IllegalArgumentException iae) {
                System.err.println("wrong type, please provide AUTO or MANUAL");
            }
        } while (transmission == null);
        return transmission;
    }


}


