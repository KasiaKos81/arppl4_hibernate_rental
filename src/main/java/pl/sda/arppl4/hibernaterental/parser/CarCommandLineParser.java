package pl.sda.arppl4.hibernaterental.parser;

import pl.sda.arppl4.hibernaterental.dao.CarDao;
import pl.sda.arppl4.hibernaterental.model.BodyType;
import pl.sda.arppl4.hibernaterental.model.Car;
import pl.sda.arppl4.hibernaterental.model.Transmission;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CarCommandLineParser {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Scanner scanner;
    private CarDao dao;

    public CarCommandLineParser(Scanner scanner, CarDao dao) {
        this.scanner = scanner;
        this.dao = dao;
    }

    public void ogarnij() {
        String command;

        do {
            System.out.println("Please, give command: add/remove/getOne/getAll/update or 'quit' to exit");
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
            }
        }

        while (!command.equals("quit"));
    }

    private void handleUpdateCommand() {
        System.out.println("Provide the id of the car you want to update");
        Long id = scanner.nextLong();

        Optional<Car> carOptional = dao.getOneCar(id);
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
                    car.getCapacity();
                    break;
                default:
                    System.out.println("Field with this name is not handled");
            }
            dao.updateCar(car);
            System.out.println("Car has been updated");
        } else {
            System.out.println("404 not found");
        }
    }

    private void handleRemoveCommand() {
        System.out.println("Provide the id of the car you want to remove");
        Long id = scanner.nextLong();

        Optional<Car> carOptional = dao.getOneCar(id);
        if(carOptional.isPresent()){
            Car car = carOptional.get();
            dao.removeCar(car);
            System.out.println("Car removed");
        } else {
            System.out.println("404 not found");
        }
    }

    private void handlegetAllCommand() {
        List<Car> carList = dao.getAllCars();
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

        Car car = new Car(null, name, brand, date, bodyType, seats, transmission, capacity);
        dao.addCar(car);

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
                    // b????d product date jest po dzisiejszym dniu
                    throw new IllegalArgumentException("Date is after today");
                }

            } catch (DateTimeException iae) {
                productionDate = null; // ??eby wyczy??ci??o jak kto?? wpisa?? z???? date
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
                System.out.println("Provide body type CABRIO, SUV or SEDAN:");
                String typeString = scanner.next();

                bodyType = BodyType.valueOf(typeString.toUpperCase());
            } catch (IllegalArgumentException iae) {
                System.err.println("wrong type, please provide CABRIO, SUV, SEDAN");
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


