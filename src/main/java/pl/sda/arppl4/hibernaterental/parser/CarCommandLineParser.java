package pl.sda.arppl4.hibernaterental.parser;

import pl.sda.arppl4.hibernaterental.dao.CarDao;
import pl.sda.arppl4.hibernaterental.model.BodyType;
import pl.sda.arppl4.hibernaterental.model.Car;
import pl.sda.arppl4.hibernaterental.model.Transmission;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
                case "remove":
                    handleRemoveCommand();

            }


        }

        while (!command.equals("quit"));
    }

    private void handleRemoveCommand() {

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
                    // błąd product date jest po dzisiejszym dniu
                    throw new IllegalArgumentException("Date is after today");
                }

            } catch (IllegalArgumentException | DateTimeException iae) {
                productionDate = null; // żeby wyczyściło jak ktoś wpisał złą date
                System.err.println("Wrong date formatt, should be yyyy-MM-dd");
            }
        } while (productionDate == null);

        return productionDate;
    }

    private BodyType loadBodyTypeFromUser() {
        BodyType bodyType = null;
        do {
            try {
                System.out.println("Provide body type CABRIO, SUV or SEDANL:");
                String typeString = scanner.next();

                bodyType = BodyType.valueOf(typeString.toUpperCase());
            } catch (IllegalArgumentException iae) {
                System.err.println("wrong type, please provide CABRIO, SUV, SEDANL");
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


